package com.pythoncat.totoro;

import android.app.Application;

/**
 * Created by pythonCat on 2016/5/7.
 */
public class App {

    private static Application mApplication;

    public static void set(Application a){
        mApplication  =a;
    }

    public static Application get() {
        return mApplication;
    }
    public static String getString(int resId,Object... args) {
        return mApplication.getString(resId,args);
    }
    public static String getString(int resId) {
        return mApplication.getString(resId);
    }
}
