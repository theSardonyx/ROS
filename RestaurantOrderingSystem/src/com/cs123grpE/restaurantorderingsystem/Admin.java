package com.cs123grpE.restaurantorderingsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Admin extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.admin, menu);
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
	
	public void editMenu (View v) {
		// go to edit menu screen
		Intent i = new Intent (this, EditMenu.class);
		startActivity(i);
	}
	
	public void editBill (View v) {
		// go to edit bill screen
		Intent i = new Intent (this, EditBill.class);
		startActivity(i);
	}
	
	public void config (View v) {
		// go to settings screen
		Intent i = new Intent (this, Settings.class);
		startActivity(i);
	}
	
	public void logoff (View v) {
		Intent i = new Intent (this, Login.class);
		startActivity(i);
		finish();
	}
	
	public void onBackPressed() {
		finish();
	}
}
