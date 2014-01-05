package com.example.gameon.objects;

import com.example.gameon.util.DatabaseManager;

public class Game {

	private String username;
	private Sport sport;
	private String location;
	private String dat;
	private String gender;
	private int size;
	private String objID;

	public Game() {
		
	}
	
	public Game(String username, Sport sport, String location, String dat, String gender, int size) {
		this.username = username;
		this.sport = sport;
		this.location = location;
		this.gender = gender;
		this.size = size;
		this.dat = dat;

	}

	/*
	 * Add game to server
	 */
	public boolean addGameToDB(DatabaseManager dbhandler) {
		
		return dbhandler.addGame(this);
				
	}
	
	/*
	 * Delete game from server
	 */
	public boolean deleteGameFromDB(DatabaseManager dbhandler) {
		
		return dbhandler.deleteGame(this);
	}
	
	
	public String getUser(){
		return this.username;
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

	public void setLocation(String place) {
		this.location = place;
	}

	public String getDAT() {
		return this.dat;
	}

	public void setDAT(String dat) {
		this.dat = dat;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public String getID(){
		return this.objID;
	}
	
	public void setID(String id){
		this.objID = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
