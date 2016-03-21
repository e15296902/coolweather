package com.example.h.coolweather.db;

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
    private CoolWeatherDB coolWeatherDB;
    private SQLiteDatabase db;
    private final int VERSION=1;

    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbHelper = new CoolWeatherOpenHelper(context,"Database",null,VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public CoolWeatherDB getCoolWeatherDB(Context context){
        if(coolWeatherDB == null){
            coolWeatherDB = new CoolWeatherDB(context);
        }
        return coolWeatherDB;
    }

    public void saveProvince(Province province){

        if(province != null){
            db.execSQL("insert info Province (province_name, province_code) values(?,?)",new String []{province.getProvinceName(),province.getProvinceCode()});
        }

    }

    public void saveCity(City city){
        if (city != null){
            db.execSQL("insert info City (city_name,city_code,province_id) values(?,?,?)",new String[]{city.getCityName(),city.getCityCode(),city.getProvinceId()+""});
        }

    }

    public void saveCounty(County county){
        if(county != null){
            db.execSQL("insert info County (county_name,county_code,city_id) values(?,?,?)",new String[]{county.getCountyName(),county.getCountyCode(),county.getCityId()+""});
        }
    }

    public List<Province> getProvince(){
        List<Province> listProvince = new ArrayList<Province>();
        if(listProvince.isEmpty()){
            Cursor cursor = db.rawQuery("select * from Province",null);
            if(cursor.isFirst()) {
                cursor.moveToFirst();
            }
            do{
                Province p = new Province();
                p.setId(cursor.getInt(cursor.getColumnIndex("id")));
                p.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                p.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                listProvince.add(p);
            }while(cursor.moveToNext());

        }
        return listProvince;
    }
    public List<City> getCity(int provinceId){
        List<City> listCity = new ArrayList<City>();
        if(listCity.isEmpty()){
//            Cursor cursor = db.rawQuery("select (province_id) from City",new String[]{provinceId+""});
            Cursor cursor = db.query("City",null,"province_id=?",new String[]{String.valueOf(provinceId)},null,null,null);
            if(cursor.isFirst()) {
                cursor.moveToFirst();
            }
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
        Cursor cursor = db.query("County",null,"city_id=?",new String[]{String.valueOf(cityId)},null,null,null);
        if(cursor.isFirst()) {
            cursor.moveToFirst();
        }
        do{
            County c = new County();
            c.setId(cursor.getInt(cursor.getColumnIndex("id")));
            c.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
            c.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
            c.setCityId(cityId);
            listCounty.add(c);
        }while(cursor.moveToNext());

        return listCounty;
    }



//    public String netConnect(){
//        HttpURLConnection http = null;
//        try{
//            Log.d("Db","This is Second");
//            URL url = new URL("http://www.weather.com.cn/data/list3/city.xml");
//            Log.d("Db","This is Third");
//            http =(HttpURLConnection) url.openConnection();
//            Log.d("Db","This is Four");
//            http.setRequestMethod("GET");
//            Log.d("Db","This is Five");
//            http.setDoInput(true);
//            http.setReadTimeout(8000);
//            http.setConnectTimeout(8000);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
//            StringBuilder stringBuilder = null;
//            String temp ;
//            if((temp = reader.readLine())!=null){
//                stringBuilder.append(temp);
//            }
//            Log.d("Db",stringBuilder.toString());
//
//        }catch (Exception e){
//            Log.d("Db","Error has occurrence ");
//            e.printStackTrace();
//        }finally{
//            if(http != null) {
//                http.disconnect();
//            }
//        }
//        return "false";
//
//    }
}
