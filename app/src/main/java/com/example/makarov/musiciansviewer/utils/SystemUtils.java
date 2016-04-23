package com.example.makarov.musiciansviewer.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

public class SystemUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }

        if (supportLollipop()) {
            Network[] networks = manager.getAllNetworks();
            NetworkInfo info;
            for (Network network : networks) {
                info = manager.getNetworkInfo(network);
                if (info != null && info.isConnected()) {
                    return true;
                }
            }
        } else {
            NetworkInfo[] networkInfo = manager.getAllNetworkInfo();
            if (networkInfo != null) {
                for (NetworkInfo info : networkInfo) {
                    if (info != null && info.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean supportLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static void toastMassage(Context context, String massage) {
        Toast.makeText(context, massage, Toast.LENGTH_LONG).show();
    }
}
