package com.example.gameon;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {
	
	private EditText inputUsername, inputPassword, inputEmail;
	public boolean signedup = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}
	
	public void signupOnClick (View v){
				
		inputUsername = (EditText) findViewById(R.id.username_editText_signup);
		inputPassword = (EditText) findViewById(R.id.password_editText_signup);
		inputEmail = (EditText) findViewById(R.id.email_editText_signup);
		
		final String username = inputUsername.getText().toString();
		final String password = inputPassword.getText().toString();
		final String email = inputEmail.getText().toString();
		
		if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
	    	Toast.makeText( getApplicationContext(), "Fill in all feilds", Toast.LENGTH_LONG).show();
	    	return;
		}
		
		ParseUser user = new ParseUser();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		
		user.signUpInBackground(new SignUpCallback() {
			  public void done(ParseException e) {
			    if (e == null) {
			      // Hooray! Let them use the app now.
			    	Toast.makeText( getApplicationContext(), "Signed up, you're awesome", Toast.LENGTH_LONG).show();
			    	Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(intent);			    
					} else {
			      // Sign up didn't succeed. Look at the ParseException
			      // to figure out what went wrong
			    	signedup = false;
			    	Toast.makeText( getApplicationContext(), "Sign up failed", Toast.LENGTH_LONG).show();
			    }
			  }
		});
	}
}
