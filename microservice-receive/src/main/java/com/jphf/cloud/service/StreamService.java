package com.jphf.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.jphf.cloud.shared.RoomMessage;

@Service
public class StreamService {

	StreamBridge streamBridge;

	@Autowired
	public StreamService(StreamBridge streamBridge) {
		this.streamBridge = streamBridge;
	}

	public void publish(RoomMessage message) {
		streamBridge.send("sentMessage-out-0", message);
	}

}
