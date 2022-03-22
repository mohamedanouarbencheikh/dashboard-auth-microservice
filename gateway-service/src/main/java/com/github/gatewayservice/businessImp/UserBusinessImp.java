package com.github.gatewayservice.businessImp;

import com.github.gatewayservice.dao.UserRepository;
import com.github.gatewayservice.business.UserBusiness;
import com.github.gatewayservice.models.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project gateway-service
 */
@Service
public class UserBusinessImp implements UserBusiness {
	
	@Autowired
	private UserRepository userRepository;

	private HashMap<String, Boolean> usersStatus = new HashMap<String, Boolean>();

	@Override
	public UserDb findOneByUserNameAndExisteExcludePassWord(String username, boolean existe){
		UserDb utilisateur = new UserDb();
		try {
		    utilisateur = userRepository.findOneByUserNameAndExisteExcludePassWord(username, true);
		} catch (DataAccessException dae) {
			System.out.println("problem recovering a user");
		}	
		return utilisateur;
	}
	@Override
	public UserDb modifyUser(UserDb user){
		UserDb utilisateur = new UserDb();
		modifyUserDb(user);
		try {
			utilisateur = userRepository.findOneByUserNameExcludePassWord(user.getUserName());
		} catch (DataAccessException dae) {
			System.out.println("problem recovering a user");
		}
		return utilisateur;
	}
	@Override
	public UserDb findOneByUserNameExcludePassWord(String username){
		UserDb utilisateur = new UserDb();
		try {
			utilisateur = userRepository.findOneByUserNameExcludePassWord(username);
		} catch (DataAccessException dae) {
			System.out.println("problem recovering a user");
		}
		return utilisateur;
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
		UserDb utilisateur = new UserDb();
		try {
			utilisateur = userRepository.findOneByUserName(userName);
		} catch (DataAccessException dae) {
			System.out.println("problem recovering a user");
		}
		return utilisateur;
	}

	@Transactional
	public UserDb modifyUserDb(UserDb user){
		UserDb UtilisateurDb =null;
		try {
			UtilisateurDb = userRepository.save(user);
		} catch (DataAccessException dae) {
			System.out.println("problem recovering a user");
		}
		return UtilisateurDb;
	}

	
}
