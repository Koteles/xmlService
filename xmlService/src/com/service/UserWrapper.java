 package com.service;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;


/**

 * This class is the Java Bean class

 *

 */

@ManagedBean
@SessionScoped
public class UserWrapper implements Serializable {

	@Inject
	AuthenticationService service;
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private String password;

	public String authenticate() {	
		if(service.authenticate(username, password)) {
		return "addStudent";
		}
		return null;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

}
