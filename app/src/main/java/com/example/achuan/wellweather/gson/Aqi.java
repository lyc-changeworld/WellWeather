package com.example.achuan.wellweather.gson;

/**
 * Created by achuan on 17-1-12.
 * 功能：GSON数据包中aqi部分的数据模型的实体类
 * "aqi": {
 "city": {
                  "aqi": "60",
 "co": "0",
 "no2": "14",
 "o3": "95",
 "pm10": "67",
                  "pm25": "15",
 "qlty": "良",  //共六个级别，分别：优，良，轻度污染，中度污染，重度污染，严重污染
 "so2": "10"
 }
 },
 */

public class Aqi {
    public class AqiCity{
        public String aqi;
        public String pm25;
    }
    public AqiCity city;
}
