package com.example.achuan.wellweather;

import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.achuan.wellweather.app.Constants;
import com.example.achuan.wellweather.db.City;
import com.example.achuan.wellweather.db.County;
import com.example.achuan.wellweather.db.Province;
import com.example.achuan.wellweather.util.DialogUtil;
import com.example.achuan.wellweather.util.HttpUtil;
import com.example.achuan.wellweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by achuan on 17-1-7.
 */

public class ChooseAreaFragment extends Fragment {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.bt_back)
    Button mBtBack;
    @BindView(R.id.lv_area)
    ListView mLvArea;


    /*列表数据的适配器引用变量*/
    private ArrayAdapter<String> mAdapter;
    /*列表需要展示的数据的集合体,是一个实例对象*/
    private List<String> mList = new ArrayList<>();
    /*当前处于列表数据的级别*/
    private int currentLevel;

    /*--------------省市县的列表集合的引用变量----------*/
    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<County> mCountyList;
    /*---------------省市县的被选中的实例的引用变量-----------------*/
    private Province mProvince;
    private City mCity;
    private County mCounty;


    /*1-为碎片创建视图（加载布局）时调用*/
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    /*2-确保与碎片相关联的活动一定已经创建完毕的时候调用*/
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //创建一个列表数据适配器实例对象,传入了数据集合体和设置了显示风格
        mAdapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,mList);
        //为列表对象添加适配器实例对象
        mLvArea.setAdapter(mAdapter);
        //Activity初次创建时执行省级数据的查询展示方法
        currentLevel=Constants.LEVEL_PROVINCE;
        queryProvinces();
        //为列表设置item的点击监听事件
        mLvArea.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //根据当前的列表数据的级别来进行对应的逻辑处理
                switch (currentLevel){
                    case Constants.LEVEL_PROVINCE:
                        mProvince=mProvinceList.get(position);
                        currentLevel= Constants.LEVEL_CITY;
                        //查询并展示城市数据列表
                        queryCities();
                        break;
                    case Constants.LEVEL_CITY:
                        mCity=mCityList.get(position);
                        currentLevel=Constants.LEVEL_COUNTY;
                        //查询并展示县城数据列表
                        queryCounties();
                        break;
                    case Constants.LEVEL_COUNTY:
                        mCounty=mCountyList.get(position);
                        //获取当前选中县的weather_id
                        String weatherId=mCounty.getWeatherId();
                        if(getActivity()instanceof MainActivity){
                            //启动一个意图跳转到天气显示界面,并将weather_id信息携带过去
                            Intent intent=new Intent(getActivity(),WeatherActivity.class);
                            intent.putExtra("weather_id",weatherId);
                            startActivity(intent);
                            getActivity().finish();
                        }else if(getActivity()instanceof WeatherActivity){
                            WeatherActivity activity= (WeatherActivity) getActivity();
                            activity.mDrawLayout.closeDrawers();//关闭菜单栏
                            activity.mSwipeRefresh.setRefreshing(true);//启动刷新动画
                            activity.requestWeather(weatherId);//重新进行网络请求并更新内容显示
                        }
                        break;
                    default:break;
                }
            }
        });
    }

    /*－－－－－－－－－按钮点击事件的统一回调处理方法,使用了简洁写法－－－－－－－－－－*/
    @OnClick({R.id.bt_back})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_back:
                //根据当前的级别来进行back后的列表数据刷新
                if(currentLevel==Constants.LEVEL_COUNTY){
                    //更新当前的级别为:市
                    currentLevel=Constants.LEVEL_CITY;
                    queryCities();
                }else if(currentLevel==Constants.LEVEL_CITY){
                    //更新当前的级别为:省
                    currentLevel=Constants.LEVEL_PROVINCE;
                    queryProvinces();
                }
                break;
            default:break;
        }
    }

    /*－－－－－－－－－－－－根据传入的地址和等级代号从服务器上查询省市县数据－－－－－－－*/
    private void queryFromServer(String address, final int level){
        //创建并打开一个加载进度的对话框
        DialogUtil.createProgressDialog(getActivity(),null,"正在加载...");
        //开始执行网络请求服务
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到返回的具体内容
                String responseText=response.body().string();
                //定义一个布尔变量来记录JSON数据解析及保存到数据库的工作是否顺利完成
                boolean result=false;
                //根据传入的类型来进行数据处理
                switch (level){
                    case Constants.LEVEL_PROVINCE:
                        result= Utility.handleProvinceResponse(responseText);
                        break;
                    case Constants.LEVEL_CITY:
                        result=Utility.handleCityResponse(responseText,mProvince.getProvinceCode());
                        break;
                    case Constants.LEVEL_COUNTY:
                        result=Utility.handleCountyResponse(responseText,mCity.getCityCode());
                        break;
                    default:break;
                }
                //如果上面的数据处理成功了
                if(result){
                    /*跳转到UI(主)线程进行后续处理*/
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //关闭加载进度对话框
                            DialogUtil.closeProgressDialog();
                            //服务器返回的数据解析存储成功后,根据类型再去数据库中查询
                            switch (level){
                                case Constants.LEVEL_PROVINCE:
                                    queryProvinces();
                                    break;
                                case Constants.LEVEL_CITY:
                                    queryCities();
                                    break;
                                case Constants.LEVEL_COUNTY:
                                    queryCounties();
                                    break;
                                default:break;
                            }
                        }
                    });
                }else {//否则说明数据解析和存储的过程失败了
                    queryFailure();
                }
            }
            @Override
            public void onFailure(Call call, IOException e) {
                queryFailure();
            }
        });
    }
    /*查询失败后的逻辑处理*/
    private void queryFailure(){
        //回到主线程处理逻辑
        getActivity().runOnUiThread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {
                DialogUtil.closeProgressDialog();
                Toast.makeText(getContext(), "加载失败",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*－－－－－－－－－－－－－－－－－查询省市县的列表数据－－－－－－－－－－－－－－－－－－－*/
    /*查询全国所有的"省",优先从数据库查询,如果没有再去服务器上查询,查询成功后进行列表的数据刷新*/
    private void queryProvinces(){
        mTvTitle.setText("中国");
        mBtBack.setVisibility(View.GONE);//省级别时隐藏back键
        //查询Province表中所有的数据
        mProvinceList= DataSupport.findAll(Province.class);
        if(mProvinceList.size()>0){
            //每次查询时将列表显示的数据集合体清空
            mList.clear();
            for(Province province:mProvinceList){
                mList.add(province.getProvinceName());//重新填装集合体
            }
            //刷新列表显示
            mAdapter.notifyDataSetChanged();
            mLvArea.setSelection(0);//刷新数据后将屏幕的首行item数据对到0数据位
        }else {
            //数据库中无数据可取,那就去网络端获取
            queryFromServer(Constants.GUO_LIN_WEATHER_ADDRESS,currentLevel);
        }
    }
    /*查询选中省内所有的"市",优先从数据库查询,如果没有再去服务器上查询*/
    private void queryCities(){
        mTvTitle.setText(mProvince.getProvinceName());//标题显示当前的省
        mBtBack.setVisibility(View.VISIBLE);//显示back按键
        //获取当前选中的省的代号
        int provinceCode=mProvince.getProvinceCode();
        mCityList=DataSupport.where("provinceId = ?",
                String.valueOf(provinceCode)).find(City.class);
        if(mCityList.size()>0){
            mList.clear();
            for(City city:mCityList){
                mList.add(city.getCityName());
            }
            mAdapter.notifyDataSetChanged();
            mLvArea.setSelection(0);
        }else {
            queryFromServer(Constants.GUO_LIN_WEATHER_ADDRESS +
                    "/"+provinceCode, currentLevel);
        }
    }
    /*查询选中市内所有的"县",优先从数据库查询,如果没有再去服务器上查询*/
    private void queryCounties(){
        mTvTitle.setText(mCity.getCityName());
        mBtBack.setVisibility(View.VISIBLE);
        int cityCode=mCity.getCityCode();
        mCountyList=DataSupport.where("cityId = ?",
                String.valueOf(cityCode)).find(County.class);
        if(mCountyList.size()>0){
            mList.clear();
            for(County county:mCountyList){
                mList.add(county.getCountyName());
            }
            mAdapter.notifyDataSetChanged();
            mLvArea.setSelection(0);
        }else {
            int provinceCode=mProvince.getProvinceCode();
            queryFromServer(Constants.GUO_LIN_WEATHER_ADDRESS +
                    "/"+provinceCode+"/"+cityCode, currentLevel);
        }
    }

}
