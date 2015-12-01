	package com.cs123grpE.restaurantorderingsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import com.parse.*;



public class EditMenu extends Activity {

	private ExpandableAdapter epa;
    private ExpandableListView exp;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<String, List<ParseObject>> listDataChildObject;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_menu);
		
		prepareLists();		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_menu, menu);
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
	
	public void addMenu (View v) {
		// add a MenuListItem
		Intent i = new Intent (this, AddEditProfile.class);
		i.putExtra("mode","Add");
		startActivityForResult(i, 1);
	}
	
	public void editMenu (String cat, int childPos) {
		String po = listDataChild.get(cat).get(childPos);
		
		Intent i = new Intent (this, AddEditProfile.class);
		i.putExtra("mode","Edit");
		i.putExtra("object", po);
		startActivityForResult(i, 1);
		
	}
	
	public void deleteMenu (View v) {
		// show dialog for confirmation
		Toast.makeText(this, "You will delete a Menu Item.", Toast.LENGTH_SHORT).show();
		
	}
	
	private ParseObject findMenuItem(String nameOfItem) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Menu_Item").whereMatches("item_name", nameOfItem);

		List<ParseObject> list = null;
		try {
			list = query.find();
		} catch(Exception e) {}
		
		for(ParseObject a: list) {
			if(isActive(a)) return a;
		}
		
		return null;
	}

	private boolean isActive(ParseObject p) {
		// Date currDate = Calendar.getInstance().getTime();
		// Date activeFrom = (Date) p.get("active_from");
		// Date activeUntil = (Date) p.get("active_from");
		return p.getBoolean("active");
	}



	private void prepareLists() {
		prepareListData();
		
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

				editMenu(listDataHeader.get(groupPosition), childPosition);
				
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
		listDataHeader.add("Appetizer");
		listDataHeader.add("Main");
		listDataHeader.add("Dessert");
		listDataHeader.add("Beverage");
		int n = listDataHeader.size();
		
		// Adding child data
		for(int i = 0; i < n; i++) {
			String s = listDataHeader.get(i);
			List<String> list = new ArrayList<String>();
			List<ParseObject> listObject = new ArrayList<ParseObject>();
			ParseQuery<ParseObject> query = ParseQuery.getQuery("Menu_Item").whereMatches("category", s);
			List<ParseObject> matches = null;
			
			try {
				matches = query.find();
			} catch(Exception e) {}
			
			for(ParseObject a: matches) {
				//if(isActive(a)) {
					list.add((String) a.get("item_name"));
					listObject.add(a);
				//}
			}
			listDataChild.put(s, list);
			listDataChildObject.put(s, listObject);
		}
	}
	
	private void addMenuItem(String name, double price, String desc, String tag, String cat) {
		ParseObject item = new ParseObject("Menu_Item");
		item.put("item_name", name);
		item.put("item_price", price);
		item.put("item_desc", desc);
		item.put("active", true);
		item.put("category", cat);
		item.saveInBackground();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, getIntent());
	    if(resultCode==RESULT_OK && requestCode==1){
	        System.out.println("RESULT :D");
	    }

		prepareLists();
	}
	
	/*private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("MEAT");
		listDataHeader.add("SOUP");
		listDataHeader.add("FISH");

		// Adding child data
		List<String> top250 = new ArrayList<String>();
		top250.add("CRISPY PATA");
		top250.add("ADUBA");
		top250.add("PATAATIM");
		top250.add("SINIGANG");

		List<String> nowShowing = new ArrayList<String>();
		nowShowing.add("CHICKEN NOODLE SOUP");
		nowShowing.add("SINIGANG SA MISO");
		nowShowing.add("SPINACH");
		nowShowing.add("BIRD'S NEST");

		List<String> comingSoon = new ArrayList<String>();
		comingSoon.add("TALAKITOK");
		comingSoon.add("PAKSIW");
		comingSoon.add("ESCABECHE");

		listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
		listDataChild.put(listDataHeader.get(1), nowShowing);
		listDataChild.put(listDataHeader.get(2), comingSoon);
	}*/
}
