package com.recivilize.naokikudo.coordish;

import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class GetGPS extends AppCompatActivity implements LocationListener{

    private static final String TAG = MainActivity.class.getSimpleName();
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

    public float[] getGPS (){
        Wash_Recommend washRecommend = new Wash_Recommend();
        mLocationManager = washRecommend.mLocationManager;
        location = washRecommend.location;
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
        editor.commit();
        float gpsData[] = {(float)latitude, (float)longitude};

        return gpsData;


    }



    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "onLocationChanged");//out put log message
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e(TAG, "onStatusChanged");
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
        Log.e(TAG, "onProviderEnabled");
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            requestLocationUpdates();
        }

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    public void requestLocationUpdates() {
        Log.e(TAG, "requestLocationUpdates");
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
