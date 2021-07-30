package com.cloud.jphf.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.jphf.util.entity.User;
import com.cloud.jphf.util.repository.UserRepository;

@Service
public class UserServce {

	UserRepository userRepository;

	@Autowired
	public UserServce(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Set<String> getAllUsernames() {
		Set<String> usernames = new HashSet<String>();
		for (User user : userRepository.findAll()) {
			usernames.add(user.getUsername());
		}
		return usernames;
	}
}
