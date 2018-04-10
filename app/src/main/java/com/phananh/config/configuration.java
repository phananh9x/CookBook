package com.phananh.config;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Phan Anh on 3/31/2016.
 */
public class configuration {
    public static String SERVER_URL="http://minamino.16mb.com/monanviet7.php";
    public static String SERVER_URL_LOGIN="http://minamino.16mb.com/monanviet7.php";

    public static boolean checkInternet(Context context) {
        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // notify user you are online
            return true;
        } else
            return false;
    }
}
