package com.example.gameon;

import java.util.ArrayList;
import android.content.Context;
import android.widget.ArrayAdapter;

public class CustomListViewAdapter extends ArrayAdapter<MenuItem>{

	public CustomListViewAdapter(Context context, int resource,
			int textViewResourceId, ArrayList<MenuItem> objects) {
		super(context, resource, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

}
