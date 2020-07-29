package com.billi.hocdot.Models;

import java.util.ArrayList;
import java.util.List;

public class MonHoc {
    private List<String> tenSach;

    public MonHoc(){
        tenSach = new ArrayList<>();
    }

    public List<String> getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach.add(tenSach);
    }

    public void clearSach(){
        tenSach.clear();
    }
    private void addFirstDropdow(){
        tenSach.add("----------Chọn môn----------");
    }
}
