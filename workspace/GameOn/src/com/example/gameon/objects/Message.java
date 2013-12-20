package com.example.gameon.objects;

public class Message {

	private String toUsername;
	private String fromUsername;
	private String message;
	private String time;
	private String ID;

	public Message(String toUsername, String fromUsername, String message, String time) {
		this.toUsername = toUsername;
		this.fromUsername = fromUsername;
		this.time = time;
		this.message = message;
	}

	public String getToUsername() {
		return toUsername;
	}

	public void setToUsername(String toUsername) {
		this.toUsername = toUsername;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public void setFromUsername(String fromUsername) {
		this.fromUsername = fromUsername;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}
}
