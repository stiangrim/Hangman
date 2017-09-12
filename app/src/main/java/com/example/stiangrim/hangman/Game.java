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

    GameDTO gameDTO;
    WordHandler wordHandler;

    Drawable red = new PaintDrawable(Color.RED);
    Drawable green = new PaintDrawable(Color.GREEN);

    int hangmanState;
    int correctLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (savedInstanceState != null) {
            gameDTO = (GameDTO) savedInstanceState.getSerializable("game");
            this.invisibleWordLayout = gameDTO.getInvisibleWordLayout();
            this.tableLayout = gameDTO.getTableLayout();
            this.hangmanImage = gameDTO.getHangmanImage();
            this.wordHandler = gameDTO.getWordHandler();
            this.hangmanState = gameDTO.getHangmanState();
            this.correctLetters = gameDTO.getCorrectLetters();

            this.hangmanImage.setImageResource(getResources().getIdentifier("hangman_" + hangmanState, "drawable", getPackageName()));
        } else {
            invisibleWordLayout = (LinearLayout) findViewById(R.id.word_layout);
            tableLayout = (TableLayout) findViewById(R.id.lettersLayout);
            hangmanImage = (ImageView) findViewById(R.id.hangman_image);

            hangmanState = 0;
            correctLetters = 0;

            wordHandler = new WordHandler(getResources().getStringArray(R.array.words));

            setInvisibleWord();
            placePossibleLetters();
        }
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
        hangmanImage.setImageResource(getResources().getIdentifier("hangman_" + hangmanState, "drawable", getPackageName()));
        wordHandler.setNewWord();
        setInvisibleWord();
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
                    openAlertDialog(getString(R.string.congratulations), getString(R.string.correctWord1p), Game.class);
                } else {
                    setNewWord();
                }
            }
        } else {
            button.setBackground(red);
            incrementHangman();
            if (hangmanState == 8) {
                StatisticsHandler.setLosses(this, 1);
                openAlertDialog(getString(R.string.gameOver), getString(R.string.youLost) + " '" + wordHandler.getOriginalWord() + "'. \n" + getString(R.string.tryAgain), Home.class);
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
        StringBuilder word = wordHandler.getWord();

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

        wordHandler.setWord(word);
    }

    private void setCorrectLetters(char letter) {
        StringBuilder word = wordHandler.getWord();

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != '\u0000' && word.charAt(i) == letter) {
                TextView textView = (TextView) invisibleWordLayout.getChildAt(i);
                textView.setText(Character.toString(letter));
                correctLetters++;
                word.setCharAt(i, '\u0000');
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
        gameDTO = new GameDTO(invisibleWordLayout, tableLayout, hangmanImage, wordHandler, hangmanState, correctLetters);
        outState.putSerializable("game", gameDTO);
    }
}
