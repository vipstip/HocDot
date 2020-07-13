package com.billi.hocdot.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.billi.hocdot.Contans.MonHoc;
import com.billi.hocdot.Helpers.FragmentNavigationManage;
import com.billi.hocdot.Interface.NavigationManage;
import com.billi.hocdot.MainActivity;
import com.billi.hocdot.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

public class AdapterMonHoc extends BaseAdapter {
    private Context context;
    private MonHoc monHoc;
    private String lop;
    private int layout;
    private NavigationManage navigationManage;

    public AdapterMonHoc(Context context, int layout, MonHoc monHoc, String lop, MainActivity mainActivity) {
        this.context = context;
        this.monHoc = monHoc;
        this.lop = lop;
        this.layout = layout;

        navigationManage = FragmentNavigationManage.getInstance(mainActivity);
    }

    @Override
    public int getCount() {
        return monHoc.getTenSach().size();
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
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigationManage.showFragmentChild(lop,monHoc.getTenSach().get(i));
            }
        });
        viewHolder.txtGridMonHoc.setText(monHoc.getTenSach().get(i));
        switch (monHoc.getTenSach().get(i)) {
            case "Môn Toán":
                setIcon(viewHolder.imgGridMonHoc,R.drawable.ic_toan);
                break;
            case "Môn Văn":
                setIcon(viewHolder.imgGridMonHoc,R.drawable.ic_ngu_van);
                break;
            case "Môn Tiếng Anh":
                setIcon(viewHolder.imgGridMonHoc,R.drawable.ic_tieng_anh);
                break;
            case "Môn Vật Lý":
                setIcon(viewHolder.imgGridMonHoc,R.drawable.ic_vat_ly);
                break;
            case "Môn Hoá Học":
                setIcon(viewHolder.imgGridMonHoc,R.drawable.ic_hoa_hoc);
                break;
            case "Môn Sinh Học":
                setIcon(viewHolder.imgGridMonHoc,R.drawable.ic_sinh_hoc);
                break;
            case "Môn Lịch Sử":
                setIcon(viewHolder.imgGridMonHoc,R.drawable.ic_lich_su);
                break;
            case "Môn Địa Lý":
                setIcon(viewHolder.imgGridMonHoc,R.drawable.ic_dia_ly);
                break;
            case "Môn Tin Học":
                setIcon(viewHolder.imgGridMonHoc,R.drawable.ic_tin_hoc);
                break;
            case "Môn GDCD":
                setIcon(viewHolder.imgGridMonHoc,R.drawable.ic_gdcd);
                break;
            case "Môn Công Nghệ":
                setIcon(viewHolder.imgGridMonHoc,R.drawable.ic_cong_nghe);
                break;
            case "Môn GDQP":
                setIcon(viewHolder.imgGridMonHoc,R.drawable.ic_gdqp);
                break;
            default:
                setIcon(viewHolder.imgGridMonHoc,R.drawable.ic_no_image);
        }


        return view;
    }

    private static class ViewHolder{
        TextView txtGridMonHoc;
        ImageView imgGridMonHoc;
    }
    private void setIcon (ImageView image, int icon){
        Glide.with(context)
                .load(icon)
                .override(160, 160)
                .transform(new CenterCrop(), new RoundedCorners(10))
                .into(image);
    }
}
