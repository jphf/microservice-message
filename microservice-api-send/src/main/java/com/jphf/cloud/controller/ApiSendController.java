package com.jphf.cloud.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jphf.cloud.service.KafkaMessaingService;
import com.jphf.cloud.shared.UserMessage;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/send")
public class ApiSendController {

	private static final Logger logger = LoggerFactory.getLogger(ApiSendController.class);

	private KafkaMessaingService kafkaMessaingService;

	@Autowired
	public ApiSendController(KafkaMessaingService kafkaMessaingService) {
		super();
		this.kafkaMessaingService = kafkaMessaingService;
	}

	@GetMapping
	public Mono<String> get() {
		return Mono.just("work");
	}

	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Void> send(@RequestBody Mono<UserMessage> messageMono) {

		return messageMono.flatMap(m -> {
			logger.info("{}", m);
			kafkaMessaingService.sendMessage(m);
			return Mono.empty();
		});

	}
}
