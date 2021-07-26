package com.jphf.cloud.util.document;

import java.util.HashMap;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DocumentUserMessage {
	@Id
	private ObjectId id;

	@Indexed
	private String username;

	private Map<String, Sender> senders = new HashMap<>();

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Map<String, Sender> getSenders() {
		return senders;
	}

	public void setSenders(Map<String, Sender> senders) {
		this.senders = senders;
	}

}
