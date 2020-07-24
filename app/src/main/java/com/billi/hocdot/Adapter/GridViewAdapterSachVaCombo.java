package com.billi.hocdot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.billi.hocdot.Models.SachVaCombo;
import com.billi.hocdot.Helpers.FragmentNavigationManage;
import com.billi.hocdot.Interface.NavigationManage;
import com.billi.hocdot.MainActivity;
import com.billi.hocdot.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

public class GridViewAdapterSachVaCombo extends BaseAdapter {
    private Context context;
    private SachVaCombo sachVaCombo;
    private String lop;
    private String mon;
    private int layout;
    private NavigationManage navigationManage;

    public GridViewAdapterSachVaCombo(Context context, int layout, SachVaCombo sachVaCombo, String lop,String mon, MainActivity mainActivity) {
        this.context = context;
        this.layout = layout;
        this.sachVaCombo = sachVaCombo;
        this.lop = lop;
        this.mon = mon;
        navigationManage = FragmentNavigationManage.getInstance(mainActivity);
    }

    @Override
    public int getCount() {
        return sachVaCombo.getListTenSach().size() + sachVaCombo.getListTenCombo().size();
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
            viewHolder.txtGridMonHoc = view.findViewById(R.id.txtGirdMonHoc);
            viewHolder.imgGridMonHoc = view.findViewById(R.id.imgGridMonHoc);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        if (i < sachVaCombo.getListTenCombo().size() ){
                viewHolder.txtGridMonHoc.setText(sachVaCombo.getListTenCombo().get(i));
                Glide.with(context)
                        .load(sachVaCombo.getHinhanhtheoTenCombo().get(sachVaCombo.getListTenCombo().get(i)))
                        .override(200, 300)
                        .transform(new CenterCrop(),new RoundedCorners(10))
                        .into(viewHolder.imgGridMonHoc);
                view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        navigationManage.showFragmentCombo(lop,mon,sachVaCombo.getListTenCombo().get(i));
                    }
                });
        } else {
                viewHolder.txtGridMonHoc.setText(sachVaCombo.getListTenSach().get(i-sachVaCombo.getListTenCombo().size()));
                Glide.with(context)
                        .load(sachVaCombo.getHinhanhtheoTenSach().get(sachVaCombo.getListTenSach().get(i-sachVaCombo.getListTenCombo().size())))
                        .override(200, 300)
                        .transform(new CenterCrop(),new RoundedCorners(10))
                        .into(viewHolder.imgGridMonHoc);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    navigationManage.showFragmentBook(lop,mon,sachVaCombo.getListTenSach().get(i-sachVaCombo.getListTenCombo().size()));
                }
            });
        }

        return view;
    }

    private class ViewHolder{
        TextView txtGridMonHoc;
        ImageView imgGridMonHoc;

    }
}
