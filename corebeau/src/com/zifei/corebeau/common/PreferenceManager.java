package com.zifei.corebeau.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by im14s_000 on 2015/3/23.
 */
public class PreferenceManager {

    private static PreferenceManager instance;
    private SharedPreferences preference;

    private PreferenceManager(Context context) {
        preference = context.getSharedPreferences(PreferenceConstants.PREPERENCE_KEY,Context.MODE_PRIVATE);
    }

    public static PreferenceManager getInstance(Context context) {
        if (instance == null) {
            instance = new PreferenceManager(context);
        }

        return instance;
    }


    @Deprecated
    private PreferenceManager(Context context, String string) {
        preference = context.getSharedPreferences(string, Context.MODE_PRIVATE);
    }

    @Deprecated
    public static PreferenceManager getInstance(Context context, String string) {

        return instance = new PreferenceManager(context, string);
    }


    public void savePreferencesString(String key, String value) {
        Editor editor = preference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getPreferencesString(String key) {
        return preference.getString(key, null);
    }

    public void savePreferencesBoolean(String key, boolean value) {
        Editor editor = preference.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getPreferencesBoolean(String key, boolean defValue) {
        return preference.getBoolean(key, defValue);
    }

    public void savePreferencesInteger(String key, int value) {
        Editor editor = preference.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getPreferencesInteger(String key, int defValue) {
        return preference.getInt(key, defValue);
    }

    public void savePreferencesLong(String key, long value) {
        Editor editor = preference.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public long getPreferencesLong(String key, Long defValue) {
        return preference.getLong(key, defValue);
    }
}
