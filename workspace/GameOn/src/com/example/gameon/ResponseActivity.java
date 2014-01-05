package com.example.gameon;

import java.util.ArrayList;

import com.example.gameon.animation.FlyOutContainer;
import com.example.gameon.layouts.MenuAdapter;
import com.example.gameon.layouts.ResponseAdapter;
import com.example.gameon.objects.MenuItem;
import com.example.gameon.objects.Response;
import com.example.gameon.util.DatabaseManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ResponseActivity extends Activity {
	
	FlyOutContainer root;
	private ArrayList<Response> rArr;
	private DatabaseManager dbh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_response, null);
		this.setContentView(root);
		
		//************************** Flyout container START **********************************

				ArrayList<MenuItem> m1Arr = new ArrayList<MenuItem>();
				final ArrayList<MenuItem> m2Arr = new ArrayList<MenuItem>();
				
				MenuAdapter customListAdapter1;
				MenuAdapter customListAdapter2;
				
				ListView primaryListView = (ListView) findViewById(R.id.menu_primary_list);
				
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
				
				dbh = new DatabaseManager(this, getIntent());
				
				rArr = dbh.getAllResponsesForUser(dbh.getCurrentUser());
						
				ListView responseList = (ListView) findViewById(R.id.response_listView);
				
				ResponseAdapter messageAdapter = new ResponseAdapter(this,
						R.layout.response_item, rArr, dbh);
				responseList.setAdapter(messageAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification, menu);
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
	
	public static void hideSoftKeyboard(final Activity activity) {
	    InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
	    inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}
	
}
