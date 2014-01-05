package com.example.gameon;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.gameon.animation.FlyOutContainer;
import com.example.gameon.objects.Message;
import com.example.gameon.util.DatabaseManager;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

public class NewMessageActivity extends Activity {

	FlyOutContainer root;
	private String toUser;
	private DatabaseManager dbh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.root = (FlyOutContainer) this.getLayoutInflater().inflate(
				R.layout.activity_new_message, null);
		this.setContentView(root);
		Bundle extra = getIntent().getExtras();
		toUser = extra.getString("message_to_user");
		
		dbh = new DatabaseManager(this, getIntent());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_message, menu);
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
	public void sendOnClick(View v) {
		
		EditText message_et = (EditText) findViewById(R.id.new_message_editText);
		String message = message_et.getText().toString();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.US);
		Calendar cal = Calendar.getInstance();
		String time = format.format(cal.getTime());		
		Message m = new Message( toUser, dbh.getCurrentUser().getUsername(), message, time);
		dbh.addMessage(m);
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
