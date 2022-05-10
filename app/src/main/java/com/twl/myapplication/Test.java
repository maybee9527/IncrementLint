package com.twl.myapplication;

import android.util.Log;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Test implements Serializable {
    private static final String TAG = "Test";

    @NonNull
    @Override
    public String toString() {
        Log.d(TAG, "fuck toString: ");
        return super.toString();
    }
}
