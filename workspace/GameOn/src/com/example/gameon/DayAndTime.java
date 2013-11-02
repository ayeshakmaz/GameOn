package com.example.gameon;

import java.sql.Time;
import java.util.Date;

public class DayAndTime {
	Date date;
	Time time;
	
	public DayAndTime(Date date, Time time) {
		this.date = date;
		this.time = time;
	
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}
}
