package com.example.gameon.objects;

import android.app.Activity;
import android.graphics.drawable.Drawable;

public class MenuItem {

	private String title;
	private Class<?> activity;
	private Drawable image;
	
	public Drawable getImage() {
		return image;
	}
	
	public MenuItem (String t, Class<?> a, Drawable i) {
		this.title = t;
		this.activity = a;
		this.image = i;
	}

	public void setImage(Drawable image) {
		this.image = image;
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
