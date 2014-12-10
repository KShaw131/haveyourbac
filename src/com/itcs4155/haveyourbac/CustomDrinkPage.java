package com.itcs4155.haveyourbac;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class CustomDrinkPage extends Activity {
	
	/* Spinner where user sets up the custom drink*/
	private Spinner drinkTypeSelector;
	private Button submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_drink_page);
		
		addListenerOnButton();
		addListenerOnSpinnerItemSelection();
	}
	
	public void addListenerOnSpinnerItemSelection() {
		drinkTypeSelector = (Spinner) findViewById(R.id.typeSelector);
	  }
	
	public void addListenerOnButton() {
		 
		drinkTypeSelector = (Spinner) findViewById(R.id.typeSelector);
		submit = (Button) findViewById(R.id.saveCustomDrink);
	 
		submit.setOnClickListener(new OnClickListener(){
	 
		  public void onClick(View v) {		  
		    Toast.makeText(CustomDrinkPage.this, "OnClickListener : " + "\nSpinner 1 : "+ String.valueOf(drinkTypeSelector.getSelectedItem()), Toast.LENGTH_SHORT).show();
		    Intent intent = new Intent(getBaseContext(), MyTab.class);
		    intent.putExtra("customType", String.valueOf(drinkTypeSelector.getSelectedItem()));
		    String customAlcoholContent = "20.0";
		    intent.putExtra("customAlcoholContent", customAlcoholContent);
	        setResult(2, intent);
	        finish();
		  }
	 
		});
	  }
}
