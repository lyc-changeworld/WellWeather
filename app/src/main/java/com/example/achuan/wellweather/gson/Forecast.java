package com.example.achuan.wellweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by achuan on 17-1-11.
 * 功能：GSON数据包中daily_forecast部分的数据模型的实体类
 * daily_forecast: [
                  {
 astro: {
 mr: "16:25",
 ms: "05:14",
 sr: "06:51",
 ss: "17:29"
 },
                  cond: {
 code_d: "305",
 code_n: "305",
                  txt_d: "小雨",
 txt_n: "小雨"
                },
                 date: "2017-01-11",
 hum: "88",
 pcpn: "0.4",
 pop: "98",
 pres: "1022",
                 tmp:  {
                    max: "8",
                    min: "6"
                   },
 uv: "4",
 vis: "10",
 wind: {
 deg: "65",
 dir: "东北风",
 sc: "微风",
 spd: "3"
 }
                  },
 {
 astro: {
 mr: "17:27",
 ms: "06:14",
 sr: "06:51",
 ss: "17:30"
 },
 cond: {
 code_d: "305",
 code_n: "305",
 txt_d: "小雨",
 txt_n: "小雨"
 },
 date: "2017-01-12",
 hum: "94",
 pcpn: "2.5",
 pop: "92",
 pres: "1020",
 tmp: {
 max: "7",
 min: "6"
 },
 uv: "1",
 vis: "5",
 wind: {
 deg: "49",
 dir: "东北风",
 sc: "微风",
 spd: "7"
 }
 },
 {
 astro: {
 mr: "18:29",
 ms: "07:11",
 sr: "06:51",
 ss: "17:30"
 },
 cond: {
 code_d: "104",
 code_n: "104",
 txt_d: "阴",
 txt_n: "阴"
 },
 date: "2017-01-13",
 hum: "95",
 pcpn: "3.2",
 pop: "100",
 pres: "1020",
 tmp: {
 max: "9",
 min: "6"
 },
 uv: "1",
 vis: "2",
 wind: {
 deg: "83",
 dir: "东北风",
 sc: "微风",
 spd: "8"
 }
 }
 ],
 */

public class Forecast {
    /*该实体类中只需定义单日天气的实体类,使用时通过集合类型来声明*/

    public String date;
    //定义内部类,并添加映射关系
    public class More{
        @SerializedName("txt_d")
        public String info;
    }
    @SerializedName("cond")
    public More more;

    public class Temperature{
        public String max;
        public String min;
    }
    @SerializedName("tmp")
    public Temperature temperature;

}
