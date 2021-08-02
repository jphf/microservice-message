package com.jphf.cloud.util.pojo;

public class OutputMessage extends Message {
	private String from;
	private String time;

	public OutputMessage(String from, String text, String time) {
		this.from = from;
		this.time = time;

		setText(text);
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
