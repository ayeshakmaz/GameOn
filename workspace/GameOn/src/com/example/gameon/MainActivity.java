package com.example.gameon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;
import android.os.Handler;

import com.example.gameon.layouts.GameAdapter;
import com.example.gameon.layouts.MenuAdapter;
import com.example.gameon.layouts.view.viewgroup.FlyOutContainer;
import com.example.gameon.objects.Game;
import com.example.gameon.objects.MenuItem;
import com.example.gameon.sports.Basketball;
import com.example.gameon.sports.Football;
import com.example.gameon.util.DatabaseManager;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	FlyOutContainer root;
	private ArrayList<Game> gArr;
	private GameAdapter customGridAdapter;
	private String searchItemSelected = "Sport";
	DatabaseManager dbhandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_main, null);
		this.setContentView(root);

		gArr = new ArrayList<Game>();

		
		dbhandler = new DatabaseManager(this, getIntent());

		ParseUser currentUser = dbhandler.getCurrentUser();

		if (currentUser == null) {
			// show the signup or login screen
			Log.d("MainAct", "null");
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
			finish();
			return;
		}

		addListenerOnSpinnerItemSelection();

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gArr = dbhandler.getAllGames();

		customGridAdapter = new GameAdapter(this, R.layout.game_tile, gArr, dbhandler);
		
		gridview.setAdapter(customGridAdapter);
		
		//************************** Flyout container START **********************************

		ArrayList<MenuItem> m1Arr = new ArrayList<MenuItem>();
		final ArrayList<MenuItem> m2Arr = new ArrayList<MenuItem>();
		
		MenuAdapter customListAdapter1;
		MenuAdapter customListAdapter2;
		
		ListView primaryListView = (ListView) findViewById(R.id.menu_primary_list);
		EditText searchText = (EditText) findViewById(R.id.search_input);
		
		m1Arr.add(new MenuItem("Add Game", MainActivity.class, getResources().getDrawable(R.drawable.compass)));
		
		customListAdapter1 = new MenuAdapter(this, R.layout.menu_list_item, m1Arr);
		primaryListView.setAdapter(customListAdapter1);
		primaryListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				// TODO Auto-generated method stub
				
			}
		});
		
		ListView secondaryListView = (ListView) findViewById(R.id.menu_secondary_list);
		
		m2Arr.add(new MenuItem("Games", MainActivity.class, getResources().getDrawable(R.drawable.compass)));
		m2Arr.add(new MenuItem("Messages", MessageBoardActivity.class, getResources().getDrawable(R.drawable.compass)));
		m2Arr.add(new MenuItem("Responses", ResponseActivity.class, getResources().getDrawable(R.drawable.compass)));
		
		customListAdapter2 = new MenuAdapter(this, R.layout.menu_list_item, m2Arr);
		customListAdapter2.setTextColor(Color.BLACK);
		secondaryListView.setAdapter(customListAdapter2);
		secondaryListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				MenuItem m = m2Arr.get(position);
				Intent intent = new Intent(getApplicationContext(), m.getActivity());
				startActivity(intent);
				finish();			
			}
		});
		
		//************************** Flyout container END **********************************

		searchText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int size, int arg2, int arg3) {
				Log.d("TextChanged", s.toString().concat(" ").concat(Integer.toString(size)).concat(" ").concat(Integer.toString(arg2)).concat(" ").concat(Integer.toString(arg3)));
				Log.d("TextChanged", searchItemSelected);
				updateGridView(s);
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
		ListView lv = (ListView) findViewById(R.id.menu_setting_list); 
		lv.setVisibility(View.INVISIBLE);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				if (parent.getItemAtPosition(position).toString().equals("Log Out")){
					ParseUser.getCurrentUser().logOut();
					Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
					startActivity(intent);
				}
				
			}
		});
	}

	public void addListenerOnSpinnerItemSelection() {
		Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long arg3) {
				searchItemSelected = parent.getItemAtPosition(position).toString();
				EditText et = (EditText) findViewById(R.id.search_input);
				updateGridView(et.getText().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
//		Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
//		spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void settingOnClick(View v) {
		ListView lv = (ListView) findViewById(R.id.menu_setting_list); 
		if(lv.getVisibility() == View.VISIBLE) {
			lv.setVisibility(View.INVISIBLE);
		} else {
			lv.setVisibility(View.VISIBLE);
		}
	}
	public void toggleMenu(View v) {
		this.root.toggleMenu();
		hideSoftKeyboard(this);
	}
	
	public void updateGridView(CharSequence s) {
		GridView gridview = (GridView) findViewById(R.id.gridview);
		
		ArrayList<Game> newArr = new ArrayList<Game>();
		
		if (searchItemSelected.equals("Location")){
			for (Game g: gArr){
				if(g.getLocation().toLowerCase(Locale.US).contains(s.toString().toLowerCase(Locale.US))){
					newArr.add(g);
				}
			}
		}
		else if (searchItemSelected.equals("Sport")){
			for (Game g: gArr){
				if(	g.getSport().getName().length() >= s.length() &&
					g.getSport().getName().toLowerCase(Locale.US).substring(0, s.length()).equals(s.toString().toLowerCase(Locale.US))){
					newArr.add(g);
				}
			}
		}
		else if (searchItemSelected.equals("Gender")){
			for (Game g: gArr){
				if( g.getGender().length() >= s.length() &&
					g.getGender().toLowerCase(Locale.US).substring(0, s.length()).equals(s.toString().toLowerCase(Locale.US))){
					newArr.add(g);
				}
			}
		}
		if (newArr.size() != 0) {
			findViewById(R.id.gridview).setVisibility(View.VISIBLE);
			customGridAdapter.notifyDataSetChanged();
			gridview.setAdapter(new GameAdapter(this, R.layout.game_tile, newArr, dbhandler));
		} else {
			findViewById(R.id.gridview).setVisibility(View.INVISIBLE);
		}
		Log.d("TextChanged", "updateView");
	}
	
	public static void hideSoftKeyboard(final Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
	public void messageOnClick(View v){
		Toast.makeText(this, "Message Clicked", Toast.LENGTH_LONG).show();
		v.setVisibility(View.INVISIBLE);
		((RelativeLayout)v.getParent()).invalidate();
		Log.d("message", v.getParent().toString());
	}
}
