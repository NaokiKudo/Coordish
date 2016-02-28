package com.recivilize.naokikudo.coordish;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


public class Wash_Recommend extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    GetWeatherForecast getWeatherForecast = new GetWeatherForecast();
    float currentTemp = getWeatherForecast.currentTemp;
    TextView todayWeatherText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_wash__recommend);
        todayWeatherText = (TextView) findViewById(R.id.todayWeather);
        GetWeatherForecast();




    }





}