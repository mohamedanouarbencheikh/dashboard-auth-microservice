package com.github.gatewayservice.business;

import com.github.gatewayservice.models.UserDb;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project gateway-service
 */
public interface UserBusiness {

	public UserDb findOneByUserNameAndExisteExcludePassWord(String username, boolean existe);
	public UserDb modifyUser(UserDb user);
	public UserDb findOneByUserNameExcludePassWord(String username);
	public void connectUser(String idUser, boolean etat);
	public UserDb findOneByUserName(String userName);
}
