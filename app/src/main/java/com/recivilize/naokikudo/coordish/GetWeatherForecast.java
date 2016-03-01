package com.recivilize.naokikudo.coordish;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;



public class GetWeatherForecast extends AppCompatActivity {

    String requestURL;
    String data;
    int id;
    String cityName;
    float currentTemp;
    float minTemp;
    float maxTemp;




    public String[] getForecast (float latitude, float longitude) {
         requestURL = "http://api.openweathermap.org/data/2.5/find?lat="+String.valueOf(latitude)+"&lon="+String.valueOf(longitude)+"&cnt=1&APPID=d7689f4744a178cb7c399d8bf0e3c6f8";
        try {
            URL url = new URL(requestURL);
            InputStream is = url.openConnection().getInputStream();

            // JSON形式で結果が返るためパースのためにStringに変換する
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while (null != (line = reader.readLine())) {
                stringBuilder.append(line);
            }
            data = stringBuilder.toString();


        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }try {
            JSONObject rootObj = new JSONObject(data);
            JSONArray listArray = rootObj.getJSONArray("list");

            JSONObject obj = listArray.getJSONObject(0);

            // 地点ID
            id = obj.getInt("id");

            // 地点名
            cityName = obj.getString("name");

            // 気温(Kから℃に変換)
            JSONObject mainObj = obj.getJSONObject("main");
            currentTemp = (float) (mainObj.getDouble("temp") - 273.15f);

            minTemp = (float) (mainObj.getDouble("temp_min") - 273.15f);

            maxTemp = (float) (mainObj.getDouble("temp_max") - 273.15f);

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

            String weatherInformation[] = {
                    String.valueOf(id), cityName, String.valueOf(minTemp), String.valueOf(maxTemp)
            };

            return weatherInformation;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }





    }

}
