package com.jphf.cloud.shared;

import java.util.List;

public class Room {

	String _id;

	List<String> usernames;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public List<String> getUsernames() {
		return usernames;
	}

	public void setUsernames(List<String> usernames) {
		this.usernames = usernames;
	}

}
