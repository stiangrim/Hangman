package com.example.stiangrim.hangman.Model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by stiantornholmgrimsgaard on 20.09.2017.
 */

public class LanguageHandler {

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public static void setDefaultLanguage(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("language", input);
        editor.apply();
    }

    public static String getDefaultLanguage(Context context) {
        return getPrefs(context).getString("language", "");
    }

}
