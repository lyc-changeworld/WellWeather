package com.example.achuan.wellweather.util;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.example.achuan.wellweather.db.City;
import com.example.achuan.wellweather.db.County;
import com.example.achuan.wellweather.db.Province;
import com.example.achuan.wellweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by achuan on 17-1-7.
 * 功能：多功能的工具类
 */

public class Utility {

    /*1-解析和处理服务器返回的省级数据*/
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                //创建数组实例来存储服务器返回的数据
                JSONArray allProvinces = new JSONArray(response);
                //循环遍历数组,取出每个元素,每个元素都是一个JSONObject对象,并取出元素对应的数据信息
                for (int i = 0; i <allProvinces.length(); i++) {
                    //先拿到对象数组中对应的JSONObject对象
                    JSONObject provinceObject=allProvinces.getJSONObject(i);
                    //创建一个Province实例,映射于Province表中的一行数据
                    Province province=new Province();
                    //为对应的属性赋值
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    //将数据存储到对应的表中
                    province.save();
                }
                return true;//最后别忘了返回true,代表数据存储成功
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /*2-解析和处理服务器返回的市级数据*/
    public static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            try {
                //创建数组实例来存储服务器返回的数据
                JSONArray allCities = new JSONArray(response);
                //循环遍历数组,取出每个元素,每个元素都是一个JSONObject对象,并取出元素对应的数据信息
                for (int i = 0; i <allCities.length(); i++) {
                    //先拿到对象数组中对应的JSONObject对象
                    JSONObject cityObject=allCities.getJSONObject(i);
                    //创建一个City实例,映射于City表中的一行数据
                    City city=new City();
                    //为对应的属性赋值
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    //将数据存储到对应的表中
                    city.save();
                }
                return true;//最后别忘了返回true,代表数据存储成功
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /*3-解析和处理服务器返回的县级数据*/
    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try {
                //创建数组实例来存储服务器返回的数据
                JSONArray allCounties = new JSONArray(response);
                //循环遍历数组,取出每个元素,每个元素都是一个JSONObject对象,并取出元素对应的数据信息
                for (int i = 0; i <allCounties.length(); i++) {
                    //先拿到对象数组中对应的JSONObject对象
                    JSONObject countyObject=allCounties.getJSONObject(i);
                    //创建一个County实例,映射于County表中的一行数据
                    County county=new County();
                    //为对应的属性赋值
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    //将数据存储到对应的表中
                    county.save();
                }
                return true;//最后别忘了返回true,代表数据存储成功
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /*4-解析天气JSON数据成Weather实体类对象*/
    public static Weather handleWeatherResponse(String response){
        try {
            //先把JSON数据转换成JSONObject对象
            JSONObject jsonObject=new JSONObject(response);
            //获取"HeWeather5"字段下的内容,并存储到JOSN数组对象中
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather5");
            //由于"HeWeather5"数值中只有一个元素,这里提取第一个元素成分即可
            String weatherInfo=jsonArray.getJSONObject(0).toString();
            //利用Gson对象来将数据转换成Weather对象
            Gson gson=new Gson();
            return gson.fromJson(weatherInfo,Weather.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName
     *            是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

}
