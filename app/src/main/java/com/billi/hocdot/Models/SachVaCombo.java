package com.billi.hocdot.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SachVaCombo {
    private List<String> listTenSach;
    private Map<String,String> hinhanhtheoTenSach;
    private List<String> listTenCombo;
    private Map<String,String> hinhanhtheoTenCombo;


    public SachVaCombo(){
        listTenSach = new ArrayList<>();
        hinhanhtheoTenSach = new HashMap<>();

        listTenCombo = new ArrayList<>();
        hinhanhtheoTenCombo = new HashMap<>();
    }

    public void setSach(String tensach,String hinh){
        listTenSach.add(tensach);
        hinhanhtheoTenSach.put(tensach,hinh);
    }

    public void setCombo(String tencombo,String hinh){
        listTenCombo.add(tencombo);
        hinhanhtheoTenCombo.put(tencombo,hinh);
    }

    public List<String> getListTenSach() {
        return listTenSach;
    }

    public Map<String, String> getHinhanhtheoTenSach() {
        return hinhanhtheoTenSach;
    }

    public List<String> getListTenCombo() {
        return listTenCombo;
    }

    public Map<String, String> getHinhanhtheoTenCombo() {
        return hinhanhtheoTenCombo;
    }
}
