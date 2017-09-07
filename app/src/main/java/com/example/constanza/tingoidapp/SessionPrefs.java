package com.example.constanza.tingoidapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class SessionPrefs {

    public static final String PREFS_EMAIL = "PREFS_EMAIL";
    public static final String PREFS_NAME = "PREFS_NAME";
    public static final String PREFS_TOKEN = "PREFS_TOKEN";

    private final SharedPreferences mPrefs;
    private boolean mIsloggedIn = false;

    private static SessionPrefs INSTANCE;

    public static SessionPrefs get(Context context){
        if (INSTANCE==null){
            INSTANCE = new SessionPrefs(context);
        }
        return INSTANCE;
    }

    private SessionPrefs(Context context){
        mPrefs=context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME,context.MODE_PRIVATE);
        mIsloggedIn = !TextUtils.isEmpty(mPrefs.getString(PREFS_TOKEN,null));
    }

    public boolean isloggedIn() {
        return mIsloggedIn;
    }

    public void saveUser(User user){
        if (user != null){
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(PREFS_NAME,user.getName());
            editor.putString(PREFS_TOKEN,user.getToken());
            editor.putString(PREFS_EMAIL,user.getEmail());
            editor.apply();
            mIsloggedIn = true;
        }
    }

    public void logOut(){
        mIsloggedIn = false;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREFS_NAME,null);
        editor.putString(PREFS_EMAIL,null);
        editor.putString(PREFS_TOKEN,null);
        editor.apply();
    }
}
