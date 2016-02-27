package com.recivilize.naokikudo.coordish;


import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class Wash_Recommend extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    double latitude;
    double longitude;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_wash__recommend);
        mHandler = new android.os.Handler();

        getWeather();
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