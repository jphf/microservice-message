package com.cloud.jphf.util.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jphf.cloud.shared.UserMessage;

@FeignClient(name = "microservice-history")
public interface HistoryFeignClient {
	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public List<UserMessage> get(@RequestParam("username") String username, @RequestParam("username2") String username2,
			@RequestParam("before") long before);
}
