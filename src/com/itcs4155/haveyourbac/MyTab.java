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
		
		final TextView txtbeer = (TextView) findViewById(R.id.lastDrinkName);
		final TextView txtbrand = (TextView) findViewById(R.id.lastDrinkDetails);
		final TextView txtalcoholcontent = (TextView) findViewById(R.id.lastDrinkAlch);

		// Set results to the TextViews
		txtbeer.setText(beer);
		txtbrand.setText(brand);
		txtalcoholcontent.setText(alcoholContent);
		
		
		/*Calculator*/

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
						double alcoholInOunces = 3.0;
						double setAlc = (alcoholInOunces* 5.14/doubleWeight * ratio); //- (.015 * timeTaken);
						String testString = ""+setAlc;
						TextView bac = (TextView)findViewById(R.id.bacLevelLabel);
						bac.setText(testString);

					}
				}
					});	

		}else{

		}
				
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
	
}
