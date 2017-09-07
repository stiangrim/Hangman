package com.example.stiangrim.hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Statistics extends AppCompatActivity {

    TextView wordsGuessed;
    TextView wordsNotGuessed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        wordsGuessed = (TextView) findViewById(R.id.words_guessed_result);
        wordsGuessed.setText(Integer.toString(StatisticsHandler.getWins(this)));

        wordsNotGuessed = (TextView) findViewById(R.id.words_not_guessed_result);
        wordsNotGuessed.setText(Integer.toString(StatisticsHandler.getLosses(this)));
    }
}
