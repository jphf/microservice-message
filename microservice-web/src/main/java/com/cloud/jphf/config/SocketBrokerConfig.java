package com.cloud.jphf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import com.cloud.jphf.util.data.Constants;

@Configuration
@EnableWebSocketMessageBroker
public class SocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(Constants.SECURED_CHAT_ROOM).addInterceptors(new HttpSessionHandshakeInterceptor())
				.withSockJS();
		registry.addEndpoint(Constants.SECURED_CHAT_USER).addInterceptors(new HttpSessionHandshakeInterceptor())
		.withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableStompBrokerRelay("/queue").setRelayHost("localhost").setRelayPort(61613).setClientLogin("guest")
				.setClientPasscode("guest");
		registry.setApplicationDestinationPrefixes("/app", "/user");
		registry.setUserDestinationPrefix("/user");
	}

}
