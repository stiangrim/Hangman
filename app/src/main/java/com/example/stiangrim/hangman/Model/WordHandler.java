package com.example.stiangrim.hangman.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by stiangrim on 12.09.2017.
 */

public class WordHandler {

    private StringBuilder word;
    private String originalWord;
    private ArrayList<String> words;
    private Random randomGenerator;

    public WordHandler(String[] rawArray) {
        words = getWords(rawArray);
        randomGenerator = new Random();
        setNewWord();
    }

    public void setWord(StringBuilder word) {
        this.word = word;
    }

    public StringBuilder getWord() {
        return word;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public boolean allWordsUsed() {
        return words.size() == 0;
    }

    public String getTrimmedWord() {
        return word.toString().replaceAll(" ", "");
    }

    public boolean letterExists(char letter) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                return true;
            }
        }
        return false;
    }

    public void setNewWord() {
        int index = randomGenerator.nextInt(words.size());
        this.word = new StringBuilder(words.get(index).toUpperCase());
        originalWord = word.toString();
        words.remove(index);
    }

    private ArrayList<String> getWords(String[] rawArray) {
        words = new ArrayList<>();
        Collections.addAll(words, rawArray);
        return words;
    }
}
