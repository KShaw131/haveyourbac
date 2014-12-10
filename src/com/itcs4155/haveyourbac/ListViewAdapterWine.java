package com.itcs4155.haveyourbac;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapterWine extends BaseAdapter implements Filterable {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	//
	private List<Wine> Winelist = null;
	private ArrayList<Wine> arraylist;
	private ArrayList<Wine> mStringFilterList;
	ValueFilter valueFilter;

	public ListViewAdapterWine(Context context,
			List<Wine> Winelist) {
		super();
		this.context = context;
		this.Winelist = Winelist;
		inflater = LayoutInflater.from(context);
		this.arraylist = new ArrayList<Wine>();
		this.arraylist.addAll(Winelist);
		mStringFilterList = arraylist;
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
		if (position % 2 == 0){
		    view.setBackgroundResource(R.drawable.alterselector1);
		} else {
		    view.setBackgroundResource(R.drawable.alterselector2);
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
				((Activity) context).startActivityForResult(intent, 1);
			}
		});
		return view;
	}
	
	@Override
	public Filter getFilter() {
	    if(valueFilter==null) {

	        valueFilter=new ValueFilter();
	    }

	    return valueFilter;
	}
	
	private class ValueFilter extends Filter {

	    //Invoked in a worker thread to filter the data according to the constraint.
	    @Override
	    protected FilterResults performFiltering(CharSequence constraint) {
	        FilterResults results=new FilterResults();
	        if(constraint!=null && constraint.length()>0){
	            ArrayList<Wine> filterList=new ArrayList<Wine>();
	            for(int i=0;i<mStringFilterList.size();i++)
	            {
	                if((mStringFilterList.get(i).getWine().toUpperCase()).contains(constraint.toString().toUpperCase()))
	                {
	                	Wine drink = new Wine();
	                    drink.setWine(mStringFilterList.get(i).getWine());
	                    drink.setAlcoholContent(mStringFilterList.get(i).getAlcoholContent());
	                    filterList.add(drink);
	                }
	            }
	            results.count=filterList.size();
	            results.values=filterList;
	        }else{
	            results.count=mStringFilterList.size();
	            results.values=mStringFilterList;
	        }
	        return results;
	    }


	    //Invoked in the UI thread to publish the filtering results in the user interface.
	    @SuppressWarnings("unchecked")
	    @Override
	    protected void publishResults(CharSequence constraint,
	            FilterResults results) {
	        Winelist=(List<Wine>) results.values;
	        notifyDataSetChanged();
	    }
	}

}
