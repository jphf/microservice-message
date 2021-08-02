package com.jphf.cloud.history.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.jphf.cloud.history.config.HistoryConfig;
import com.jphf.cloud.shared.Message;
import com.jphf.cloud.shared.Room;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	HistoryConfig historyConfig;

	ReactiveMongoTemplate template;

	public MessageService(HistoryConfig historyConfig, ReactiveMongoTemplate template) {
		this.historyConfig = historyConfig;
		this.template = template;
	}

	public Flux<Message> getMessages(String roomId, long before) {
		Query query = new Query();

		Criteria time = Criteria.where("createdAt").lt(before);

		query.addCriteria(time);

		logger.debug("{}", query);

		return this.template.find(query, Message.class, roomId).takeLast(historyConfig.getBatchSize());
	}

//	@Transactional
	public Mono<Room> getOrCreateRoom(List<String> usernames) {
		Query query = new Query();
		query.addCriteria(Criteria.where("usernames").size(usernames.size()).all(usernames));

		return this.template.findOne(query, Room.class, "room").switchIfEmpty(createRoom(usernames));
	}
	
	public Mono<Room> createRoom(List<String> usernames){
		Room room = new Room();
		room.setUsernames(usernames);
		return template.insert(room, "room");
	}
}
