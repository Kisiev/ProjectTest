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

    public void saveStateEditText(String key, String text){
        prefs.edit().putString(key, text).apply();
        prefs.edit().apply();
    }

    public String getSaveEditText(String key){
        return prefs.getString(key, "");
    }
    public void saveStateSpinner(String key, String id){
        prefs.edit().putString(key, id).apply();
        prefs.edit().apply();
    }

    public String getSaveSpinner(String key){
        return prefs.getString(key, "");
    }

    public void saveTextBox(String key, String name){
        prefs.edit().putString(key, name).apply();
        prefs.edit().apply();
    }

    public String getSaveTextBox(String key){
        return prefs.getString(key, "");
    }

    public void saveStateDialogScreen(boolean state, boolean isRediction, int position){
        prefs.edit().putBoolean(ConstantsManager.DIALOG_INSTANCE_NAME, state).apply();
        prefs.edit().putBoolean(ConstantsManager.DIALOG_ISREDUCTION, isRediction).apply();
        prefs.edit().putInt(ConstantsManager.DIALOG_POSITION, position).apply();
        prefs.edit().apply();
    }

    public void savePosition(int position){
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

    public void setUseName(String email, String password, String id, String userName, String userSurname, String userPatron, int userRoleId, int userIsActivated) {
        prefs.edit().putString(ConstantsManager.LOGIN, email).apply();
        prefs.edit().putString(ConstantsManager.PASSWORD, password).apply();
        prefs.edit().putString(ConstantsManager.ID, id).apply();
        prefs.edit().putString(ConstantsManager.SURNAME_USER, userSurname).apply();
        prefs.edit().putString(ConstantsManager.NAME_USER, userName).apply();
        prefs.edit().putString(ConstantsManager.PATRONIMYC_USER, userPatron).apply();
        prefs.edit().putInt(ConstantsManager.ROLE_ID_USER, userRoleId).apply();
        prefs.edit().putInt(ConstantsManager.ISACTIVATED_USER, userIsActivated).apply();
        prefs.edit().apply();
    }

    public int getRoleId(){
        return prefs.getInt(ConstantsManager.ROLE_ID_USER, 0);
    }
    public String getID() {
        return prefs.getString(ConstantsManager.ID, "");
    }

    public String getUserName() {
        return prefs.getString(ConstantsManager.NAME_USER, "");
    }
    public String getUserSurname() {
        return prefs.getString(ConstantsManager.SURNAME_USER, "");
    }
    public String getUserEmail() {
        return prefs.getString(ConstantsManager.LOGIN, "");
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
        prefs.edit().putString(ConstantsManager.ID, "").apply();
        prefs.edit().putString(ConstantsManager.SURNAME_USER, "").apply();
        prefs.edit().putString(ConstantsManager.NAME_USER, "").apply();
        prefs.edit().putString(ConstantsManager.PATRONIMYC_USER, "").apply();
        prefs.edit().putInt(ConstantsManager.ROLE_ID_USER, -1).apply();
        prefs.edit().putInt(ConstantsManager.ISACTIVATED_USER, -1).apply();
        prefs.edit().apply();
    }
}
