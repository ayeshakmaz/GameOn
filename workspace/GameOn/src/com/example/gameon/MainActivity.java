package com.example.gameon;

import android.os.Bundle;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parse.initialize(this, "TpTvZ7H4ABQPG9ig5Io3lko0VcUVlOZpDiQXHZuj", "1p4926LkDGfpKY5PEgiJwGejMgYMUSWPtaBaDPPX"); 
        ParseAnalytics.trackAppOpened(getIntent());
        
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
          // do stuff with the user
        } else {
          // show the signup or login screen
        	Intent intent = new Intent(this, LoginActivity.class);
    		startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
