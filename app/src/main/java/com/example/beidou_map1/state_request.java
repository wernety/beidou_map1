package com.example.beidou_map1;



import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.BatteryManager;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class state_request {


    public static String getMIEI(Context context) {
        String MIEI;
        TelephonyManager telephonemanage = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        try {
            MIEI = telephonemanage.getDeviceId();
            return MIEI;
        } catch (Exception e) {
            Log.i("error", e.getMessage());
            return null;
        }
    }


    public static int getBattery(Context context){
        BatteryManager batteryManager = (BatteryManager)context.getSystemService(context.BATTERY_SERVICE);
        int battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return battery;
    }


//    public static int get(Context context) {
//
//
//
//    }
//    }





}
