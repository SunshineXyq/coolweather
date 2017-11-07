package com.example.sunshinexu.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Yuanqiang.Xu on 2017/11/3.
 * 知止而不怠
 * 明心而不愚
 * 清静而不私
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More {
        @SerializedName("txt")
        public String info;
    }
}
