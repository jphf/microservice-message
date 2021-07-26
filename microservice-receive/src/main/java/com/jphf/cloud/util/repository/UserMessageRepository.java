package com.jphf.cloud.util.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jphf.cloud.util.document.DocumentUserMessage;

import reactor.core.publisher.Mono;

public interface UserMessageRepository extends ReactiveMongoRepository<DocumentUserMessage, Long> {
	Mono<DocumentUserMessage> findByUsername(String username);
}
