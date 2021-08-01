package com.jphf.cloud.user.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jphf.cloud.user.entity.User;
import com.jphf.cloud.user.repository.UserRepository;

@Service
public class UserService {

	UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User registerAndCheck(String username, String password) {
		User user = this.userRepository.findByUsername(username);
		if (user == null) {
			return register(username, password);
		}
		return null;
	}

	@Transactional
	public User register(String username, String password) {
		User user = this.userRepository.findByUsername(username);
		if (user == null) {
			user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user = this.userRepository.save(user);
			return user;
		}
		return null;
	}

	public List<String> getAllUsernames() {
		List<String> usernames = new ArrayList<>();
		for (User user : userRepository.findAll()) {
			usernames.add(user.getUsername());
		}
		return usernames;
	}
}
