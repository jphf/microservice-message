package com.cloud.jphf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.cloud.jphf.util.data.Constants;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
	
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/message");
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableStompBrokerRelay(Constants.SECURED_CHAT_SPECIFIC_USER).setRelayHost("localhost").setRelayPort(61613).setClientLogin("guest").setClientPasscode("guest");
		registry.setApplicationDestinationPrefixes("/app");
		registry.setUserDestinationPrefix("/secured/user");
	}
}
