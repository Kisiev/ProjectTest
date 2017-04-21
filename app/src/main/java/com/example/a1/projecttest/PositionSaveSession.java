package com.example.a1.projecttest;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.example.a1.projecttest.utils.ConstantsManager;

/**
 * Created by 1 on 21.04.2017.
 */

public class PositionSaveSession {
    SharedPreferences prefs;
    public PositionSaveSession(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void saveRecyclerViewPositions(int posSchedule, int idSchedule, int posChild, int idChild){
        prefs.edit().putInt(ConstantsManager.POS_SCHEDULE, posSchedule).apply();
        prefs.edit().putInt(ConstantsManager.ID_SCHEDULE, idSchedule).apply();
        prefs.edit().putInt(ConstantsManager.POS_CHILD, posChild).apply();
        prefs.edit().putInt(ConstantsManager.ID_CHILD, idChild).apply();
        prefs.edit().apply();
    }

    public int getPosSchedule(){
        return prefs.getInt(ConstantsManager.POS_SCHEDULE, 0);
    }

    public int getIdSchedule(){
        return prefs.getInt(ConstantsManager.ID_SCHEDULE, 0);
    }

    public int getPosChild(){
        return prefs.getInt(ConstantsManager.POS_CHILD, 0);
    }

    public int getIdChild(){
        return prefs.getInt(ConstantsManager.ID_CHILD, 0);
    }
}
