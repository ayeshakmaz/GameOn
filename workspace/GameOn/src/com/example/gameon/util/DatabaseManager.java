package com.example.gameon.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.gameon.objects.Game;
import com.example.gameon.objects.Message;
import com.example.gameon.objects.Response;
import com.example.gameon.sports.Basketball;
import com.example.gameon.sports.Football;
import com.example.gameon.sports.Sport;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class DatabaseManager {

	public DatabaseManager(Context c, Intent i) {

		Parse.initialize(c, "TpTvZ7H4ABQPG9ig5Io3lko0VcUVlOZpDiQXHZuj",
				"1p4926LkDGfpKY5PEgiJwGejMgYMUSWPtaBaDPPX");
		ParseAnalytics.trackAppOpened(i);
	}

	public boolean addGame(Game g) {

		// Populate ParseObject with values

		ParseObject gameobj = new ParseObject("game");
		gameobj.put("user", g.getUser());
		gameobj.put("sport_name", g.getSport().getName());
		gameobj.put("sport_level", g.getSport().getLevel());
		gameobj.put("location", g.getLocation());
		gameobj.put("date", g.getDAT());
		gameobj.put("gender", g.getGender());
		gameobj.put("size", g.getSize());

		// Add to 'game' table
		try {
			gameobj.save();
			Log.d("dbh", "Game Saved!");
		} catch (ParseException e) {
			e.printStackTrace();
			Log.d("dbh", "Game Not Saved!");
			Log.d("dbh", e.getMessage());
			return false;
		}

		// Save objectID in Game object
		g.setID(gameobj.getObjectId());
		return true;
	}

	public boolean deleteGame(Game g) {

		// Delete game using objectID
		try {
			ParseObject.createWithoutData("game", g.getID()).delete();
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public ArrayList<Game> getAllGames() {

		ParseQuery<ParseObject> query = ParseQuery.getQuery("game");
		List<ParseObject> results;
		try {
			results = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

		ArrayList<Game> gArr = new ArrayList<Game>();
		Game addGame;
		for (ParseObject o : results) {
			addGame = new Game((String) o.get("user"),
					getSportObject((String) o.get("sport_name"),
							(String) o.get("sport_level")),
					(String) o.get("location"), (String) o.get("date"),
					(String) o.get("gender"), (Integer) o.get("size"));
			addGame.setID(o.getObjectId());
			if (addGame != null) {
				gArr.add(addGame);
			}
		}

		return gArr;
	}

	private Sport getSportObject(String name, String level) {
		if (name.equals("Football")) {
			return new Football(level);
		} else if (name.equals("Basketball")) {
			return new Basketball(level);
		}
		return null;
	}

	public ParseUser getCurrentUser() {
		return ParseUser.getCurrentUser();
	}

	public boolean addMessage(Message m) {
		// Populate ParseObject with values

		ParseObject gameobj = new ParseObject("messages");
		gameobj.put("to_user", m.getToUsername());
		gameobj.put("from_user", m.getFromUsername());
		gameobj.put("message", m.getMessage());
		gameobj.put("time", m.getTime());

		// Add to 'game' table
		try {
			gameobj.save();
			Log.d("dbh", "Message Saved!");
		} catch (ParseException e) {
			e.printStackTrace();
			Log.d("dbh", "Message Not Saved!");
			Log.d("dbh", e.getMessage());
			return false;
		}

		// Save objectID in Game object
		m.setID(gameobj.getObjectId());
		return true;
	}

	public ArrayList<Message> getAllMessagesForUser(ParseUser user) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("messages");
		query.whereEqualTo("to_user", user.getUsername());
		List<ParseObject> results;
		try {
			results = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

		ArrayList<Message> mArr = new ArrayList<Message>();
		Message addMessage;
		for (ParseObject o : results) {
			addMessage = new Message((String) o.get("to_user"),
					(String) o.get("from_user"), (String) o.get("message"),
					(String) o.get("time"));
			if (addMessage != null) {
				mArr.add(addMessage);
			}
		}
		return mArr;
	}
	public boolean addResponse(Response r) {
		// Populate ParseObject with values

		ParseObject gameobj = new ParseObject("responses");
		gameobj.put("to_user", r.getToUser());
		gameobj.put("from_user", r.getFromUser());
		gameobj.put("teamSize", r.getTeamSize());
		gameobj.put("time", r.getTime());
		gameobj.put("skill", r.getSkill());
		gameobj.put("game_id", r.getGameID());

		// Add to 'game' table
		try {
			gameobj.save();
			Log.d("dbh", "Message Saved!");
		} catch (ParseException e) {
			e.printStackTrace();
			Log.d("dbh", "Message Not Saved!");
			Log.d("dbh", e.getMessage());
			return false;
		}

		// Save objectID in Game object
		r.setID(gameobj.getObjectId());
		return true;
	}

	public ArrayList<Response> getAllResponsesForUser(ParseUser currentUser) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("responses");
		query.whereEqualTo("from_user", currentUser.getUsername());
		List<ParseObject> results;
		try {
			results = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

		ArrayList<Response> rArr = new ArrayList<Response>();
		Response addResponse;
		for (ParseObject o : results) {
			addResponse = new Response((String) o.get("to_user"), (String) o.get("from_user"),
									   (Integer) o.get("teamSize"), (String) o.get("skill"),
									   (String) o.get("time"), (String) o.get("game_id"));
			if (addResponse != null) {
				rArr.add(addResponse);
			}
		}
		return rArr;
	}

	public Game getGameByID(String gameID) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("game");
		ParseObject o = null;
		try {
			o = query.get(gameID);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		if (o != null){
			Game g = new Game((String) o.get("user"),
						getSportObject((String) o.get("sport_name"),
								(String) o.get("sport_level")),
						(String) o.get("location"), (String) o.get("date"),
						(String) o.get("gender"), (Integer) o.get("size"));
			g.setID(o.getObjectId());
			return g;
		}
		else {
			return null;
		}
	}
}
