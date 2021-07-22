package com.cloud.jphf.util.pojo;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.cloud.jphf.util.entity.User;

public class RegistrationForm {

	private String username;
	private String password;
	private String email;
	
	
	
	public RegistrationForm() {
	}

	public RegistrationForm(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(username, passwordEncoder.encode(password), email);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
