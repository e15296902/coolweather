package com.example.h.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.h.coolweather.model.City;
import com.example.h.coolweather.model.County;
import com.example.h.coolweather.model.Province;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by H on 2016/3/19.
 */
public class CoolWeatherDB {

    private static CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;
    private final int VERSION=1;

    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,"Database.db",null,VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public synchronized static CoolWeatherDB getCoolWeatherDB(Context context){
        if(coolWeatherDB == null){
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }



    public void saveProvince(Province province){

        if(province != null){
            db.execSQL("insert into Province (province_name, province_code) values(?,?)",new Object[]{province.getProvinceName(),province.getProvinceCode()});
        }

    }

    public void saveCity(City city){
        if (city != null){
            db.execSQL("insert into City (city_name, city_code, province_id) values(?,?,?)",new Object[]{city.getCityName(),city.getCityCode(),city.getProvinceId()});
        }

    }

    public void saveCounty(County county){
        if(county != null){
            db.execSQL("insert into County (county_name, county_code, city_id) values(?,?,?)",new Object[]{county.getCountyName(),county.getCountyCode(),county.getCityId()});
        }
    }

    public List<Province> getProvince(){
        List<Province> listProvince = new ArrayList<Province>();
//            Cursor cursor = db.query("Province",null,null,null,null,null,null);
            Cursor cursor = db.rawQuery("select * from Province",null);
            if(cursor.moveToFirst()) {
                do {
                    Province p = new Province();
                    p.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    p.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                    p.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                    listProvince.add(p);
                } while (cursor.moveToNext());
            }
//        }
        return listProvince;
    }
    public List<City> getCity(int provinceId){
        List<City> listCity = new ArrayList<City>();
            Cursor cursor = db.rawQuery("select * from City WHERE province_id=?",new String[]{String.valueOf(provinceId)});
//            Cursor cursor = db.query("City",null,"province_id=?",new String[]{String.valueOf(provinceId)},null,null,null);
        if(cursor.moveToFirst()){
            do{
                City c = new City();
                c.setId(cursor.getInt(cursor.getColumnIndex("id")));
                c.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                c.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                c.setProvinceId(provinceId);
                listCity.add(c);
            }while(cursor.moveToNext());
        }

        return listCity;
    }

    public List<County> getCounty(int cityId){
        List<County> listCounty = new ArrayList<County>();
//        Cursor cursor = db.query("County",null,"city_id=?",new String[]{String.valueOf(cityId)},null,null,null);
        Cursor cursor = db.rawQuery("select * from County WHERE city_id=?",new String[]{String.valueOf(cityId)});
        if(cursor.moveToFirst()) {
            do {
                County c = new County();
                c.setId(cursor.getInt(cursor.getColumnIndex("id")));
                c.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                c.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                c.setCityId(cityId);
                listCounty.add(c);
            } while (cursor.moveToNext());
        }
        return listCounty;
    }

}
