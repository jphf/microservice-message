package com.jphf.cloud.util.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jphf.cloud.util.document.DocumentUserMessage;

public interface UserMessageRepository extends ReactiveMongoRepository<DocumentUserMessage, Long> {
	
}
