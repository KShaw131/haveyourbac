package com.itcs4155.haveyourbac;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MyTab extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_tab);
		
		final ImageButton uber = (ImageButton)findViewById(R.id.uberButton);
		
			uber.setOnClickListener(new View.OnClickListener(){
				
        	public void onClick(View view){
        		//Used to open the uber app
        		Intent i;
        		PackageManager manager = getPackageManager();
        		try {
        		   i = manager.getLaunchIntentForPackage("com.ubercab");
        		if (i == null)
        		    throw new PackageManager.NameNotFoundException();
        		i.addCategory(Intent.CATEGORY_LAUNCHER);
        		startActivity(i);
        		} catch (PackageManager.NameNotFoundException e) {
        			
        			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:com.ubercab") ) );
        		    
        		}     		
        	}
        
      });
	}
	
}
