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

import com.example.stiangrim.hangman.DTO.GameDTO;
import com.example.stiangrim.hangman.Model.StatisticsHandler;
import com.example.stiangrim.hangman.Model.WordHandler;

public class Game extends AppCompatActivity {

    LinearLayout invisibleWordLayout;
    TableLayout tableLayout;
    ImageView hangmanImage;

    WordHandler wordHandler;

    StringBuilder correctGuessedLetters;
    StringBuilder wrongGuessedLetters;

    Drawable red = new PaintDrawable(Color.RED);
    Drawable green = new PaintDrawable(Color.GREEN);

    int hangmanState;
    int correctLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        invisibleWordLayout = (LinearLayout) findViewById(R.id.word_layout);
        tableLayout = (TableLayout) findViewById(R.id.lettersLayout);
        hangmanImage = (ImageView) findViewById(R.id.hangman_image);

        if (savedInstanceState != null) {
            restoreSavedInstanceVariables((GameDTO) savedInstanceState.getSerializable("game"));
        } else {
            hangmanState = 0;
            correctLetters = 0;

            wordHandler = new WordHandler(getResources().getStringArray(R.array.words));
            wrongGuessedLetters = new StringBuilder();
            instantiateGuessedLetters();
            setInvisibleWord(wordHandler.getWord());
            placePossibleLetters();
        }
    }

    private void instantiateGuessedLetters() {
        correctGuessedLetters = new StringBuilder();
        for (int i = 0; i < wordHandler.getWord().length(); i++) {
            if (wordHandler.getWord().charAt(i) == ' ') correctGuessedLetters.append(' ');
            else correctGuessedLetters.append('\u0000');
        }
    }

    private void restoreSavedInstanceVariables(GameDTO gameDTO) {
        this.wordHandler = gameDTO.getWordHandler();
        this.hangmanState = gameDTO.getHangmanState();
        this.correctLetters = gameDTO.getCorrectLetters();
        this.correctGuessedLetters = gameDTO.getCorrectGuessedLetters();
        this.wrongGuessedLetters = gameDTO.getWrongGuessedLetters();

        this.hangmanImage.setImageResource(getResources().getIdentifier("hangman_" + hangmanState, "drawable", getPackageName()));
        setInvisibleWordFromSavedInstanceState(correctGuessedLetters);
        placePossibleLetters();
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

        updateButtonIfUsed(button);

        return button;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void updateButtonIfUsed(Button button) {
        for (int i = 0; i < wrongGuessedLetters.length(); i++) {
            if(wrongGuessedLetters.charAt(i) == button.getText().charAt(0)) {
                button.setBackground(red);
                button.getBackground().setAlpha(50);
                button.setClickable(false);
            }
        }
        for (int i = 0; i < correctGuessedLetters.length(); i++) {
            if (correctGuessedLetters.charAt(i) == button.getText().charAt(0)) {
                button.setBackground(green);
                button.getBackground().setAlpha(50);
                button.setClickable(false);
            }
        }
    }

    private void setNewWord() {
        tableLayout.removeAllViews();
        invisibleWordLayout.removeAllViews();
        hangmanState = 0;
        correctLetters = 0;
        wrongGuessedLetters = new StringBuilder();
        hangmanImage.setImageResource(getResources().getIdentifier("hangman_" + hangmanState, "drawable", getPackageName()));
        wordHandler.setNewWord();
        instantiateGuessedLetters();
        setInvisibleWord(wordHandler.getWord());
        placePossibleLetters();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void checkLetter(Button button) {
        char letter = button.getText().charAt(0);

        if (wordHandler.letterExists(letter)) {
            button.setBackground(green);
            setCorrectLetters(letter);
            if (wordHandler.getTrimmedWord().length() == correctLetters) {
                StatisticsHandler.setWins(this, 1);
                if (wordHandler.allWordsUsed()) {
                    openAlertDialog(getString(R.string.congratulations), getString(R.string.correctWord1p));
                } else {
                    setNewWord();
                }
            }
        } else {
            wrongGuessedLetters.append(letter);
            button.setBackground(red);
            incrementHangman();
            if (hangmanState == 8) {
                StatisticsHandler.setLosses(this, 1);
                openAlertDialog(getString(R.string.gameOver), getString(R.string.youLost) + " '" + wordHandler.getOriginalWord() + "'. \n" + getString(R.string.tryAgain));
            }
        }

        button.getBackground().setAlpha(50);
        button.setClickable(false);
    }

    private void openAlertDialog(String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Game.this, Game.class);
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

    private void setInvisibleWord(StringBuilder word) {
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

    private void setInvisibleWordFromSavedInstanceState(StringBuilder word) {
        TextView textView;
        for (int i = 0; i < word.length(); i++) {
            textView = new TextView(this);
            textView.setTextSize(30);

            if (word.charAt(i) == '\u0000') {
                textView.setText(" _ ");
            } else if (word.charAt(i) == ' ') {
                textView.setText(" ");
            } else {
                textView.setText(Character.toString(word.charAt(i)));
            }
            invisibleWordLayout.addView(textView);
        }
    }

    private void setCorrectLetters(char letter) {
        StringBuilder word = wordHandler.getWord();

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != '\u0000' && word.charAt(i) == letter) {
                TextView textView = (TextView) invisibleWordLayout.getChildAt(i);
                textView.setText(Character.toString(letter));
                correctLetters++;
                word.setCharAt(i, '\u0000');
                correctGuessedLetters.setCharAt(i, letter);
            }
        }

        wordHandler.setWord(word);
    }

    private void incrementHangman() {
        hangmanState++;
        hangmanImage.setImageResource(getResources().getIdentifier("hangman_" + hangmanState, "drawable", getPackageName()));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        GameDTO gameDTO = new GameDTO(wordHandler, hangmanState, correctLetters, correctGuessedLetters, wrongGuessedLetters);
        outState.putSerializable("game", gameDTO);
    }
}
