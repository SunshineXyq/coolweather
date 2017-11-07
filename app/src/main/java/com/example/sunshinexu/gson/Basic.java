package com.example.sunshinexu.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yuanqiang.Xu on 2017/11/3.
 * 知止而不怠
 * 明心而不愚
 * 清静而不私
 */

public class Basic {
    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    public class Update {
        @SerializedName("loc")
        public String upadateTime;
    }

}
