package com.example.stiangrim.hangman;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends AppCompatActivity {


    LinearLayout layout;
    LinearLayout possibleLettersFirstRow;
    LinearLayout possibleLettersSecondRow;
    ImageView imageView;
    TextView correctWordsTextView;

    StringBuilder word;
    String originalWord;
    ArrayList<String> wordsInCategory;
    Random randomGenerator;
    boolean twoPlayer;
    int hangmanState = 0;
    int correctLetters = 0;
    int correctWords = 0;
    List<Character> guessedLetters;
    String[] toasts = {"Good job!", "Awesome!", "Well done!", "Excellent!", "Amazing!"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        imageView = (ImageView) findViewById(R.id.hangman_image);
        possibleLettersFirstRow = (LinearLayout) findViewById(R.id.lettersFirstRow);
        possibleLettersSecondRow = (LinearLayout) findViewById(R.id.lettersSecondRow);
        correctWordsTextView = (TextView) findViewById(R.id.correct_words);

        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("category") != null) {
            wordsInCategory = getWordsInCategory(bundle.getString("category"));
            randomGenerator = new Random();
            updateWordFromArrayList();
        } else if (bundle.getString("word") != null) {
            twoPlayer = true;
            word = new StringBuilder(bundle.getString("word").toUpperCase());
            originalWord = word.toString();
        }


        setInvisibleWord();
        placePossibleLetters();
        guessedLetters = new ArrayList<>();
    }

    private ArrayList<String> getWordsInCategory(String category) {
        String[] rawArray = getResources().getStringArray(R.array.category);
        ArrayList<String> wordsInCategory = new ArrayList<>();
        boolean categoryFound = false;

        for (String item : rawArray) {
            if (categoryFound) {
                if (!item.contains("--CATEGORY--")) {
                    wordsInCategory.add(item);
                } else {
                    return wordsInCategory;
                }
            }
            if (item.equals("--CATEGORY--" + category)) {
                categoryFound = true;
            }
        }

        return wordsInCategory;
    }

    private void placePossibleLetters() {
        for (char alphabet = 'A'; alphabet <= 'M'; alphabet++) {
            final TextView textView = new TextView(this);
            textView.setTextSize(20);
            textView.setPadding(8, 0, 8, 0);
            textView.setText(Character.toString(alphabet));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (textView.getText() != " ") checkLetter(textView);
                }
            });
            possibleLettersFirstRow.addView(textView);
        }
        for (char alphabet = 'N'; alphabet <= 'Z'; alphabet++) {
            final TextView textView = new TextView(this);
            textView.setTextSize(20);
            textView.setPadding(8, 0, 8, 0);
            textView.setText(Character.toString(alphabet));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (textView.getText() != " ") checkLetter(textView);
                }
            });
            possibleLettersSecondRow.addView(textView);
        }
    }

    private void setNewWord() {
        possibleLettersFirstRow.removeAllViews();
        possibleLettersSecondRow.removeAllViews();
        layout.removeAllViews();
        hangmanState = 0;
        correctLetters = 0;
        guessedLetters = new ArrayList<>();
        imageView.setImageResource(getResources().getIdentifier("hangman_" + hangmanState, "drawable", getPackageName()));
        updateWordFromArrayList();
        setInvisibleWord();
        placePossibleLetters();
    }

    public void checkLetter(TextView textView) {
        char letter = textView.getText().charAt(0);

        //Delete letter
        textView.setText(" ");

        if (letterExists(letter)) {
            setLetters(letter);
            if (word.toString().replaceAll(" ", "").length() == correctLetters) {
                correctWords++;
                StatisticsHandler.setWins(this, 1);

                if (wordsInCategory.size() == 0) {
                    openAlertDialog(getString(R.string.congratulations), getString(R.string.correctWord1p), Category.class);
                } else {
                    correctWordsTextView.setText(getString(R.string.correct_words_text) + Integer.toString(correctWords));
                    final Toast toast = Toast.makeText(this, getRandomToastText(), Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 600);

                    setNewWord();
                }
            }
        } else {
            incrementHangman();
            if (hangmanState == 8) {
                StatisticsHandler.setLosses(this, 1);
                openAlertDialog(getString(R.string.gameOver), getString(R.string.youLost) + " '" + originalWord + "'. \n" + getString(R.string.tryAgain), Category.class);
            }
        }
    }

    private String getRandomToastText() {
        int index = randomGenerator.nextInt(toasts.length);
        return toasts[index];
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
        layout = (LinearLayout) findViewById(R.id.word_layout);

        TextView textView;
        for (int i = 0; i < word.length(); i++) {
            textView = new TextView(this);
            textView.setTextSize(20);

            if (word.charAt(i) == ' ') {
                textView.setText(" ");
            } else {
                textView.setText("_ ");
            }

            layout.addView(textView);
        }
    }

    private boolean letterAlreadyGuessed(char letter) {
        for (Character guessedLetter : guessedLetters) {
            if (letter == guessedLetter) return true;
        }
        return false;
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
                TextView textView = (TextView) layout.getChildAt(i);
                textView.setText(Character.toString(letter));
                correctLetters++;
                guessedLetters.add(letter);
                word.setCharAt(i, '\u0000');
            }
        }
    }

    private void incrementHangman() {
        hangmanState++;
        imageView.setImageResource(getResources().getIdentifier("hangman_" + hangmanState, "drawable", getPackageName()));
    }

    private void updateWordFromArrayList() {
        int index = randomGenerator.nextInt(wordsInCategory.size());
        this.word = new StringBuilder(wordsInCategory.get(index).toUpperCase());
        originalWord = word.toString();
        wordsInCategory.remove(index);
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
