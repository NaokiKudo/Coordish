package com.recivilize.naokikudo.coordish;


import android.app.Service;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class Wash_Recommend extends AppCompatActivity implements LocationListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int LOCATION_UPDATE_MIN_TIME = 0;
    //update time (approximately)
    private static final int LOCATION_UPDATE_MIN_DISTANCE = 1;
    //update Distance (approximately)
    private LocationManager mLocationManager;
    double latitude;
    double longitude;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_wash__recommend);
        mHandler = new android.os.Handler();

        mLocationManager = (LocationManager) this.getSystemService(Service.LOCATION_SERVICE);
        requestLocationUpdates();
        getWeather();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "onLocationChanged");//out put log message
        showLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.e(TAG, "onStatusChanged");
        showProvider(provider);
        switch (status) {
            case LocationProvider.OUT_OF_SERVICE:
                String outOfServiceMessage = provider + "が圏外になっていて利用できません。";
                showMessage(outOfServiceMessage);
                break;

            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                String temporarilyUnavailableMessage = "一時的に" + provider + "が利用できません。";
                showMessage(temporarilyUnavailableMessage);
                break;

            case LocationProvider.AVAILABLE:
                if (provider.equals(LocationManager.GPS_PROVIDER)) {
                    String availableMessage = provider + "が利用できます。";
                    showMessage(availableMessage);
                    requestLocationUpdates();
                }
                break;
        }
    }


    @Override
    public void onProviderEnabled(String provider) {
        Log.e(TAG, "onProviderEnabled");
        String message = provider + "有効になりました。";
        showMessage(message);
        showProvider(provider);
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            requestLocationUpdates();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.e(TAG, "onProviderDisabled");
        showProvider(provider);
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            String message = provider + "無効になりました。";
            showMessage(message);
        }

    }

    public void requestLocationUpdates() {
        Log.e(TAG, "requestLocationUpdates");
        showProvider(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        showNetworkEnabled(isNetworkEnabled);
        if (isNetworkEnabled) {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    LOCATION_UPDATE_MIN_TIME,
                    LOCATION_UPDATE_MIN_DISTANCE,
                    this
            );
            Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                showLocation(location);
            }
        } else {
            String message = "Networkが無効になっています。";
            showMessage(message);
        }
    }

    private void showMessage(String message) {
        TextView textView = (TextView) findViewById(R.id.message);
        textView.setText(message);
    }

    private void showProvider(String networkProvider) {
        TextView textView = (TextView) findViewById(R.id.provider);
        textView.setText(networkProvider);
    }

    private void showNetworkEnabled(boolean isNetworkEnabled) {
        TextView textView = (TextView) findViewById(R.id.enabled);
        textView.setText("GPS Enabled : " + String.valueOf(isNetworkEnabled));
    }

    private void showLocation(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        long time = location.getTime();
        Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
        String dateFormatted = formatter.format(date);
        TextView longitudeTextView = (TextView) findViewById(R.id.longitude);
        longitudeTextView.setText("経度 :" + String.valueOf(longitude));
        TextView latitudeTextView = (TextView) findViewById(R.id.latitude);
        latitudeTextView.setText("緯度 :" + String.valueOf(latitude));
        TextView geoTimeTextView = (TextView) findViewById(R.id.geo_time);
        geoTimeTextView.setText("取得時間 :" + dateFormatted);

    }

    public void getWeather () {

        String url = "http://api.openweathermap.org/data/2.5/find?lat="+String.valueOf(latitude)+"&lon="+String.valueOf(longitude)+"&cnt=1&APPID=d7689f4744a178cb7c399d8bf0e3c6f8";
        // リクエストオブジェクトを作って
        Request request = new Request.Builder()
                // URLを生成
                .url(url)
                .get()
                .build();
        // クライアントオブジェクトを作成する
        OkHttpClient client = new OkHttpClient();
        // 新しいリクエストを行う
        client.newCall(request).enqueue(new Callback() {
            // 通信が失敗した時
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            // 通信が成功した時
            @Override
            public void onResponse(Response response) throws IOException {
                // 通信結果をログに出力する
                Log.d("onResponse", response.toString());
                final String json = response.body().string();
                boolean post = mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        parseJson(json);

                    }
                });
            }

            public void parseJson(String json) {
                try {
                    JSONObject jsonObject = new JSONObject(json);


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

        });
    }

}