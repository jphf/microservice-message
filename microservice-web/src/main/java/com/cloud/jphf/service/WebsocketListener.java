package com.cloud.jphf.service;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.jphf.cloud.shared.UserMessage;


@Service
public class WebsocketListener {
	
	private static final Logger logger = LoggerFactory.getLogger(WebsocketListener.class);
	
	@Bean
	Consumer<UserMessage> receivedMessage(){
		return new Consumer<UserMessage>() {
			
			@Override
			public void accept(UserMessage t) {
				// TODO Auto-generated method stub
				logger.info("receivedMessage");
				logger.info("{}", t.getMessage().getText());
			}
		};
	}
}
