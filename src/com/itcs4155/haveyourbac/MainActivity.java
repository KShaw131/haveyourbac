//DONE. DON'T TOUCH!!

package com.itcs4155.haveyourbac;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "MWiazlI1gWZiFz2rADwQ1Z3MKPrS5jVEYAHBko5W", "XNCKhjDQGUPuxgdKBHXrBW2xYHQgZHs8O9MpsZxr");
        setContentView(R.layout.activity_main);
        
        //Setting application id and client key
        
        //Subscribing app to receive push notifications from Parse
        ParsePush.subscribeInBackground("HaveYourBAC", new SaveCallback() {
        	  @Override
        	  public void done(ParseException e) {
        	    if (e == null) {
        	      Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
        	    } else {
        	      Log.e("com.parse.push", "failed to subscribe for push", e);
        	    }
        	  }
        	});
        
        //Button to goto login screen
        final Button agree = (Button)findViewById(R.id.agree);
		
        agree.setOnClickListener(new View.OnClickListener(){
        	
        	public void onClick(View view){
        		//Used to goto my UserLoginPage
        		Intent intent = new Intent(MainActivity.this, UserLogin.class);
        		startActivity(intent);
        	}
        
      });
        
    }
}
