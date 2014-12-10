package com.itcs4155.haveyourbac;

import java.util.ArrayList;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
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
	double timeTaken;
	double totalTime;
	boolean startTimeBool;
	double startTime;
	public static ArrayList<GraphPoints> bacPoints = new ArrayList<GraphPoints>();
	
	private static boolean isFirstScreen;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		ParsePush.subscribeInBackground("HaveYourBAC", new SaveCallback() {
	      	  @Override
	      	  public void done(ParseException e) {
	      	    if (e == null) {
	      	      Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
	      	    } else {
	      	      Log.e("com.parse.push", "failed to subscribe for push", e);
	      	    }
	      	    Log.d("com.parse.push", "crappy :(");
	      	  }
	   
	      	});
		
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
		totalTime = 0;
		timeTaken = 0;
		initializeUI();
		reorderDrink = (Button)findViewById(R.id.reorderDrink);
		lastDrinkHeader= (TextView) findViewById(R.id.lastDrinkHeader);
		reorderDrink.setEnabled(false);
		reorderDrink.setVisibility(View.GONE);
		lastDrinkHeader.setVisibility(View.GONE);
		startTimeBool = false;
		
	}
	
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
			calculateBAC(totalTime);
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
			calculateBAC(totalTime);
			Log.d("Error:", switchString);
		}
		
	}

//	@Override
//
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_my_tab);
//		txtbeer = (TextView) findViewById(R.id.lastDrinkName);
//		txtbrand = (TextView) findViewById(R.id.lastDrinkDetails); 
//		txtalcoholcontent = (TextView) findViewById(R.id.lastDrinkAlch);
//		drink = "";
//		brand = "";
//		content = "";
//		alcoholInOunces = 0;
//		txtbeer.setText("");
//		txtbrand.setText("");
//		txtalcoholcontent.setText("");
//		Log.d("create", "called");
//		   
//		initializeUI();
//	}
	@Override

	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
		setContentView(R.layout.activity_my_tab);
		initializeUI();
	}
	
	protected void initializeUI() {

		// Set results to the TextViews
		
		
		/*Calculator*/
		//calculateBAC(totalTime);
		
		/* Reorder Drink Button*/
		Button reorderDrink = (Button)findViewById(R.id.reorderDrink);

		
		reorderDrink.setOnClickListener(new View.OnClickListener(){
			public void onClick(View view){
				double myContent = Double.parseDouble(content); 
				
				txtbeer.setText(drink);
				txtbrand.setText("");
				
				
				alcoholInOunces += myContent * lastDrinkOunces * 0.01;
				calculateBAC(totalTime);
				
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
        		//Used to goto close tab with graph
        		Intent i = new Intent(getBaseContext(), CloseTabScreen.class);
        	    startActivity(i);
        		//finish();
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
	
		
	private void calculateBAC(final double t){
		if(startTimeBool==false){
			startTime = System.currentTimeMillis();
			startTimeBool=true;
		}
		ParseUser currentUser = ParseUser.getCurrentUser();
		//This is when the Timer starts
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
						//change totaltime to hours from milliseconds
						double time = t/3600000;
						//Actual calculation of BAC
						//split into part1 and 2 because we don't want a negative value
						double part1 = (alcoholInOunces* 5.14/doubleWeight * ratio);
						double part2 = (.015 * time);
						double setAlc = 0.0;
						if(part2>part1){
							setAlc = 0.0;
						}else{
							setAlc = part1 - part2;
						}
						 
						//Adds totaltime and bac level to arraylist for graph
						bacPoints.add(new GraphPoints(time,setAlc));
						String testString = String.format("%.8f", setAlc);
						TextView bac = (TextView)findViewById(R.id.bacValue);
						txtbeer.setText(drink);
						txtbrand.setText(brand);
						txtalcoholcontent.setText(content);
						bac.setText(testString);
					}
				}
					});	
			new TimerTask(startTime).execute();
		}
		
		
	}
	
	public class TimerTask extends AsyncTask<Double, Void, Void> {
			double startTime = 0;
	    public TimerTask(double startTime) {
			// TODO Auto-generated constructor stub
	    	this.startTime = startTime;
	    	
		}

		@Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	       }

	    @Override
	    protected Void doInBackground(Double... params) {
	        try {
	        	//checks every 20sec for new bac
	            Thread.sleep(1000);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    @Override
	    protected void onPostExecute(Void result) {
	        super.onPostExecute(result);
	        Log.d("TestTask", "difftime = "
	                + (System.currentTimeMillis() - startTime));
	        totalTime = System.currentTimeMillis() - startTime;
	        calculateBAC(totalTime);
	    }
	}
	
}
