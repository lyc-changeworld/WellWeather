package com.example.achuan.wellweather;

import android.content.Intent;
import android.os.Bundle;

import com.example.achuan.wellweather.base.BaseActivity;
import com.example.achuan.wellweather.util.SharedPreferenceUtil;

public class MainActivity extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
    @Override
    protected void initEventAndData() {
        //已经缓存了天气数据时将直接跳转到weather显示界面
        if(SharedPreferenceUtil.getWeatherInfo()!=null){
            Intent intent=new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
