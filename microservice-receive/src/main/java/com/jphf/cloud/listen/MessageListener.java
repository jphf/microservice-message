package com.jphf.cloud.listen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.jphf.cloud.service.StreamService;
import com.jphf.cloud.service.TransactionalService;
import com.jphf.cloud.shared.UserMessage;

@Component
public class MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

	StreamService streamService;
	TransactionalService transactionalService;

	@Autowired
	public MessageListener(StreamService streamService, TransactionalService transactionalService) {
		this.streamService = streamService;
		this.transactionalService = transactionalService;
	}

	@KafkaListener(topics = "messages.topic")
	public void handle(UserMessage userMessage, Acknowledgment acknowledgment) {
		logger.info("{}", userMessage.getMessage().getText());

		streamService.publish(userMessage);
		transactionalService.insert(userMessage).block();

		acknowledgment.acknowledge();
	}

}
