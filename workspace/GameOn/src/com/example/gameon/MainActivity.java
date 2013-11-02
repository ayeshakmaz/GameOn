package com.example.gameon;

import android.os.Bundle;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
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
        
        addListenerOnSpinnerItemSelection();
    	
    	GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
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
    
}
