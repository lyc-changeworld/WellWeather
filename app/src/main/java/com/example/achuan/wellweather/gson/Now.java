package com.example.achuan.wellweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by achuan on 17-1-11.
 * 功能：GSON数据包中now部分的数据模型的实体类
 * now: {
                   cond: {
 code: "300",
                   txt: "阵雨"
 },
 fl: "11",
 hum: "95",
 pcpn: "0.2",
 pres: "1023",
                 tmp: "5",
 vis: "5",
 wind: {
 deg: "30",
 dir: "北风",
 sc: "5-6",
 spd: "26"
 }
 },
 */

public class Now {
    @SerializedName("tmp")
    public String temperature;
    //定义内部类
    public class More{
        @SerializedName("txt")
        public String info;
    }
    @SerializedName("cond")
    public More more;

}
