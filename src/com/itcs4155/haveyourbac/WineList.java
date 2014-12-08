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
 
public class WineList extends Activity {
	// Declare Variables
	ListView listview2;
	List<ParseObject> ob2;
	ProgressDialog wProgressDialog;
	ListViewAdapterWine adapter2;
	private List<Wine> Winelist = null;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// the view from listview_main = activity_drink_list.xml
		setContentView(R.layout.activity_wine_list);
		// Execute RemoteDataTask AsyncTask
		new RemoteDataTask().execute();
	}
 
	// RemoteDataTask AsyncTask
	private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			wProgressDialog = new ProgressDialog(WineList.this);
			// Set progressdialog title
			wProgressDialog.setTitle("Gathering Wine Database");
			// Set progressdialog message
			wProgressDialog.setMessage("Loading...");
			wProgressDialog.setIndeterminate(false);
			// Show progressdialog
			wProgressDialog.show();
		}
 
		@Override
		protected Void doInBackground(Void... params) {
			// Create the array
			Winelist = new ArrayList<Wine>();
			try {
				// Locate the class table named "WineList" in Parse.com
				ParseQuery<ParseObject> winequery = new ParseQuery<ParseObject>("WineList");
				// by ascending
				winequery.orderByAscending("type");
				winequery.setLimit(100);
				ob2 = winequery.find();
				for (ParseObject wine : ob2) {
					Wine map2 = new Wine();
					map2.setWine((String) wine.get("type"));
					map2.setAlcoholContent((String) wine.get("alcoholContent").toString());
					Winelist.add(map2);
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
			listview2 = (ListView) findViewById(R.id.listview2);
			// Pass the results into ListViewAdapter.java
			adapter2 = new ListViewAdapterWine(WineList.this, Winelist);
			// Binds the Adapter to the ListView
			listview2.setAdapter(adapter2);
			// Close the progressdialog
			wProgressDialog.dismiss();
		}
	}
}