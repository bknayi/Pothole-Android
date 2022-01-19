package com.potholedetection;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class UserData {

    private String PREF = "user";
    void saveUserData(Context c, String user){
        SharedPreferences.Editor editor = c.getSharedPreferences(PREF, MODE_PRIVATE).edit();
        Log.d("UserData",user);
        editor.putString("username", user);
        editor.apply();
        editor.commit();
        Log.d("UserData",user);


    }

    public String getUsername(Context c){
        String user= "no";
        SharedPreferences prefs = c.getSharedPreferences(PREF, MODE_PRIVATE);
        String restoredText = prefs.getString("username", null);
        if (restoredText != null) {
            user = prefs.getString("username", "no");//"No name defined" is the default value.
            Log.d("UserData",user);
        }

        return user;
    }

    public void deleteUser(Context c){
        SharedPreferences.Editor editor = c.getSharedPreferences(PREF, MODE_PRIVATE).edit();
        editor.remove("username");
        editor.apply();
    }

    public void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }
}
