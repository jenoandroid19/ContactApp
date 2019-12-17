package com.example.contactapp;

import android.content.Context;
import android.content.SharedPreferences;

public class PermissionUtil {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PermissionUtil(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.permission_preference), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void updatePermissionPreference() {

        editor.putBoolean(context.getString(R.string.permission_contact), true);
        editor.commit();
    }


    public boolean  checkPermissionPreference() {
        boolean isShowing = false;

        isShowing = sharedPreferences.getBoolean(context.getString(R.string.permission_contact), false);

        return isShowing;
    }
}
