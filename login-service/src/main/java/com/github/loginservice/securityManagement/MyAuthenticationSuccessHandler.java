package com.github.loginservice.securityManagement;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.loginservice.business.UserBusiness;
import com.github.loginservice.models.*;
import com.github.loginservice.tools.business.ToolsBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Component
@PropertySource({ "classpath:application.properties" })
public class MyAuthenticationSuccessHandler  {

	@Autowired
	UserBusiness userBusiness;

	@Autowired
	ToolsBusiness outilMetier;

	@Autowired
	LoginAttemptService loginAttemptService;

	@Autowired
	TokenService tokenService;

	@Autowired
    Environment env;

	private MessagesLogin messagesLogin = new MessagesLogin();
	private UserDb user;
	private Map<String, Object> userState;

	
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		final String xfHeader = request.getHeader("X-Forwarded-For");
		if(!loginAttemptService.isBlocked(request.getRemoteAddr())){		
		if (xfHeader == null) {
			loginAttemptService.loginSucceeded(request.getRemoteAddr());
		} else {
			loginAttemptService.loginSucceeded(xfHeader.split(",")[0]);
		}

		user = userBusiness.findOneByUserNameExcludePassWord(authentication.getName());

		userState = new HashMap<String, Object>();

		userState.put("connectionState", "success");
		succesAuthentication(authentication.getName(), request, response);

		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream out = response.getOutputStream();
		String output = outilMetier.objectToParse(userState);
		out.print(output);

		}
		else{
			response.addHeader("message", messagesLogin.getNumberConnectionAttemptsExceeded(loginAttemptService.getMessage()));
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");		
		}

	}

	public void succesAuthentication(String userName, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		 String token = null;
		 token = tokenService.createToken(user);

		 userBusiness.connectUser(userName, true);
		 userState.put("token",token);
	}
	
}
