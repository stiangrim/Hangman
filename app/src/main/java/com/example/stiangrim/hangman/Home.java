package com.example.stiangrim.hangman;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.example.stiangrim.hangman.Model.LanguageHandler;
import com.example.stiangrim.hangman.Model.ToastHandler;

import java.util.Locale;

public class Home extends AppCompatActivity {

    ImageView norwegian;
    ImageView english;
    ToastHandler toastHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        norwegian = (ImageView) findViewById(R.id.flag_norway);
        english = (ImageView) findViewById(R.id.flag_united_kingdom);
        toastHandler = new ToastHandler(this);

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getBoolean("norwegian")) {
                english.setAlpha(100);
                norwegian.setClickable(false);
            } else {
                norwegian.setAlpha(100);
                english.setClickable(false);
            }
        } else {
            String lang = LanguageHandler.getDefaultLanguage(this);
            if (lang.equals("nb")) {
                setLocale("nb", true);
                english.setAlpha(100);
                norwegian.setClickable(false);
            } else if (lang.equals("en")) {
                norwegian.setAlpha(100);
                english.setClickable(false);
            }
        }
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
        setLocale("nb", true);
        LanguageHandler.setDefaultLanguage(this, "nb");
    }

    public void setLanguageToEnglish(View view) {
        setLocale("en", false);
        LanguageHandler.setDefaultLanguage(this, "en");
    }

    private void setLocale(String lang, boolean norwegianClicked) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        Intent intent = new Intent(this, Home.class);
        intent.putExtra("norwegian", norwegianClicked);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        System.exit(0);
    }

}
