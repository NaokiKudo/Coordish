package com.recivilize.naokikudo.coordish;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class GetWeatherForecast extends AppCompatActivity {
    JSONArray forecastArray;
    Handler mHandler = new android.os.Handler();
    float maxTemp;







    public float getForecast (float latitude, float longitude) {
        //リクエストオブジェクトを作って
        Request request = new Request.Builder()
                //URLを生成
                .url("http://api.openweathermap.org/data/2.5/forecast?lat=" + String.valueOf(latitude) + "&lon=" + String.valueOf(longitude) + "&APPID=d7689f4744a178cb7c399d8bf0e3c6f8")
                .get().build();
        //クライアントオブジェクトを作成する
        OkHttpClient client = new OkHttpClient();
        //新しいリクエストを行う
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            //通信が成功した時
            @Override
            public void onResponse(Response response) throws IOException {
                //通信結果をログに出力する
                Log.d("onResponse", response.toString());
                final String json = response.body().string();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        parseJson(json);
                    }
                });
            }

            public float parseJson(String json) {

                try {
                    JSONObject jsonObject = new JSONObject(json);
                    JSONArray listArray = jsonObject.getJSONArray("list");
                    Log.d("TAG1" , listArray.toString());
                    for(int i = 0; i < 36; i++) {
                        JSONObject obj = listArray.getJSONObject(i);
                        Log.d(i+"+"+"TAG", obj.toString());
                        JSONArray weatherArray = obj.getJSONArray("weather");
                        JSONObject descriptionObject = weatherArray.getJSONObject(0);
                        if (obj.getString("dt_txt").endsWith("12:00:00")){
                            String description = descriptionObject.getString("description");
                            Log.d(i + "+" + "weatherrrrrrr", description);
                        }

                    }


                    return maxTemp;



                } catch (JSONException e) {
                    e.printStackTrace();
                    return 8.8f;

                }
            }

        });
        return maxTemp;
    }

}
