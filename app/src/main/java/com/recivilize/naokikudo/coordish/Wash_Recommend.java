package com.recivilize.naokikudo.coordish;

import android.app.Activity;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


public class Wash_Recommend extends Activity implements LocationListener{

    //位置情報関連
    private static final int LOCATION_UPDATE_MIN_TIME = 0;
    private static final int LOCATION_UPDATE_MIN_DISTANCE = 1;
    double latitude;
    double longitude;
    public SharedPreferences gpsData;
    private SharedPreferences.Editor editor;
    LocationManager mLocationManager;
    Location location;

    GetWeatherForecast getWeatherForecast = new GetWeatherForecast();

    //レイアウト関連
    //テキスト(日)
    static TextView[] date;
    //テキスト(天気)
    static TextView todayWeather;
    static TextView tomorrowWeather;
    static TextView threeDaysAfterWeather;
    static TextView fourDaysAfterWeather;
    static TextView fiveDaysAfterWeather;

    //テキスト(場所,推奨日)
    static TextView weatherLocation;
    static TextView recommendation;
    static TextView when;
    //画像




    @Override
    public void onCreate (Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash__recommend);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        //------------ビュー始まり------------
        todayWeather = (TextView) findViewById(R.id.todayWeather);
        tomorrowWeather = (TextView)findViewById(R.id.tomorrowWeather);
        threeDaysAfterWeather = (TextView)findViewById(R.id.threeDaysAfterWeather);
        fourDaysAfterWeather = (TextView)findViewById(R.id.fourDaysAfterWeather);
        fiveDaysAfterWeather = (TextView)findViewById(R.id.fiveDaysAfterWeather);
        recommendation = (TextView)findViewById(R.id.recommendation);
        when = (TextView)findViewById(R.id.when);
        weatherLocation = (TextView) findViewById(R.id.location);

        date = new TextView[]{(TextView) findViewById(R.id.today),
                (TextView) findViewById(R.id.tomorrow),
                (TextView) findViewById(R.id.threeDaysAfter),
                (TextView) findViewById(R.id.fourDaysAfter),
                (TextView) findViewById(R.id.fiveDaysAfter)};
        //----------ビューおしまい-----------


        //位置情報を取得
        requestLocationUpdates();
        //緯度経度
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            //位置情報を保存
            gpsData = getSharedPreferences("SaveGPS", MODE_PRIVATE);
            editor = gpsData.edit();
            editor.putFloat("latitude", (float) latitude);
            editor.putFloat("longitude", (float) longitude);
            Log.d("GPSSSSS", latitude + "");
            Log.d("GPSSSSS", longitude + "");
            editor.apply();

            //天気情報を取得
            getWeatherForecast.getForecast(gpsData.getFloat("latitude", 0), gpsData.getFloat("longitude", 0));
        } else {
            Toast.makeText(this, "Cannot get location", Toast.LENGTH_LONG).show();
        }


    }




    //GPSメソッド群
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
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            location = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Toast.makeText(this, "GPS is disabled", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Get location from network", Toast.LENGTH_LONG).show();
        }

    }

}
