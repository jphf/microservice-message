package com.jphf.cloud.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jphf.cloud.user.entity.User;
import com.jphf.cloud.user.repository.UserRepository;
import com.jphf.cloud.user.service.UserService;

@RestController
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	UserService userService;

	UserRepository userRepository;

	public UserController(UserService userService, UserRepository userRepository) {
		this.userService = userService;
		this.userRepository = userRepository;
	}

	@PostMapping("/register")
	public User register(String username, String password) {
		logger.info("{}, {}", username, password);

		return userService.register(username, password);
	}

	@GetMapping("/findByUsername")
	public User findByUsername(String username) {
		return this.userRepository.findByUsername(username);
	}

	@GetMapping("/getAllUsernames")
	public List<String> getAllUsernames() {
		return this.userService.getAllUsernames();
	}
}
