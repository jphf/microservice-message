package com.cloud.jphf.service;

import java.text.SimpleDateFormat;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.cloud.jphf.util.data.Constants;
import com.cloud.jphf.util.pojo.OutputMessage;
import com.jphf.cloud.shared.UserMessage;

@Service
public class StreamListener {

	private static final Logger logger = LoggerFactory.getLogger(StreamListener.class);

	SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	public StreamListener(SimpMessagingTemplate simpMessagingTemplate) {
		this.simpMessagingTemplate = simpMessagingTemplate;
	}

	@Bean
	Consumer<UserMessage> receivedMessage() {
		return new Consumer<UserMessage>() {

			@Override
			public void accept(UserMessage t) {
				// TODO Auto-generated method stub
				logger.info("receivedMessage");
				logger.info("{} {} {}", t.getFrom(), t.getTo(), t.getText());

				OutputMessage out = new OutputMessage(t.getTo(), t.getText(),
						new SimpleDateFormat("HH:mm").format(t.getCreatedAt()));

				simpMessagingTemplate.convertAndSendToUser(t.getTo(), Constants.SECURED_CHAT_SPECIFIC_USER, out);
			}
		};
	}
}