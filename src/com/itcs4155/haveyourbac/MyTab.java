package com.itcs4155.haveyourbac;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
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
	
	private String drink = "";
	private String brand = "";
	private String alcoholContent = "";
	private TextView txtbeer;
	private TextView txtbrand;
	private TextView txtalcoholcontent;
	private double alcoholInOunces;
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == 1) {
			drink = data.getStringExtra("drink");
			txtbeer.setText(drink);
			brand = data.getStringExtra("brand");
			txtbrand.setText(brand);
			alcoholContent = data.getStringExtra("alcoholContent");
			txtalcoholcontent.setText(alcoholContent);
			double myAlcoholInOunces = data.getDoubleExtra("ounces", 0);
			double myContent = Double.parseDouble(alcoholContent);
			alcoholInOunces += myContent * myAlcoholInOunces * 0.01;
			calculateBAC();
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tab);
		
		Intent i = getIntent();
		// Get the result of rank
		String beer = i.getStringExtra("beer");
		// Get the result of country
		String brand = i.getStringExtra("brand");
		// Get the result of population
		String alcoholContent = i.getStringExtra("alcoholContent");
		
		alcoholInOunces = 0;
		
		
		txtbeer = (TextView) findViewById(R.id.lastDrinkName);
		txtbrand = (TextView) findViewById(R.id.lastDrinkDetails);
		txtalcoholcontent = (TextView) findViewById(R.id.lastDrinkAlch);

		// Set results to the TextViews
		txtbeer.setText(beer);
		txtbrand.setText(brand);
		txtalcoholcontent.setText(alcoholContent);
		
		
		/*Calculator*/
		calculateBAC();
				
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
						bac.setText(testString);
					}
				}
					});	

		}else{

		}
	}
	
}
