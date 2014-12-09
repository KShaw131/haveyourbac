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

public class BACCarrier {
	
	private String brand;
	private String drink;
	private String alcoholContent;
	
	public String getBrand() {
		Log.d("MYKEY", brand);
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getDrink() {
		return drink;
	}
	public void setDrink(String drink) {
		this.drink = drink;
	}
	public String getAlcoholContent() {
		return alcoholContent;
	}
	public void setAlcoholContent(String alcoholContent) {
		this.alcoholContent = alcoholContent;
	}
	
	
	
	
	
}