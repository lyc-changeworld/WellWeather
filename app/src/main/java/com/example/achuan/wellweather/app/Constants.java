package com.example.achuan.wellweather.app;

/**
 * Created by achuan on 17-1-7.
 * 功能：该文件存放全局使用的静态不变量　
 * 　　　关键字：public static final
 */

public class Constants {

    //郭霖提供的天气服务的访问地址,可以获取到全国的省市县的具体数据
    public static final String GUO_LIN_WEATHER_ADDRESS ="http://guolin.tech/api/china";

    /*－－－－－－－－－－－－－－－－－－－－－和风天气相关－－－－－－－－－－－－－－－－－－－－*/
    //和风天气账号的个人认证key
    public static final String HEFENG_WEATHER_MY_KEY="ac4154148b0e4b83ae74ff1a0aa649ad";
    public static final String HEFENG_WEATHER_GUO_LIN_KEY="bc0418b57b2d4918819d3974ac1285d9";
    //和风天气v5接口地址
    public static final String HEFENG_WEATHER_V5_ADDRESS="https://free-api.heweather.com/v5/";

    /*必应每日一图的接口地址*/
    public static final String GUO_LIN_BING_PICTURE_ADDRESS=
            "http://guolin.tech/api/bing_pic";

    /*----------------------------------省市县的等级代号--------------------------------*/
    public static final int LEVEL_PROVINCE=100;
    public static final int LEVEL_CITY=101;
    public static final int LEVEL_COUNTY=102;

    /*－－－－－－－－－－－－－－－SharedPreferences文件存储信息的键的名称－－－－－－－－－－－－－－－－－－*/
    public static final String SP_WEATHER_INFO="weather_info";
    public static final String SP_BING_PIC_ADDRESS="bing_pic_address";
    public static final String SP_AUTO_UPDATE_TIME="weather_auto_update_time";


    /*－－－－－－－－－－－－－－－一些使用到的固定常量－－－－－－－－－－－－－－－－－－－－－－*/
    public static final int anHour=60*60*1000;//这是1小时的毫秒数


}
