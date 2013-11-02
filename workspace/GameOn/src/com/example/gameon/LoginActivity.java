package com.example.gameon;

import com.parse.LogInCallback;
import com.parse.ParseUser;
import com.parse.ParseException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText inputUsername, inputPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void loginOnClick (View v){
		inputUsername = (EditText) findViewById(R.id.username_editText);
		inputPassword = (EditText) findViewById(R.id.password_editText);
		
		final String username = inputUsername.getText().toString();
		final String password = inputPassword.getText().toString();
		
		ParseUser.logInInBackground(username, password, new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			      // Hooray! The user is logged in.
			    	Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(intent);
			    } else {
			      // Signup failed. Look at the ParseException to see what happened.
			    	Toast.makeText( getApplicationContext(), "Invalid Login", Toast.LENGTH_LONG).show();
			    }
			  }
			});

	}
	
	public void signupOnClick (View v){
    	Intent intent = new Intent(this, SignupActivity.class);
		startActivity(intent);
	}

}
