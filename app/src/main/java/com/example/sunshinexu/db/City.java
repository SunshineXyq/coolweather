package com.example.sunshinexu.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Yuanqiang.Xu on 2017/11/2.
 * 知止而不怠
 * 明心而不愚
 * 清静而不私
 */

public class City extends DataSupport {
    private int id;
    private String cityName;
    private int cityCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }
}