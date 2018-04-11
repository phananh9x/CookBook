package com.phananh.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by My on 4/11/2018.
 */

public class Storage {
    public static String getToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("", MODE_PRIVATE);
        return preferences.getString("token", "");
    }
}
