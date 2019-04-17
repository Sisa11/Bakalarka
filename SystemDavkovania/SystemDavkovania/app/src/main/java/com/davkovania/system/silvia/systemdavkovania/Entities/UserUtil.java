package com.davkovania.system.silvia.systemdavkovania.Entities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.davkovania.system.silvia.systemdavkovania.Database.User;
import com.davkovania.system.silvia.systemdavkovania.Windows.LoginActivity;
import com.davkovania.system.silvia.systemdavkovania.Windows.MainActivity;
import com.google.gson.Gson;

public class UserUtil {

    public static final String PREFS_NAME = "USER_PREFS";
    public static final int PREFS_MODE = Context.MODE_PRIVATE;
    public static final String USER_OBJECT_KEY = "userObject";

    public static void saveUserToSharedPteferencie(User user, SharedPreferences prefs){
        SharedPreferences.Editor prefsEditor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        prefsEditor.putString(USER_OBJECT_KEY, json);
        prefsEditor.commit();

    }

    public static User getUserFromSharedPreferencies(SharedPreferences prefs){
        Gson gson = new Gson();
        String json = prefs.getString(USER_OBJECT_KEY, "");
        return gson.fromJson(json, User.class);
    }

    public static void logOut(SharedPreferences prefs){
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(USER_OBJECT_KEY);
        editor.apply();
    }

    public static boolean isLoogedIn(SharedPreferences prefs){

        return prefs.getString(USER_OBJECT_KEY, null) != null;
    }
}
