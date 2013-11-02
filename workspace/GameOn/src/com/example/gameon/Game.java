package com.example.gameon;

import com.parse.ParseObject;
import com.parse.ParseUser;

public class Game {

	private ParseUser user;
	private Sport sport;
	private String location;
	private DayAndTime dat;
	private String gender;
	private String size;
	

	public Game(ParseUser user, Sport sport, String location, DayAndTime dat, String gender, String size) {
		this.user = user;
		this.sport = sport;
		this.location = location;
		this.dat = dat;
		this.gender = gender;
		this.size = size;
	}
	
	public boolean addGame(Game game) {
		ParseObject gameobj = new ParseObject("game");
		gameobj.put("user", game.getUser().getUsername());
		gameobj.put("sport_type", game.getSport().getName());
		gameobj.put("location", game.getLocation());
		gameobj.put("date", game.getDAT().getDate()+" "+game.getDAT().getTime());
		gameobj.put("gender", game.getGender());
		gameobj.put("size", game.getSize());
		
		ParseObject sportobj = new ParseObject("sport");
		sportobj.put("name", game.getSport().getName());
		sportobj.put("level", game.getSport().getLevel());
		
		gameobj.saveInBackground();
		sportobj.saveInBackground();
		return true;		
	}
	
	
	
	
	public ParseUser getUser(){
		return this.user;
	}
	
	public Sport getSport() {
		return this.sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	public String getLocation() {
		return this.location;
	}

	public void setPlace(String place) {
		this.location = place;
	}

	public DayAndTime getDAT() {
		return this.dat;
	}

	public void setDAT(DayAndTime dat) {
		this.dat = dat;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}
	
	
}
