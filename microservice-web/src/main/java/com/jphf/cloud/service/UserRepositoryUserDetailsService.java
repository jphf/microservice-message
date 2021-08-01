package com.jphf.cloud.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jphf.cloud.shared.User;
import com.jphf.cloud.util.feign.UserFeignClient;
import com.jphf.cloud.util.pojo.SecurityUser;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

	private UserFeignClient userFeignClient;

	public UserRepositoryUserDetailsService(UserFeignClient userFeignClient) {
		this.userFeignClient = userFeignClient;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = this.userFeignClient.findByUsername(username);
		if (user != null) {
			return new SecurityUser(user);
		}
		throw new UsernameNotFoundException("User " + username + " not found");
	}

}
