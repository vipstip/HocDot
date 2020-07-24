package com.billi.hocdot.Interface;

public interface NavigationManage {
    void showFragment(String title);
    void showFragmentBangTinh();
    void showFragmentChild(String lop, String mon);
    void showFragmentCombo(String lop, String mon, String tenCombo);
    void showFragmentBook(String lop, String mon, String tenCombo);
    void showFragmentPost(String lop, String mon, String tenSach, String tenChuong, String tenBai, Boolean isCombo);
    void backFragment();
}
