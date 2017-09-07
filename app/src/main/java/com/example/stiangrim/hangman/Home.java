package com.example.stiangrim.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.Locale;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void startNewGame(View view) {
        Intent intent = new Intent(this, Category.class);
        startActivity(intent);
    }

    public void goTo2Player(View view) {
        Intent intent = new Intent(this, WordInput2P.class);
        startActivity(intent);
    }

    public void goToStatistics(View view) {
        Intent intent = new Intent(this, Statistics.class);
        startActivity(intent);
    }

    public void setLanguageToEnglish(View view) {
        setLocale("");
    }

    public void goToRules(View view) {
        Intent intent = new Intent(this, Rules.class);
        startActivity(intent);
    }

    public void setLanguageToNorwegian(View view) {
        setLocale("nb");
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
