package com.itcs4155.haveyourbac;

import java.text.DecimalFormat;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Calculator {
	
	double bac;
	
	public void setBac(double bac){
		
		this.bac = bac;
	}
	
	public double calcMe(double alcoholInOunces)
	{
		calculatedBAC(alcoholInOunces);
		return bac;
	}

	public void calculatedBAC(final double alcoholInOunces){

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

						//setBac(alcoholInOunces* 5.14/doubleWeight * ratio); //- (.015 * timeTaken);
						
					}
				}
					});	

		}else{

		}
	}
}
