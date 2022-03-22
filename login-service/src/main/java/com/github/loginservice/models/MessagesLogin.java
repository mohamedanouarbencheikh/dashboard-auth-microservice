package com.github.loginservice.models;

public class MessagesLogin {

	private String accountDisabled = "account disabled  !";
	private String passwordExpires = "password expired !";
	private String passwordChangedByAdministrator = "Password changed by an administrator !";
	private String numberConnectionAttemptsExceeded;
	private String loginFails = "The connection failed. Please check your username and password and try again.";

	public String getAccountDisabled() {
		return accountDisabled;
	}

	public void setAccountDisabled(String accountDisabled) {
		this.accountDisabled = accountDisabled;
	}

	public String getPasswordExpires() {
		return passwordExpires;
	}

	public void setPasswordExpires(String passwordExpires) {
		this.passwordExpires = passwordExpires;
	}

	public String getPasswordChangedByAdministrator() {
		return passwordChangedByAdministrator;
	}

	public void setPasswordChangedByAdministrator(String passwordChangedByAdministrator) {
		this.passwordChangedByAdministrator = passwordChangedByAdministrator;
	}

	public String getNumberConnectionAttemptsExceeded(String time) {
		numberConnectionAttemptsExceeded = "you have exceeded the number of attempts!, you must wait" + time +"to be able to authenticate";
		return numberConnectionAttemptsExceeded;
	}

	public void setNumberConnectionAttemptsExceeded(String numberConnectionAttemptsExceeded) {
		this.numberConnectionAttemptsExceeded = numberConnectionAttemptsExceeded;
	}

	public String getLoginFails() {
		return loginFails;
	}

	public void setLoginFails(String loginFails) {
		this.loginFails = loginFails;
	}


}
