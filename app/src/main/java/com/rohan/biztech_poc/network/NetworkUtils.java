package com.rohan.biztech_poc.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    public static boolean isOnline(ConnectivityManager mConnectivityManager) {
        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
