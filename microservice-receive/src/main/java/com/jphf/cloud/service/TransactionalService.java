package com.jphf.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;

import com.jphf.cloud.shared.Message;
import com.jphf.cloud.shared.User;
import com.jphf.cloud.shared.UserMessage;
import com.jphf.cloud.util.document.DocumentUserMessage;
import com.jphf.cloud.util.document.Sender;
import com.jphf.cloud.util.repository.UserMessageRepository;

import reactor.core.publisher.Mono;

@Service
public class TransactionalService {

	UserMessageRepository userMessageRepository;

	TransactionalOperator transactionalOperator;


	@Autowired
	public TransactionalService(UserMessageRepository userMessageRepository,
			TransactionalOperator transactionalOperator) {
		this.userMessageRepository = userMessageRepository;
		this.transactionalOperator = transactionalOperator;
	}

//	@Transactional
	public Mono<Void> insert(UserMessage userMessage) {
		User user = userMessage.getReceiver();
		long userId = user.getId();

		User sender = userMessage.getSender();
		long senderId = sender.getId();

		Message message = userMessage.getMessage();

		Mono<Void> r = userMessageRepository.findById(userId).defaultIfEmpty(new DocumentUserMessage()).flatMap(doc -> {
			Sender s = doc.getSenders().get(senderId);
			if (s == null) {
				s = new Sender();
				doc.getSenders().put(senderId, s);
			}
			s.getMessages().add(message);
			
			return Mono.empty();
		});
		return r;
//		return transactionalOperator.transactional(r);
	}
}
