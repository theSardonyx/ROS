package com.cs123grpE.restaurantorderingsystem;

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
import java.util.*;

public class Helper {
	
	public static boolean isActive(ParseObject p) {
		// Date currDate = Calendar.getInstance().getTime();
		// Date activeFrom = (Date) p.get("active_from");
		// Date activeUntil = (Date) p.get("active_from");
		return p.getBoolean("active");
	}
	
	public static ParseObject findObject(String className, String key, String itemName) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(className).whereMatches(key, itemName);

		List<ParseObject> list = null;
		try {
			list = query.find();
		} catch(Exception e) {
			return null;
		}
		
		for(ParseObject a: list) {
			if(className.equals("Menu_Item") && isActive(a)) return a;
			
			if(className.equals("Category")) return a;
		}
		
		return null;
	}
	
	public static void addMenuItem(String name, double price, String desc, String tag, String cat) {
		ParseObject item = new ParseObject("Menu_Item");
		item.put("item_name", name);
		item.put("item_price", price);
		item.put("item_desc", desc);
		item.put("active", true);
		
		ParseObject obj = findObject("Category", "category_name", cat);
		if(obj==null) obj = addCategory(cat);
		
		item.put("category", obj);
		item.saveInBackground();
	}
	
	public static void editMenuItem(ParseObject item, String name, double price, String desc, String tag, String cat) {
		item.put("item_name", name);
		item.put("item_price", price);
		item.put("item_desc", desc);
		item.put("active", true);
		
		ParseObject obj = findObject("Category", "category_name", cat);
		if(obj==null) obj = addCategory(cat);
		
		item.put("category", obj);
		item.saveInBackground();
	}

	public static ParseObject addCategory(String cat) {
		ParseObject p = new ParseObject("Category");
		p.put("category_name", cat);
		p.saveInBackground();
		return p;
	}
	
	public static void addOrder(ParseObject item, int table_num, int qty) {
		ParseObject order = new ParseObject("Order");
		order.put("item_id", item);
		//order.put("table_id", table_num); // get pointer
		order.put("quantity", qty);
		order.saveInBackground();
	}
	
}
