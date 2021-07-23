package com.jphf.cloud.util.document;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.jphf.cloud.shared.User;

@Document
public class DocumentUserMessage {
	@Id
	private Long userId;

	private Map<Long, Sender> senders = new HashMap<>();

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Map<Long, Sender> getSenders() {
		return senders;
	}

	public void setSenders(Map<Long, Sender> senders) {
		this.senders = senders;
	}

}
