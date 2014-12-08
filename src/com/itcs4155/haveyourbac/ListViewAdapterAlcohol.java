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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapterAlcohol extends BaseAdapter implements Filterable {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	//ImageLoader imageLoader;
	private List<Alcohol> Alcohollist = null;
	private ArrayList<Alcohol> arraylist;
	private ArrayList<Alcohol> mStringFilterList;
	ValueFilter valueFilter;

	public ListViewAdapterAlcohol(Context context,
			List<Alcohol> Alcohollist) {
		super();
		this.context = context;
		this.Alcohollist = Alcohollist;
		inflater = LayoutInflater.from(context);
		this.arraylist = new ArrayList<Alcohol>();
		this.arraylist.addAll(Alcohollist);
		mStringFilterList = arraylist;
	}

	public class ViewHolder {
		TextView drink;
		TextView type;
		TextView alcoholContent;
	}

	@Override
	public int getCount() {
		return Alcohollist.size();
	}

	@Override
	public Object getItem(int position) {
		return Alcohollist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			holder = new ViewHolder();
			view = inflater.inflate(R.layout.alcohol_listview_item, null);
			holder.drink = (TextView) view.findViewById(R.id.drink);
			holder.type = (TextView) view.findViewById(R.id.type);
			holder.alcoholContent = (TextView) view.findViewById(R.id.alcoholContent5);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		// Set the results into TextViews
		holder.drink.setText(Alcohollist.get(position).getDrink());
		holder.type.setText(Alcohollist.get(position).getType());
		holder.alcoholContent.setText(Alcohollist.get(position)
				.getAlcoholContent());
		// Set the results into ImageView
//		imageLoader.DisplayImage(Alcohollist.get(position).getFlag(),
//				holder.flag);
		// Listen for ListView Item Click
		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Send single item click data to SingleItemView Class
				Intent intent = new Intent(context, SingleItemViewAlcohol.class);
				// Pass all data rank
				intent.putExtra("drink",
						(Alcohollist.get(position).getDrink()));
				// Pass all data country
				intent.putExtra("type",
						(Alcohollist.get(position).getType()));
				// Pass all data population
				intent.putExtra("alcoholContent",
						(Alcohollist.get(position).getAlcoholContent()));
//				// Pass all data flag
//				intent.putExtra("flag",
//						(Drinklist.get(position).getFlag()));
				// Start SingleItemView Class
				context.startActivity(intent);
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
	            ArrayList<Alcohol> filterList=new ArrayList<Alcohol>();
	            for(int i=0;i<mStringFilterList.size();i++)
	            {
	                if((mStringFilterList.get(i).getType().toUpperCase()).contains(constraint.toString().toUpperCase()) || (mStringFilterList.get(i).getDrink().toUpperCase()).contains(constraint.toString().toUpperCase()))
	                {
	                	Alcohol drink = new Alcohol();
	                    drink.setDrink(mStringFilterList.get(i).getDrink());
	                    drink.setType(mStringFilterList.get(i).getType());
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
	        Alcohollist=(List<Alcohol>) results.values;
	        notifyDataSetChanged();
	    }
	}

}