package com.example.achuan.wellweather.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.achuan.wellweather.app.App;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());//设置布局文件
        ButterKnife.bind(this);//初始化控件加载
        initEventAndData();//初始化事件和数据操作
        App.getInstance().addActivity(this);//存储活动对象到集合中
        /*实现背景图和状态栏融合的方法*/
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();//获取装饰view的对象
            //设置活动的布局为全屏并显示在状态栏上面
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//布局全屏
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;//布局浮到状态栏上面
            decorView.setSystemUiVisibility(option);
            //最后别忘了设置状态栏为透明色
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().removeActivity(this);
    }
    protected abstract int getLayout();//添加布局文件
    protected abstract void initEventAndData();//初始化事件及数据
}
