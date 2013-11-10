package com.example.gameon;

import android.app.Activity;
import android.graphics.drawable.Drawable;

public class MenuItem {

	private String title;
	private Class<?> activity;
	private Drawable image;
	
	public Drawable getImage() {
		return image;
	}

	public void setImage(Drawable image) {
		this.image = image;
	}

	public MenuItem (String t, Class<?> a) {
		this.title = t;
		this.activity = a;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Class<?> getActivity() {
		return activity;
	}

	public void setActivity(Class<?> activity) {
		this.activity = activity;
	}
}
