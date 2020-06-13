package com.example.pancaketimer.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefUtil {

    // preference file
    public static final String SHARED_PREF_FILE = "com.example.android.pancaketimersharedprefs";

    // default values
    public static final int SIDE1_DEFAULT = 85000;
    public static final int SIDE2_DEFAULT = 60000;
    public static final int FLIP_DEFAULT = 5000;


    public static final String SIDE1_KEY = "com.example.pancaketimer.SIDE1_KEY";
    public static int getSide1(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE);
        return pref.getInt(SIDE1_KEY, SIDE1_DEFAULT);
    }
    public static void setSide1Seconds(int seconds, Context context) {
        SharedPreferences.Editor prefedit = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE).edit();
        prefedit.putInt(SIDE1_KEY, seconds*1000);
        prefedit.apply();
    }

    public static final String SIDE2_KEY = "com.example.pancaketimer.SIDE2_KEY";
    public static int getSide2(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE);
        return pref.getInt(SIDE2_KEY, SIDE2_DEFAULT);
    }
    public static void setSide2Seconds(int seconds, Context context) {
        SharedPreferences.Editor prefedit = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE).edit();
        prefedit.putInt(SIDE2_KEY, seconds*1000);
        prefedit.apply();
    }

    int getPreviousSide1() {
        //placeholder
        return 85000;
    }

    int getPreviousSide2() {
        //placeholder
        return 85000;
    }
}
