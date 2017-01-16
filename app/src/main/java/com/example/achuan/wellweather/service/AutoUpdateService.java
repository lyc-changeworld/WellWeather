package com.example.achuan.wellweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.example.achuan.wellweather.app.Constants;
import com.example.achuan.wellweather.gson.Weather;
import com.example.achuan.wellweather.util.HttpUtil;
import com.example.achuan.wellweather.util.SharedPreferenceUtil;
import com.example.achuan.wellweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //定时执行的任务方法
        updateWeatherAndBingPic();
        //先获得系统定时服务管理者实例
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        //设置目标时间点
        long triggerAtTime = SystemClock.elapsedRealtime()//系统开机至今的时间毫秒数
                + Constants.anHour * SharedPreferenceUtil.getAutoWeatherUpdateTime();//设定的定时毫秒数
        //设置一个意图,任务是启动本身的后台服务,这样就形成了轮回定时的效果
        Intent service_intent = new Intent(this, AutoUpdateService.class);
        //创建一个延缓的意图
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, service_intent, 0);
        manager.cancel(pendingIntent);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,//系统开机的时间
                triggerAtTime,//目标时间点,即意图事件相应的时间
                pendingIntent);//到了（系统开机时间+开机后计算的时间毫秒数）时,启动这个意图
        return super.onStartCommand(intent, flags, startId);
    }

    /*需要定时执行的方法体*/
    private void updateWeatherAndBingPic() {
        String weatherInfo = SharedPreferenceUtil.getWeatherInfo();
        if (weatherInfo != null) {
            //解析获取到天气信息实例对象
            Weather weather = Utility.handleWeatherResponse(weatherInfo);
            String weatherId = weather.basic.weatherId;
            //组装请求链接
            String weatherUrl = Constants.HEFENG_WEATHER_V5_ADDRESS + "weather?city=" + weatherId +
                    "&key=" + Constants.HEFENG_WEATHER_GUO_LIN_KEY;
            //执行天气信息的网络请求
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    Weather weather1 = Utility.handleWeatherResponse(responseData);
                    if (weather1 != null && "ok".equals(weather1.status)) {
                        //把JSON数据存储到本地文件
                        SharedPreferenceUtil.setWeatherInfo(responseData);
                    }
                }
            });
            //执行必应每日一图的网络请求
            HttpUtil.sendOkHttpRequest(Constants.GUO_LIN_BING_PICTURE_ADDRESS, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String bingPicAddress=response.body().string();
                    SharedPreferenceUtil.setBingPicAddress(bingPicAddress);//更新地址
                }
            });
        }
    }

}
