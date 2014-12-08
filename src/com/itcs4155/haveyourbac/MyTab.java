package com.itcs4155.haveyourbac;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
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
		String drink = i.getStringExtra("drink");
		// Get the result of country
		String brand = i.getStringExtra("brand");
		// Get the result of population
		String alcoholContent = i.getStringExtra("alcoholContent");

		final TextView txtdrink = (TextView) findViewById(R.id.lastDrinkName);
		final TextView txtbrand = (TextView) findViewById(R.id.lastDrinkDetails);
		final TextView txtalcoholcontent = (TextView) findViewById(R.id.lastDrinkAlch);
		final TextView txtlastDrinkHeader = (TextView) findViewById(R.id.lastDrinkHeader);
		final TextView bacValue = (TextView)findViewById(R.id.bacValue);

		//Set results to the TextViews
		txtdrink.setText(drink);
		txtbrand.setText(brand);
		txtalcoholcontent.setText(alcoholContent);


//		if (drink!=null){
//			Calculator calc = new Calculator();
//			double value = calc.calculatedBAC();
//			bacValue.setText(value);
//		}else{
//			double ozOfAlch = 0;
//			Time now = new Time();
//			now.setToNow();
//		}
		


		final Button chooseDrink = (Button)findViewById(R.id.chooseDrinkButton);

		chooseDrink.setOnClickListener(new View.OnClickListener(){

			public void onClick(View view){
				Intent intent = new Intent(getBaseContext(), ChooseDrink.class);
				startActivity(intent);
			}

		});


		final Button reorderDrink = (Button)findViewById(R.id.reorderDrink);
		if(drink==null){
			reorderDrink.setVisibility(View.GONE);
			reorderDrink.setEnabled(false);
			txtlastDrinkHeader.setVisibility(View.GONE);
		}else{
			reorderDrink.setVisibility(View.VISIBLE);
			reorderDrink.setEnabled(true);
			txtlastDrinkHeader.setVisibility(View.VISIBLE);
		}

		reorderDrink.setOnClickListener(new View.OnClickListener(){

			public void onClick(View view){
				//Readd Previous Drink to Tab Array List
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
