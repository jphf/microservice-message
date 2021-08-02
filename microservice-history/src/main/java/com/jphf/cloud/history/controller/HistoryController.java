package com.jphf.cloud.history.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jphf.cloud.history.service.MessageService;
import com.jphf.cloud.shared.RoomMessage;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/history")
public class HistoryController {

	private static final Logger logger = LoggerFactory.getLogger(HistoryController.class);

	MessageService messageService;

	public HistoryController(MessageService messageService) {
		this.messageService = messageService;
	}

	@GetMapping
	public Flux<RoomMessage> getMessages(String roomId,
			long before) {

		logger.info("roomId={}, before={}", roomId, before);
		return this.messageService.getMessages(roomId, before);
	}
}
