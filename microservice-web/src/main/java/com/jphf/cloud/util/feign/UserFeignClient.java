package com.jphf.cloud.util.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jphf.cloud.shared.User;

@FeignClient(name = "microservice-user")
public interface UserFeignClient {
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public User register(@RequestParam("username") String username, @RequestParam("password") String password);

	@RequestMapping(value = "/findByUsername", method = RequestMethod.GET)
	public User findByUsername(@RequestParam("username") String username);

	@RequestMapping(value = "/getAllUsernames", method = RequestMethod.GET)
	public List<String> getAllUsernames();
}
