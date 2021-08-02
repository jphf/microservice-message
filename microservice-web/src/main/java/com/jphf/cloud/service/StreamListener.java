package com.jphf.cloud.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.jphf.cloud.shared.Room;
import com.jphf.cloud.shared.RoomMessage;
import com.jphf.cloud.util.data.Constants;
import com.jphf.cloud.util.pojo.OutputMessage;

@Service
public class StreamListener {

	private static final Logger logger = LoggerFactory.getLogger(StreamListener.class);

	SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	public StreamListener(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	@Bean
	Consumer<RoomMessage> receivedMessage() {
		return new Consumer<RoomMessage>() {

			@Override
			public void accept(RoomMessage t) {
				// TODO Auto-generated method stub
				logger.info("receivedMessage");
				logger.info("{} {}", t.getFrom(), t.getText());

				OutputMessage out = new OutputMessage(t.getFrom(), t.getText(),
						new SimpleDateFormat("HH:mm").format(t.getCreatedAt()));

				Room room = t.getRoom();
				
				List<String> usernames = room.getUsernames();
				for(String username:usernames) {
					logger.info("stomp {}", username);
					simpMessagingTemplate.convertAndSendToUser(username, Constants.SECURED_CHAT_SPECIFIC_USER, out);
				}
			}
		};
	}
}
