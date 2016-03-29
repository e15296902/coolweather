package com.example.h.coolweather.util;


import android.text.TextUtils;

import com.example.h.coolweather.activity.MainActivity;
import com.example.h.coolweather.db.CoolWeatherDB;
import com.example.h.coolweather.model.City;
import com.example.h.coolweather.model.County;
import com.example.h.coolweather.model.Province;

/**
 * Created by H on 2016/3/22.
 */
public class Utility {

    public synchronized static boolean handleProvinceData(CoolWeatherDB coolWeatherDB,String data){
        if(!TextUtils.isEmpty(data)){
            String[] allProvinces = data.split(",");
            if(allProvinces != null && allProvinces.length>0){
                for (String s : allProvinces) {
                    String[] array = s.split("\\|");
                    Province p = new Province();
                    p.setProvinceCode(array[0]);
                    p.setProvinceName(array[1]);
                    coolWeatherDB.saveProvince(p);

                }
                return true;
            }

        }
        return false;
    }

    public static boolean handleCityData(CoolWeatherDB coolWeatherDB,String data, int provinceId){
        if(!TextUtils.isEmpty(data)){
            String[] allCities = data.split(",");
            if(allCities != null && allCities.length>0){
                for (String s : allCities) {
                    String[] array = s.split("\\|");
                    City c = new City();
                    c.setCityCode(array[0]);
                    c.setCityName(array[1]);
                    c.setProvinceId(provinceId);
                    coolWeatherDB.saveCity(c);

                }
                return true;
            }

        }
        return false;
    }

    public static boolean handleCounty(CoolWeatherDB coolWeatherDB,String data,int cityId){
        if(!TextUtils.isEmpty(data)){
            String[] allCounty = data.split(",");
            if(allCounty != null && allCounty.length>0){
                for (String s : allCounty) {
                    String[] array = s.split("\\|");
                    County c = new County();
                    c.setCountyCode(array[0]);
                    c.setCountyName(array[1]);
                    c.setCityId(cityId);
                    coolWeatherDB.saveCounty(c);

                }
                return true;
            }

        }
        return false;
    }
}
