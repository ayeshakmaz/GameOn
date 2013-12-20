package com.example.gameon.layouts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.example.gameon.MainActivity;
import com.example.gameon.NewMessageActivity;
import com.example.gameon.R;
import com.example.gameon.objects.Game;
import com.example.gameon.objects.Response;
import com.example.gameon.util.DatabaseManager;
import com.tekle.oss.android.animation.AnimationFactory;
import com.tekle.oss.android.animation.AnimationFactory.FlipDirection;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class GameAdapter extends ArrayAdapter<Game> {

	 private Context context;
	 private int layoutResourceId;
	 private ArrayList<Game> data = new ArrayList<Game>();
	 private ArrayList<Integer> responded = new ArrayList<Integer>();
	 private DatabaseManager dbh;
	 private String skillItem = "nub";
	 private int responseTeamSize;
	 

	public ArrayList<Game> getData() {
		return data;
	}

	public void setData(ArrayList<Game> data) {
		this.data = data;
	}

	public GameAdapter(Context context, int resource,
			ArrayList<Game> objects, DatabaseManager dbh) {
		super(context, resource, objects);

		this.context = context;
		this.layoutResourceId = resource;
		this.data = objects;
		this.dbh = dbh;
		
		ArrayList<Response> rArr = dbh.getAllResponsesForUser(dbh.getCurrentUser());
		for (Response r : rArr) {
			for (int i = 0; i < data.size(); i++) {
				if (r.getGameID().equals(data.get(i).getID())) {
					responded.add(i);
				}
			}
		}
	}
	
	@SuppressLint("NewApi")
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		RecordHolder holder = null;
		final Game game = data.get(position);
		
		if (row == null) {		
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			
			holder = new RecordHolder();
			holder.sport = (TextView) row.findViewById(R.id.menu_title_textView);
			holder.DAT = (TextView) row.findViewById(R.id.DAT_textView_gridView);
			holder.location = (TextView) row.findViewById(R.id.location_textView_gridView);
			holder.gender = (TextView) row.findViewById(R.id.gender_textView_gridView);
			holder.needed = (TextView) row.findViewById(R.id.need_textView_gridView);
			holder.gameon = (Button) row.findViewById(R.id.game_on_button);
			holder.message = (Button) row.findViewById(R.id.message_button);
			holder.teamSize = (EditText) row.findViewById(R.id.num_players_et);
			holder.user = (TextView) row.findViewById(R.id.user_textView_gridView);
			holder.skill = (Spinner) row.findViewById(R.id.skill_spinner);
			holder.overlay = (TextView) row.findViewById(R.id.response_overlay);
			
			row.setTag(holder);
		} else {
			holder = (RecordHolder) row.getTag();
		}
		
		final ViewFlipper flipper = (ViewFlipper) row.findViewById(R.id.my_view_flipper) ;
		
		Drawable background;
		
		if(game.getSport().getLevel() == "nub") {
			background = getContext().getResources().getDrawable(R.drawable.tile_bg_nub);
		}
		else if (game.getSport().getLevel() == "avg") {
			background = getContext().getResources().getDrawable(R.drawable.tile_bg_avg);
		}
		else {
			background = getContext().getResources().getDrawable(R.drawable.tile_bg_pro);
		}
		
		if (Build.VERSION.SDK_INT >= 16) {
		    View child1 = flipper.getChildAt(0);
		    View child2 = flipper.getChildAt(1);
		    child1.setBackground(background);
		    child2.setBackgroundColor(context.getApplicationContext().getResources().getColor(R.color.menu_bg));
		} else {
		    View child1 = flipper.getChildAt(0);
		    View child2 = flipper.getChildAt(1);
		    child1.setBackgroundDrawable(background);
		    child2.setBackgroundColor(context.getApplicationContext().getResources().getColor(R.color.menu_bg));
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.US);
		Date d = null;
		try {
			d = format.parse(game.getDAT());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat formatTime = new SimpleDateFormat("h:mm a", Locale.US);
		
		holder.sport.setText(game.getSport().getName());
		holder.DAT.setText(formatTime.format(d));
		holder.location.setText(game.getLocation());
		holder.gender.setText(game.getGender());
		holder.needed.setText(Integer.toString(game.getSize()));
		holder.user.setText(game.getUser());
		
		if (responded.contains(position)){
			holder.gameon.setVisibility(View.INVISIBLE);
			holder.overlay.setVisibility(View.VISIBLE);
			((RelativeLayout)holder.gameon.getParent()).invalidate();
		}
		
		holder.gameon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("dbh", "ID: " + game.getID());
				Log.d("dbh", "user: " + game.getUser());
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.US);
				Calendar cal = Calendar.getInstance();
				String time = format.format(cal.getTime());
				boolean result = dbh.addResponse(new Response(game.getUser(), 
												dbh.getCurrentUser().getUsername(),
												responseTeamSize, skillItem, time, game.getID()));
			
				if (true) {
					v.setVisibility(View.INVISIBLE);
					RelativeLayout rel = ((RelativeLayout)((ViewFlipper)v.getParent().getParent()).getChildAt(0));
					rel.getChildAt(rel.getChildCount()-1).setVisibility(View.VISIBLE);
					((RelativeLayout)v.getParent()).invalidate();
					Toast.makeText(getContext(), "Response sent", Toast.LENGTH_LONG).show();
				}
				else {
					Toast.makeText(getContext(), "Response not sent", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		holder.message.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, NewMessageActivity.class);
				intent.putExtra("message_to_user", game.getUser());
				context.startActivity(intent);
			}
		});
				
	    flipper.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MainActivity.hideSoftKeyboard((Activity)context);
				AnimationFactory.flipTransition(flipper, FlipDirection.LEFT_RIGHT);
			}
		});
	    
	    holder.skill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
	            skillItem = (String) parent.getItemAtPosition(pos);
	        }
	        public void onNothingSelected(AdapterView<?> parent) {
	        }
	    });
	    
	    holder.teamSize.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int size, int arg2, int arg3) {
				responseTeamSize = Integer.parseInt(s.toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});
	    
		return row;
	}
	
	static class RecordHolder {
		TextView overlay;
		TextView sport;
		TextView DAT;
		TextView location;
		TextView gender;
		TextView needed;
		TextView user;
		EditText teamSize;
		Spinner skill;
		Button gameon;
		Button message;
	}
}
