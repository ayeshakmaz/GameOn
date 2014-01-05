package com.example.gameon.objects;

public class Sport {
	
	private String name;
	private String level;
	private String id;
	
	public Sport(String name, String level) {
		this.level = level;
		this.name = name;
	}	
	
	public Sport(String name, String level, String id) {
		this.id = id;
		this.level = level;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
