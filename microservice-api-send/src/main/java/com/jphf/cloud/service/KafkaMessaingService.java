package com.jphf.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.jphf.cloud.shared.UserMessage;

@Service
public class KafkaMessaingService {
	
	private KafkaTemplate<String, UserMessage> kafkaTemplate;

	@Autowired
	public KafkaMessaingService(KafkaTemplate<String, UserMessage> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage(UserMessage message) {
		this.kafkaTemplate.sendDefault(message);
	}
}
