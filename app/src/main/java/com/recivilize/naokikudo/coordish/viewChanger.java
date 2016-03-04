package com.recivilize.naokikudo.coordish;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.recivilize.naokikudo.coordish.activity.WashRecommendActivity;

import java.util.List;

public class ViewChanger {

    public void viewChange (Context context) {
        //---------------------変数の定義
        List<String> descriptionList = GetWeatherForecast.descriptionList;
        List<String> maxTempList = GetWeatherForecast.maxTempList;
        List<String> minTempList = GetWeatherForecast.minTempList;
        List<String> humidityList = GetWeatherForecast.humidityList;
        List<String> windSpeedList = GetWeatherForecast.windSpeedList;

        TextView recommendation = WashRecommendActivity.recommendation;
        TextView weatherLocation = WashRecommendActivity.weatherLocation;
        TextView when = WashRecommendActivity.when;
        String name = GetWeatherForecast.name;
        String country = GetWeatherForecast.country;
        Log.d("TAG1", descriptionList.get(0));
        //天気取得場所の設定
        weatherLocation.setText("in " + name + ", "+ country);
        //日にちの配列
        String[] whenText = {"Today", "Tomorrow", "2 Days After", "3 Days After", "4 Days After", "5 DaysAfter"};



        //12時以降は取得する天気予報が次の日からになるので、表示をずらす


        //推奨日をセットしたかのスイッチ
        int switch1 = 0;
        int switch2 = 0;
        for (int i = 0; i < descriptionList.size(); i++){
            if(descriptionList.get(i).startsWith("Clear")) {
                switch1 ++;
            }
            if(descriptionList.get(i).startsWith("Clouds")) {
                switch2++;
            }
        }


        //直前の晴れの日を洗濯推奨日にする。
        if(switch1 != 0) {
            for (int i = 0; i < descriptionList.size(); i++) {
                if (descriptionList.get(i).startsWith("Clear")) {
                    if (descriptionList.size()==4) {
                        when.setText(whenText[i + 1]);
                        break;
                    } else {
                        when.setText(whenText[i]);
                        break;
                    }
                }

            }
        }
        //晴れの日がなかった場合、直前の曇りの日を推奨日にする
        if (switch1 == 0){
            for(int i =0; i < descriptionList.size(); i++) {
                if (descriptionList.get(i).startsWith("Clouds") ) {
                    if (descriptionList.size() ==4){
                        when.setText(whenText[i+1]);
                        break;
                    } else {
                        when.setText(whenText[i]);
                        break;
                    }
                }
            }
        }

        if(switch1==0 && switch2==0){
            when.setText("");
            recommendation.setText("No chance to wash in a few days");
        }

        //

        //天気、最高気温、最低気温、湿度、風速をレイアウトにセット
        LinearLayout cardLinear = WashRecommendActivity.cardLinear;
        cardLinear.removeAllViews();

        for (int i = 0; i < descriptionList.size(); i++) {
            Log.d("description", descriptionList.get(i));
            LayoutInflater inflater = WashRecommendActivity.inflater;

            LinearLayout linearLayout;
            TextView description;
            TextView dateText;
            TextView maxTempText;
            TextView minTempText;
            TextView humidityText;
            TextView windSpeedText;
            ImageView weatherImage;

            linearLayout = (LinearLayout) inflater.inflate(R.layout.cards_layout, null);
            description = (TextView) linearLayout.findViewById(R.id.description);
            dateText = (TextView) linearLayout.findViewById(R.id.date);
            maxTempText = (TextView) linearLayout.findViewById(R.id.maxTemp);
            minTempText = (TextView) linearLayout.findViewById(R.id.minTemp);
            humidityText = (TextView) linearLayout.findViewById(R.id.humidityNum);
            windSpeedText = (TextView)linearLayout.findViewById(R.id.windSpeed);
            weatherImage = (ImageView) linearLayout.findViewById(R.id.weatherImage);
            weatherImage.setScaleType(ImageView.ScaleType.FIT_XY);

            description.setText(descriptionList.get(i));
            dateText.setText(whenText[i]);
            maxTempText.setText(maxTempList.get(i) + "℃");
//            Log.d("最高気温", maxTempList.get(i));
            minTempText.setText(minTempList.get(i) + "℃");
//            Log.d("最低気温", minTempList.get(i));
            humidityText.setText(humidityList.get(i) + "%");
            windSpeedText.setText(windSpeedList.get(i) + "m/s");


            if (descriptionList.get(i).startsWith("Clear")) {
                weatherImage.setImageResource(R.mipmap.sunny);
            }  if(descriptionList.get(i).startsWith("Clouds")) {
                weatherImage.setImageResource(R.mipmap.cloudy);
            }  if (descriptionList.get(i).startsWith("Rain")) {
                weatherImage.setImageResource(R.mipmap.rainy);
            }  if (descriptionList.get(i).startsWith("Snow")) {
                weatherImage.setImageResource(R.mipmap.snow);
            }

            cardLinear.addView(linearLayout);

        }

    }
}
