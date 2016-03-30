package com.example.h.coolweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.h.coolweather.R;
import com.example.h.coolweather.util.HttpUtil;
import com.example.h.coolweather.util.Utility;


/**
 * Created by H on 2016/3/29.
 */
public class WeatherActivity extends Activity implements View.OnClickListener{

    private RelativeLayout relativeLayout;
    private TextView textPublishTime;
    private TextView textDate ;
    private TextView textWeather;
    private TextView textTemperature ;
    private TextView titleText;
    private Button switchCity;
    private Button refreshWeather;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);
        relativeLayout = (RelativeLayout) findViewById(R.id.weather_info_layout);
        relativeLayout.setVisibility(View.INVISIBLE);
        textPublishTime = (TextView) findViewById(R.id.text_updatetime);
        textDate =(TextView) findViewById(R.id.text_data);
        textWeather = (TextView) findViewById(R.id.text_weather);
        textTemperature = (TextView) findViewById(R.id.text_temperature);
        titleText = (TextView) findViewById(R.id.title_text);
        switchCity = (Button) findViewById(R.id.switch_city);
        refreshWeather = (Button) findViewById(R.id.refresh_weather);

        switchCity.setOnClickListener(this);
        refreshWeather.setOnClickListener(this);


        String countyCode = getIntent().getStringExtra("location");
        if(!TextUtils.isEmpty(countyCode)){
            textPublishTime.setText("正在同步...");
            relativeLayout.setVisibility(View.INVISIBLE);
            queryWeatherCode(countyCode);
        }else{
            showWeather();
        }


    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.switch_city:
                SharedPreferences.Editor editor = getSharedPreferences("weatherinfo",MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(this, ChooseAreaActivity.class);
                intent.putExtra("from_weather_activity", true);
                startActivity(intent);
                finish();
                break;
            case R.id.refresh_weather:
                textPublishTime.setText("同步中...");
                SharedPreferences prefs = getSharedPreferences("weatherinfo", MODE_PRIVATE);
                String cityId = prefs.getString("cityId","");
                if(!TextUtils.isEmpty(cityId)){
                    queryWeatherInfo(cityId);
                }
                break;
            default:

        }
    }

    private void queryWeatherCode(String countyCode){
        String address =  "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";

        queryFromServer(address,"weatherCode");
    }

    private void queryWeatherInfo(String weatherCode){
        String address = "http://www.weather.com.cn/adat/cityinfo/" + weatherCode + ".html";

        queryFromServer(address, "weatherInfo");
    }

    private void queryFromServer(String address, final String type){
        HttpUtil.startConnection(address, new HttpCallBackListener() {
            @Override
            public void onFinish(String data) {
                switch (type){
                    case "weatherCode":
                        String[] array = data.split("\\|");
                        if(array != null && array.length==2){
                            String weatherCode = array[1];
                            queryWeatherInfo(weatherCode);
                        }
                        break;
                    case "weatherInfo":
                        Utility.handleWeatherResponse(WeatherActivity.this,data);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showWeather();
                            }
                        });
                        break;
                    default:
                }
            }

            @Override
            public void onError(final Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textPublishTime.setText("同步失败："+e.toString());
                    }
                });
            }
        });
    }


    private void showWeather(){

        SharedPreferences sp = getSharedPreferences("weatherinfo", MODE_PRIVATE);
        Boolean state = sp.getBoolean("city_selected",false);
        String cityName = sp.getString("cityName",null);
        String temp1 = sp.getString("temp1", null);
        String temp2 = sp.getString("temp2", null);
        String weatherDesp = sp.getString("weatherDesp", null);
        String publishTime = sp.getString("publishTime", null);
        String currentData = sp.getString("current_data", null);
        if(state){
            textPublishTime.setText("今天"+publishTime+"发布");
            textDate.setText(currentData);
            textWeather.setText(weatherDesp);
            textTemperature.setText(temp2 + "~" + temp1);
            titleText.setText(cityName);
            relativeLayout.setVisibility(View.VISIBLE);
        }
    }

    public interface HttpCallBackListener{

        void onFinish(String data);
        void onError(Exception e);
    }
}
