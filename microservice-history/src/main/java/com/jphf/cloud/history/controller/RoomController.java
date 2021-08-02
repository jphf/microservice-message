package com.jphf.cloud.history.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jphf.cloud.history.service.MessageService;
import com.jphf.cloud.shared.Room;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/room")
public class RoomController {
	private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

	MessageService messageService;

	public RoomController(MessageService messageService) {
		this.messageService = messageService;
	}

	@GetMapping
	public Mono<Room> get(@RequestParam("username") List<String> usernames) {
		return this.messageService.getOrCreateRoom(usernames);
	}
}
