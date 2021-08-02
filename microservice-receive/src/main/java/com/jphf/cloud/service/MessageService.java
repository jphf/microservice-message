package com.jphf.cloud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jphf.cloud.shared.Message;
import com.jphf.cloud.shared.Room;
import com.jphf.cloud.shared.RoomMessage;

@Service
public class MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	MongoTemplate template;

	public MessageService(MongoTemplate template) {
		this.template = template;
	}

	@Transactional
	public void insert(RoomMessage userMessage) {
		Room room = userMessage.getRoom();
		Message message = userMessage.getMessage();

		template.insert(message, room.get_id());
	}
}
