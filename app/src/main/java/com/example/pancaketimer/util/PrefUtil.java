package com.example.pancaketimer.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.EnumMap;

public class PrefUtil {

    // preference file
    public static final String SHARED_PREF_FILE = "com.example.android.pancaketimersharedprefs";

    // modes
    public static final int MODE_PANCAKE = 0;
    public static final int MODE_CREPE = 1;
    public static final int MODE_CUSTOM = 2;

    // default values
    public static final int PANCAKE_SIDE1_DEFAULT = 85;
    public static final int PANCAKE_SIDE2_DEFAULT = 60;
    public static final int PANCAKE_FLIP_DEFAULT = 5;
    public static final int CREPE_SIDE1_DEFAULT = 80;
    public static final int CREPE_SIDE2_DEFAULT = 40;
    public static final int CREPE_FLIP_DEFAULT = 10;

    public static final String MODE_KEY = "com.example.pancaketimer.MODE_KEY";
    public static int getMode(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE);
        return pref.getInt(MODE_KEY, MODE_PANCAKE);
    }
    public static void setMode(int mode, Context context) {
        SharedPreferences.Editor prefedit = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE).edit();
        prefedit.putInt(MODE_KEY, mode);
        prefedit.apply();

        switch(mode) {
            case MODE_PANCAKE:
                setSide1Seconds(PANCAKE_SIDE1_DEFAULT, context);
                setSide2Seconds(PANCAKE_SIDE2_DEFAULT, context);
                setFlipSeconds(PANCAKE_FLIP_DEFAULT, context);
                break;
            case MODE_CREPE:
                setSide1Seconds(CREPE_SIDE1_DEFAULT, context);
                setSide2Seconds(CREPE_SIDE2_DEFAULT, context);
                setFlipSeconds(CREPE_FLIP_DEFAULT, context);
                break;
            default:
                break;
        }
    }

    public static final String SIDE1_KEY = "com.example.pancaketimer.SIDE1_KEY";
    public static int getSide1(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE);
        return pref.getInt(SIDE1_KEY, PANCAKE_SIDE1_DEFAULT*1000);
    }
    public static void setSide1Seconds(int seconds, Context context) {
        SharedPreferences.Editor prefedit = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE).edit();
        prefedit.putInt(SIDE1_KEY, seconds*1000);
        prefedit.apply();
    }

    public static final String SIDE2_KEY = "com.example.pancaketimer.SIDE2_KEY";
    public static int getSide2(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE);
        return pref.getInt(SIDE2_KEY, PANCAKE_SIDE2_DEFAULT*1000);
    }
    public static void setSide2Seconds(int seconds, Context context) {
        SharedPreferences.Editor prefedit = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE).edit();
        prefedit.putInt(SIDE2_KEY, seconds*1000);
        prefedit.apply();
    }

    public static final String FLIP_KEY = "com.example.pancaketimer.FLIP_KEY";
    public static int getFlip(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE);
        return pref.getInt(FLIP_KEY, PANCAKE_FLIP_DEFAULT*1000);
    }
    public static void setFlipSeconds(int seconds, Context context) {
        SharedPreferences.Editor prefedit = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE).edit();
        prefedit.putInt(FLIP_KEY, seconds*1000);
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
