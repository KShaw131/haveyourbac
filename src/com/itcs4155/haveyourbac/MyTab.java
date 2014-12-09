package com.itcs4155.haveyourbac;

import java.text.DecimalFormat;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyTab extends Activity {
	
public double alcoholDouble = 0.00;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tab);
		
		Intent i = getIntent();
		// Get the intent
		String beer = i.getStringExtra("beer");
		// Get the result of beer
		String brand = i.getStringExtra("brand");
		// Get the result of brand
		String alcoholContent = i.getStringExtra("alcoholContent");
		
		final TextView txtbeer = (TextView) findViewById(R.id.lastDrinkName);
		final TextView txtbrand = (TextView) findViewById(R.id.lastDrinkDetails);
		final TextView txtalcoholcontent = (TextView) findViewById(R.id.lastDrinkAlch);
		

		// Set results to the TextViews
		txtbeer.setText(beer);
		txtbrand.setText(brand);
		txtalcoholcontent.setText(alcoholContent);
//		double alcohol;
//		if (alcoholContent != null){
//		alcohol = Double.parseDouble(alcoholContent);
//		}
//		else{
//			alcohol = 0.0;
//		}
//		
			/*Calculator*/

		Calculator calc = new Calculator();
		alcoholDouble += calc.getBac();
		TextView init = (TextView)findViewById(R.id.bacValue);
		init.setText(""+alcoholDouble);
		
		/*Choose Drink Button*/
		
		final Button chooseDrink = (Button)findViewById(R.id.chooseDrinkButton);
		
		chooseDrink.setOnClickListener(new View.OnClickListener(){
			
    	public void onClick(View view){
    		Intent intent = new Intent(getBaseContext(), ChooseDrink.class);
    		startActivity(intent);
    	}
    
  });
		 //Button to goto close tab screen
        final Button closeTab = (Button)findViewById(R.id.closeMe);
		
        closeTab.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View view){
        		//Used to goto close tab page
        		Intent intent = new Intent(getBaseContext(), CloseTabScreen.class);
        		startActivity(intent);
        		finish();
        	}
      });
        
        /*The Custom Drink Button*/
        final Button custom = (Button)findViewById(R.id.customButton);
        custom.setOnClickListener(new View.OnClickListener(){
        public void onClick(View view){
        Intent intent = new Intent(getBaseContext(), CustomDrinkPage.class);
        startActivity(intent);
        }
        });
        
        /*The uber button*/
        
		final ImageButton uber = (ImageButton)findViewById(R.id.uberButton);
		
			uber.setOnClickListener(new View.OnClickListener(){
				
        	public void onClick(View view){
        		Intent i;
        		PackageManager manager = getPackageManager();
        		try {
        		   i = manager.getLaunchIntentForPackage("com.ubercab");
        		if (i == null)
        		    throw new PackageManager.NameNotFoundException();
        		i.addCategory(Intent.CATEGORY_LAUNCHER);
        		startActivity(i);
        		} catch (PackageManager.NameNotFoundException e) {
        			Intent intent = new Intent();
        	        intent.setAction(Intent.ACTION_VIEW);
        	        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        	        intent.setData(Uri.parse("market://details?id=com.ubercab"));
        	        startActivity(intent);
        		}     		
        	}
        
      });
	}
	
}
