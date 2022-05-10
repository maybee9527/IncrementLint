package com.twl.plugin.lint.hook;

public class InfoLogger {
    private static String TAG = "InfoLogger v2: ";
    private static boolean enableLog = true;

    public static void debug(String msg) {
        if (enableLog) {
            System.out.println(TAG + msg);
        }
    }

    public static void error(String msg) {
        System.out.println(TAG + msg);
    }
}
