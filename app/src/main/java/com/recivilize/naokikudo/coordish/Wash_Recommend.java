package com.recivilize.naokikudo.coordish;


import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


public class Wash_Recommend extends AppCompatActivity {

    LocationManager mLocationManager;
    Location location;
    private static final String TAG = MainActivity.class.getSimpleName();
    TextView todayWeatherText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_wash__recommend);
        todayWeatherText = (TextView) findViewById(R.id.todayWeather);
        Intent intent = new Intent(getApplication(), GetGPS.class);
        startActivity(intent);


    }





}