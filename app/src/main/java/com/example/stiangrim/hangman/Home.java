package com.example.stiangrim.hangman;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import java.util.Locale;

public class Home extends AppCompatActivity {

    ImageView norwegian;
    ImageView english;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        norwegian = (ImageView) findViewById(R.id.flag_norway);
        english = (ImageView) findViewById(R.id.flag_united_kingdom);
    }

    public void goToGameActivity(View view) {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    public void goToStatisticsActivity(View view) {
        Intent intent = new Intent(this, Statistics.class);
        startActivity(intent);
    }

    public void goToRulesActivity(View view) {
        Intent intent = new Intent(this, Rules.class);
        startActivity(intent);
    }

    public void setLanguageToNorwegian(View view) {
        setLocale("nb");
    }

    public void setLanguageToEnglish(View view) {
        setLocale("");
    }

    private void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }

}
