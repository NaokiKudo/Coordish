package com.recivilize.naokikudo.coordish;


import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class Wash_Recommend extends AppCompatActivity {

    ImageView forecastImage[];
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    LocationManager mLocationManager;

    boolean getPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash__recommend);
        pref = getSharedPreferences("position", MODE_PRIVATE);
        if (getPosition == false) {
            getGPS();
            getPosition = true;
        }

    }
    // GPSボタン
    public void getGPS() {
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, //LocationManager.NETWORK_PROVIDER,
                3000, // 通知のための最小時間間隔（ミリ秒）
                10, // 通知のための最小距離間隔（メートル）
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        String msg = "Lat=" + location.getLatitude()
                                + "\nLng=" + location.getLongitude();
                        Log.d("GPS", msg);
                        mLocationManager.removeUpdates(this);
                    }
                    @Override
                    public void onProviderDisabled(String provider) {
                    }
                    @Override
                    public void onProviderEnabled(String provider) {
                    }
                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }
                }
        );
    }
}

