package com.cloud.jphf.service;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.cloud.jphf.util.pojo.ReceivedMessage;


@Service
public class WebsocketListener {
	
	private static final Logger logger = LoggerFactory.getLogger(WebsocketListener.class);
	
	@Bean
	Consumer<ReceivedMessage> receivedMessage(){
		return new Consumer<ReceivedMessage>() {
			
			@Override
			public void accept(ReceivedMessage t) {
				// TODO Auto-generated method stub
				logger.info("receivedMessage");
			}
		};
	}
}
