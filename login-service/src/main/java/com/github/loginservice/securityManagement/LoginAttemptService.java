package com.github.loginservice.securityManagement;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.github.loginservice.dao.SettingAdminRepository;
import com.github.loginservice.models.SettingAdmin;
import com.github.loginservice.models.SettingAttempt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Service
public class LoginAttemptService {

    private int MAX_ATTEMPT;
    private LoadingCache<String, Integer> attemptsCache;
    private long time;
	private String message;

	SettingAttempt settingAttempt;
	
	@Autowired
	SettingAdminRepository settingAdminRepository;

	@PostConstruct
	public void initSettingAdminRepository(){
	 try {
		if(settingAdminRepository.findAll().isEmpty()){
			SettingAdmin settingAdmin = new SettingAdmin();
			settingAdmin.setNumberAttempt(5);
			settingAdmin.setNumberAttemptDefault(5);
			settingAdmin.setTimes(15);
			settingAdmin.setTimesDefault(15);

			settingAdmin.setDefaultDateExpiryPasswordYears(0);
			settingAdmin.setDefaultDateExpiryPasswordDay(0);
			settingAdmin.setDefaultDateExpiryPasswordMonth(6);

			settingAdmin.setDatePasswordExpirationYears(0);
			settingAdmin.setDefaultDateExpiryPasswordDay(0);
			settingAdmin.setDatePasswordExpirationMonth(6);

			settingAdminRepository.save(settingAdmin);
		}	
		SettingAdmin settingAdmin = settingAdminRepository.findAll().get(0);
		MAX_ATTEMPT = settingAdmin.getNumberAttempt();
		time = settingAdmin.getTimes();
		message = time + " minute";
		
		attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(time, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(final String key) {
                return 0;
            }
        });
	} catch (DataAccessException dae) {
		 System.out.println("problem recovering configuration password and connection attempt parameter exceeded");
    }
	}

    public LoginAttemptService() {

    }

    public void loginSucceeded(final String key) {
        attemptsCache.invalidate(key);
    }

    public void loginFailed(final String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (final ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(final String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (final ExecutionException e) {
            return false;
        }
    }
    
    public void unlock(final String key){
    	//attemptsCache.put(key, 0);
    	attemptsCache.invalidate(key);
    
    }
    
    public SettingAttempt changeSetting(int time, int nbrAttempt){
    	int hours =0;
    	int minute =0;
    	Set<String> listIpBlocked;
    	
    	SettingAdmin settingAdmin = settingAdminRepository.findAll().get(0);
		settingAdmin.setNumberAttempt(nbrAttempt);
		settingAdmin.setTimes((time+3600000)/(1000*60));

		modifyAttemptSettingBdd(settingAdmin);
		
        time= (time+3600000)/(1000*60);
    	MAX_ATTEMPT = nbrAttempt;
    	message = time + " minute";
    	
    	hours = (time+3600000)/(1000*3600);
    	minute = (((time+3600000)/1000)%3600)/60;
    	
    	if(hours == 0)
    		message =  minute + " minute";
    	else
    		message = hours + " hour " + minute + " minute";
    	
    	listIpBlocked = attemptsCache.asMap().keySet();
    	attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(time, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
            @Override
            public Integer load(final String key) {
                return 0;
            }         	
        });
        for(String key : listIpBlocked)
        	loginFailed(key);
        
        return getAttemptSetting();

    }

	public String getMessage() {
		return message;
	}
	
	public SettingAttempt getAttemptSetting(){
		settingAttempt = new SettingAttempt();
		try {
		int getTime =  ((int)settingAdminRepository.findAll().get(0).getTimes()*1000*60-3600000);
			settingAttempt.setNbrAttempt(MAX_ATTEMPT);
			settingAttempt.setHours((getTime+3600000)/(1000*3600));
			settingAttempt.setMinutes((((getTime+3600000)/1000)%3600)/60);
			settingAttempt.setListIp(attemptsCache.asMap().keySet());
		} catch (DataAccessException dae) {
			System.out.println("problem retrieving configuration connection attempt exceeded");
	      }
		return settingAttempt;
	}
	
	public SettingAttempt DefaultSettingAttempt(){
		try {
		SettingAdmin settingAdmin = settingAdminRepository.findAll().get(0);
			settingAdmin.setNumberAttempt(settingAdmin.getNumberAttemptDefault());
			settingAdmin.setTimes(settingAdmin.getTimesDefault());
		modifyAttemptSettingDefaultBdd(settingAdmin);
		MAX_ATTEMPT = 5;
		time = 15;
		message = time + " minute";
		}catch (DataAccessException dae) {
			System.out.println("problem retrieving configuration connection attempt exceeded");
	    }
		return getAttemptSetting();
	}
	
	@Transactional
	public void modifyAttemptSettingBdd(SettingAdmin parametreAdmin){
		 try {
			 settingAdminRepository.save(parametreAdmin);
	      } catch (DataAccessException dae) {
			 System.out.println("problem changing configuration connection attempt timed out");
	      }
	}
	
	@Transactional
	public void modifyAttemptSettingDefaultBdd(SettingAdmin parametreAdmin){
		 try {
			 settingAdminRepository.save(parametreAdmin);
	      } catch (DataAccessException dae) {
			 System.out.println("problem modifying default configuration of connection attempts exceeded");
	      }
	}

}
