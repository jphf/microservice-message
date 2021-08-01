package com.jphf.cloud.util.pojo;

public class OutputUser {
	public enum Type {
		ADD, REMOVE
	}

	String username;
	Type type;

	public OutputUser(String username, Type type) {
		this.username = username;
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}
