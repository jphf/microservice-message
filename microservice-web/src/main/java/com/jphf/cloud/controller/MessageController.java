package com.jphf.cloud.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

import com.jphf.cloud.shared.Message;
import com.jphf.cloud.shared.Room;
import com.jphf.cloud.shared.RoomMessage;
import com.jphf.cloud.util.data.Constants;
import com.jphf.cloud.util.feign.HistoryFeignClient;
import com.jphf.cloud.util.feign.SendFeignClient;
import com.jphf.cloud.util.feign.UserFeignClient;
import com.jphf.cloud.util.pojo.ChooseUser;
import com.jphf.cloud.util.pojo.InMessage;
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
	UserFeignClient userFeignClient;

	DateFormat dateFormat = new SimpleDateFormat("HH:mm");

	@MessageMapping(Constants.SECURED_CHAT_USER)
	public void chooseUser(@Payload ChooseUser chooseUser, Principal user, SimpMessageHeaderAccessor headerAccessor) {

		String toUsername = chooseUser.getUsername();

		logger.debug("choose user: {}", toUsername);

		List<String> usernames = Arrays.asList(user.getName(), toUsername);
		Room room = historyFeignClient.getOrCreateRoom(usernames);

		headerAccessor.getSessionAttributes().put("room", room);

		List<Message> messages = historyFeignClient.get(room.get_id(), new Date().getTime());

		messages.forEach(m -> {
			Date time = new Date(m.getCreatedAt());
			OutputMessage msg = new OutputMessage(m.getFrom(), m.getText(), new SimpleDateFormat("HH:mm").format(time));
			simpMessagingTemplate.convertAndSendToUser(user.getName(), Constants.SECURED_CHAT_SPECIFIC_USER, msg);
		});
	}

	@MessageMapping(Constants.SECURED_CHAT_ROOM)
	public void sendSpecific(@Payload InMessage msg, Principal user, @Header("simpSessionId") String sessionId,
			SimpMessageHeaderAccessor headerAccessor) {
		logger.debug("{} {}", user.getName(), msg.getText());

		Room room = (Room) headerAccessor.getSessionAttributes().get("room");

		Date now = new Date();
		OutputMessage out = new OutputMessage(user.getName(), msg.getText(), dateFormat.format(now));

		if ("".equals(msg.getText()) || msg.getText() == null) {
			out = new OutputMessage(null, "Empty", dateFormat.format(now));
			simpMessagingTemplate.convertAndSendToUser(user.getName(), Constants.SECURED_CHAT_SPECIFIC_USER, out);
			return;
		}

//		simpMessagingTemplate.convertAndSendToUser(user.getName(), Constants.SECURED_CHAT_SPECIFIC_USER, out);

		Message message = new Message();
		message.setFrom(user.getName());
		message.setText(msg.getText());
		message.setCreatedAt(now.getTime());

		RoomMessage userMessage = new RoomMessage();
		userMessage.setRoom(room);
		userMessage.setMessage(message);
		sendFeignClient.send(userMessage);
	}

	@SubscribeMapping(Constants.SECURED_CHAT_SPECIFIC_USER)
	public void welcomeMessage(Principal user) {
		Date now = new Date();
		OutputMessage out = new OutputMessage(null, "Choose user", new SimpleDateFormat("HH:mm").format(now));

		logger.info("{}", user.getName());

		simpMessagingTemplate.convertAndSendToUser(user.getName(), Constants.SECURED_CHAT_SPECIFIC_USER, out);
	}

	@SubscribeMapping(Constants.SECURED_CHAT_SPECIFIC_USER_FRIENDS)
	public void sendFriends(Principal user) {
		List<String> usernames = userFeignClient.getAllUsernames();
		List<OutputUser> users = usernames.stream().filter(username -> !user.getName().equalsIgnoreCase(username))
				.sorted().map(username -> new OutputUser(username, Type.ADD)).collect(Collectors.toList());
		simpMessagingTemplate.convertAndSendToUser(user.getName(), Constants.SECURED_CHAT_SPECIFIC_USER_FRIENDS, users);
	}

}
