package com.itcs4155.haveyourbac;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Signup extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		final Button signup = (Button)findViewById(R.id.signupSignup);
		final Button cancel = (Button)findViewById(R.id.signupCancel);
		final EditText username = (EditText)findViewById(R.id.signupUsername);
		final EditText password = (EditText)findViewById(R.id.signupPassword);
		final EditText email = (EditText)findViewById(R.id.signupEmail);
		final EditText weight = (EditText)findViewById(R.id.signupWeight);
		final RadioGroup gender = (RadioGroup)findViewById(R.id.radioGroup1);
		
		signup.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View view){
        		
        		//getting value from Radio Button
        		int id= gender.getCheckedRadioButtonId();
				View radioButton = gender.findViewById(id);
				int radioId = gender.indexOfChild(radioButton);
				RadioButton btn = (RadioButton) gender.getChildAt(radioId);
				String selection = (String) btn.getText();
				
				//Changing Weight into a number
				String weightNum = weight.getText().toString();
        		
        		ParseUser user = new ParseUser();
        		user.setUsername(username.getText().toString());
        		user.setPassword(password.getText().toString());
        		user.setEmail(email.getText().toString());
        		user.put("weight", weightNum);
        		user.put("gender", selection);
        		Log.d("WEIGHT", weightNum);
        		Log.d("Gender", selection);
               
        		user.signUpInBackground(new SignUpCallback() {
        			  public void done(ParseException e) {
        			    if (e == null) {
        			      // Hooray! Let them use the app now.
        			    	Toast.makeText(getBaseContext(), "User Created!", Toast.LENGTH_LONG).show();
        			    	finish();
        			    } else {
        			      // Sign up didn't succeed. Look at the ParseException
        			      // to figure out what went wrong
        			    	Toast.makeText(getBaseContext(), "Something went wrong, please try again", Toast.LENGTH_LONG).show();
        			    }
        			  }
        			});
        		
        	}
        
      });
		
		cancel.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View view){
        		// Finish activity
                finish();
        	}
        
      });
		
	}
}
