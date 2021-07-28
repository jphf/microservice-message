package com.cloud.jphf.util.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jphf.cloud.shared.UserMessage;

@FeignClient(name="microservice-api-send")
public interface SendFeignClient {
	
	@RequestMapping(value = "/api/send", method = RequestMethod.POST)
	public void send(UserMessage userMessage);
}
