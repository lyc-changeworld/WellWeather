package com.example.achuan.wellweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by achuan on 17-1-11.
 * 功能：GSON数据包中basic部分的数据模型的实体类
 * basic: {
                         city: "抚州",
 cnty: "中国",
                        id: "CN101240401",
 lat: "26.180000",
 lon: "119.362000",
                     update: {
                        loc: "2017-01-11 08:51",
 utc: "2017-01-11 00:51"
 }
 },
 */

public class Basic {
    /*使用@SerializedName注解的方式让JSON字段和Java字段间建立映射关系*/

    @SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;

    //定义内部类
    public class Update{
        @SerializedName("loc")
        public String updateTime;
    }
    public Update update;

}
