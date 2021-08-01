package com.jphf.cloud.util.pojo;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.jphf.cloud.shared.User;

public class RegistrationForm {

	private String username;
	private String password;

	public RegistrationForm() {
	}

	public RegistrationForm(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User toUser(PasswordEncoder passwordEncoder) {
		return new User(username, passwordEncoder.encode(password));
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

}
