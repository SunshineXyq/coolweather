package com.example.sunshinexu.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Yuanqiang.Xu on 2017/11/3.
 * 知止而不怠
 * 明心而不愚
 * 清静而不私
 */

public class Weather {
    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;
    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
