package com.cs123grpE.restaurantorderingsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.*;

public class ViewProfile extends Activity {
	
	String itemname = "";
	String description = "";
	String tags = "";
	double price = 0;
	ParseObject obj;
	ParseObject table;
	int tableNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_profile);
		
		NumberPicker np = (NumberPicker) findViewById(R.id.quantity);
		np.setMinValue(1);
	    np.setMaxValue(20);
	    np.setWrapSelectorWheel(true);
	    np.setValue(1);
		
		Intent i = getIntent();
		String name = i.getStringExtra("menu_name");
		tableNum = i.getIntExtra("tableNum", 0);
		table = Helper.findTable(ParseUser.getCurrentUser(), tableNum);
		
		obj = null;
		try{
			obj = (new ParseQuery("Menu_Item")).whereMatches("item_name", name).getFirst();
			itemname = obj.getString("item_name");
			description = obj.getString("item_desc");
			tags = obj.getString("tag");
			price = obj.getDouble("item_price");
		}catch(Exception e) {}
		
		TextView ax = (TextView) findViewById (R.id.tvName);
		TextView bx = (TextView) findViewById (R.id.priceName);
		TextView cx = (TextView) findViewById (R.id.tvDesc);
		TextView dx = (TextView) findViewById (R.id.tvTags);
		ax.setText(itemname);
		bx.setText(Double.toString(price));
		cx.setText(description);
		dx.setText(tags);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_profile, menu);
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
	
	public void back (View v) {
		// go back to the Customer screen
		finish();
	}
	
	public void addToCart (View v) {
		// add selected item to Cart
		
		NumberPicker np = (NumberPicker) findViewById(R.id.quantity);
		int qty = np.getValue();
		
		//Helper.addOrder(table, obj, qty);
		
		setResult(Activity.RESULT_OK, 
			    new Intent().putExtra("itemName", itemname).putExtra("quantity", qty).putExtra("tableNumber", tableNum));
			finish();
		
		Toast.makeText(getApplicationContext(),
			"Ordered " + qty + " " + itemname,
			Toast.LENGTH_SHORT).show();
		
		finish();
	}
	
	public void onBackPressed() {
		// go back to the Customer screen
		setResult(Activity.RESULT_CANCELED, new Intent().putExtra("tableNumber", tableNum));
		finish();
	}
}
