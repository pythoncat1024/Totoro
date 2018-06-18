package com.pythoncat.totoro.utils;

import android.app.Application;

import com.apkfuns.logutils.LogUtils;
import com.github.johnpersano.supertoasts.SuperToast;
import com.pythoncat.totoro.App;

/**
 * Created by pythonCat on 2016/5/7.
 */
public class ToastUtil {

    public static void cancelAllToast() {
        SuperToast.cancelAllSuperToasts();
    }

    public static void showToast(CharSequence textCharSequence, int durationInteger) {
        cancelAllToast();
        SuperToast.create(App.get(), textCharSequence, durationInteger).show();
    }

    public static void showToastVeryShort(CharSequence textCharSequence) {
        cancelAllToast();
        SuperToast.create(App.get(), textCharSequence, SuperToast.Duration.VERY_SHORT).show();
    }

    public static void showToastShort(CharSequence textCharSequence) {
        cancelAllToast();
        Application context = App.get();
        LogUtils.e("context====:" + context);
        SuperToast.create(context, textCharSequence, SuperToast.Duration.SHORT).show();

    }

    public static void showToastMedium(CharSequence textCharSequence) {
        cancelAllToast();
        SuperToast.create(App.get(), textCharSequence, SuperToast.Duration.MEDIUM).show();
    }

    public static void showToastLong(CharSequence textCharSequence) {
        cancelAllToast();
        SuperToast.create(App.get(), textCharSequence, SuperToast.Duration.LONG).show();
    }

    public static void showToastVeryLong(CharSequence textCharSequence) {
        cancelAllToast();
        SuperToast.create(App.get(), textCharSequence, SuperToast.Duration.EXTRA_LONG).show();
    }

    //    ####################################################
    public static void showToast(Object textCharSequence, int durationInteger) {
        cancelAllToast();
        SuperToast.create(App.get(), textCharSequence + "", durationInteger).show();
    }

    public static void showToastVeryShort(Object textCharSequence) {
        cancelAllToast();
        SuperToast.create(App.get(), textCharSequence + "", SuperToast.Duration.VERY_SHORT).show();
    }

    public static void showToastShort(Object textCharSequence) {
        cancelAllToast();
        SuperToast.create(App.get(), textCharSequence + "", SuperToast.Duration.SHORT).show();
    }

    public static void showToastMedium(Object textCharSequence) {
        cancelAllToast();
        SuperToast.create(App.get(), textCharSequence + "", SuperToast.Duration.MEDIUM).show();
    }

    public static void showToastLong(Object textCharSequence) {
        cancelAllToast();
        SuperToast.create(App.get(), textCharSequence + "", SuperToast.Duration.LONG).show();
    }

    public static void showToastVeryLong(Object textCharSequence) {
        cancelAllToast();
        SuperToast.create(App.get(), textCharSequence + "", SuperToast.Duration.EXTRA_LONG).show();
    }

}
