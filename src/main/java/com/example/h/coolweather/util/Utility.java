package com.example.h.coolweather.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.h.coolweather.activity.MainActivity;
import com.example.h.coolweather.db.CoolWeatherDB;
import com.example.h.coolweather.model.City;
import com.example.h.coolweather.model.County;
import com.example.h.coolweather.model.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static void handleWeatherResponse(Context context,String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weatherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String pushTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context,cityName,weatherCode,temp1,temp2,weatherDesp,pushTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void saveWeatherInfo(Context context,String cityName,String cityId, String temp1, String temp2, String weatherDesp, String publishTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = context.getSharedPreferences("weatherinfo",context.MODE_PRIVATE).edit();
        editor.putBoolean("city_selected",true);
        editor.putString("cityName",cityName);
        editor.putString("cityId",cityId);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weatherDesp", weatherDesp);
        editor.putString("publishTime", publishTime);
        editor.putString("current_data", sdf.format(new Date()));
        editor.commit();
    }
}
