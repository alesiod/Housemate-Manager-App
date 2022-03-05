package edu.vassar.cmpu203.housematemanager.controller;

import android.app.Application;
import android.content.Context;

public class ContextActivity extends Application {

    public static Context context;

    @Override public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}