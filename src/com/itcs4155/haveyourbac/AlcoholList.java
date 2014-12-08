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
 
public class AlcoholList extends Activity {
	// Declare Variables
	ListView listview;
	List<ParseObject> ob;
	ProgressDialog mProgressDialog;
	ListViewAdapterAlcohol adapter;
	private List<Alcohol> Alcohollist = null;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// the view from listview_main = activity_drink_list.xml
		setContentView(R.layout.activity_alcohol_list);
		// Execute RemoteDataTask AsyncTask
		new RemoteDataTask().execute();
	}
 
	// RemoteDataTask AsyncTask
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(AlcoholList.this);
			// Set progressdialog title
			mProgressDialog.setTitle("Database of Alcohols");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}
 
		@Override
		protected Void doInBackground(Void... params) {
			// Create the array
			Alcohollist = new ArrayList<Alcohol>();
			try {
				// Locate the class table named "Country" in Parse.com
				ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
						"LiquorList");
				// Locate the column named "ranknum" in Parse.com and order list
				// by ascending
				query.orderByAscending("type");
				query.setLimit(300);
				ob = query.find();
				for (ParseObject liquor : ob) {
					Alcohol map = new Alcohol();
					map.setDrink((String) liquor.get("drink"));
					map.setType((String) liquor.get("type"));
					map.setAlcoholContent((String) liquor.get("alcoholContent").toString());
					Alcohollist.add(map);
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
			listview = (ListView) findViewById(R.id.listview_alcohol);
			// Pass the results into ListViewAdapter.java
			adapter = new ListViewAdapterAlcohol(AlcoholList.this, Alcohollist);
			// Binds the Adapter to the ListView
			listview.setAdapter(adapter);
			// Close the progressdialog
			mProgressDialog.dismiss();
		}
	}
}