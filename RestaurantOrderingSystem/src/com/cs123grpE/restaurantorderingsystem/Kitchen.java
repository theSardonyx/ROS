package com.cs123grpE.restaurantorderingsystem;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class Kitchen extends Activity {
	
	ListView lv;
	KitchenAdapter adapter;
	 public  Kitchen CustomListView = null;
     public  ArrayList<OrderModel> CustomListViewValuesArr = new ArrayList<OrderModel>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kitchen);
		
		 CustomListView = this;
         
         /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
         setListData();
          
         Resources res =getResources();
         lv= ( ListView )findViewById( R.id.list );  // List defined in XML ( See Below )
          
         /**************** Create Custom Adapter *********/
         adapter=new KitchenAdapter( CustomListView, CustomListViewValuesArr,res );
         lv.setAdapter( adapter );
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
	 public void onItemClick(int mPosition)
     {
		 CustomListViewValuesArr.remove(mPosition);
		 adapter.notifyDataSetChanged();
     }
}