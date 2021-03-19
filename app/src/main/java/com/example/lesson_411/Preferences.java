package com.example.lesson_411;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static Preferences setInstance = null;
    private final SharedPreferences preferences;

    public Preferences(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
    }

    public void saveIsShown() {
        preferences.edit().putBoolean("isShown", true).apply();
    }

    public boolean isShown() {
        return preferences.getBoolean("isShown", false);
    }

    public static Preferences getInstance(Context context) {
        return setInstance = new Preferences(context);
    }

    public static Preferences getInstance() {
        return setInstance;
    }

    public void saveImg(String txt) {
        preferences.edit().putString("saveImg", txt).apply();
    }

    public String getImg() {
        return preferences.getString("saveImg", null);
    }

    public void save(String txt) {
        preferences.edit().putString("save", txt).apply();
    }

    public String getSave() {
        return preferences.getString("save", null);
    }
}