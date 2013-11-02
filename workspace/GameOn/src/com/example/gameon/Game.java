package com.example.gameon;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class Game {

	private ParseUser user;
	private Sport sport;
	private String location;
	private DayAndTime dat;
	private String gender;
	private String size;
	private String objID;

	public Game(ParseUser user, Sport sport, String location, DayAndTime dat, String gender, String size) {
		this.user = user;
		this.sport = sport;
		this.location = location;
		this.dat = dat;
		this.gender = gender;
		this.size = size;
	}
	
	
	/*
	 * Add game to server
	 */
	public boolean addGame(Game game) {
		
		//Populate ParseObject with values
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
		
		
		//Add to 'game' table
		try {
			gameobj.save();
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
		//Add to 'sport' table
		try {
			sportobj.save();
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
		//Save objectID in Game object
		game.setID(gameobj.getObjectId());
		game.getSport().setID(sportobj.getObjectId());
		return true;		
	}
	
	/*
	 * Delete game from server
	 */
	public boolean deleteGame(Game game) {
		
		//Delete game using objectID
		try {
			ParseObject.createWithoutData("game", game.getID()).delete();
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			ParseObject.createWithoutData("sport", game.getSport().getID()).delete();
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		
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
	
	public String getID(){
		return this.objID;
	}
	
	public void setID(String id){
		this.objID = id;
	}
	
}
