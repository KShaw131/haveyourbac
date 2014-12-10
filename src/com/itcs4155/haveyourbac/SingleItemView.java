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
	String beer;
	String brand;
	String alcoholContent;
	Context context;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Get the view from singleitemview.xml
		setContentView(R.layout.activity_single_item_view);
 
		Intent i = getIntent();
		// Get the result of rank
		beer = i.getStringExtra("beer");
		// Get the result of country
		brand = i.getStringExtra("brand");
		// Get the result of population
		alcoholContent = i.getStringExtra("alcoholContent");
 
		// Locate the TextViews in singleitemview.xml
		final TextView txtbeer = (TextView) findViewById(R.id.beer2);
		final TextView txtbrand = (TextView) findViewById(R.id.brand2);
		final TextView txtalcoholcontent = (TextView) findViewById(R.id.alcoholcontent2);
 
		// Set results to the TextViews
		txtbeer.setText(beer);
		txtbrand.setText(brand);
		txtalcoholcontent.setText(alcoholContent);
		
final Button cancel = (Button)findViewById(R.id.cancelWine);
final Button add = (Button)findViewById(R.id.addWine);

			add.setOnClickListener(new View.OnClickListener(){
				
				public void onClick(View arg0) {
					Intent intent = new Intent(getBaseContext(), MyTab.class);
					intent.putExtra("drink", txtbeer.getText());
					intent.putExtra("brand", txtbrand.getText());
					intent.putExtra("alcoholContent", txtalcoholcontent.getText());
					intent.putExtra("ounces", 12.0);
					setResult(1, intent);
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