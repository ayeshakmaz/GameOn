package com.example.gameon.sports;

import com.example.gameon.Sport;

public class Football implements Sport {

	private String name = "football";
	private String level;
	private String id;
	
	public Football(String level) {
		this.level = level;
	}
	

	public String getName() {
		return this.name;
	}


	public String getLevel() {
		return this.level;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getID(){
		return this.id;
	}
	
	public void setID(String id){
		this.id = id;
	}
}
