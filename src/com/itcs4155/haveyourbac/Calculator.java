package com.itcs4155.haveyourbac;

import java.text.DecimalFormat;
import java.util.List;

import com.parse.FindCallback;
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

	public String calculatedBAC(){

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// get weight and gender and save them to global variables
			ParseQuery<ParseObject> userInfoQuery = ParseQuery.getQuery("User");
			String user = currentUser.getUsername();
			userInfoQuery.whereEqualTo("weight", user);

;			Log.d(user, "This should be the username");
			
			userInfoQuery.findInBackground(new FindCallback<ParseObject>() {
				public void done(List<ParseObject> results, ParseException e) {
					if(e == null){
						Log.d("This should be the results size:", "Is this " + results.size() + " end");
						Log.d("Here is results:", results.toString());
					}
				}
				
			});
		} else {
			// show the signup or login screen
		}
//		double ratio;
//
//		bac = (alcohol * 5.14/weight * ratio) - (.015 * timeTaken);
//
//
//		if(genderString.equals("Male")){
//			double ratio = 0.73;
//		} else{
//			ratio = 0.66;
//		}
		return null;


	}
}
