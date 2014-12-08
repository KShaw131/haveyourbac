package com.itcs4155.haveyourbac;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	//ImageLoader imageLoader;
	private List<Drink> Drinklist = null;
	private ArrayList<Drink> arraylist;

	public ListViewAdapter(Context context,
			List<Drink> Drinklist) {
		this.context = context;
		this.Drinklist = Drinklist;
		inflater = LayoutInflater.from(context);
		this.arraylist = new ArrayList<Drink>();
		this.arraylist.addAll(Drinklist);
		//imageLoader = new ImageLoader(context);
	}

	public class ViewHolder {
		TextView beer;
		TextView brand;
		TextView alcoholContent;
	}

	@Override
	public int getCount() {
		return Drinklist.size();
	}

	@Override
	public Object getItem(int position) {
		return Drinklist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.listview_item, null);
			// Locate the TextViews in listview_item.xml
			holder.beer = (TextView) view.findViewById(R.id.beer);
			holder.brand = (TextView) view.findViewById(R.id.brand);
			holder.alcoholContent = (TextView) view.findViewById(R.id.alcoholContent);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.beer.setText(Drinklist.get(position).getBeer());
		holder.brand.setText(Drinklist.get(position).getBrand());
		holder.alcoholContent.setText(Drinklist.get(position)
				.getAlcoholContent());
		// Set the results into ImageView
//		imageLoader.DisplayImage(Drinklist.get(position).getFlag(),
//				holder.flag);
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(context, SingleItemView.class);
				// Pass all data rank
				intent.putExtra("beer",
						(Drinklist.get(position).getBeer()));
				// Pass all data country
				intent.putExtra("brand",
						(Drinklist.get(position).getBrand()));
				// Pass all data population
				intent.putExtra("alcoholContent",
						(Drinklist.get(position).getAlcoholContent()));
//				// Pass all data flag
//				intent.putExtra("flag",
//						(Drinklist.get(position).getFlag()));
				// Start SingleItemView Class
				context.startActivity(intent);
			}
		});
		return view;
	}

}
