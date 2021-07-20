package com.cloud.jphf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cloud.jphf.util.entity.User;
import com.cloud.jphf.util.repository.UserRepository;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public UserRepositoryUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByUsername(username);
		if (user != null) {
			return user;
		}
		throw new UsernameNotFoundException("User "+username+" not found");
	}

}
