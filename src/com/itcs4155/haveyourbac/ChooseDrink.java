package com.itcs4155.haveyourbac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ChooseDrink extends Activity {

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 1) {
			setResult(1,data);
			finish();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_drink);
		
		final ImageButton drinkTypeBeer = (ImageButton)findViewById(R.id.chooseBeerButton);
		final ImageButton drinkTypeLiq = (ImageButton)findViewById(R.id.chooseLiquorButton);
		final ImageButton drinkTypeWine = (ImageButton)findViewById(R.id.chooseWineButton);
		
		
		drinkTypeBeer.setOnClickListener(new View.OnClickListener(){
			
    	public void onClick(View view){
    		Intent intent = new Intent(getBaseContext(), DrinkList.class);
    		startActivityForResult(intent, 1);
    	}
    
		});
		drinkTypeLiq.setOnClickListener(new View.OnClickListener(){
			
	    	public void onClick(View view){
	    		Intent intent = new Intent(getBaseContext(), AlcoholList.class);
	    		startActivityForResult(intent, 1);
	    	}
	    
			});
		drinkTypeWine.setOnClickListener(new View.OnClickListener(){
			
	    	public void onClick(View view){
	    		Intent intent = new Intent(getBaseContext(), WineList.class);
	    		startActivityForResult(intent, 1);
	    	}
	    
			});
		
		
		
	}
}
