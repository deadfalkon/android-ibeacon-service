package com.radiusnetworks.utils;

import android.util.Log;

public class LOG {

    public static boolean silent  = true;

    public static void w(String tag, String s, Exception e) {
        if (silent) {
            return;
        }
        Log.w(tag, s, e);
    }

    public static void d(String tag, String s) {
        if (silent) {
            return;
        }
        Log.d(tag, s);
    }

    public static void e(String tag, String s, Exception e) {
        if (silent) {
            return;
        }
        Log.e(tag, s);
    }

    public static void i(String tag, String s) {
        if (silent) {
            return;
        }
        Log.i(tag, s);
    }

    public static void e(String tag, String message) {
        if (silent) {
            return;
        }
        Log.e(tag, message);
    }
}
