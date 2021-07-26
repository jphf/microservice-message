package com.cloud.jphf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.cloud.jphf.util.data.Constants;


@Configuration
@EnableWebSocketMessageBroker
public class SocketBrokerConfig implements WebSocketMessageBrokerConfigurer{
	
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(Constants.SECURED_CHAT_ROOM).withSockJS();
	}
	
	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableStompBrokerRelay(Constants.SECURED_CHAT_SPECIFIC_USER, Constants.SECURED_CHAT_SPECIFIC_USER_FRIENDS).setRelayHost("localhost").setRelayPort(61613).setClientLogin("guest").setClientPasscode("guest");
//		registry.enableSimpleBroker(Constants.SECURED_CHAT_SPECIFIC_USER, Constants.SECURED_CHAT_FRIENDS);
		registry.setApplicationDestinationPrefixes("/spring-security-mvc-socket");
		registry.setUserDestinationPrefix("/secured/user");
	}
	
}
