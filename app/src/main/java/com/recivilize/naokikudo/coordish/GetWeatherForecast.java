package com.recivilize.naokikudo.coordish;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GetWeatherForecast {
    ViewChanger ViewChanger = new ViewChanger();
    Handler mHandler = new android.os.Handler();
    static List<String> descriptionList = new ArrayList<>();
    static List<String> maxTempList = new ArrayList<>();
    static List<String> minTempList = new ArrayList<>();
    static List<String> humidityList = new ArrayList<>();
    static List<String> windSpeedList = new ArrayList<>();
    static String name;
    static String country;



    public void getForecast (float latitude, float longitude, final Context context) {
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
                        //JSON文字列を変換
                        parseJson(json);
                        //別クラスでViewを配置
                        ViewChanger.viewChange(context);

                    }
                });
            }

            public void parseJson(String json) {



                try {
                    JSONObject jsonObject = new JSONObject(json);
                    //国情報、地域名を取得
                    String location = jsonObject.getString("city");
                    JSONObject locationObject = new JSONObject(location);
                    name = locationObject.getString("name");
                    country = locationObject.getString("country");

                    //list配列を取得
                    JSONArray listArray = jsonObject.getJSONArray("list");
                    Log.d("TAG1", listArray.toString());

                    //listから天気情報を取り出す
                    for(int i = 0; i < 36; i++) {

                        JSONObject obj = listArray.getJSONObject(i);
                        Log.d("TAG2", obj.toString());
                        //weather配列を取得
                        JSONArray weatherArray = obj.getJSONArray("weather");



                        if (obj.getString("dt_txt").endsWith("12:00:00")){

                            int j = 0;
                            //晴れ、曇り、雨なのかを判定
                            JSONObject descriptionObject = weatherArray.getJSONObject(0);
                            String description = descriptionObject.getString("main");
                            descriptionList.add(description);

                            //気温と湿度オブジェクトを取得
                            String temperature = obj.getString("main");
                            JSONObject temperatureObject = new JSONObject(temperature);
                            //最高気温を取得,リストに追加
                            String maxTemp = temperatureObject.getString("temp_max");
                            Log.d("最高気温", maxTemp);
                            double parseMaxTemp = Double.parseDouble(maxTemp) - 273.15;
                            int parseMaxTempInt = (int)parseMaxTemp;
                            maxTempList.add(String.valueOf(parseMaxTempInt));

                            //最低気温を取得,リストに追加
                            String minTemp = temperatureObject.getString("temp_min");
                            Log.d("最低気温", minTemp);
                            double parseMinTemp = Double.parseDouble(minTemp) - 273.15;
                            int parseMinTempInt = (int)parseMinTemp;
                            minTempList.add(String.valueOf(parseMinTempInt));

                            //湿度を取得,リストに追加
                            String humidity = temperatureObject.getString("humidity");
                            humidityList.add(humidity);


                            //風速オブジェクトを取得
                            String wind = obj.getString("wind");
                            JSONObject windObject = new JSONObject(wind);
                            //風速を取得,リストに追加
                            String windSpeed = windObject.getString("speed");
                            windSpeedList.add(windSpeed);


                            j++;

                        }



                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

        });

    }

}
