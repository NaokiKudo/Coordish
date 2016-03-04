package com.recivilize.naokikudo.coordish;


import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class viewChanger {






    public void viewChange () {
        //---------------------変数の定義
        List<String> descriptionList = GetWeatherForecast.descriptionList;
        List<String> maxTempList = GetWeatherForecast.maxTempList;
        List<String> minTempList = GetWeatherForecast.minTempList;
        List<String> humidityList = GetWeatherForecast.humidityList;
        List<String> windSpeedList = GetWeatherForecast.windSpeedList;

        TextView[] date = Wash_Recommend.date;

        TextView todayWeather = Wash_Recommend.todayWeather;
        TextView tomorrowWeather = Wash_Recommend.tomorrowWeather;
        TextView threeDaysAfterWeather = Wash_Recommend.threeDaysAfterWeather;
        TextView fourDaysAfterWeather = Wash_Recommend.fourDaysAfterWeather;
        TextView fiveDaysAfterWeather = Wash_Recommend.fiveDaysAfterWeather;
        TextView weatherLocation = Wash_Recommend.weatherLocation;
        TextView recommendation = Wash_Recommend.recommendation;
        TextView when = Wash_Recommend.when;
        String name = GetWeatherForecast.name;
        String country = GetWeatherForecast.country;
        //---------------------ここまで



        weatherLocation.setText("in " + name + ", "+ country);
        todayWeather.setText(descriptionList.get(0));
        tomorrowWeather.setText(descriptionList.get(1));
        threeDaysAfterWeather.setText(descriptionList.get(2));
        fourDaysAfterWeather.setText(descriptionList.get(3));
        if(descriptionList.size()==5) {
            fiveDaysAfterWeather.setText(descriptionList.get(4));
        }

        String[] whenText = {"Today", "Tomorrow", "3 Days After", "4 Days After", "5Days After", "6DaysAfter"};

        //時間(hour)を取得
        long currentTimeMillis = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);
        Log.d("日にちい", ""+calendar.get(Calendar.DATE));

        //12時以降は取得する天気予報が次の日からになるので、表示をずらす
        if(descriptionList.size()==4){
            for(int i = 0; i < date.length - 1; i++) {
                date[i].setText(whenText[i+1]);
            }
        }

        //推奨日をセットしたかのスイッチ
        //0はセットなし 1はセット済み
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
                    if (calendar.get(Calendar.HOUR_OF_DAY) >= 12) {
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
                    if (calendar.get(Calendar.HOUR_OF_DAY) >= 12){
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

        //天気、最高気温、最低気温、湿度、風速をレイアウトにセット

    }
}
