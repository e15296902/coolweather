package com.example.h.coolweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.service.chooser.ChooserTargetService;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.h.coolweather.R;
import com.example.h.coolweather.db.CoolWeatherDB;
import com.example.h.coolweather.model.Province;
import com.example.h.coolweather.util.HttpUtil;
import com.example.h.coolweather.util.Utility;

import java.net.HttpURLConnection;

/**
 * Created by H on 2016/3/20.
 */
public class MainActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonTest = (Button) findViewById(R.id.button_test);

        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseAreaActivity.class);
                startActivity(intent);
            }
        });

    }


    public interface HttpCallBackListener{

        void onFinish(String data);
        void onError(Exception e);
    }

}
