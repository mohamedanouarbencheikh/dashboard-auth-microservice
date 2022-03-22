package com.github.loginservice.securityManagement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.loginservice.business.UserBusiness;
import com.github.loginservice.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Component
public class MyLogoutSuccessHandler{

	@Autowired
	UserBusiness userBusiness;

	UserDb userDb;
	String userName;

	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response){
		userName = request.getParameter("userName");
		userBusiness.connectUser(userName, false);
		userDb = userBusiness.findOneByUserNameAndExisteExcludePassWord(userName, true);
    }
}
