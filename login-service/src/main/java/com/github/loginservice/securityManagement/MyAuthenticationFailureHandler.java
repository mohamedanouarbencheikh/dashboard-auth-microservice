package com.github.loginservice.securityManagement;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.loginservice.models.MessagesLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Component
public class MyAuthenticationFailureHandler {

	@Autowired
	private LoginAttemptService loginAttemptService;

	private MessagesLogin messagesLogin = new MessagesLogin();
	

	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
		
        final String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			loginAttemptService.loginFailed(request.getRemoteAddr());
			response.addHeader("message",messagesLogin.getLoginFails());
			if(loginAttemptService.isBlocked(request.getRemoteAddr())){
				response.setHeader("message", messagesLogin.getNumberConnectionAttemptsExceeded(loginAttemptService.getMessage()));
			}

		} else {
			loginAttemptService.loginFailed(xfHeader.split(",")[0]);
			if(loginAttemptService.isBlocked(request.getRemoteAddr())){
				response.setHeader("message", messagesLogin.getNumberConnectionAttemptsExceeded(loginAttemptService.getMessage()));
			}
		}
		((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
	}

	
}