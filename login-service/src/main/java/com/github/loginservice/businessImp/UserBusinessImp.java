package com.github.loginservice.businessImp;
import com.github.loginservice.dao.RoleRepository;
import com.github.loginservice.dao.SettingAdminRepository;
import com.github.loginservice.dao.UserRepository;
import com.github.loginservice.business.UserBusiness;
import com.github.loginservice.models.Role;
import com.github.loginservice.models.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Service
public class UserBusinessImp implements UserBusiness {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private SettingAdminRepository settingAdminRepository;

	private HashMap<String, Boolean> usersStatus = new HashMap<String, Boolean>();

	@PostConstruct
	public void createFirstUser(){

		 UserDb firstUser = userRepository.findOneByUserName("admin");

		 if(firstUser == null){
			 List roles = new ArrayList();
			 Role role = new Role();
			 roles.add("ADMIN");
			 roles.add("USER");

			 role.setCreationDate(new Date());
			 role.setFirstName("admin");
			 role.setUserName("admin");
			 role.setLastName("admin");
			 role.setRole(roles);

			 Role roleUser = addRoleDb(role);

			 UserDb userDb = new UserDb();


			 userDb.setCreationDate(new Date());
			 userDb.setAddress("Algiers");
			 userDb.setCity("Algiers");
			 userDb.setCountry("Algeria");
			 userDb.setPostalCode(1600);
			 userDb.setLastName("admin");
			 userDb.setFirstName("admin");

			 userDb.setRoleUser(roleUser);
			 userDb.setCompany("github");
			 userDb.setEmail("admin@github.dz");
			 userDb.setUserName("admin");

			 BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			 userDb.setPassword(passwordEncoder.encode("admin"));
			 addUserDb(userDb);

		 }

         UserDb user = new UserDb();

		 user.setCreationDate(new Date());
		 user.setAddress("Algiers");
	}

	@Override
	public UserDb findOneByUserNameAndExisteExcludePassWord(String username, boolean existe){
		UserDb userDb = new UserDb();
		try {
			userDb = userRepository.findOneByUserNameAndExisteExcludePassWord(username, true);
		} catch (DataAccessException dae) {
			System.out.println("problem recovering a user");
		}
		return userDb;
	}

	@Override
	public UserDb findOneByUserNameExcludePassWord(String username){
		UserDb userDb = new UserDb();
		try {
			userDb = userRepository.findOneByUserNameExcludePassWord(username);
		} catch (DataAccessException dae) {
			System.out.println("problem recovering a user");
		}
		return userDb;
	}

	@Override
	public void connectUser(String idUser, boolean etat){
		if(!usersStatus.containsKey(idUser))
			usersStatus.put(idUser, etat);
		else
			usersStatus.replace(idUser, etat);
	}

	@Override
	public UserDb findOneByUserName(String userName){
		UserDb userDb = new UserDb();
		try {
			userDb = userRepository.findOneByUserName(userName);
		} catch (DataAccessException dae) {
			System.out.println("problem recovering a user");
		}
		return userDb;
	}

	public List<UserDb> getUsers(){
		return userRepository.findAll();
	}

    @Transactional
	public Role addRoleDb(Role role){
		return roleRepository.save(role);
	}

	@Transactional
	public UserDb addUserDb(UserDb userDb){
		return userRepository.save(userDb);
	}
	
}
