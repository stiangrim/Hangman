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

    private LinearLayout invisibleWordLayout;
    private TableLayout tableLayout;
    private ImageView hangmanImage;
    private WordHandler wordHandler;
    private int hangmanState;
    private int correctLetters;

    public GameDTO(LinearLayout invisibleWordLayout, TableLayout tableLayout, ImageView hangmanImage, WordHandler wordHandler, int hangmanState, int correctLetters) {
        this.invisibleWordLayout = invisibleWordLayout;
        this.tableLayout = tableLayout;
        this.hangmanImage = hangmanImage;
        this.wordHandler = wordHandler;
        this.hangmanState = hangmanState;
        this.correctLetters = correctLetters;
    }

    public LinearLayout getInvisibleWordLayout() {
        return invisibleWordLayout;
    }

    public TableLayout getTableLayout() {
        return tableLayout;
    }

    public ImageView getHangmanImage() {
        return hangmanImage;
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
}
