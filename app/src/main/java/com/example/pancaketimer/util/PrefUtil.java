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
    public static final int CREPE_SIDE1_DEFAULT = 90;
    public static final int CREPE_SIDE2_DEFAULT = 50;
    public static final int CREPE_FLIP_DEFAULT = 10;

    // Saved mode preferences
    public static final String MODE_KEY = "com.example.pancaketimer.MODE_KEY";
    public static int getMode(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE);
        return pref.getInt(MODE_KEY, MODE_PANCAKE);
    }
    public static void setMode(int mode, Context context) {
        SharedPreferences.Editor prefedit = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE).edit();
        prefedit.putInt(MODE_KEY, mode);
        prefedit.apply();
    }

    // Saved custom time preferences
    public static final String CUSTOM_SIDE1_KEY = "com.example.pancaketimer.CUSTOM_SIDE1_KEY";
    public static int getCustomSide1Milliseconds(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE);
        return pref.getInt(CUSTOM_SIDE1_KEY, PANCAKE_SIDE1_DEFAULT*1000);
    }
    public static int getCustomSide1Seconds(Context context) {
        return getCustomSide1Milliseconds(context) / 1000;
    }
    public static void setCustomSide1Seconds(int seconds, Context context) {
        SharedPreferences.Editor prefedit = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE).edit();
        prefedit.putInt(CUSTOM_SIDE1_KEY, seconds*1000);
        prefedit.apply();
    }

    public static final String CUSTOM_SIDE2_KEY = "com.example.pancaketimer.CUSTOM_SIDE2_KEY";
    public static int getCustomSide2Milliseconds(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE);
        return pref.getInt(CUSTOM_SIDE2_KEY, PANCAKE_SIDE2_DEFAULT*1000);
    }
    public static int getCustomSide2Seconds(Context context) {
        return getCustomSide2Milliseconds(context) / 1000;
    }
    public static void setCustomSide2Seconds(int seconds, Context context) {
        SharedPreferences.Editor prefedit = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE).edit();
        prefedit.putInt(CUSTOM_SIDE2_KEY, seconds*1000);
        prefedit.apply();
    }

    public static final String CUSTOM_FLIP_KEY = "com.example.pancaketimer.CUSTOM_FLIP_KEY";
    public static int getCustomFlipMilliseconds(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE);
        return pref.getInt(CUSTOM_FLIP_KEY, PANCAKE_FLIP_DEFAULT*1000);
    }
    public static void setCustomFlipSeconds(int seconds, Context context) {
        SharedPreferences.Editor prefedit = context.getSharedPreferences(PrefUtil.SHARED_PREF_FILE, context.MODE_PRIVATE).edit();
        prefedit.putInt(CUSTOM_FLIP_KEY, seconds*1000);
        prefedit.apply();
    }

    // Get saved preferences
    public static int getSide1Milliseconds(Context context) {
        int side1time = PANCAKE_SIDE1_DEFAULT*1000;
        int mode = getMode(context);
        switch(mode) {
            case MODE_CREPE:
                side1time = CREPE_SIDE1_DEFAULT*1000;
                break;
            case MODE_CUSTOM:
                side1time = getCustomSide1Milliseconds(context);
                break;
            default:
                break;
        }

        return side1time;
    }

    public static int getSide2Milliseconds(Context context) {
        int side2time = PANCAKE_SIDE2_DEFAULT*1000;
        int mode = getMode(context);
        switch(mode) {
            case MODE_CREPE:
                side2time = CREPE_SIDE2_DEFAULT*1000;
                break;
            case MODE_CUSTOM:
                side2time = getCustomSide2Milliseconds(context);
                break;
            default:
                break;
        }

        return side2time;
    }

    public static int getFlipMilliseconds(Context context) {
        int fliptime = PANCAKE_FLIP_DEFAULT*1000;
        int mode = getMode(context);
        switch(mode) {
            case MODE_CREPE:
                fliptime = CREPE_FLIP_DEFAULT*1000;
                break;
            case MODE_CUSTOM:
                fliptime = getCustomFlipMilliseconds(context);
                break;
            default:
                break;
        }

        return fliptime;
    }
}
