package br.com.opsocial.server.utils.instagram.entities;

import java.io.Serializable;

public class Comment implements Serializable {

	private static final long serialVersionUID = 2922474222372440012L;
	
	private String id;
	private String text;
	private String timestamp;
	private User user;
	
	public Comment() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
