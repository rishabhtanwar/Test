package com.example.rishabh.curotest.Network;



import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.example.rishabh.curotest.Utils.Application;
import java.io.Serializable;
import java.util.HashMap;

public class ConnectionDetector implements Serializable {

    private static String TAG = "ConnectionDetector";
    private Context _context;

    public ConnectionDetector(Context context) {
        this._context = context;
    }

    public boolean isConnectingToInternet() {
        if(_context==null){
            _context= Application.getAppContext();
        }
        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo activeNetwork = connectivity.getActiveNetworkInfo();
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
            = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

//    public String isConnectedFast(Context context) {
//        if (context==null){
//            context=MyApplication.getAppContext();
//        }
//        NetworkInfo info = ConnectionDetector.getNetworkInfo(context);
//        ////Log.e(TAG, "net speed = " + info.getSubtype());
//        if (info != null && info.isConnected()){
//            return String.valueOf(ConnectionDetector.Connection_TYPE(info.getType(),info.getSubtype()));
//        }else {
//            return null;
//        }
////        return (info != null && info.isConnected() && ConnectionDetector.isConnectionFast(info.getType(), info.getSubtype()));
//    }
//
//    /**
//     * Check if the connection is fast
//     *
//     * @param type
//     * @param subType
//     * @return
//     */
//    public static boolean isConnectionFast(int type, int subType) {
//        if (type == ConnectivityManager.TYPE_WIFI) {
//            return true;
//        } else if (type == ConnectivityManager.TYPE_MOBILE) {
//            switch (subType) {
//                case TelephonyManager.NETWORK_TYPE_1xRTT:
//                    return false; // ~ 50-100 kbps
//                case TelephonyManager.NETWORK_TYPE_CDMA:
//                    return false; // ~ 14-64 kbps
//                case TelephonyManager.NETWORK_TYPE_EDGE:
//                    return false; // ~ 50-100 kbps
//                case TelephonyManager.NETWORK_TYPE_EVDO_0:
//                    return true; // ~ 400-1000 kbps
//                case TelephonyManager.NETWORK_TYPE_EVDO_A:
//                    return true; // ~ 600-1400 kbps
//                case TelephonyManager.NETWORK_TYPE_GPRS:
//                    return false; // ~ 100 kbps
//                case TelephonyManager.NETWORK_TYPE_HSDPA:
//                    return true; // ~ 2-14 Mbps
//                case TelephonyManager.NETWORK_TYPE_HSPA:
//                    return true; // ~ 700-1700 kbps
//                case TelephonyManager.NETWORK_TYPE_HSUPA:
//                    return true; // ~ 1-23 Mbps
//                case TelephonyManager.NETWORK_TYPE_UMTS:
//                    return true; // ~ 400-7000 kbps
//            /*
//             * Above API level 7, make sure to set android:targetSdkVersion
//			 * to appropriate level to use these
//			 */
//                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
//                    return true; // ~ 1-2 Mbps
//                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
//                    return true; // ~ 5 Mbps
//                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
//                    return true; // ~ 10-20 Mbps
//                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
//                    return false; // ~25 kbps
//                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
//                    return true; // ~ 10+ Mbps
//                // Unknown
//                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
//                default:
//                    return false;
//            }
//        } else {
//            return false;
//        }
//    }
//
//    public static HashMap<String,String> Connection_TYPE(int type, int subType) {
//        HashMap<String,String> networkInfo= new HashMap<>();
//        if (type == ConnectivityManager.TYPE_WIFI) {
//            networkInfo.put("Type","WIFI");
//            networkInfo.put("SubType","WIFI");
//            return networkInfo;
//        } else if (type == ConnectivityManager.TYPE_MOBILE) {
//            switch (subType) {
//                case TelephonyManager.NETWORK_TYPE_1xRTT:
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_1xRTT");
//                     return networkInfo; // ~ 50-100 kbps
//                case TelephonyManager.NETWORK_TYPE_CDMA:
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_CDMA");
//                    return networkInfo;// ~ 14-64 kbps
//                case TelephonyManager.NETWORK_TYPE_EDGE:
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_EDGE"); // ~ 50-100 kbps
//                    return networkInfo;
//                case TelephonyManager.NETWORK_TYPE_EVDO_0:
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_EVDO_0"); // ~ 400-1000 kbps
//                    return networkInfo;
//                case TelephonyManager.NETWORK_TYPE_EVDO_A:
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_EVDO_A"); // ~ 600-1400 kbps
//                    return networkInfo;
//                case TelephonyManager.NETWORK_TYPE_GPRS:
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_GPRS"); // ~ 100 kbps
//                    return networkInfo;
//                case TelephonyManager.NETWORK_TYPE_HSDPA:
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_HSDPA"); // ~ 2-14 Mbps
//                    return networkInfo;
//                case TelephonyManager.NETWORK_TYPE_HSPA:
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_HSPA"); // ~ 700-1700 kbps
//                    return networkInfo;
//                case TelephonyManager.NETWORK_TYPE_HSUPA:
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_HSUPA"); // ~ 1-23 Mbps
//                    return networkInfo;
//                case TelephonyManager.NETWORK_TYPE_UMTS:
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_UMTS"); // ~ 400-7000 kbps
//                    return networkInfo;
//            /*
//			 * Above API level 7, make sure to set android:targetSdkVersion
//			 * to appropriate level to use these
//			 */
//                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_EHRPD"); // ~ 1-2 Mbps
//                    return networkInfo;
//                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_EVDO_B");// ~ 5 Mbps
//                    return networkInfo;
//                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_HSPAP"); // ~ 10-20 Mbps
//                    return networkInfo;
//                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_IDEN"); // ~25 kbps
//                    return networkInfo;
//                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_LTE"); // ~ 10+ Mbps
//                    return networkInfo;
//                // Unknown
//                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","NETWORK_TYPE_UNKNOWN");
//                    return networkInfo;
//                default:
//                    networkInfo.put("Type","MOBILE");
//                    networkInfo.put("SubType","MOBILE");
//                    return networkInfo;
//            }
//        } else {
//            networkInfo.put("Type","MOBILE");
//            networkInfo.put("SubType","UNKNOWN");
//            return networkInfo;
//        }
//    }
}


