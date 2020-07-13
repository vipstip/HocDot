package com.billi.hocdot.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.billi.hocdot.R;

import java.util.List;
import java.util.Map;

public class CustomExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listTitle;
    private Map<String,List<String>> listItem;

    public CustomExpandableListAdapter(Context context, List<String> listTitle, Map<String,List<String>> listItem) {
        this.context = context;
        this.listTitle = listTitle;
        this.listItem = listItem;
    }

    @Override
    public int getGroupCount() {
        return listTitle.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if (listItem.get(listTitle.get(i)) != null){
            return listItem.get(listTitle.get(i)).size();
        } else return 0;
    }

    @Override
    public Object getGroup(int i) {
        return listTitle.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listItem.get(listTitle.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String title = (String) getGroup(i);
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_group,null);
        }
        TextView txtTitle = (TextView) view.findViewById(R.id.list_title);
        txtTitle.setTypeface(null, Typeface.BOLD);
        txtTitle.setText(title);
        ImageView icList = view.findViewById(R.id.ic_lsGroup);
        switch (i){
            case 0:
                icList.setImageResource(R.drawable.ic_search_black_24dp);
                break;
            case 1:
                icList.setImageResource(R.drawable.ic_replay);
                break;
            case 2:
                icList.setImageResource(R.drawable.ic_table);
                break;
        }
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String title = (String) getChild(i,i1);
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item,null);
        }
        TextView txtChild = (TextView) view.findViewById(R.id.expandableListitem);
        txtChild.setText(title);

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
