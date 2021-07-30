package com.jphf.cloud.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jphf.cloud.shared.UserMessage;

@Service
public class MessageService {

	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	MongoTemplate template;

	public MessageService(MongoTemplate template) {
		this.template = template;
	}

	@Transactional
	public void insert(UserMessage userMessage) {
		String fromUsername = userMessage.getFrom();
		String toUsername = userMessage.getTo();

		logger.info("from={}, to={}", fromUsername, toUsername);

		Query query = new Query();
		query.addCriteria(Criteria.where("user").is(toUsername));

		Query query2 = new Query();
		query2.addCriteria(Criteria.where("user").is(fromUsername));

		Update update = new Update();
		update.addToSet("message", userMessage);

		template.upsert(query, update, fromUsername);
		template.upsert(query2, update, toUsername);

	}
}
