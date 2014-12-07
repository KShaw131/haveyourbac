package com.itcs4155.haveyourbac;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserLogin extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_login);
		      
        final Button login = (Button)findViewById(R.id.loginButton);
        final Button signup = (Button)findViewById(R.id.signUpButton);
        final EditText usernameText = (EditText)findViewById(R.id.username);
        final EditText passwordText = (EditText)findViewById(R.id.password);
		
        
        login.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View view){
        		//Logs user profile in if found from Parse
        		
        		ParseUser.logInInBackground(usernameText.getText().toString(), passwordText.getText().toString(), new LogInCallback() {
        			  public void done(ParseUser user, ParseException e) {
        			    if (user != null) {
        			      // Hooray! The user is logged in.
        			    	Toast.makeText(getBaseContext(), "Welcome "+usernameText.getText().toString()+"!", Toast.LENGTH_LONG).show();
        			    	//goto MyTab activity if login is successful
        			    	Intent intent = new Intent(getBaseContext(), MyTab.class);
        			    	startActivity(intent);
        			    } else {
        			      // Signup failed. Look at the ParseException to see what happened.
        			    	Toast.makeText(getBaseContext(), "Login Failed. Please try again!", Toast.LENGTH_LONG).show();
        			    }
        			  }
        			});
        		
        	}
        
      });
		
        signup.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View view){
        		//goto signup activity
        		Intent intent = new Intent(getBaseContext(), Signup.class);
        		startActivity(intent);
        	}
        
      });
		
	}
}
