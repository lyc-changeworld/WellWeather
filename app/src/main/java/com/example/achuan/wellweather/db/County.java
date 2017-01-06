package com.example.achuan.wellweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by achuan on 17-1-6.
 * 功能：县的数据库表结构
 */

public class County extends DataSupport {
    private int id;
    private String countyName;//县的名字
    private String weatherId;//对应天气的id
    private int cityId;//归属城市的id值

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
