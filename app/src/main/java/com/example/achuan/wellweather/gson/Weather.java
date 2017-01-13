package com.example.achuan.wellweather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by achuan on 17-1-11.
 * 功能：完整版的天气JSON数据的实体类
 *{
 HeWeather5: [
 {
    basic: {},
  daily_forecast: [],
  hourly_forecast: [],
 now: {},
 status: "ok",
 suggestion: {}
 }
 ]
 }
 */

public class Weather {

    public String status;
    public Basic basic;
    public Now now;
    public Suggestion suggestion;
    public Aqi aqi;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

}
