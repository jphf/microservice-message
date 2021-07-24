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
import com.cloud.jphf.util.pojo.Message;
import com.cloud.jphf.util.pojo.OutputMessage;



@Controller
public class MessageController {

	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
	
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;
	
	@MessageMapping(Constants.SECURED_CHAT_SPECIFIC_USER)
	public void sendSpecific(@Payload Message msg, Principal user, @Header("simpSessionId") String sessionId) {
		logger.info("{}", msg.getText());
		
		OutputMessage out = new OutputMessage(msg.getFrom(), msg.getText(), new SimpleDateFormat("HH:mm").format(new Date()));
		simpMessagingTemplate.convertAndSendToUser(msg.getTo(), Constants.SECURED_CHAT_SPECIFIC_USER, out);
	}
	
}
