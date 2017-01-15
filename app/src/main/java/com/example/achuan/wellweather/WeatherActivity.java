package com.example.achuan.wellweather;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.achuan.wellweather.app.Constants;
import com.example.achuan.wellweather.base.BaseActivity;
import com.example.achuan.wellweather.gson.Forecast;
import com.example.achuan.wellweather.gson.Weather;
import com.example.achuan.wellweather.util.HttpUtil;
import com.example.achuan.wellweather.util.SharedPreferenceUtil;
import com.example.achuan.wellweather.util.Utility;

import java.io.IOException;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends BaseActivity {

    @BindView(R.id.tv_cityName)
    TextView mTvCityName;
    @BindView(R.id.tv_updateTime)
    TextView mTvUpdateTime;
    @BindView(R.id.tv_degree)
    TextView mTvDegree;
    @BindView(R.id.tv_weatherInfo)
    TextView mTvWeatherInfo;
    @BindView(R.id.ll_forecast)
    LinearLayout mLlForecast;
    @BindView(R.id.tv_aqi)
    TextView mTvAqi;
    @BindView(R.id.tv_pm25)
    TextView mTvPm25;
    @BindView(R.id.tv_comfort)
    TextView mTvComfort;
    @BindView(R.id.tv_carWash)
    TextView mTvCarWash;
    @BindView(R.id.tv_sport)
    TextView mTvSport;
    @BindView(R.id.weather_layout)
    ScrollView mWeatherLayout;
    @BindView(R.id.iv_bingPic)
    ImageView mIvBingPic;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.bt_nav)
    Button mBtNav;
    @BindView(R.id.draw_layout)
    DrawerLayout mDrawLayout;

    private String mWeatherId;//用来记录当前选中的县的天气代号


    //添加布局文件
    @Override
    protected int getLayout() {
        return R.layout.activity_weather;
    }

    //初始化事件及数据
    @Override
    protected void initEventAndData() {
        String weather_info = SharedPreferenceUtil.getWeatherInfo();
        /*天气信息显示的判断*/
        if (weather_info != null) {
            /*有缓存时直接解析天气数据并显示出来*/
            //将缓存的JOSN数据解析成weather对象
            Weather weather = Utility.handleWeatherResponse(weather_info);
            showWeatherInfo(weather);
            mWeatherId = weather.basic.weatherId;
        } else {
            /*无缓存时去服务器查询天气*/
            //获取县城选择activity传递过来的县城天气代号
            mWeatherId = getIntent().getStringExtra("weather_id");
            mWeatherLayout.setVisibility(View.INVISIBLE);//暂时先隐藏内容显示布局
            requestWeather(mWeatherId);
        }
        /*必应图片加载的判断*/
        String bingPic = SharedPreferenceUtil.getBingPicAddress();
        if (bingPic != null) {//本地有缓存记录,直接加载显示图片
            Glide.with(WeatherActivity.this).//传入上下文(Context|Activity|Fragment)
                    load(bingPic).//加载图片,传入(URL地址｜资源id｜本地路径)
                    into(mIvBingPic);//将图片设置到具体某一个IV中
        } else {//本地无链接地址记录,进行网络请求来获取地址并加载显示
            loadBingPic();
        }
        /*刷新显示控件的初始化工作*/
        mSwipeRefresh.setColorSchemeResources(R.color.colorAccent);//设置进度条的颜色
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });
        /**/
        mBtNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开滑动菜单栏
                mDrawLayout.openDrawer(GravityCompat.START);
            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /*1-将传入的weather对象进行处理并展示到布局中无*/
    public void showWeatherInfo(Weather weather) {
        /*从weather对象中获取我们想要的数据*/
        String cityName = weather.basic.cityName;
        //将更新日期如"2017-01-11 08:51" 按照" "符分段存储到数组中,这里分成了两部分,取后部分的内容来显示
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "°C";
        String weatherInfo = weather.now.more.info;
        String comfort = "舒适度: " + weather.suggestion.comfort.info;
        String carWash = "洗车指数: " + weather.suggestion.carWash.info;
        String sport = "运动建议: " + weather.suggestion.sport.info;
        /*－－－－－－－－－－－－－－－将数据显示在控件上－－－－－－－－－－－－*/
        //显示基本信息和最近一次的更新时间
        mTvCityName.setText(cityName);
        mTvUpdateTime.setText(updateTime);
        //显示实时天气信息
        mTvDegree.setText(degree);
        mTvWeatherInfo.setText(weatherInfo);
        //刷新显示天气预报部分的信息
        mLlForecast.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            //加载引入item的布局视图
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,
                    mLlForecast, false);
            //通过父布局获取各自的子view控件
            TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
            TextView tvInfo = (TextView) view.findViewById(R.id.tv_info);
            TextView tvMax = (TextView) view.findViewById(R.id.tv_maxTmp);
            TextView tvMin = (TextView) view.findViewById(R.id.tv_minTmp);
            //将数据显示到控件中
            tvDate.setText(forecast.date);
            tvInfo.setText(forecast.more.info);
            tvMax.setText(forecast.temperature.max);
            tvMin.setText(forecast.temperature.min);
            //这里的view为一行的布局,代表一天的预报
            mLlForecast.addView(view);
        }
        //aqi信息获取到的信息不为空时才显示(当前免费用户的aqi信息已经无法获取了)
        if (weather.aqi != null) {
            mTvAqi.setText(weather.aqi.city.aqi);
            mTvPm25.setText(weather.aqi.city.pm25);
        }else {
            mTvAqi.setText("");
            mTvPm25.setText("");
        }
        //显示生活建议信息
        mTvComfort.setText(comfort);
        mTvCarWash.setText(carWash);
        mTvSport.setText(sport);
        //待数据都加载显示好后将天气布局展露出来
        mWeatherLayout.setVisibility(View.VISIBLE);
    }

    /*2-根据列表界面点击后传入的weather_id来进行网络请求并存储获取到的JSON数据*/
    public void requestWeather(String weatherId) {
        mWeatherId=weatherId;//更新当前选中县的天气代号
        /*从新加载必应每日更新的图片*/
        loadBingPic();
        //先构建完整的网络资源访问地址
        /*String weatherUrl= Constants.HEFENG_WEATHER_V5_ADDRESS+"weather?city="+weatherId+
                "&key="+Constants.HEFENG_WEATHER_MY_KEY;*/
        String weatherUrl = Constants.HEFENG_WEATHER_V5_ADDRESS + "weather?city=" + weatherId +
                "&key=" + Constants.HEFENG_WEATHER_GUO_LIN_KEY;
        //开始执行网络请求
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到返回数据的具体内容
                final String responseData = response.body().string();
                //将JOSN数据解析成weather对象
                final Weather weather = Utility.handleWeatherResponse(responseData);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            //把JSON数据存储到本地文件
                            SharedPreferenceUtil.setWeatherInfo(responseData);
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败",
                                    Toast.LENGTH_SHORT).show();
                        }
                        //如果在进行下拉刷新,则停止
                        if (mSwipeRefresh.isRefreshing()) {
                            mSwipeRefresh.setRefreshing(false);
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败",
                                Toast.LENGTH_SHORT).show();
                        //如果在进行下拉刷新,则停止
                        if (mSwipeRefresh.isRefreshing()) {
                            mSwipeRefresh.setRefreshing(false);
                        }
                    }
                });
            }
        });
    }

    /*3-加载必应每日一图的方法*/
    public void loadBingPic() {
        HttpUtil.sendOkHttpRequest(Constants.GUO_LIN_BING_PICTURE_ADDRESS, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //获取到必应每日图片的链接地址
                final String bingPic = response.body().string();
                //存储地址到SP中
                SharedPreferenceUtil.setBingPicAddress(bingPic);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).//传入上下文(Context|Activity|Fragment)
                                load(bingPic).//加载图片,传入(URL地址｜资源id｜本地路径)
                                into(mIvBingPic);//将图片设置到具体某一个IV中
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }

}
