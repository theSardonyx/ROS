package com.cs123grpE.restaurantorderingsystem;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;
import com.parse.*;

public class TableChooser extends Activity {

	
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_table_chooser);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}
	
	public void onBackPressed() {
		// do nothing
	}
	
	public void toCustomer(View v) {
		Intent i = new Intent (this, Customer.class);
		EditText nm = (EditText) findViewById (R.id.tableNum);
		i.putExtra("tableNumber", Integer.parseInt(nm.getText().toString().trim()));
		startActivity(i);
		finish();
	}
	
}
