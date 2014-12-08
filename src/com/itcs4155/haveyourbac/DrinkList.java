package com.itcs4155.haveyourbac;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
 
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
 
public class DrinkList extends Activity {
	// Declare Variables
	ListView listview;
	List<ParseObject> ob;
	ProgressDialog mProgressDialog;
	ListViewAdapter adapter;
	private List<Drink> Drinklist = null;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// the view from listview_main = activity_drink_list.xml
		setContentView(R.layout.activity_drink_list);
		// Execute RemoteDataTask AsyncTask
		new RemoteDataTask().execute();
	}
 
	// RemoteDataTask AsyncTask
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(DrinkList.this);
			// Set progressdialog title
			mProgressDialog.setTitle("Gathering Beer Database");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}
 
		@Override
		protected Void doInBackground(Void... params) {
			// Create the array
			Drinklist = new ArrayList<Drink>();
			try {
				// Locate the class table named "Country" in Parse.com
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
						"BeerList");
				// Locate the column named "ranknum" in Parse.com and order list
				// by ascending
				query.orderByAscending("beer");
				query.setLimit(300);
				ob = query.find();
				for (ParseObject beer : ob) {
					Drink map = new Drink();
					map.setBeer((String) beer.get("beer"));
					map.setBrand((String) beer.get("brand"));
					map.setAlcoholContent((String) beer.get("alcoholContent").toString());
					Drinklist.add(map);
				}
			} catch (ParseException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return null;
		}
 
		@Override
		protected void onPostExecute(Void result) {
			// Locate the listview in listview_main.xml
			listview = (ListView) findViewById(R.id.listview);
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapter(DrinkList.this, Drinklist);
			// Binds the Adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}
}