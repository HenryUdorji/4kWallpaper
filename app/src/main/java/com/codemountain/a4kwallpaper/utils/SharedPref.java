package com.codemountain.a4kwallpaper.utils;

import android.content.SharedPreferences;

import com.codemountain.a4kwallpaper.WallpaperApplication;

import static com.codemountain.a4kwallpaper.utils.Constants.CATEGORY;


public class SharedPref {
    private static SharedPref instance;

    public SharedPref() {
    }

    public static SharedPref init() {
        if (instance == null){
            instance = new SharedPref();
        }
        return instance;
    }

    public static SharedPref getSharedPrefInstance() {
        return instance;
    }

    public void putStringInPref(String key, String value) {
        SharedPreferences.Editor editor = WallpaperApplication.getSharedPref().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putIntInPref(String key, int value) {
        SharedPreferences.Editor editor = WallpaperApplication.getSharedPref().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putBooleanInPref(String key, boolean value) {
        SharedPreferences.Editor editor = WallpaperApplication.getSharedPref().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getCategoryName() {
        return  WallpaperApplication.getSharedPref().getString(CATEGORY, "");
    }

}
