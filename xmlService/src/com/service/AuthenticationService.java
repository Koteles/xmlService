package com.service;

public class AuthenticationService {

	public boolean authenticate(String username, String password) {
		if(username.equals("kote") && password.equals("pass")) {
		return true;
		}
		return false;
	}
}
