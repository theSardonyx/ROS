package com.cs123grpE.restaurantorderingsystem;

import android.app.Activity;
import android.content.Intent;

import java.util.List;

import com.parse.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddEditProfile extends Activity {
	private String mode;
	private ParseObject obj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_edit_profile);
		
		Intent i = getIntent();
		mode = i.getStringExtra("mode");
		if(mode.equals("Edit")) {
			String name = i.getStringExtra("object");
			obj = Helper.findObject("Menu_Item", "item_name", name);
			fillInText();
		}
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
	
	public void fillInText() {
		EditText nm = (EditText) findViewById (R.id.txtItemName);
		nm.setText((String) obj.get("item_name"));
		EditText price = (EditText) findViewById (R.id.txtPrice);
		price.setText(""+ obj.getDouble("item_price"));
		EditText des = (EditText) findViewById (R.id.txtDescription);
		des.setText((String) obj.get("item_desc"));
		EditText tag = (EditText) findViewById (R.id.txtIngredients);
		tag.setText((String) obj.get("tag"));
		EditText cat = (EditText) findViewById (R.id.txtCategory);
		cat.setText( (String) ( (ParseObject) obj.get("category") ).get("category_name") );
	}
	
	public void save (View v) {
		// do stuff to save fields
		
		EditText nm = (EditText) findViewById (R.id.txtItemName);
		EditText price = (EditText) findViewById (R.id.txtPrice);
		EditText des = (EditText) findViewById (R.id.txtDescription);
		EditText tag = (EditText) findViewById (R.id.txtIngredients);
		EditText cat = (EditText) findViewById (R.id.txtCategory);
			
		if(mode.equals("Add")) {
			Helper.addMenuItem(nm.getText().toString(), Double.parseDouble(price.getText().toString()), 
						des.getText().toString(), tag.getText().toString(), cat.getText().toString());
	
			Toast.makeText (this, "Item is added to menu.", Toast.LENGTH_SHORT).show();
			finish();
		}
		else {
			Helper.editMenuItem(obj, nm.getText().toString(), Double.parseDouble(price.getText().toString()), 
					des.getText().toString(), tag.getText().toString(), cat.getText().toString());

			Toast.makeText (this, "Item is edited.", Toast.LENGTH_SHORT).show();
			finish();
		}
		
		
	}
	
	
	public void onBackPressed() {
		// go back to the menu
		finish();
	}
}
