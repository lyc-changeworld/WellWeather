package com.example.achuan.wellweather.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.achuan.wellweather.app.App;
import com.example.achuan.wellweather.app.Constants;


/**
 * Created by achuan on 16-9-10.
 * 功能：存储设置及一些全局的信息到SharedPreferences文件中
 */
public class SharedPreferenceUtil {
    //创建的SharedPreferences文件的文件名
    private static final String SHAREDPREFERENCES_NAME = "my_sp";

    //1-创建一个SharedPreferences文件
    public static  SharedPreferences getAppSp() {
        return App.getInstance().getSharedPreferences(
                SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }
    //2-天气信息的set和get方法
    public static String getWeatherInfo() {
        return getAppSp().getString(Constants.SP_WEATHER_INFO, null);
    }
    public static void setWeatherInfo(String weatherInfo) {
        getAppSp().edit().putString(Constants.SP_WEATHER_INFO,weatherInfo).commit();
    }
    //3-必应每日图片
    public static String getBingPicAddress(){
        return getAppSp().getString(Constants.SP_BING_PIC_ADDRESS,null);
    }
    public static void setBingPicAddress(String bingPicAddress){
        getAppSp().edit().putString(Constants.SP_BING_PIC_ADDRESS,bingPicAddress).commit();
    }


}
