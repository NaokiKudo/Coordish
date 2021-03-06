

//一時的に無効化されたクラス


package com.recivilize.naokikudo.coordish.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.recivilize.naokikudo.coordish.R;

public class MainActivity extends AppCompatActivity {

    TextView welcomeText;
    Button maleButton;
    Button femaleButton;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //すぐ画面移行(一時的)
        Intent intent = new Intent(this, WashRecommendActivity.class);
        startActivity(intent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        welcomeText = (TextView)findViewById(R.id.welcomeText);
        maleButton = (Button)findViewById(R.id.maleButton);
        femaleButton = (Button)findViewById(R.id.femaleButton);
        //データの保存領域を確保する
        pref = getSharedPreferences("dataSave", MODE_PRIVATE);
//        if (pref.getInt("sex", 0) == 1 || pref.getInt("sex", 0) == 2) {
//            Intent intent = new Intent(this, WashRecommendActivity.class);
//            startActivity(intent);
//        }



    }

    public void setMaleColor (View v){
        //ここに色の変更を書き込む

        //男という情報を保存する
        editor= pref.edit();
        editor.putInt("sex", 1);
        editor.commit();

        Intent intent = new Intent(this, WashRecommendActivity.class);
        startActivity(intent);


    }

    public void setFemaleColor (View v){
        //ここに色の変更を書き込む

        //女という情報を保存する
        editor= pref.edit();
        editor.putInt("sex", 2);
        editor.commit();
        Intent intent = new Intent(this, WashRecommendActivity.class);
        startActivity(intent);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
