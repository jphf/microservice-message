package com.jphf.cloud.listen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import com.jphf.cloud.service.MessageService;
import com.jphf.cloud.service.StreamService;
import com.jphf.cloud.shared.Message;
import com.jphf.cloud.shared.RoomMessage;

@Component
public class MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

	StreamService streamService;
	MessageService transactionalService;

	@Autowired
	public MessageListener(StreamService streamService, MessageService transactionalService) {
		this.streamService = streamService;
		this.transactionalService = transactionalService;
	}

	@KafkaListener(topics = "messages.topic")
	public void handle(RoomMessage userMessage, Acknowledgment acknowledgment) {
		logger.debug("{}", userMessage.getMessage().getText());

		transactionalService.insert(userMessage);

		streamService.publish(userMessage);

		acknowledgment.acknowledge();
	}

}
