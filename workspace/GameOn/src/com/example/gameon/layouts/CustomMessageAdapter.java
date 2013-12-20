package com.example.gameon.layouts;

import java.util.ArrayList;

import com.example.gameon.R;
import com.example.gameon.R.id;
import com.example.gameon.layouts.CustomGameAdapter.RecordHolder;
import com.example.gameon.objects.Message;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomMessageAdapter extends ArrayAdapter<Message>{

	ArrayList<Message> objects;
	Context context;
	int layoutResId;
	int textColor = Color.BLACK;
	
	public CustomMessageAdapter(Context context, int resource,
			ArrayList<Message> objects) {
		super(context, resource, resource, objects);
		this.objects = objects;
		this.context = context;
		this.layoutResId = resource;
	}
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		Message item = objects.get(position);
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResId, parent, false);
			
			holder = new RecordHolder();
			holder.fromUser = (TextView) row.findViewById(R.id.message_item_fromUser);
			holder.time = (TextView) row.findViewById(R.id.message_item_time);
			holder.message = (TextView) row.findViewById(R.id.message_item_message);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		
		holder.fromUser.setText(item.getFromUsername());
		holder.fromUser.setTextColor(textColor);
		holder.time.setText(item.getTime());
		holder.time.setTextColor(textColor);
		holder.message.setText(item.getMessage());
		holder.message.setTextColor(textColor);
		return row;
	}

	public int getTextColor() {
		return textColor;
	}
	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}


	static class RecordHolder {
		TextView message;
		TextView time;
		TextView fromUser;
	}
}