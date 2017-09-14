package com.example.stiangrim.hangman.DTO;

import com.example.stiangrim.hangman.Model.WordHandler;

import java.io.Serializable;

/**
 * Created by stiangrim on 12.09.2017.
 */

public class GameDTO implements Serializable {

    private WordHandler wordHandler;
    private int hangmanState;
    private int correctLetters;
    private StringBuilder correctGuessedLetters;
    private StringBuilder wrongGuessedLetters;

    public GameDTO(WordHandler wordHandler, int hangmanState, int correctLetters, StringBuilder correctGuessedLetters, StringBuilder wrongGuessedLetters) {
        this.wordHandler = wordHandler;
        this.hangmanState = hangmanState;
        this.correctLetters = correctLetters;
        this.correctGuessedLetters = correctGuessedLetters;
        this.wrongGuessedLetters = wrongGuessedLetters;
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

    public StringBuilder getCorrectGuessedLetters() {
        return correctGuessedLetters;
    }

    public StringBuilder getWrongGuessedLetters() {
        return wrongGuessedLetters;
    }
}
