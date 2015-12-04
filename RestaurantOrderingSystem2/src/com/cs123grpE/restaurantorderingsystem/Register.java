package com.cs123grpE.restaurantorderingsystem;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.content.*;
import com.parse.*;

public class Register extends Activity {
	SharedPreferences list;
	ArrayList<User> array;
	ParseUser currUser;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		//Parse.enableLocalDatastore(this);
        
        //Parse.initialize(this, "x2JAmf97XCaZbCJDTgxqGAUoLqcpzTxx48xlj4m4", "d3EwdWEYXfB93SH5jG8TWuwteH5d2yS94X1oegNv");
        
        currUser = new ParseUser();

		// load SharedPreferences, if it exists. else, make empty arraylist
		list = getSharedPreferences("Users", Context.MODE_PRIVATE);
		if (!list.contains("nutzlich")) {
			array = new ArrayList<User>();
		} else {
			try {
				array = convert(list.getString("nutzlich", null));
			} catch (Exception e) {}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public ArrayList<User> convert (String json) throws Exception {
    	ObjectMapper mapper = new ObjectMapper();		
    	ArrayList<User> stuff = mapper.readValue(json, 
    				new TypeReference<ArrayList<User>>(){});
    	return stuff;
    }
	
	public void sayYes (View v) throws Exception {
		EditText name = (EditText) findViewById (R.id.txtResto);
		EditText user = (EditText) findViewById (R.id.txtUser2);
		EditText pass = (EditText) findViewById (R.id.txtPass2);
		
		String username = user.getText().toString().trim();
    	String password = pass.getText().toString().trim();
    	String restaurant = name.getText().toString().trim();
		
		if (restaurant.length() == 0 ||	username.length() == 0 || password.length() == 0) {
			Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
		}
		else {
			ParseUser currentUser = new ParseUser();
			currentUser.setUsername(username);
			currentUser.setPassword(password);
			currentUser.signUpInBackground(new SignUpCallback() {
			  public void done(ParseException e) {
			    if (e == null) {
			      // Hooray! Let them use the app now.
			    } else {
			      // Sign up didn't succeed. Look at the ParseException
			      // to figure out what went wrong
			    }
			  }
			});
			
			List<ParseUser> al = null;
	        ParseObject resto = Helper.findObject("Restaurant", "rest_name", restaurant);
	   
	        if(resto==null) {
	        	resto = Helper.addRestaurant(restaurant);
	        	al = new ArrayList<ParseUser>();
	        	resto.put("rest_name", restaurant);
	        }
	        else
	        	al = resto.getList("users");
	        resto.saveInBackground();
	        
	        
	        currentUser.put("restaurant", resto);
		    currentUser.saveInBackground(); 
    		//al.add(currentUser);
    		//resto.put("users", al);
    		
	        
			/*SharedPreferences.Editor editor = list.edit();
			editor.putString("nutzlich", json);
			editor.commit();*/
			Intent i = new Intent (this, Login.class);
			startActivity(i);
			finish();
		}
	}
	
	public void sayNo (View v) {
		Intent i = new Intent (this, Login.class);
		startActivity(i);
		finish();
	}
}