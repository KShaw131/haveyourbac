package com.itcs4155.haveyourbac;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MyTab extends Activity {
	
	private String drink;
	private String brand;
	private String content;
	private TextView txtbeer;
	private TextView txtbrand;
	private TextView txtalcoholcontent;
	private TextView lastDrinkHeader;
	private double alcoholInOunces;
	private double lastDrinkOunces;
	private Button reorderDrink;
	
	private static boolean isFirstScreen;
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == 1) {
			drink = data.getStringExtra("drink");
			txtbeer.setText(drink);
			brand = data.getStringExtra("brand");
			txtbrand.setText(brand);
			content = data.getStringExtra("alcoholContent");
			txtalcoholcontent.setText(content);
			double myAlcoholInOunces = data.getDoubleExtra("ounces", 0);
			double myContent = Double.parseDouble(content);
			alcoholInOunces += myContent * myAlcoholInOunces * 0.01;
			lastDrinkOunces = myAlcoholInOunces;
			calculateBAC();
		}
		if(resultCode == 2){
			double myAlcoholInOunces;
			double myContent;
			String switchString = data.getStringExtra("customType");
			switch (switchString){
			case "Beer":
				drink = "Custom Beer";
				myAlcoholInOunces = 12;
				myContent = 6; //change me
				break;
			case "Wine":
				drink = "Custom Wine";
				myAlcoholInOunces = 5;
				myContent = 15; //change me
				break;
			default:
				drink = "Custom Liquor";
				myAlcoholInOunces = 1.5;
				myContent = 30;
			}
			lastDrinkOunces = myAlcoholInOunces;
			content = data.getStringExtra("customAlcoholContent");
			txtalcoholcontent.setText(content);
			myContent = Double.parseDouble(content); 
			
			txtbeer.setText(drink);
			txtbrand.setText("");
			
			alcoholInOunces += myContent * myAlcoholInOunces * 0.01;
			calculateBAC();
			Log.d("Error:", switchString);
		}
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tab);
		txtbeer = (TextView) findViewById(R.id.lastDrinkName);
		txtbrand = (TextView) findViewById(R.id.lastDrinkDetails); 
		txtalcoholcontent = (TextView) findViewById(R.id.lastDrinkAlch);
		drink = "";
		brand = "";
		content = "";
		alcoholInOunces = 0;
		txtbeer.setText("");
		txtbrand.setText("");
		txtalcoholcontent.setText("");
		Log.d("create", "called");
		initializeUI();
		reorderDrink = (Button)findViewById(R.id.reorderDrink);
		lastDrinkHeader= (TextView) findViewById(R.id.lastDrinkHeader);
		reorderDrink.setEnabled(false);
		reorderDrink.setVisibility(View.GONE);
		lastDrinkHeader.setVisibility(View.GONE);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.activity_my_tab);
		initializeUI();
	}
	protected void initializeUI() {

		// Set results to the TextViews
		
		
		/*Calculator*/
		calculateBAC();
		
		/* Reorder Drink Button*/

		
		reorderDrink.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				double myContent = Double.parseDouble(content); 
				
				txtbeer.setText(drink);
				txtbrand.setText("");
				
				
				alcoholInOunces += myContent * lastDrinkOunces * 0.01;
				calculateBAC();
				
	    	}  
  });
		
				
		/*Choose Drink Button*/
		
		final Button chooseDrink = (Button)findViewById(R.id.chooseDrinkButton);
		
		chooseDrink.setOnClickListener(new View.OnClickListener(){
			
    	public void onClick(View view){
    		Intent intent = new Intent(getBaseContext(), ChooseDrink.class);
    		startActivityForResult(intent, 1);
    	}
    
  });
		 //Button to goto close tab screen
        final Button closeTab = (Button)findViewById(R.id.closeMe);
		
        closeTab.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View view){
        		//Used to goto my UserLoginPage
        		Intent intent = new Intent(getBaseContext(), CloseTabScreen.class);
        		startActivity(intent);
        	}
      });
        
        /*The Custom Drink Button*/
        final Button custom = (Button)findViewById(R.id.customButton);
        custom.setOnClickListener(new View.OnClickListener(){
        public void onClick(View view){
        Intent intent = new Intent(getBaseContext(), CustomDrinkPage.class);
        startActivityForResult(intent,1);
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
	
		
	private void calculateBAC(){
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// get weight and gender and save them to global variables
			ParseQuery<ParseObject> userInfoQuery = ParseQuery.getQuery("_User");
			String user = currentUser.getUsername();
			userInfoQuery.whereEqualTo("username", user);
			//			userInfoQuery.whereEqualTo("weight", user);
			Log.d(user, "This should be the username");
			
			userInfoQuery.getFirstInBackground(new GetCallback<ParseObject>()
					{
				public void done(ParseObject object, ParseException e)
				{
					if (object == null) 
					{
						Log.d(";(", "Didnt work");
					} 
					else 
					{
						String weight = object.getString("weight").toString();
						String gender = object.getString("gender").toString();

						double doubleWeight = Double.parseDouble(weight);

						double ratio;

						if(gender.equals("Male")){
							ratio = 0.73;
						} else{
							ratio = 0.66;
						}
						double setAlc = (alcoholInOunces* 5.14/doubleWeight * ratio); //- (.015 * timeTaken);
						String testString = String.format("%.2f", setAlc);
						TextView bac = (TextView)findViewById(R.id.bacValue);
						txtbeer.setText(drink);
						txtbrand.setText(brand);
						txtalcoholcontent.setText(content);
						bac.setText(testString);
					}
				}
					});	

		}
	}
	
}
