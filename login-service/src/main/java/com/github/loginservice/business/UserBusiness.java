package com.github.loginservice.business;

import com.github.loginservice.models.Role;
import com.github.loginservice.models.UserDb;

import java.util.List;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
public interface UserBusiness {

	public UserDb findOneByUserNameAndExisteExcludePassWord(String username, boolean existe);
	public UserDb findOneByUserNameExcludePassWord(String username);
	public void connectUser(String idUser, boolean etat);
	public UserDb findOneByUserName(String userName);
	public Role addRoleDb(Role role);
	public UserDb addUserDb(UserDb userDb);
	public List<UserDb> getUsers();
}
