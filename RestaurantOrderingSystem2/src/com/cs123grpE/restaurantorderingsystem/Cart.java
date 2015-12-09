package com.cs123grpE.restaurantorderingsystem;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Cart extends Activity {

	ListView lv;
	TextView rn;
	
	ArrayList<String> cartName;
    ArrayList<Integer> cartQty;
    ArrayList<Double> cartPrice;
    
    public double total = 0;
	public ArrayList<CartItem> cartitems = new ArrayList<CartItem>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		
		 ArrayList<Double> cartPrice = new ArrayList<Double>();
		    
		Intent i = getIntent();
		cartName = i.getStringArrayListExtra("cartName");
		cartQty = i.getIntegerArrayListExtra("cartQty");
		
		getData();
		
		lv = (ListView)findViewById(R.id.listCart);
		rn = (TextView)findViewById(R.id.totesPrice);
		rn.setText(Double.toString(total));
		@SuppressWarnings("unchecked")
        final ArrayAdapter<OrderModel> adapter = new ArrayAdapter(getBaseContext(), R.layout.cart_item, R.id.nameF, cartitems) {
       	  @Override
       	  public View getView(int position, View convertView, ViewGroup parent) {
       	    View view = super.getView(position, convertView, parent);
       	    TextView text1 = (TextView) view.findViewById(R.id.nameF);
       	    TextView text2 = (TextView) view.findViewById(R.id.quantity);
       	    TextView text3 = (TextView) view.findViewById(R.id.price);


       	    text1.setText(cartitems.get(position).getFoodname());
       	    text2.setText(cartitems.get(position).getQuantity());
       	    text3.setText(Double.toString(cartitems.get(position).getPrice()));
       	    return view;
       	  }
       	};
		
		//lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menu));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cart, menu);
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
	
	public void finalize (View v) {
		// Send cart contents to kitchen and clear
		setResult(Activity.RESULT_OK, 
				new Intent().putExtra("finalize", true));
		finish();
		Toast.makeText(this, "Orders finalized.", Toast.LENGTH_SHORT).show();
	}

	
	public void onBackPressed() {
		setResult(Activity.RESULT_CANCELED, null);
		finish();
	}
	
	public void getData(){
		
        for (int i = 0; i < cartName.size(); i++) {
             
            final CartItem sched = new CartItem();
            
            	//ParseObject obj = queue.get(i);
              /******* Firstly take data in model object ******/
            	//ParseObject item = obj.getParseObject("item_id");
               sched.setFoodname(cartName.get(i));
               sched.setQuantity(cartQty.get(i));
               
               ParseObject obj = Helper.findObject("Menu_Item", "item_name", cartName.get(i));
               sched.setPrice(obj.getDouble("item_price")*cartQty.get(i));
               total += (cartQty.get(i) * obj.getDouble("item_price"));
                
            /******** Take Model Object in ArrayList **********/
            cartitems.add( sched );
        }
	}

}
