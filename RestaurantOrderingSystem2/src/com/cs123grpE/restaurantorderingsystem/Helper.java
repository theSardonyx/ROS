package com.cs123grpE.restaurantorderingsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
			
			if(!className.equals("Menu_Item")) return a;
		}
		
		return null;
	}
	
	public static void addMenuItem(String name, double price, String desc, String tag, String cat) {
		ParseObject item = new ParseObject("Menu_Item");
		item.put("item_name", name);
		item.put("item_price", price);
		item.put("item_desc", desc);
		item.put("active", true);
		parseIngredients(item, tag);
		item.put("tag", tag);
		
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
		parseIngredients(item, tag);
		item.put("tag", tag);
		
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
	
	public static void addOrder(ParseObject table, ParseObject item, int qty) {
		ParseObject order = new ParseObject("Item_Order");
		order.put("item_id", item);
		order.put("item_name", item.getString("item_name"));
		order.put("quantity", qty);
		order.put("table_id", table);
		order.put("table_no", table.getInt("table_id"));
		order.put("paid", false);
		order.put("completed", false);
		order.saveInBackground(new SaveCallback() {
			   public void done(ParseException e) {
			     if (e == null) {
			      // myObjectSavedSuccessfully();
			     } else {
			       //myObjectSaveDidNotSucceed();
			     }
			   }
			 });
	}
	
	public static void setOrderAttribute(ParseObject order, String key, boolean value) {
		order.put(key, value);
		order.saveInBackground();
	}
	
	public static void parseIngredients(ParseObject item, String ingredients) {
		String[] ingr = ingredients.split(", ");
		ArrayList<ParseObject> al = new ArrayList<ParseObject>();
		
		for(String x: ingr) {
			ParseObject p = findObject("Ingredient", "ingredient_name", x);
			if(p==null)
				p = addIngredient(x);
			
			al.add(p);
		}
		
		item.put("item_ingredients", al);
	}
	
	public static ParseObject addIngredient(String ingr) {
		ParseObject p = new ParseObject("Ingredient");
		p.put("ingredient_name", ingr);
		p.saveInBackground();
		return p;
	}
	
	public static ParseObject addRestaurant(String rest) {
		ParseObject p = new ParseObject("Restaurant");
		p.put("rest_name", rest);
		p.saveInBackground();
		return p;
	}
	
	public static ParseObject addTable(ParseUser user, int num) {
		ParseObject p = new ParseObject("Table");
		//ParseObject rest = user.getParseObject("restaurant");
		//p.put("rest_id", rest);
		p.put("table_id", num);
		p.saveInBackground();
		return p;
	}
	
	public static ParseObject findTable(ParseUser user, int num) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Table");
		//ParseObject rest = user.getParseObject("restaurant");
		//query.whereEqualTo("rest_id", rest).whereEqualTo("table_id", num);
		query.whereEqualTo("table_id", num);
				
		List<ParseObject> list = null;
		try {
			list = query.find();
		} catch(Exception e) {
			return null;
		}
		
		for(ParseObject a: list) {
			return a;
		}
		
		return null;
	}
	
	public static List<ParseObject> getQueue() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Item_Order").whereEqualTo("completed", false);
		List<ParseObject> list = null;
		try {
			list = query.find();
		} catch(Exception e) {
			return null;
		}
		
		return list;
	}
}
