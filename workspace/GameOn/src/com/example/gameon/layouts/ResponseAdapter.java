package com.example.gameon.layouts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


import com.example.gameon.R;
import com.example.gameon.objects.Game;
import com.example.gameon.objects.Response;
import com.example.gameon.util.DatabaseManager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class ResponseAdapter extends ArrayAdapter<Response>{

	private ArrayList<Response> objects;
	private ArrayList<Game> games;
	private Context context;
	private int layoutResId;
	private int textColor = Color.BLACK;
	private DatabaseManager dbh;
	
	public ResponseAdapter(Context context, int resource,
			ArrayList<Response> objects, DatabaseManager dbh) {
		super(context, resource, resource, objects);
		this.objects = objects;
		this.context = context;
		this.layoutResId = resource;
		this.dbh = dbh;
		games = dbh.getAllGames();
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		Response item = objects.get(position);
		Game g = getGameByID(item.getGameID());
		Log.d("dbh", g.toString());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.US);
		Date d = null;
		try {
			d = format.parse(g.getDAT());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat formatTime = new SimpleDateFormat("h:mm a", Locale.US);
		String time = formatTime.format(d);
		
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResId, parent, false);
			
			holder = new RecordHolder();
			holder.teamsize = (TextView) row.findViewById(R.id.response_teamsize_tv);
			holder.notification = (TextView) row.findViewById(R.id.response_notification_tv);
			holder.skill = (TextView) row.findViewById(R.id.response_skill_tv);
			holder.accept = (Button) row.findViewById(R.id.response_accept_button);
			holder.deny = (Button) row.findViewById(R.id.response_deny_button);
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
			holder.teamsize.setText(Integer.toString(item.getTeamSize()));
			holder.skill.setText(item.getSkill()+"'s");
			holder.skill.setTextColor(textColor);
			holder.notification.setText(item.getFromUser() + 
										" wants to play " + g.getSport().getName() +
										" @ " + time + " at " + g.getLocation());
			holder.notification.setTextColor(textColor);

		return row;
	}

	private Game getGameByID(String gameID) {
		for (Game g: games) {
			if (g.getID().equals(gameID)) {
				return g;
			}
		}
		return null;
	}

	public int getTextColor() {
		return textColor;
	}
	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	static class RecordHolder {
		TextView teamsize;
		TextView notification;
		TextView skill;
		Button accept;
		Button deny;
	}
}