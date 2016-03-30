package com.example.h.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;

import com.example.h.coolweather.activity.WeatherActivity;
import com.example.h.coolweather.util.HttpUtil;
import com.example.h.coolweather.util.Utility;

/**
 * Created by H on 2016/3/30.
 */
public class AutoUpdateService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        });
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int period = 4*3600*1000;
        long triggerAtTime = SystemClock.elapsedRealtime()+period;
        Intent i = new Intent(this, AutoUpdateService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateWeather(){
        SharedPreferences prefs = getSharedPreferences("weatherinfo", MODE_PRIVATE);
        String cityId = prefs.getString("cityId","");
        queryWeatherInfo(cityId);
    }

    private void queryWeatherInfo(String weatherCode){
        String address = "http://www.weather.com.cn/adat/cityinfo/" + weatherCode + ".html";
        queryFromServer(address);
    }

    private void queryFromServer(String address){
        HttpUtil.startConnection(address, new WeatherActivity.HttpCallBackListener() {
            @Override
            public void onFinish(String data) {
                Utility.handleWeatherResponse(AutoUpdateService.this,data);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
