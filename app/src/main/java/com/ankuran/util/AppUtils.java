package com.ankuran.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ankuran.AppMain;

import java.util.List;

public class AppUtils {

    public static boolean isValidList(List list) {
        if (list != null && list.size() > 0)
            return true;
        return false;
    }

    public static boolean isConnectedToInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) AppMain.getAppMainContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Since NetworkInfo is null incase of no current network
        boolean isNetworkConnected = false;
        if (networkInfo != null) {
            isNetworkConnected = networkInfo.isConnected();
        }
        return isNetworkConnected;
    }
}
