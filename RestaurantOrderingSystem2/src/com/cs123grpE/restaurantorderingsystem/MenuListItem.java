package com.cs123grpE.restaurantorderingsystem;

import java.util.ArrayList;

public class MenuListItem {
	String name, description;
	ArrayList<String> tags;
	double price;

	public MenuListItem() {
		// TODO Auto-generated constructor stub
	}
	
	public MenuListItem (String name, String description, ArrayList<String> tags,
			double price) {
		this.name = name;
		this.description = description;
		this.tags = tags;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public String getDesc() {
		return description;
	}
	
	public void setDesc (String description) {
		this.description = description;
	}
	
	public ArrayList<String> getTags() {
		return tags;
	}
	
	public void setTags (ArrayList<String> tags) {
		this.tags = tags;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

}