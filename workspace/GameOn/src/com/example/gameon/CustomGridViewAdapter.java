package com.example.gameon;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomGridViewAdapter extends ArrayAdapter<Game> {

	 Context context;
	 int layoutResourceId;
	 ArrayList<Game> data = new ArrayList<Game>();

	public CustomGridViewAdapter(Context context, int resource,
			ArrayList<Game> objects) {
		super(context, resource, objects);

		this.context = context;
		this.layoutResourceId = resource;
		this.data = objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		Game game = data.get(position);

		System.out.println(position+ ": " + game.getSport().getLevel());
		if(game.getSport().getLevel() == "nub") {
			this.layoutResourceId = R.layout.grid_tile_nub;
		}
		else if (game.getSport().getLevel() == "avg") {
			this.layoutResourceId = R.layout.grid_tile_avg;
		}
		else {
			this.layoutResourceId = R.layout.grid_tile_pro;
		}
				
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		row = inflater.inflate(layoutResourceId, parent, false);
		
		holder = new RecordHolder();
		holder.sport = (TextView) row.findViewById(R.id.sport_textView_gridView);
		holder.DAT = (TextView) row.findViewById(R.id.DAT_textView_gridView);
		holder.location = (TextView) row.findViewById(R.id.location_textView_gridView);
		holder.gender = (TextView) row.findViewById(R.id.gender_textView_gridView);
		holder.needed = (TextView) row.findViewById(R.id.need_textView_gridView);
		row.setTag(holder);

		Format formatter = new SimpleDateFormat("HH:mm");
		
		
		holder.sport.setText(game.getSport().getName());
		holder.DAT.setText(formatter.format(game.getDAT().getTime()));
		holder.location.setText(game.getLocation());
		holder.gender.setText(game.getGender());
		holder.needed.setText(game.getSize());		
		return row;
	}
	
	static class RecordHolder {
		TextView sport;
		TextView DAT;
		TextView location;
		TextView gender;
		TextView needed;
	}
}
