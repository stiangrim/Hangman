package com.example.stiangrim.hangman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.stiangrim.hangman.Model.StatisticsHandler;

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
