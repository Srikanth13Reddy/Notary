package com.apptomate.notary.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SharedPrefs
{
    // User name (make variable public to access from outside)


    public static final String IS_PAID = "IS_PAID";
    // Sharedpref file name
    private static final String PREF_NAME = "Notary";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String LOGIN_DATA="LOGIN_DATA";
    public static final String STATUS_DATA="STATUS_DATA";
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    Editor editor;
    Context context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    public SharedPrefs(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     *  *
     */

    public void LoginSuccess() {

        editor.putBoolean(IS_LOGIN, true);
        editor.commit();

    }

    public void logOut()
    {
        editor.clear();
        editor.commit();
    }



    public boolean isLoggedIn()
    {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void saveLoginData(String res)
    {
        editor.putString(LOGIN_DATA, String.valueOf(res));
        editor.commit();
    }
    public HashMap<String, String> getLoginData()
    {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name

        user.put(LOGIN_DATA, pref.getString(LOGIN_DATA, "0"));

        return user;
    }

    public void saveStatus(String res)
    {
        editor.putString(STATUS_DATA, String.valueOf(res));
        editor.commit();
    }
    public HashMap<String, String> getStatus()
    {
        HashMap<String, String> user = new HashMap<String, String>();
        // user name

        user.put(STATUS_DATA, pref.getString(STATUS_DATA, ""));

        return user;
    }





}

