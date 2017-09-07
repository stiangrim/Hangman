package com.example.stiangrim.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class WordInput2P extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_input_2p);
    }

    public void createGame(View view) {
        EditText word = (EditText) findViewById(R.id.word_to_be_guessed);

        //TODO: Her må det legges inn en sjekk på at input kun er bokstaver

        if(word.toString() != null) {
            Intent intent = new Intent(this, Game.class);
            intent.putExtra("word", word.getText().toString());
            startActivity(intent);
        }
    }
}
