package com.cloud.jphf.util.pojo;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.cloud.jphf.util.entity.User;

public class RegistrationForm {

	private String username;
	private String password;
	private String email;
	
	public RegistrationForm(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(username, passwordEncoder.encode(password), email);
	}
}
