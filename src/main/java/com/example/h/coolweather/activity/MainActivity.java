package com.example.h.coolweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.h.coolweather.R;
import com.example.h.coolweather.db.CoolWeatherDB;

/**
 * Created by H on 2016/3/20.
 */
public class MainActivity extends Activity{

    private CoolWeatherDB coolWeatherDB;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("DB","This is MainActivity");
//        coolWeatherDB = new CoolWeatherDB(this);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.d("Db",coolWeatherDB.netConnect());
//            }
//        });
    }

}
