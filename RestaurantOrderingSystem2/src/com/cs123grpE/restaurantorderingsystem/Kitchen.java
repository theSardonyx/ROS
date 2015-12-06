package com.cs123grpE.restaurantorderingsystem	;

import java.util.ArrayList;
import com.parse.*;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Kitchen extends Activity {
	
	ListView lv;
	//KitchenAdapter adapter;
	 //public  Kitchen CustomListView = this;
     public  ArrayList<OrderModel> CustomListViewValuesArr = new ArrayList<OrderModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kitchen);
		
         
         /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
         setListData();
         //Resources res = getResources();
         lv = ( ListView )findViewById( R.id.listKitchen );  
          
         /**************** Create Custom Adapter *********/
        // adapter=new KitchenAdapter( CustomListView, CustomListViewValuesArr,res );
         //lv.setAdapter( new ArrayAdapter<OrderModel>(this, R.layout.menu_item, R.id.foodName, CustomListViewValuesArr));
         @SuppressWarnings("unchecked")
         final ArrayAdapter<OrderModel> adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_2, android.R.id.text1, CustomListViewValuesArr) {
        	  @Override
        	  public View getView(int position, View convertView, ViewGroup parent) {
        	    View view = super.getView(position, convertView, parent);
        	    TextView text1 = (TextView) view.findViewById(android.R.id.text1);
        	    TextView text2 = (TextView) view.findViewById(android.R.id.text2);

        	    text1.setText(CustomListViewValuesArr.get(position).getFoodName());
        	    text2.setText(CustomListViewValuesArr.get(position).getTableNumber());
        	    return view;
        	  }
        	};
         lv.setAdapter(adapter);
         lv.setOnItemClickListener(new OnItemClickListener()
         {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				CustomListViewValuesArr.remove(arg2);
				adapter.notifyDataSetChanged();
			}
         });
        // adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kitchen, menu);
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
		Intent i = new Intent (this, SelectAccount.class);
		startActivity(i);
	}
	
	public void setListData()
    {
		
        for (int i = 0; i < 11; i++) {
             
            final OrderModel sched = new OrderModel();
            
            
              /******* Firstly take data in model object ******/
               sched.setFoodName(""+i);
               sched.setTableNumber("Table Number: "+i);
                
            /******** Take Model Object in ArrayList **********/
            CustomListViewValuesArr.add( sched );
        }
    }
	
	//public void prepareListData() {
	//	ParseQuery<ParseObject> query = ParseQuery.getQuery("Order").whereMatches(key, itemName);
		
	//}
}
