package com.example.stiangrim.hangman.Model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by stiangrim on 07.09.2017.
 */

public class StatisticsHandler {

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public static void clearAllData(Context context) {
        getPrefs(context).edit().clear().apply();
    }

    public static void setWins(Context context, int input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt("wins", getWins(context) + input);
        editor.apply();
    }

    public static int getWins(Context context) {
        return getPrefs(context).getInt("wins", 0);
    }

    public static void setLosses(Context context, int input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt("losses", getLosses(context) + input);
        editor.apply();
    }

    public static int getLosses(Context context) {
        return getPrefs(context).getInt("losses", 0);
    }
}
