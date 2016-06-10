package com.recivilize.naokikudo.coordish.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.recivilize.naokikudo.coordish.GetWeatherForecast;
import com.recivilize.naokikudo.coordish.R;


public class WashRecommendActivity extends Activity implements LocationListener {

    //位置情報関連
    private static final int LOCATION_UPDATE_MIN_TIME = 0;
    private static final int LOCATION_UPDATE_MIN_DISTANCE = 1;
    double latitude;
    double longitude;
    public SharedPreferences gpsData;
    private SharedPreferences.Editor editor;
    LocationManager mLocationManager;
    Location location;

    ProgressDialog progressDialog;

    GetWeatherForecast getWeatherForecast = new GetWeatherForecast();

    //レイアウト関連
    public static TextView when;
    public static TextView weatherLocation;
    public static TextView recommendation;
    public static LinearLayout cardLinear;
    //画像
    public static LayoutInflater inflater;
    public static LinearLayout linearLayout;
    public static CardView cardView;
    public static TextView description;
    public static TextView dateText;
    public static TextView maxTempText;
    public static TextView minTempText;
    public static TextView humidityText;
    public static TextView windSpeedText;
    public static ImageView weatherImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wash__recommend);

        /////////////////////////////////////////////////
        ///関連づけ
        /////////////////////////////////////////////////

        //カードView関連
        cardLinear = (LinearLayout) findViewById(R.id.cardLinear);
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        cardLinear.removeAllViews();
        //ActivityのView
        when = (TextView) findViewById(R.id.when);
        weatherLocation = (TextView) findViewById(R.id.location);
        recommendation = (TextView) findViewById(R.id.recommendation);

        //LocationManagerの取得
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                LOCATION_UPDATE_MIN_TIME, LOCATION_UPDATE_MIN_DISTANCE, this);
        //取得中ダイアログを表示
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("位置情報を取得しています");
        progressDialog.setCancelable(false);
        progressDialog.show();


        //location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //位置情報を取得

//        //緯度経度
//        if (location != null) {
//            latitude = location.getLatitude();
//            longitude = location.getLongitude();
////            位置情報を保存
//            gpsData = getSharedPreferences("SaveGPS", MODE_PRIVATE);
//            editor = gpsData.edit();
//            editor.putFloat("latitude", (float) latitude);
//            editor.putFloat("longitude", (float) longitude);
//            editor.apply();
//            Log.d("GPSSSSS", latitude + "");
//            Log.d("GPSSSSS", longitude + "");
//
//            //天気情報を取得
//            getWeatherForecast.getForecast(gpsData.getFloat("latitude", 0), gpsData.getFloat("longitude", 0), this);
//        } else {
//            Toast.makeText(this, "Cannot get location", Toast.LENGTH_LONG).show();
//        }


    }


    @Override
    public void onLocationChanged(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        mLocationManager.removeUpdates(this);
        progressDialog.dismiss();

        getWeatherForecast.getForecast((float)latitude, (float)longitude, this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
