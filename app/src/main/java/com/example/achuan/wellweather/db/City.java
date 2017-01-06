package com.example.achuan.wellweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by achuan on 17-1-6.
 * 功能：市的数据库表结构
 */

public class City extends DataSupport {
    private int id;
    private String cityName;//市的名字
    private int cityCode;//市的代号
    private int provinceId;//归属省份的id值

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
