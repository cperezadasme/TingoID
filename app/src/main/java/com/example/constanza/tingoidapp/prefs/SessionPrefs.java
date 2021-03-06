package com.example.constanza.tingoidapp.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.constanza.tingoidapp.api.model.User;

public class SessionPrefs {

    public static String PREFS_EMAIL = "PREFS_EMAIL";
    public static String PREFS_ID = "PREFS_ID";


    //public static final String PREFS_NAME = "PREFS_NAME";
    //public static final String PREFS_TOKEN = "PREFS_TOKEN";

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
                .getSharedPreferences(PREFS_EMAIL,context.MODE_PRIVATE);
//        mIsloggedIn = !TextUtils.isEmpty(mPrefs.getString(PREFS_TOKEN,null));
        mIsloggedIn = !TextUtils.isEmpty(mPrefs.getString(PREFS_EMAIL,null));

    }

    public boolean isloggedIn() {
        return mIsloggedIn;
    }

    public void saveUser(User user){
        if (user != null){
            SharedPreferences.Editor editor = mPrefs.edit();
            //editor.putString(PREFS_NAME,user.getName());
            //editor.putString(PREFS_TOKEN,user.getToken());
            editor.putString(PREFS_EMAIL,user.getEmail());
            editor.putString(PREFS_ID,user.getId());
            //editor.apply();
            editor.commit();

            mIsloggedIn = true;
        }
    }

    public void logOut(){
        mIsloggedIn = false;
        SharedPreferences.Editor editor = mPrefs.edit();
        //editor.putString(PREFS_NAME,null);
        editor.putString(PREFS_EMAIL,null);
        editor.putString(PREFS_ID,null);
        //editor.putString(PREFS_TOKEN,null);
        editor.apply();

    }

    public String getPrefsEmail() {
        return mPrefs.getString(PREFS_EMAIL,"");
    }

    public String getPrefsId() {
        return mPrefs.getString(PREFS_ID,"");
    }

}
