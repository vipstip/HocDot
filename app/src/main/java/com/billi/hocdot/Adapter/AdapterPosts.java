package com.billi.hocdot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bil.bilmobileads.ADRewarded;
import com.billi.hocdot.Helpers.FragmentNavigationManage;
import com.billi.hocdot.Interface.NavigationManage;
import com.billi.hocdot.MainActivity;
import com.billi.hocdot.R;


import java.util.List;

public class AdapterPosts extends BaseAdapter {
    private Context context;
    private int layout;
    private String lop;
    private String mon;
    private String tensach;
    private String tenchuong;
    private List<String> lstPost;
    private NavigationManage navigationManage;
    private Boolean iscombo;
    private MainActivity mainActivity;
    public AdapterPosts(Context context, int layout, List<String> posts, String lop, String mon, String tensach, String tenchuong,Boolean iscombo, MainActivity mainActivity){
        this.context = context;
        this.layout = layout;
        this.lstPost = posts;
        this.iscombo = iscombo;
        this.lop = lop;
        this.mon = mon;
        this.tensach = tensach;
        this.tenchuong = tenchuong;
        this.mainActivity = mainActivity;
        navigationManage = FragmentNavigationManage.getInstance(mainActivity);
    }

    @Override
    public int getCount() {
        return lstPost.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder.txtSTT = view.findViewById(R.id.txtSTT);
            viewHolder.txtPost = view.findViewById(R.id.txtPost);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final String stt = String.valueOf(i + 1);
            viewHolder.txtSTT.setText(stt);
            viewHolder.txtPost.setText(lstPost.get(i));

        final AlphaAnimation click = new AlphaAnimation(1F, 0.2F);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(click);
                navigationManage.showFragmentPost(lop,mon,tensach,tenchuong,lstPost.get(i),iscombo);
            }
        });
        return view;
    }

    private static class ViewHolder{
        TextView txtSTT;
        TextView txtPost;

    }
}
