package com.example.sunshinexu.gson;

/**
 * Created by Yuanqiang.Xu on 2017/11/3.
 * 知止而不怠
 * 明心而不愚
 * 清静而不私
 */

public class AQI {
    public AQICity city;
    public class AQICity {
        public String aqi;
        public String pm25;
    }
}
