package com.cs123grpE.restaurantorderingsystem;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.parse.*;

public class AddEditProfile extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_profile);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_edit_profile, menu);
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
	
	public void changeImage (View v) {
		// open explorer to select picture to replace default
		Toast.makeText (this, "Image has been changed.", Toast.LENGTH_SHORT).show();
	}
	
	public void cancel (View v) {
		finish();
	}
	
	public void save (View v) {
		// do stuff to save fields
		
		EditText nm = (EditText) findViewById (R.id.txtItemName);
		EditText price = (EditText) findViewById (R.id.txtPrice);
		EditText desc = (EditText) findViewById (R.id.txtDescription);
		EditText tag = (EditText) findViewById (R.id.txtIngredients);
		EditText cat = (EditText) findViewById (R.id.txtCategory);
		
		ParseObject menuitem = new ParseObject("Menu_Item");
		menuitem.put("item_name", nm.getText().toString());
		menuitem.put("item_price", Double.parseDouble(price.getText().toString()));
		menuitem.put("item_desc", desc.getText().toString());
		menuitem.put("category", cat.getText().toString());
		//menuitem.put("active", Boolean.valueOf(act.getText().toString()));
		//menuitem.put("active_from", act_from);
		//menuitem.put("active_until", act_until);
		menuitem.saveInBackground();
		
		Toast.makeText (this, "Item is added to menu.", Toast.LENGTH_SHORT).show();
		finish();
	}
	
	public void onBackPressed() {
		// go back to the menu
		finish();
	}
}
