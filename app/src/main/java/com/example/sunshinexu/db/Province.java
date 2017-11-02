package com.example.sunshinexu.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Yuanqiang.Xu on 2017/11/2.
 * 知止而不怠
 * 明心而不愚
 * 清静而不私
 */

public class Province extends DataSupport {
    private  int id;
    private String provineName;
    private  int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvineName() {
        return provineName;
    }

    public void setProvineName(String provineName) {
        this.provineName = provineName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
