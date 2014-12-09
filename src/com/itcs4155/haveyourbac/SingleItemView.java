package com.itcs4155.haveyourbac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
 
public class SingleItemView extends Activity {
	// Declare Variables
	String drink;
	String brand;
	String alcoholContent;
	Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.activity_single_item_view);
 
		Intent i = getIntent();
		drink = i.getStringExtra("beer");
		brand = i.getStringExtra("brand");
		alcoholContent = i.getStringExtra("alcoholContent");


		// Locate the TextViews in singleitemview.xml
		final TextView txtdrink = (TextView) findViewById(R.id.beer2);
		final TextView txtbrand = (TextView) findViewById(R.id.brand2);
		final TextView txtalcoholcontent = (TextView) findViewById(R.id.alcoholcontent2);
 
		// Set results to the TextViews
		txtdrink.setText(drink);
		txtbrand.setText(brand);
		txtalcoholcontent.setText(alcoholContent);
		
final Button cancel = (Button)findViewById(R.id.cancelWine);
final Button add = (Button)findViewById(R.id.addWine);

			add.setOnClickListener(new View.OnClickListener(){
				
				public void onClick(View arg0) {
					
//					Intent intent = new Intent(getBaseContext(), MyTab.class);
//					intent.putExtra("drink", txt.getText());
//					intent.putExtra("brand", txtbrand.getText());
//					intent.putExtra("alcoholContent", txtalcoholcontent.getText());
					BACCarrier carry = new BACCarrier();
					carry.setDrink((String) txtdrink.getText());
					carry.setDrink((String) txtbrand.getText());
					carry.setDrink((String) txtalcoholcontent.getText());
					finish();
				}
			
			});
		
        cancel.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View view){
        		//Used to goto my UserLoginPage
        		finish();
        	}
        
      });
	}
}