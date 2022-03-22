package com.github.loginservice.securityManagement;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	@Autowired
	private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	
	@Autowired
	private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

	private AuthenticationManager authenticationManager;

	@Override
	@Autowired
	public void setAuthenticationManager(@Lazy AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		super.setAuthenticationManager(authenticationManager);
	}

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getParameter("username"),request.getParameter("password")));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
		myAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }
    
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
			throws IOException {
		myAuthenticationFailureHandler.onAuthenticationFailure(request, response, failed);
	}
	

}
