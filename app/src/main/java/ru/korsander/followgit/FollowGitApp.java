package ru.korsander.followgit;

import android.app.Application;
import android.content.Context;

/**
 * Created by korsander on 12.07.2015.
 */
public class FollowGitApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
