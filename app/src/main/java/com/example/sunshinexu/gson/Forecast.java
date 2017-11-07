package com.example.sunshinexu.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yuanqiang.Xu on 2017/11/3.
 * 知止而不怠
 * 明心而不愚
 * 清静而不私
 */

public class Forecast {
    public String date;

    @SerializedName("cond")
    public More more;

    @SerializedName("tmp")
    public Temperature temperature;

    public class More {
        @SerializedName("txt_d")
        public String info;
    }
    public class Temperature {
        public String max;
        public String min;
    }
}
