package com.itcs4155.haveyourbac;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SingleItemViewWine extends Activity {

	// Declare Variables
		String wine;
		String alcoholContent;
		Context context;
	 
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// Get the view from singleitemview.xml
			setContentView(R.layout.activity_single_item_view_wine);
	 
			Intent i = getIntent();
			// Get the result of rank
			wine = i.getStringExtra("wine");
			// Get the result of population
			alcoholContent = i.getStringExtra("alcoholContent");
			
			// Locate the TextViews in singleitemview.xml
			final TextView txtwine = (TextView) findViewById(R.id.wine2);
			final TextView txtalcoholcontent2 = (TextView) findViewById(R.id.alcoholcontent4);
	 
			// Set results to the TextViews
			txtwine.setText(wine);
			txtalcoholcontent2.setText(alcoholContent);
	 
	final Button cancel = (Button)findViewById(R.id.cancelWine);
	final Button add = (Button)findViewById(R.id.addWine);

				add.setOnClickListener(new View.OnClickListener(){
					
					public void onClick(View arg0) {
						Intent intent = new Intent(getBaseContext(), MyTab.class);
						intent.putExtra("drink", txtwine.getText());
						intent.putExtra("brand", "");
						intent.putExtra("alcoholContent", txtalcoholcontent2.getText());
						intent.putExtra("ounces", 5.0);
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