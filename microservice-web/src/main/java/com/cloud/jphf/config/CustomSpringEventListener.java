package com.cloud.jphf.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import com.cloud.jphf.util.pojo.OutputMessage;

//@Component
public class CustomSpringEventListener implements ApplicationListener<SessionSubscribeEvent> {

	private static final Logger logger = LoggerFactory.getLogger(CustomSpringEventListener.class);

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public void onApplicationEvent(SessionSubscribeEvent event) {
		Message<byte[]> message = event.getMessage();
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		StompCommand command = accessor.getCommand();
		if (command.equals(StompCommand.SUBSCRIBE)) {
			String sessionId = accessor.getSessionId();
			String stompSubscriptionId = accessor.getSubscriptionId();
			String destination = accessor.getDestination();

			logger.info("sessionId={}", sessionId);
			logger.info("stompSubscriptionId={}", stompSubscriptionId);
			logger.info("destination={}", destination);

			// Handle subscription event here
			// e.g. send welcome message to *destination*

			if (SecurityContextHolder.getContext().getAuthentication() != null) {
				logger.info("{}", SecurityContextHolder.getContext().getAuthentication().getName());
			}

			OutputMessage out = new OutputMessage("admin", "welcome", new SimpleDateFormat("HH:mm").format(new Date()));
			simpMessagingTemplate.convertAndSend(destination, out);
		}
	}

}
