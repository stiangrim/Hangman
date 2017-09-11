package com.example.stiangrim.hangman;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.stiangrim.hangman.Model.StatisticsHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game extends AppCompatActivity {


    /*LinearLayout possibleLettersFirstRow;
    LinearLayout possibleLettersSecondRow;
    LinearLayout possibleLettersThirdRow;
    LinearLayout possibleLettersFourthRow;*/
    LinearLayout invisibleWordLayout;
    TableLayout tableLayout;
    ImageView hangmanImage;

    Drawable red = new PaintDrawable(Color.RED);
    Drawable green = new PaintDrawable(Color.GREEN);

    StringBuilder word;
    String originalWord;
    ArrayList<String> words;
    Random randomGenerator;
    int hangmanState = 0;
    int correctLetters = 0;
    int correctWords = 0;
    List<Character> guessedLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tableLayout = (TableLayout) findViewById(R.id.lettersLayout);
        hangmanImage = (ImageView) findViewById(R.id.hangman_image);

        words = getWords();
        randomGenerator = new Random();
        updateWordFromArrayList();


        setInvisibleWord();
        placePossibleLetters();
        guessedLetters = new ArrayList<>();
    }

    private ArrayList<String> getWords() {
        String[] rawArray = getResources().getStringArray(R.array.words);
        ArrayList<String> words = new ArrayList<>();
        Collections.addAll(words, rawArray);
        return words;
    }

    private void placePossibleLetters() {
        String alphabet = getResources().getString(R.string.alphabet);

        int length = (int) Math.ceil(Math.sqrt(alphabet.length()));
        int counter = 0;

        for (int col = 0; col < length; col++) {
            TableRow tableRow = new TableRow(this);

            TableRow.LayoutParams tlp = new TableRow.LayoutParams(170, 135);
            tableRow.setLayoutParams(tlp);

            tableLayout.addView(tableRow);

            for (int row = 0; row < length; row++) {
                if (counter < alphabet.length()) {
                    Button button = getButton(Character.toString(alphabet.charAt(counter)));
                    button.setLayoutParams(tlp);

                    tableRow.addView(button);
                }
                counter++;
            }
        }
    }

    private Button getButton(String text) {
        final Button button = new Button(this);
        button.setTextSize(20);
        button.setPadding(0, 0, 0, 0);
        button.setText(text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (button.getBackground() != red || button.getBackground() != green)
                    checkLetter(button);
            }
        });

        return button;
    }

    private void setNewWord() {
        tableLayout.removeAllViews();
        invisibleWordLayout.removeAllViews();
        hangmanState = 0;
        correctLetters = 0;
        guessedLetters = new ArrayList<>();
        hangmanImage.setImageResource(getResources().getIdentifier("hangman_" + hangmanState, "drawable", getPackageName()));
        updateWordFromArrayList();
        setInvisibleWord();
        placePossibleLetters();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void checkLetter(Button button) {
        char letter = button.getText().charAt(0);

        if (letterExists(letter)) {
            button.setBackground(green);
            setLetters(letter);
            if (word.toString().replaceAll(" ", "").length() == correctLetters) {
                correctWords++;
                StatisticsHandler.setWins(this, 1);

                if (words.size() == 0) {
                    openAlertDialog(getString(R.string.congratulations), getString(R.string.correctWord1p), Home.class);
                } else {
                    setNewWord();
                }
            }
        } else {
            button.setBackground(red);
            incrementHangman();
            if (hangmanState == 8) {
                StatisticsHandler.setLosses(this, 1);
                openAlertDialog(getString(R.string.gameOver), getString(R.string.youLost) + " '" + originalWord + "'. \n" + getString(R.string.tryAgain), Home.class);
            }
        }

        button.getBackground().setAlpha(50);
        button.setClickable(false);
    }

    private void openAlertDialog(String title, String message, final Class destinationClass) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Game.this, destinationClass);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getString(R.string.exitToMenu), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Game.this, Home.class);
                        startActivity(intent);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setTitle(title);
        alertDialog.show();
    }

    private void setInvisibleWord() {
        invisibleWordLayout = (LinearLayout) findViewById(R.id.word_layout);

        TextView textView;
        for (int i = 0; i < word.length(); i++) {
            textView = new TextView(this);
            textView.setTextSize(30);

            if (word.charAt(i) == ' ') {
                textView.setText(" ");
            } else {
                textView.setText(" _ ");
            }

            invisibleWordLayout.addView(textView);
        }
    }

    private boolean letterExists(char letter) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                return true;
            }
        }
        return false;
    }

    private void setLetters(char letter) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != '\u0000' && word.charAt(i) == letter) {
                TextView textView = (TextView) invisibleWordLayout.getChildAt(i);
                textView.setText(Character.toString(letter));
                correctLetters++;
                guessedLetters.add(letter);
                word.setCharAt(i, '\u0000');
            }
        }
    }

    private void incrementHangman() {
        hangmanState++;
        hangmanImage.setImageResource(getResources().getIdentifier("hangman_" + hangmanState, "drawable", getPackageName()));
    }

    private void updateWordFromArrayList() {
        int index = randomGenerator.nextInt(words.size());
        this.word = new StringBuilder(words.get(index).toUpperCase());
        originalWord = word.toString();
        words.remove(index);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savedInstanceState.getSerializable("game");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("game", Game.class);
    }
}
