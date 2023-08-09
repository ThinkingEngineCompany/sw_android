package com.sw.beauty;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

public class SApp extends Application {
    public static SApp s;
    @Override
    public void onCreate() {
        super.onCreate();
        s = this;
        CrashReport.initCrashReport(getApplicationContext(), "44d865f3b9", false);
    }
}
