package com.cloud.jphf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.cloud.jphf.util.pojo.SentMessage;


@Service
public class SentMessageWriter {

	StreamBridge streamBridge;

	@Autowired
	public SentMessageWriter(StreamBridge streamBridge) {
		this.streamBridge = streamBridge;
	}
	
	public void publish(Message<SentMessage> message) {
		streamBridge.send("sentMessage-out-0", message);
	}
}
