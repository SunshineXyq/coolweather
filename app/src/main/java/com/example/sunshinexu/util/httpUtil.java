package com.example.sunshinexu.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Yuanqiang.Xu on 2017/11/2.
 * 知止而不怠
 * 明心而不愚
 * 清静而不私
 */

public class httpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
