package com.github.loginservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
/**
 * @author Mohamed Anouar BENCHEIKH
 * @project login-service
 */
@Getter
@Setter
@ToString
public class SettingAttempt {
	
	private int hours;
	private int minutes;
	private int nbrAttempt;
	private Set<String> listIp;
	
}
