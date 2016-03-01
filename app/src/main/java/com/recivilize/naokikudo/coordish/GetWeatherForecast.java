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
                    JSONObject obj = listArray.getJSONObject(0);
                    Log.d("TAG2" ,obj.toString());
                    JSONObject obj1 = listArray.getJSONObject(1);
                    Log.d("TAG3" ,obj1.toString());

                    // 地点ID
                    int id = obj.getInt("id");

                    // 地点名
                    String cityName = obj.getString("name");

                    // 気温(Kから℃に変換)
                    JSONObject mainObj = obj.getJSONObject("main");
                    float currentTemp = (float) (mainObj.getDouble("temp") - 273.15f);

                    float minTemp = (float) (mainObj.getDouble("temp_min") - 273.15f);

                    maxTemp = (float) (mainObj.getDouble("temp_max"));

                    // 湿度
                    if (mainObj.has("humidity")) {
                        int humidity = mainObj.getInt("humidity");
                    }

                    // 取得時間
                    long time = obj.getLong("dt");

                    // 天気
                    JSONArray weatherArray = obj.getJSONArray("weather");
                    JSONObject weatherObj = weatherArray.getJSONObject(0);
                    String iconId = weatherObj.getString("icon");

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
