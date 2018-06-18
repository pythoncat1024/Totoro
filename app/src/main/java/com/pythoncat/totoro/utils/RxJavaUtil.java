package com.pythoncat.totoro.utils;

import rx.Subscription;

/**
 * Created by pythonCat on 2016/5/7.
 */
public class RxJavaUtil {

    public static void close(Subscription s) {
        if (s != null && !s.isUnsubscribed())
            s.unsubscribe();
    }
}
