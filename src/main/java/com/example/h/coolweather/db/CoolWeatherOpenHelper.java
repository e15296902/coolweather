package com.example.h.coolweather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by H on 2016/3/19.
 */
public class CoolWeatherOpenHelper extends SQLiteOpenHelper {

    private Context mContext;

    public CoolWeatherOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Province( id integer primary key autoincrement, province_name text, province_code text)");
        db.execSQL("create table City( id integer primary key autoincrement, city_name text, city_code text, province_id integer)");
        db.execSQL("create table County( id integer primary key autoincrement, county_name text, county_code text, city_id integer)");
        Toast.makeText(mContext,"table has created succuss!",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
