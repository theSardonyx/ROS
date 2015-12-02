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

public class Customer extends Activity {

	
    String [] menu = {"Fish", "Meat", "Soup", "Fruit", "Vegetables"};
    private ExpandableAdapter epa;
    private ExpandableListView exp;
    List<String> listDataHeader;
    List<String> listDataHeaderId;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<ParseObject>> listDataChildObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customer);
		
		prepareLists();
		
		//generateMenu("", true);
		
		ListView lv = (ListView)findViewById(R.id.listview1);
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));

		
		exp.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		exp.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Expanded",
						Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		exp.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Collapsed",
						Toast.LENGTH_SHORT).show();

			}
		});

		// Listview on child click listener
		exp.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				
				String name = listDataChild.get(listDataHeader.get(groupPosition))
				.get(childPosition);
				ParseObject x = null;
				ParseObject order = null;
				double price = 0;
				
				try {
					x = (new ParseQuery("Menu_Item")).whereMatches("item_name", name).getFirst();
					price  = x.getDouble("item_price");
					
					order = new ParseObject("Order");
					order.put("item_id", x.getObjectId());
					order.put("quantity", 1);
					order.saveInBackground();
					
					Intent i = new Intent(getApplicationContext(), ViewProfile.class);
					i.putExtra("menu_name", name);
					startActivity(i);
					
				}catch(Exception e) {}
				
				
				Toast.makeText(
					getApplicationContext(),
					listDataHeader.get(groupPosition) + " : " + 
					listDataChild.get(listDataHeader.get(groupPosition))
					.get(childPosition) + " price: " + price, Toast.LENGTH_SHORT
				).show();
				return false;
			}
		});
		
		//expList.setOnChildClickListener(this); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.customer, menu);
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
	
	public void onBackPressed() {
		// do nothing
	}
	
	public void filterOff (View v) {
		// toggle to filter items without specified item from item list
		
		EditText searchString = (EditText) findViewById (R.id.txtSearch);
		String criteria = searchString.getText().toString().trim();
		
		//generateMenu(criteria, false);
	}
	
	public void filterOn (View v) {
		// toggle to filter items with specified item from item list
		EditText searchString = (EditText) findViewById (R.id.txtSearch);
		String criteria = searchString.getText().toString().trim();
		//generateMenu(criteria, true);
	}
	
	public void search (View v) {
		// search for query in menu name or description
		
		EditText searchString = (EditText) findViewById (R.id.txtSearch);
		String criteria = searchString.getText().toString().trim();
		
	}
	
	public void viewCart (View v) {
		// go to cart screen
		Intent i = new Intent (this, Cart.class);
		startActivity (i);
	}
	
	private void prepareLists() {
		prepareListData();
		
		ListView lv = (ListView)findViewById(R.id.listview1);
		lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
		
		exp = (ExpandableListView)findViewById(R.id.list);
		
		epa = new ExpandableAdapter(this, listDataHeader, listDataChild);
			
		exp.setAdapter(epa);
		
		exp.setOnGroupClickListener(new OnGroupClickListener() {
	
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});
	
		// Listview Group expanded listener
		exp.setOnGroupExpandListener(new OnGroupExpandListener() {
	
			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Expanded",
						Toast.LENGTH_SHORT).show();
			}
		});
	
		// Listview Group collasped listener
		exp.setOnGroupCollapseListener(new OnGroupCollapseListener() {
	
			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Collapsed",
						Toast.LENGTH_SHORT).show();
	
			}
		});
	
		// Listview on child click listener
		exp.setOnChildClickListener(new OnChildClickListener() {
	
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub

				//editMenu(listDataHeader.get(groupPosition), childPosition);
				
				String cat = listDataHeader.get(groupPosition);
				String name = ((String) ( (ParseObject) listDataChildObject.get(cat).get(childPosition)).get("item_name"));
				
				Intent i = new Intent(getApplicationContext(), ViewProfile.class);
					i.putExtra("menu_name", name);
					startActivity(i);
				
				Toast.makeText(getApplicationContext(),
					listDataHeader.get(groupPosition)
					+ " : "
					+ listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT)
					.show();
				return false;
			}
		});
	}
	
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		listDataChildObject = new HashMap<String, List<ParseObject>>();

		// Adding child data
		ParseQuery<ParseObject> query1 = ParseQuery.getQuery("Category");
		List<ParseObject> listHeaders = null;
		try {
			listHeaders = query1.find();
		} catch(Exception e) {}
		
		for(ParseObject x: listHeaders) {
			listDataHeader.add((String)x.get("category_name"));
		}
		
		int n = listDataHeader.size();

		// Adding child data
		for(int i = 0; i < n; i++) {
			String s = listDataHeader.get(i);
			ParseObject obj = listHeaders.get(i);
			List<String> list = new ArrayList<String>();
			List<ParseObject> listObject = new ArrayList<ParseObject>();
			
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Menu_Item").whereEqualTo("category", obj);
			
			
			List<ParseObject> matches = null;
			
			try {
				matches = query.find();
			} catch(Exception e) {}

			for(ParseObject a: matches) {
				// if(isActive(a)) {
					list.add((String) a.get("item_name"));
					listObject.add(a);
				// }
			}
			listDataChild.put(s, list);
			listDataChildObject.put(s, listObject);
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, getIntent());
	    if(resultCode==RESULT_OK && requestCode==1){
	        System.out.println("RESULT :D");
	    }

		prepareLists();
	}
}
