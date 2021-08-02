package com.jphf.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.jphf.cloud.shared.RoomMessage;

@Service
public class KafkaMessaingService {
	
	private KafkaTemplate<String, RoomMessage> kafkaTemplate;

	@Autowired
	public KafkaMessaingService(KafkaTemplate<String, RoomMessage> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage(RoomMessage message) {
		this.kafkaTemplate.sendDefault(message);
	}
}
