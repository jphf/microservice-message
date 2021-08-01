package com.jphf.cloud.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import com.jphf.cloud.service.UserServce;
import com.jphf.cloud.shared.UserMessage;
import com.jphf.cloud.util.data.Constants;
import com.jphf.cloud.util.feign.HistoryFeignClient;
import com.jphf.cloud.util.feign.SendFeignClient;
import com.jphf.cloud.util.pojo.ChooseUser;
import com.jphf.cloud.util.pojo.Message;
import com.jphf.cloud.util.pojo.OutputMessage;
import com.jphf.cloud.util.pojo.OutputUser;
import com.jphf.cloud.util.pojo.OutputUser.Type;

@Controller
public class MessageController {

	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	SendFeignClient sendFeignClient;

	@Autowired
	HistoryFeignClient historyFeignClient;

	@Autowired
	UserServce userServce;

	DateFormat dateFormat = new SimpleDateFormat("HH:mm");

	@MessageMapping(Constants.SECURED_CHAT_USER)
	public void chooseUser(@Payload ChooseUser chooseUser, Principal user, SimpMessageHeaderAccessor headerAccessor) {

		String toUsername = chooseUser.getUsername();

		logger.debug("choose user: {}", toUsername);

		headerAccessor.getSessionAttributes().put("toUsername", toUsername);

		List<UserMessage> messages = historyFeignClient.get(user.getName(), toUsername, new Date().getTime());

		messages.forEach(m -> {
			Date time = new Date(m.getCreatedAt());
			OutputMessage msg = new OutputMessage(m.getFrom(), m.getTo(), m.getText(), new SimpleDateFormat("HH:mm").format(time));
			simpMessagingTemplate.convertAndSendToUser(user.getName(), Constants.SECURED_CHAT_SPECIFIC_USER, msg);
		});
	}

	@MessageMapping(Constants.SECURED_CHAT_ROOM)
	public void sendSpecific(@Payload Message msg, Principal user, @Header("simpSessionId") String sessionId,
			SimpMessageHeaderAccessor headerAccessor) {
		logger.debug("{} {} {}", user.getName(), msg.getTo(), msg.getText());

		logger.debug("{}", headerAccessor.getSessionAttributes().get("toUsername"));

		Date now = new Date();
		OutputMessage out = new OutputMessage(user.getName(), msg.getTo(), msg.getText(), dateFormat.format(now));

		if ("".equals(msg.getTo()) || msg.getTo() == null) {
			out = new OutputMessage(null, user.getName(), "Empty", dateFormat.format(now));
			simpMessagingTemplate.convertAndSendToUser(user.getName(), Constants.SECURED_CHAT_SPECIFIC_USER, out);
			return;
		}

		simpMessagingTemplate.convertAndSendToUser(user.getName(), Constants.SECURED_CHAT_SPECIFIC_USER, out);

		UserMessage userMessage = new UserMessage();
		userMessage.setFrom(user.getName());
		userMessage.setTo(msg.getTo());
		userMessage.setText(msg.getText());
		userMessage.setCreatedAt(now.getTime());
		sendFeignClient.send(userMessage);
	}

	@SubscribeMapping(Constants.SECURED_CHAT_SPECIFIC_USER)
	public void welcomeMessage(Principal user) {
		Date now = new Date();
		OutputMessage out = new OutputMessage(null, user.getName(), "Choose user", new SimpleDateFormat("HH:mm").format(now));

		simpMessagingTemplate.convertAndSendToUser(user.getName(), Constants.SECURED_CHAT_SPECIFIC_USER, out);
	}

	@SubscribeMapping(Constants.SECURED_CHAT_SPECIFIC_USER_FRIENDS)
	public void sendFriends(Principal user) {
		Set<String> usernames = userServce.getAllUsernames();
		Set<OutputUser> users = usernames.stream().filter(username -> !user.getName().equalsIgnoreCase(username))
				.map(username -> new OutputUser(username, Type.ADD)).collect(Collectors.toSet());
		simpMessagingTemplate.convertAndSendToUser(user.getName(), Constants.SECURED_CHAT_SPECIFIC_USER_FRIENDS, users);
	}

}
