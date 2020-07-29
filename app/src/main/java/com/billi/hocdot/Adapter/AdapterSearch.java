package com.billi.hocdot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.billi.hocdot.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterSearch extends BaseAdapter implements Filterable {
    Context context;
    private List<String> lstSearch;
    private List<String> lstSearchtam;
    CustomFilter cs;
    public AdapterSearch(Context context,List<String> lstSearch) {
        this.context = context;
        this.lstSearch = lstSearch;
        this.lstSearchtam = lstSearch;
    }

    @Override
    public int getCount() {
        return lstSearch.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_suggestion_row,null);
            viewHolder.txtSearch = view.findViewById(R.id.txtSuggestionSearch);
            view.setTag(viewHolder);
        }else viewHolder = (ViewHolder) view.getTag();

        viewHolder.txtSearch.setText(lstSearch.get(i));
        return view;
    }

    public class ViewHolder{
        TextView txtSearch;
    }

    @Override
    public Filter getFilter() {
        if (cs == null){
            cs  = new CustomFilter();
        }
        return cs;
    }
    class CustomFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence !=null && charSequence.length()>0){
                charSequence = charSequence.toString().toUpperCase();
                List<String> lstSearchFilter = new ArrayList<>();
                for (int i = 0 ; i < lstSearchtam.size(); i++){
                    if (lstSearchtam.get(i).toUpperCase().contains(charSequence)){
                        lstSearchFilter.add(lstSearchtam.get(i));
                    }
                }
                results.count = lstSearchFilter.size();
                results.values = lstSearchFilter;
            } else {
                results.count = lstSearchtam.size();
                results.values = lstSearchtam;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            lstSearch = (List<String>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
