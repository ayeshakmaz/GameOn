package com.example.gameon;

public class Game {

	private Sport sport;
	private String place;
	private DayAndTime dat;
	private String gender;
	private String size;
	

	public Game(Sport sport, String place, DayAndTime dat, String gender, String size) {
		this.sport = sport;
		this.place = place;
		this.dat = dat;
		this.gender = gender;
		this.size = size;
	}
	
	public Sport getSport() {
		return sport;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public DayAndTime getDat() {
		return dat;
	}

	public void setDat(DayAndTime dat) {
		this.dat = dat;
	}

	public String getGender() {
		return gender;
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
