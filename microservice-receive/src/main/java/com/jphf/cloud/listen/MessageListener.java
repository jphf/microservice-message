package com.jphf.cloud.listen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.jphf.cloud.shared.UserMessage;

@Component
public class MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

	@KafkaListener(topics = "messages.topic")
	public void handle(UserMessage userMessage, Acknowledgment acknowledgment) {
		logger.info("{}", userMessage.getMessage().getText());

		acknowledgment.acknowledge();
	}

}
