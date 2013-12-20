package com.example.gameon.objects;

public class Response {
	
	private String toUser;
	private String fromUser;
	private int teamSize;
	private String skill;
	private String time;
	private String ID;
	private String gameID;

	public Response(String tu, String fu, int ts, String sk, String time, String gameID) {
		this.toUser = tu;
		this.fromUser = fu;
		this.teamSize = ts;
		this.skill = sk;
		this.time = time;
		this.gameID = gameID;
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public int getTeamSize() {
		return teamSize;
	}

	public void setTeamSize(int squadSize) {
		this.teamSize = squadSize;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getGameID() {
		return gameID;
	}

	public void setGameID(String gameID) {
		this.gameID = gameID;
	}

}
