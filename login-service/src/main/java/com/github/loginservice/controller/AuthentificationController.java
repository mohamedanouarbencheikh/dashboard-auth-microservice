package com.github.loginservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.loginservice.securityManagement.MyLogoutSuccessHandler;
import com.github.loginservice.securityManagement.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@RestController
public class AuthentificationController {

	@Autowired
	TokenService tokenService;
		
	@Autowired
	MyLogoutSuccessHandler myLogoutSuccessHandler;


	@PostMapping(value = "/app/logout")
	public void logout(@RequestParam String userName, HttpServletRequest request, HttpServletResponse response) {
		myLogoutSuccessHandler.onLogoutSuccess(request, response);
	}

	
}
