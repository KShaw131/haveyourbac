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

public class ListViewAdapter extends BaseAdapter implements Filterable {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	//ImageLoader imageLoader;
	private List<Drink> Drinklist = null;
	private ArrayList<Drink> arraylist;
	private ArrayList<Drink> mStringFilterList;
	ValueFilter valueFilter;

	public ListViewAdapter(Context context,
			List<Drink> Drinklist) {
		this.context = context;
		this.Drinklist = Drinklist;
		inflater = LayoutInflater.from(context);
		this.arraylist = new ArrayList<Drink>();
		this.arraylist.addAll(Drinklist);
		mStringFilterList = arraylist;
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
		if (position % 2 == 0){
		    view.setBackgroundResource(R.drawable.alterselector1);
		} else {
		    view.setBackgroundResource(R.drawable.alterselector2);
		}
		// Set the results into TextViews
		holder.beer.setText(Drinklist.get(position).getBeer());
		holder.brand.setText(Drinklist.get(position).getBrand());
		holder.alcoholContent.setText(Drinklist.get(position)
				.getAlcoholContent());
		
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
	            ArrayList<Drink> filterList=new ArrayList<Drink>();
	            for(int i=0;i<mStringFilterList.size();i++)
	            {
	                if((mStringFilterList.get(i).getBeer().toUpperCase()).contains(constraint.toString().toUpperCase()) || (mStringFilterList.get(i).getBrand().toUpperCase()).contains(constraint.toString().toUpperCase()))
	                {
	                	Drink drink = new Drink();
	                    drink.setBeer(mStringFilterList.get(i).getBeer());
	                    drink.setBrand(mStringFilterList.get(i).getBrand());
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
	        Drinklist=(List<Drink>) results.values;
	        notifyDataSetChanged();
	    }
	}

}
