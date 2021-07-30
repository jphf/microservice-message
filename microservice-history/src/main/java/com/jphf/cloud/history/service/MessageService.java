package com.jphf.cloud.history.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.jphf.cloud.history.config.HistoryConfig;
import com.jphf.cloud.shared.UserMessage;

import reactor.core.publisher.Flux;

@Service
public class MessageService {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

	HistoryConfig historyConfig;

	ReactiveMongoTemplate template;

	public MessageService(HistoryConfig historyConfig, ReactiveMongoTemplate template) {
		this.historyConfig = historyConfig;
		this.template = template;
	}

	public Flux<UserMessage> getMessages(String username, String username2, long before) {
		Query query = new Query();

		Criteria fromTo1 = new Criteria();
		fromTo1.andOperator(Criteria.where("from").is(username), Criteria.where("to").is(username2));

		Criteria fromTo2 = new Criteria();
		fromTo2.andOperator(Criteria.where("from").is(username2), Criteria.where("to").is(username));

		Criteria time = Criteria.where("createdAt").lt(before);

		query.addCriteria(time);
		query.addCriteria(new Criteria().orOperator(fromTo1, fromTo2));

		logger.debug("{}", query);
		
		return this.template.find(query, UserMessage.class, username).takeLast(historyConfig.getBatchSize());
	}

}
