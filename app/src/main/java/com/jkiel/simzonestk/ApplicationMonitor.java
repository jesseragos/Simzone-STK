package com.jkiel.simzonestk;

import android.app.Application;

/**
 * Created by Ragos Bros on 1/26/2017.
 */
public class ApplicationMonitor extends Application {

    private static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityVisible() {
        activityVisible = true;
    }

    public static void activityInvisible() {
        activityVisible = false;
    }

}
