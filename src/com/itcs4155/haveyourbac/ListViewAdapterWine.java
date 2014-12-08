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

public class ListViewAdapterWine extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	private List<Wine> Winelist = null;
	private ArrayList<Wine> arraylist;

	public ListViewAdapterWine(Context context,
			List<Wine> Winelist) {
		this.context = context;
		this.Winelist = Winelist;
		inflater = LayoutInflater.from(context);
		this.arraylist = new ArrayList<Wine>();
		this.arraylist.addAll(Winelist);
	}

	public class ViewHolder {
		TextView wine;
		TextView alcoholContent;
	}

	@Override
	public int getCount() {
		return Winelist.size();
	}

	@Override
	public Object getItem(int position) {
		return Winelist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.wine_listview_item, null);
			// Locate the TextViews in listview_item.xml
			holder.wine = (TextView) view.findViewById(R.id.wine);
			holder.alcoholContent = (TextView) view.findViewById(R.id.alcoholContent3);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.wine.setText(Winelist.get(position).getWine());
		holder.alcoholContent.setText(Winelist.get(position)
				.getAlcoholContent());
		
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(context, SingleItemViewWine.class);
				// Pass all data wine
				intent.putExtra("wine", (Winelist.get(position).getWine()));
				intent.putExtra("alcoholContent", (Winelist.get(position).getAlcoholContent()));
				context.startActivity(intent);
			}
		});
		return view;
	}

}
