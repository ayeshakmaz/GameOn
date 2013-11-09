package com.example.gameon;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;

import com.example.gameon.sports.Basketball;
import com.example.gameon.sports.Football;
import com.example.gameon.view.viewgroup.FlyOutContainer;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

	FlyOutContainer root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_main, null);
		this.setContentView(root);

		ArrayList<Game> gArr = new ArrayList<Game>();
		CustomGridViewAdapter customGridAdapter;
		Parse.initialize(this, "TpTvZ7H4ABQPG9ig5Io3lko0VcUVlOZpDiQXHZuj",
				"1p4926LkDGfpKY5PEgiJwGejMgYMUSWPtaBaDPPX");
		ParseAnalytics.trackAppOpened(getIntent());

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// do stuff with the user
		} else {
			// show the signup or login screen
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}

		addListenerOnSpinnerItemSelection();

		GridView gridview = (GridView) findViewById(R.id.gridview);

		gArr.add(new Game(currentUser, new Football("nub"), "here",
				new DayAndTime(new Date(), new Time(3, 0, 0)), "Male", "5"));
		gArr.add(new Game(currentUser, new Basketball("avg"), "here",
				new DayAndTime(new Date(), new Time(12, 0, 0)), "Female", "5"));
		gArr.add(new Game(currentUser, new Football("pro"), "here",
				new DayAndTime(new Date(), new Time(3, 0, 0)), "Male", "3"));
		gArr.add(new Game(currentUser, new Basketball("pro"), "here",
				new DayAndTime(new Date(), new Time(12, 0, 0)), "Male", "5"));
		gArr.add(new Game(currentUser, new Basketball("avg"), "here",
				new DayAndTime(new Date(), new Time(12, 0, 0)), "Male", "5"));
		gArr.add(new Game(currentUser, new Basketball("nub"), "here",
				new DayAndTime(new Date(), new Time(12, 0, 0)), "Male", "5"));

		customGridAdapter = new CustomGridViewAdapter(this,
				R.layout.grid_tile_nub, gArr);
		gridview.setAdapter(customGridAdapter);

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(MainActivity.this, "" + position,
						Toast.LENGTH_SHORT).show();
			}
		});

	}

	public void addListenerOnSpinnerItemSelection() {
		Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner2.setOnItemSelectedListener(new CustomOnItemSelectedListener());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void toggleMenu(View v) {
		this.root.toggleMenu();
	}
}
