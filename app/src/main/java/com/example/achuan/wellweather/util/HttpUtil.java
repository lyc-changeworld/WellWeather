package com.example.achuan.wellweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by achuan on 17-1-7.
 * 功能：网络处理的工具类
 */

public class HttpUtil {

    /*１－发起一条网络Http请求的方法*/
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        //先创建一个客户连接实例
        OkHttpClient client = new OkHttpClient();
        //(发起HTTP请求需要创建一个Request对象)
        //在build()执行前可以连缀多种方法来丰富该对象
        Request requestGet = new Request.Builder()
                .url(address)//设置目标的网络地址
                .build();
        //enqueue()方法内部已经开好了子线程了,然后会在子线程中执行HTTP请求,
        //并将最终的请求回调到okhttp3.Callback当中
        client.newCall(requestGet).enqueue(callback);
    }



}
