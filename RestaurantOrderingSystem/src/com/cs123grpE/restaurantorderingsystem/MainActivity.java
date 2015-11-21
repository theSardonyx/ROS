package com.cs123grpE.restaurantorderingsystem;

import com.parse.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Parse.enableLocalDatastore(this);
	        
	    Parse.initialize(this, "x2JAmf97XCaZbCJDTgxqGAUoLqcpzTxx48xlj4m4", "d3EwdWEYXfB93SH5jG8TWuwteH5d2yS94X1oegNv");
	        
	    ParseInstallation.getCurrentInstallation().saveInBackground();
	    
	    Intent i = new Intent(this, Login.class);
    	startActivity(i);
    	finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
}
