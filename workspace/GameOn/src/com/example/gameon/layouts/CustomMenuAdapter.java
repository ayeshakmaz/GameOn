package com.example.gameon.layouts;

import java.util.ArrayList;

import com.example.gameon.R;
import com.example.gameon.R.id;
import com.example.gameon.objects.MenuItem;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomMenuAdapter extends ArrayAdapter<MenuItem>{

	ArrayList<MenuItem> objects;
	Context context;
	int layoutResId;
	int textColor = Color.WHITE;
	
	public CustomMenuAdapter(Context context, int resource,
			ArrayList<MenuItem> objects) {
		super(context, resource, resource, objects);
		this.objects = objects;
		this.context = context;
		this.layoutResId = resource;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		MenuItem item = objects.get(position);
		
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResId, parent, false);
		
		holder = new RecordHolder();
		holder.icon = (ImageView) row.findViewById(R.id.menu_item_icon);
		holder.title = (TextView) row.findViewById(R.id.menu_item_title);
		row.setTag(holder);
		
		holder.icon.setImageDrawable(item.getImage());
		holder.title.setText(item.getTitle());
		holder.title.setTextColor(textColor);
		return row;
	}

	public int getTextColor() {
		return textColor;
	}
	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}


	static class RecordHolder {
		ImageView icon;
		TextView title;
	}
}
