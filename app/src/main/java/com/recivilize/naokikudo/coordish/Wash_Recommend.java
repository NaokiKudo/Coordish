package com.recivilize.naokikudo.coordish;


import android.app.Service;
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
    GetGPS getGPS = new GetGPS();
    float[] gps = getGPS.getGPS();
    GetWeatherForecast getWeatherForecast = new GetWeatherForecast();
    TextView todayWeatherText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_wash__recommend);
        mLocationManager = (LocationManager) this.getSystemService(Service.LOCATION_SERVICE);
        location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        todayWeatherText = (TextView) findViewById(R.id.todayWeather);
        String weatherInformation[] = getWeatherForecast.getForecast(gps[0], gps[1]);
        todayWeatherText.setText(weatherInformation[1]);




    }





}