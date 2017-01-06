package com.example.achuan.wellweather.app;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by achuan on 17-1-6.
 * 功能：整个应用的配置文件
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //下面这句话的功能是将litepal.xml文件中的内容配置到应用中
        LitePal.initialize(this);
    }
}
