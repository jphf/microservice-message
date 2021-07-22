package com.jphf.cloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.jphf.cloud.util.repository.UserRepository;

import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Autowired
	UserRepository userRepository;
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public ReactiveUserDetailsService userDetailsService() {
		return new ReactiveUserDetailsService() {
			
			@Override
			public Mono<UserDetails> findByUsername(String username) {
				return Mono.just(userRepository.findByUsername(username));
			}
		};
	}
	
	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(
	  ServerHttpSecurity http) {
	    return http.authorizeExchange()
	      .anyExchange().authenticated()
	      .and().formLogin()
	      .and().build();
	}
	
//	@Bean
//	public MapReactiveUserDetailsService userDetailsService() {
//	    UserDetails user = User
//	      .withUsername("user")
//	      .password(encoder().encode("password"))
//	      .roles("USER")
//	      .build();
//	    return new MapReactiveUserDetailsService(user);
//	}
}
