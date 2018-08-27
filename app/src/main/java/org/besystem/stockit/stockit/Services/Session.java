package org.besystem.stockit.stockit.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setLogin(String login) {
        prefs.edit().putString("isLogin", login).commit();
    }

    public String isLoging() {
        String login = prefs.getString("login","");
        return login;
    }
}