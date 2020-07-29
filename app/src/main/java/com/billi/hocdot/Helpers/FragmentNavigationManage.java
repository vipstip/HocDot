package com.billi.hocdot.Helpers;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.BuildConfig;
import com.billi.hocdot.Interface.NavigationManage;
import com.billi.hocdot.MainActivity;
import com.billi.hocdot.R;
import com.billi.hocdot.fragment.FragmentBangTinh;
import com.billi.hocdot.fragment.FragmentBook;
import com.billi.hocdot.fragment.FragmentChild;
import com.billi.hocdot.fragment.FragmentClass;
import com.billi.hocdot.fragment.FragmentCombo;
import com.billi.hocdot.fragment.FragmentPost;

public class FragmentNavigationManage implements NavigationManage {
    private static FragmentNavigationManage mInstance;
    private FragmentManager mfragmentManager;
    private MainActivity mainActivity;

    private boolean isCombo = false;
    private String lop;
    private String mon;
    private String tenCombo;
    private String tenSach;
    private String tenChuong;
    private String tenBai;

    public static FragmentNavigationManage getInstance(MainActivity mainActivity){
        if (mInstance == null)
            mInstance = new FragmentNavigationManage();
        mInstance.configure(mainActivity);
        return mInstance;
    }

    private void configure(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        mfragmentManager = mainActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragment(String title) {
        showFragment(FragmentClass.newInstance(title),false);
        this.lop = title;
    }

    @Override
    public void showFragmentBangTinh() {
        showFragment(FragmentBangTinh.newInstance(),false);
    }

    @Override
    public void showFragmentChild(String lop, String mon) {
        this.lop = lop;
        this.mon = mon;
        showFragment(FragmentChild.newInstance(lop,mon),false);
    }

    @Override
    public void showFragmentCombo(String lop, String mon, String tenCombo) {
        this.lop = lop;
        this.mon = mon;
        this.tenCombo = tenCombo;
        showFragment(FragmentCombo.newInstance(lop,mon,tenCombo),false);

    }

    @Override
    public void showFragmentBook(String lop, String mon, String tenSach) {
        this.lop = lop;
        this.mon = mon;
        this.tenSach = tenSach;
        showFragment(FragmentBook.newInstance(lop,mon,tenSach),false);
    }

    @Override
    public void showFragmentPost(String lop, String mon, String tenSach, String tenChuong,String tenBai,Boolean isCombo) {
        this.lop = lop;
        this.mon = mon;
        this.tenSach = tenSach;
        this.tenChuong = tenChuong;
        this.tenBai = tenBai;
        this.isCombo = isCombo;
        showFragment(FragmentPost.newInstance(lop,mon,tenSach,tenChuong,tenBai),false);
    }

    @Override
    public void backFragment() {
        backFragmentPrev();
    }

    private void showFragment(Fragment fragment, boolean b){
        FragmentTransaction fragmentTransaction = mfragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment,fragment.getClass().toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.addToBackStack(fragment.getClass().toString());
        fragmentTransaction.commit();
    }

    private void backFragmentPrev(){
        FragmentClass fragmentClass = FragmentClass.newInstance(lop);
        FragmentChild fragmentChild = FragmentChild.newInstance(lop,mon);
        FragmentCombo fragmentCombo = FragmentCombo.newInstance(lop,mon,tenCombo);
        FragmentBook fragmentBook = FragmentBook.newInstance(lop,mon,tenSach);
        FragmentPost fragmentPost = FragmentPost.newInstance(lop,mon,tenSach,tenChuong,tenBai);
        FragmentBangTinh fragmentBangTinh = FragmentBangTinh.newInstance();
        Fragment currentFragment = mfragmentManager.findFragmentById(R.id.container);

        if(currentFragment.getTag().equals(fragmentChild.getClass().toString())){
            showFragment(FragmentClass.newInstance(lop),false);
        }
        else if(currentFragment.getTag().equals(fragmentCombo.getClass().toString())||currentFragment.getTag().equals(fragmentBook.getClass().toString())){
            showFragment(FragmentChild.newInstance(lop,mon),false);
        }
        else if(currentFragment.getTag().equals(fragmentPost.getClass().toString())){
            if (isCombo){
                showFragment(FragmentCombo.newInstance(lop,mon,tenCombo),false);
            } else {
                showFragment(FragmentBook.newInstance(lop,mon,tenSach),false);
            }
        } else if (currentFragment.getTag().equals(fragmentBangTinh.getClass().toString())){
            showFragment(lop);
        }
        else if(currentFragment.getTag().equals(fragmentClass.getClass().toString())){
            new AlertDialog.Builder(mainActivity)
                    .setTitle("Bạn muốn thoát khỏi ứng dụng")
                    .setMessage("Bạn có chắc chắn?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            mainActivity.finish();
                            dialog.dismiss();
                        }
                    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            }).show();
        }

    }
}
