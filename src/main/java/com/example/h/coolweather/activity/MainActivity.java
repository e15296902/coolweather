package com.example.h.coolweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.service.chooser.ChooserTargetService;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.h.coolweather.R;
import com.example.h.coolweather.db.CoolWeatherDB;
import com.example.h.coolweather.model.Province;
import com.example.h.coolweather.util.HttpUtil;
import com.example.h.coolweather.util.Utility;

import org.w3c.dom.Text;

import java.net.HttpURLConnection;

/**
 * Created by H on 2016/3/20.
 */
public class MainActivity extends Activity{

    private ProgressDialog progressDialog;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        TextView textPublishTime = (TextView) findViewById(R.id.text_updatetime);
        TextView textDate =(TextView) findViewById(R.id.text_data);
        TextView textWeather = (TextView) findViewById(R.id.text_weather);
        TextView textTemperature = (TextView) findViewById(R.id.text_temperature);
        TextView titleText = (TextView) findViewById(R.id.title_text);
        showProgressDialog();
        SharedPreferences sp =getSharedPreferences("weatherinfo",MODE_PRIVATE);
        Boolean state = sp.getBoolean("city_selected",false);
        String cityName = sp.getString("cityName",null);
        String cityId = sp.getString("cityId", null);
        String temp1 = sp.getString("temp1", null);
        String temp2 = sp.getString("temp2", null);
        String weatherDesp = sp.getString("weatherDesp", null);
        String publishTime = sp.getString("publishTime", null);
        String currentData = sp.getString("current_data", null);
        closeProgressDialog();
        if(state){
            textPublishTime.setText(publishTime);
            textDate.setText(currentData);
            textWeather.setText(weatherDesp);
            textTemperature.setText(temp2 + "~" + temp1);
            titleText.setText(cityName);
        }


    }


    public interface HttpCallBackListener{

        void onFinish(String data);
        void onError(Exception e);
    }
    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog(){
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
