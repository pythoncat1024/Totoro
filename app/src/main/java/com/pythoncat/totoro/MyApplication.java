package com.pythoncat.totoro;

import android.app.Application;

/**
 * Created by pythonCat on 2016/5/7.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        App.set(this);
    }
}
