package com.example.stiangrim.hangman.DTO;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.example.stiangrim.hangman.Model.WordHandler;

import java.io.Serializable;

/**
 * Created by stiangrim on 12.09.2017.
 */

public class GameDTO implements Serializable {

    private WordHandler wordHandler;
    private int hangmanState;
    private int correctLetters;
    private StringBuilder guessedLetters;

    public GameDTO(WordHandler wordHandler, int hangmanState, int correctLetters, StringBuilder guessedLetters) {
        this.wordHandler = wordHandler;
        this.hangmanState = hangmanState;
        this.correctLetters = correctLetters;
        this.guessedLetters = guessedLetters;
    }

    public WordHandler getWordHandler() {
        return wordHandler;
    }

    public int getHangmanState() {
        return hangmanState;
    }

    public int getCorrectLetters() {
        return correctLetters;
    }

    public StringBuilder getGuessedLetters() {
        return guessedLetters;
    }
}
