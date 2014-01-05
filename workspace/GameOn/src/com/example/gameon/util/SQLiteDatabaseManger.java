package com.example.gameon.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import com.example.gameon.objects.Game;
import com.example.gameon.objects.Sport;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDatabaseManger extends SQLiteOpenHelper{
	
	private static final String DB_NAME = "gameOn";
	private static final int DB_Version = 1;
	private static final String GAME_TABLE = "games";
	private static final String MESSAGE_TABLE = "messages";
	private static final String RESPONSE_TABLE = "responses";
	
	
	//GAME_TABLE's columns
	private static final String KEY_ID = "id";
	private static final String PARSE_ID = "parse_id";
	private static final String SPORT = "sport_name";
	private static final String SPORT_LEVEL = "sport_level";
	private static final String LOCATION = "location";
	private static final String DATE = "date";
	private static final String GENDER = "gender";
	private static final String TEAMSIZE = "team_size";
	private static final String USER = "user";
	
	//MESSAGE_TABLE's columns
	private static final String FROM_USER = "from_user";
	private static final String TO_USER = "to_user";
	private static final String TIME = "time";
	private static final String MESSAGE = "message";
	
	//RESPONSE_TABLE's columns
	private static final String GAME_ID = "game_id";
	
	public SQLiteDatabaseManger(Context context) {
		super(context, DB_NAME, null, DB_Version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_GAME_TABLE = "CREATE TABLE IF NOT EXISTS " + GAME_TABLE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + PARSE_ID + " TEXT,"
				+ SPORT + " TEXT," + SPORT_LEVEL + " TEXT," + LOCATION + " TEXT,"
				+ DATE + " TEXT," + GENDER + " TEXT," + TEAMSIZE + " INTEGER,"
				+ USER + " TEXT" + ")";
		
		String CREATE_MESSAGE_TABLE = "CREATE TABLE IF NOT EXISTS " + GAME_TABLE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + PARSE_ID + " TEXT,"
				+ TO_USER + " TEXT," + FROM_USER + " TEXT," + TIME + " TEXT,"
				+ MESSAGE + " TEXT" + ")";
		
		String CREATE_RESPONSE_TABLE = "CREATE TABLE IF NOT EXISTS " + GAME_TABLE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + PARSE_ID + " TEXT,"
				+ TO_USER + " TEXT," + FROM_USER + " TEXT," + TIME + " TEXT,"
				+ GAME_ID + " TEXT," + SPORT_LEVEL + " TEXT," + TEAMSIZE + " INTEGER"
				+ ")";
		
		db.execSQL(CREATE_GAME_TABLE);
		db.execSQL(CREATE_MESSAGE_TABLE);
		db.execSQL(CREATE_RESPONSE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GAME_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + MESSAGE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RESPONSE_TABLE);
	}
	
	//CRUD GAME_TABLE
	public boolean addGame(Game g) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(PARSE_ID, g.getID());
		values.put(SPORT, g.getSport().getName());
		values.put(SPORT_LEVEL, g.getSport().getLevel());
		values.put(LOCATION, g.getLocation());
		values.put(DATE, g.getDAT());
		values.put(GENDER, g.getGender());
		values.put(TEAMSIZE, g.getSize());
		values.put(USER, g.getUser());
		
		long result = db.insert(GAME_TABLE, null, values);
		
		if (result != -1 ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public ArrayList<Game> getAllGames() {
        ArrayList<Game> gameList = new ArrayList<Game>();
        String selectQuery = "SELECT  * FROM " + GAME_TABLE;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
        	do {
        		Game addGame = new Game();
        		addGame.setID(cursor.getString(1));
        		addGame.setSport(new Sport(cursor.getString(2), cursor.getString(3)));
        		addGame.setLocation(cursor.getString(4));
        		addGame.setDAT(cursor.getString(5));
        		addGame.setGender(cursor.getString(6));
        		addGame.setSize(cursor.getInt(7));
        		addGame.setUsername(cursor.getString(8));
        		gameList.add(addGame);
        	} while (cursor.moveToNext());
        }
        return gameList;
	}
	
	private void removeGameById(String gameid) {
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(GAME_TABLE, PARSE_ID + " = ?",
	            new String[] { gameid });
	    db.close();		
	}
	
	public void update_from_parse(DatabaseManager dbh) {
		ArrayList<Game> games_from_parse = dbh.getAllGames();
		ArrayList<Game> games_from_sql = this.getAllGames();
		
		if (games_from_sql.size() == 0) {
			for (Game g: games_from_parse) {
				this.addGame(g);
			}
		}
		else {
			Hashtable<Integer, String> match = new Hashtable<Integer, String>();
			
			for (Game g: games_from_sql) {
				match.put(g.getID().hashCode(), g.getID());
				Log.d("sqldbh", "hash: " + Integer.toString(g.getID().hashCode())
								+ " gameid: " + g.getID());
			}
			
			for (Game g: games_from_parse) {
				String result = match.get(g.getID().hashCode());
				Log.d("sqldbh", "parsegameid: " + Integer.toString(g.getID().hashCode())
						+ " match: " + !(result.equals(g.getID())));
				if ( !(result.equals(g.getID())) ) {
					this.addGame(g);
				}
				match.remove(g.getID().hashCode());
			}
			Enumeration<String> enumVal = match.elements();
			while (enumVal.hasMoreElements()) {
				String gameid = enumVal.nextElement();
				Log.d("sqldbh", "removegameid: " + gameid);
				if (gameid == null) { 
					match.remove(gameid);
				}
			}
		}
	}
}
