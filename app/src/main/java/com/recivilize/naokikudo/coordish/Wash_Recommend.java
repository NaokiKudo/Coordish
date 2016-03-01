package com.recivilize.naokikudo.coordish;

import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


public class Wash_Recommend extends AppCompatActivity implements LocationListener{

    private static final int LOCATION_UPDATE_MIN_TIME = 0;
    //update time (approximately)
    private static final int LOCATION_UPDATE_MIN_DISTANCE = 1;
    //update Distance (approximately)
    double latitude;
    double longitude;
    public SharedPreferences gpsData;
    private SharedPreferences.Editor editor;
    LocationManager mLocationManager;
    Location location;
    GetWeatherForecast getWeatherForecast = new GetWeatherForecast();
    TextView todayWeather;
    private final String TAG = "GPSSSSSSSSSSSS";



    @Override
    public void onCreate (Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash__recommend);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //位置情報を取得
        requestLocationUpdates();
        //緯度経度
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        //位置情報を保存
        gpsData = getSharedPreferences("SaveGPS", MODE_PRIVATE);
        editor = gpsData.edit();
        editor.putFloat("latitude", (float) latitude);
        editor.putFloat("longitude", (float) longitude);
        Log.e(TAG, longitude+"");
        editor.commit();
        String[] weather =
                getWeatherForecast.getForecast(gpsData.getFloat("latitude", 0), gpsData.getFloat("longitude", 0));
        todayWeather = (TextView) findViewById(R.id.todayWeather);
        todayWeather.setText(weather[0]);




    }



    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                break;

            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                break;

            case LocationProvider.AVAILABLE:
                if (provider.equals(LocationManager.GPS_PROVIDER)) {
                    requestLocationUpdates();
                }
                break;
        }

    }

    @Override
    public void onProviderEnabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            requestLocationUpdates();
        }

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public void requestLocationUpdates() {
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isNetworkEnabled) {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_UPDATE_MIN_TIME,
                    LOCATION_UPDATE_MIN_DISTANCE,
                    this
            );
        }
    }

}
