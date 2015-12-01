package com.cs123grpE.restaurantorderingsystem;

import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import android.content.*;
import com.parse.*;

public class Login extends Activity {
	SharedPreferences list;
	ArrayList<User> array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        /*
        ParseObject crispyPata = new ParseObject("Menu_Item");
        crispyPata.put("item_name", "Crispy Pata");
        crispyPata.put("item_price", 100.00);
        crispyPata.saveInBackground();
        
        ParseObject aduba = new ParseObject("Menu_Item");
        aduba.put("item_name", "Aduba");
        aduba.put("item_price", 103.00);
        aduba.saveInBackground();

        ParseObject pataatim = new ParseObject("Menu_Item");
        pataatim.put("item_name", "Pataatim");
        pataatim.put("item_price", 102.00);
        pataatim.saveInBackground();

        ParseObject sinigang = new ParseObject("Menu_Item");
        sinigang.put("item_name", "Sinigang");
        sinigang.put("item_price", 101.00);
        sinigang.saveInBackground();

        ParseObject cns = new ParseObject("Menu_Item");
        cns.put("item_name", "Chicken Noodle Soup");
        cns.put("item_price", 40.00);
        cns.saveInBackground();
        
        ParseObject birdsnest = new ParseObject("Menu_Item");
        birdsnest.put("item_name", "Bird's Nest Soup");
        birdsnest.put("item_price", 40.00);
        birdsnest.saveInBackground();

        ParseObject paksiw = new ParseObject("Menu_Item");
        paksiw.put("item_name", "Paksiw");
        paksiw.put("item_price", 80.00);
        paksiw.saveInBackground();
        */
        
        
        // check for saved preferences, populate the fields
        list = getSharedPreferences("Users", Context.MODE_PRIVATE);
        if (!list.contains("nutzlich")) {
        	//Toast.makeText(this, "NO USERS", Toast.LENGTH_LONG).show();
        }
        EditText user = (EditText) findViewById (R.id.txtUser);
        EditText pass = (EditText) findViewById (R.id.txtPass);
        CheckBox rem = (CheckBox) findViewById (R.id.checkRem);
        user.setText(list.getString("uname", ""), TextView.BufferType.EDITABLE);
        pass.setText(list.getString("passw", ""), TextView.BufferType.EDITABLE);
        rem.setChecked(list.getBoolean("checked", false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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
    
    public void logon (View v) throws Exception {
    	/**
    	 * if one or more fields are empty, show toast
    	 * if account doesn't exist, show toast
    	 * otherwise, go to the account selection screen
    	 */
    	EditText user = (EditText) findViewById (R.id.txtUser);
    	EditText pass = (EditText) findViewById (R.id.txtPass);
    	CheckBox rem = (CheckBox) findViewById (R.id.checkRem);
    	
    	String username = user.getText().toString().trim();
    	String password = pass.getText().toString().trim();
    	
    	try {
    	//array = convert(list.getString("nutzlich", null));
    	if ((username.length() == 0 && password.length() == 0)) {
    		//Toast.makeText(this, "No User", Toast.LENGTH_SHORT).show();
    	} 
    	else {
    		// check if inputed values exist in list
    		final Intent intent = new Intent (this, SelectAccount.class);
    		
			ParseUser.logInInBackground(username, password,	new LogInCallback() {
				public void done(ParseUser user, ParseException e) {
					if (user != null) {
						// If user exist and authenticated, send user to Welcome.class
						startActivity(intent);
						Toast.makeText(getApplicationContext(),
							"Successfully Logged in",
							Toast.LENGTH_LONG
						).show();
						finish();
					}
					else {
						Toast.makeText(
							getApplicationContext(),
							"No such user exist, please signup",
							Toast.LENGTH_LONG
						).show();
					}
				}
			});
    		
    		if (rem.isChecked()) {
				SharedPreferences.Editor editor = list.edit();
				editor.putString("uname", username);
				editor.putString("passw", password);
				editor.putBoolean("checked", rem.isChecked());
				editor.commit();
			}
    		
    		/*for (User a: array) {
    			if (user.getText().toString().equals(a.getUser()) &&
    					pass.getText().toString().equals(a.getPass())) {
    				if (rem.isChecked()) {
    					SharedPreferences.Editor editor = list.edit();
    					editor.putString("uname", user.getText().toString());
    					editor.putString("passw", pass.getText().toString());
    					editor.putBoolean("checked", rem.isChecked());
    					editor.commit();
    				}
    				startActivity(intent);
    				finish();
    			} else {
    				Toast.makeText(this, "Invalid Credentials",
    						Toast.LENGTH_SHORT).show();
    				break;
    			}
    		}*/
    	}
    	} catch (Exception e) {
    		if ((username.length() == 0 && password.length() == 0)) {
        		Toast.makeText(this, "No User", Toast.LENGTH_SHORT).show();
        	}
    		else {
        		Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        	}
    	}
    }
    
    public void register (View v) {
    	// go to register screen
    	Intent i = new Intent(this, Register.class);
    	startActivity(i);
    	finish();
    }
}
