package com.example.a1.projecttest;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;

import com.example.a1.projecttest.utils.ConstantsManager;

import java.util.List;


public class UserLoginSession {
    private SharedPreferences prefs;
    public UserLoginSession(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void saveStateDialogScreen(boolean state, boolean isRediction, int position){
        prefs.edit().putBoolean(ConstantsManager.DIALOG_INSTANCE_NAME, state).apply();
        prefs.edit().putBoolean(ConstantsManager.DIALOG_ISREDUCTION, isRediction).apply();
        prefs.edit().putInt(ConstantsManager.DIALOG_POSITION, position).apply();
        prefs.edit().apply();
    }

    public boolean getStateDialogScreen(){
        return prefs.getBoolean(ConstantsManager.DIALOG_INSTANCE_NAME, false);
    }
    public boolean getIsReductionState(){
        return prefs.getBoolean(ConstantsManager.DIALOG_ISREDUCTION, false);
    }
    public int getPositionState(){
        return prefs.getInt(ConstantsManager.DIALOG_POSITION, 0);
    }

    public void setUseName(String useName, String password, int id) {
        prefs.edit().putString(ConstantsManager.LOGIN, useName).apply();
        prefs.edit().putString(ConstantsManager.PASSWORD, password).apply();
        prefs.edit().putInt(ConstantsManager.ID, id).apply();
        prefs.edit().apply();
    }
    public String getID() {
        return prefs.getString(ConstantsManager.ID, "");
    }

    public String getLogin() {
        return prefs.getString(ConstantsManager.LOGIN, "");
    }

    public String getPassword() {
        return prefs.getString(ConstantsManager.PASSWORD, "");
    }

    public void clear(){
        prefs.edit().putString(ConstantsManager.LOGIN, "").apply();
        prefs.edit().putString(ConstantsManager.PASSWORD, "").apply();
        prefs.edit().putInt(ConstantsManager.ID, 0).apply();
        prefs.edit().apply();
    }
}
