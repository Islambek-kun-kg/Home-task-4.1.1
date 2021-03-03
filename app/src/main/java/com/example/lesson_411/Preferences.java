package com.example.lesson_411;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
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

    public void deleteIsShow() {
        preferences.edit().remove("isShown").apply();
    }
}
