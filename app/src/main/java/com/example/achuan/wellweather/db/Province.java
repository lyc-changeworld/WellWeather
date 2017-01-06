package com.example.achuan.wellweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by achuan on 17-1-6.
 * 功能：省的数据库表结构
 */

public class Province extends DataSupport {
    private int id;
    private String provinceName;//省名称
    private int provinceCode;//省的代号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
