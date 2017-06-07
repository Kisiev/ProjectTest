package com.example.a1.projecttest.utils;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.a1.projecttest.ChildActivity_;
import com.example.a1.projecttest.LoginActivity;
import com.example.a1.projecttest.MainActivity_;
import com.example.a1.projecttest.R;
import com.example.a1.projecttest.UserLoginSession;
import com.example.a1.projecttest.rest.Models.GetUserData;
import com.example.a1.projecttest.vospitatel.VospitatelMainActivity_;
import com.example.a1.projecttest.zavedushaia.MainZavDetSad_;

public class StandardWindowDialog extends DialogFragment {
    String login;
    String password;
    GetUserData getUserData;

    private void startActivityOnRole(){
        switch (getUserData.getRoleId()) {
            case "1":
                startActivity(new Intent(getActivity(), MainActivity_.class));
                break;
            case "2":
                startActivity(new Intent(getActivity(), MainZavDetSad_.class));
                break;
            case "3":
                startActivity(new Intent(getActivity(), ChildActivity_.class));
                break;
            case "4":

                break;
            case "5":
                startActivity(new Intent(getActivity(), VospitatelMainActivity_.class));
                break;
        }
    }
    public StandardWindowDialog (){}

    public StandardWindowDialog (String login, String password, GetUserData getUserData){
        this.login = login;
        this.password = password;
        this.getUserData = getUserData;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final Bundle bundle = new Bundle();
        builder.setMessage(R.string.ask_save_pass)
                .setPositiveButton(R.string.cast_tracks_chooser_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        UserLoginSession session = new UserLoginSession(getActivity());
                        session.savePassword(login, password);
                        startActivityOnRole();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivityOnRole();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}