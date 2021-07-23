package com.jphf.cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
public class MongoConfig {

	@Bean
	ReactiveMongoTransactionManager transactionManager(ReactiveMongoDatabaseFactory databaseFactory) {
		return new ReactiveMongoTransactionManager(databaseFactory);
	}
	
	@Bean
	TransactionalOperator transactionalOperator(ReactiveTransactionManager reactiveTransactionManager) {
		return TransactionalOperator.create(reactiveTransactionManager);
	}
}
