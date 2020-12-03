package com.codemountain.a4kwallpaper;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.codemountain.a4kwallpaper.utils.SharedPref;

public class WallpaperApplication extends Application {
    private Context context;
    private static SharedPreferences sharedPref;

    public static SharedPreferences getSharedPref() {
        return sharedPref;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPref.init();
        context = getApplicationContext();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
