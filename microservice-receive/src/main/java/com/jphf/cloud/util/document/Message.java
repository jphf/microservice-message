package com.jphf.cloud.util.document;

import java.util.Date;

public class Message {
	
	private String text;
	
	private Date createAt;


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	
	
	
	
}
