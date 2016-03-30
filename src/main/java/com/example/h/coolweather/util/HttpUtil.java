package com.example.h.coolweather.util;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.example.h.coolweather.activity.MainActivity;
import com.example.h.coolweather.activity.WeatherActivity;
import com.example.h.coolweather.db.CoolWeatherDB;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by H on 2016/3/21.
 */
public class HttpUtil {

    public static void startConnection(final String httpURL, final WeatherActivity.HttpCallBackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection http = null;
                try {
                    URL url = new URL(httpURL);
                    http = (HttpURLConnection) url.openConnection();
                    http.setRequestMethod("GET");
                    http.setReadTimeout(8000);
                    http.setConnectTimeout(8000);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    if(listener != null){
                        listener.onFinish(stringBuilder.toString());
                    }
                } catch (Exception e) {
                    if(listener !=null) {
                        listener.onError(e);
                    }
                } finally {
                    if (http != null) {
                        http.disconnect();
                    }
                }
            }
        }).start();

    }

}
