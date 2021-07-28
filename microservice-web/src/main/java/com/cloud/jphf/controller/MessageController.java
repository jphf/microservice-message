package com.cloud.jphf.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.cloud.jphf.util.data.Constants;
import com.cloud.jphf.util.feign.SendFeignClient;
import com.cloud.jphf.util.pojo.Message;
import com.cloud.jphf.util.pojo.OutputMessage;
import com.jphf.cloud.shared.UserMessage;

@Controller
public class MessageController {

	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	SendFeignClient sendFeignClient;

	@MessageMapping(Constants.SECURED_CHAT_ROOM)
	public void sendSpecific(@Payload Message msg, Principal user, @Header("simpSessionId") String sessionId) {
		logger.info("{}", msg.getText());
		logger.info("{}", user.getName());

		Date now = new Date();
		OutputMessage out = new OutputMessage(msg.getFrom(), msg.getText(), new SimpleDateFormat("HH:mm").format(now));

		simpMessagingTemplate.convertAndSendToUser(user.getName(), Constants.SECURED_CHAT_SPECIFIC_USER, out);

		UserMessage userMessage = new UserMessage();
		userMessage.setFrom(msg.getFrom());
		userMessage.setTo(msg.getTo());
		userMessage.setText(msg.getText());
		userMessage.setCreatedAt(now);
		sendFeignClient.send(userMessage);
//		simpMessagingTemplate.convertAndSendToUser("test", Constants.SECURED_CHAT_SPECIFIC_USER, out);
	}

}
