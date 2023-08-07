package com.sw.beauty;

import android.app.Application;

public class SApp extends Application {
    public static SApp s;
    @Override
    public void onCreate() {
        super.onCreate();
        s = this;
    }
}
