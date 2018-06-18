package com.pythoncat.totoro.utils;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by pythonCat on 2016/5/9.
 */
public class SharePerfenceUtil {

    private static final String spName = "jsonCache";
    private static final String spKey_history_category_item = "spKey_history_category_item";

    /**
     * 保存历史 item 的 json
     *
     * @param c    context
     * @param json null is ok ,如果为null，将清除缓存
     */
    public static void saveHistoryCategoriesJson(Context c, String json) {
        if (c == null) return;
        if (TextUtils.isEmpty(json)) json = "";
        c.getSharedPreferences(spName, Context.MODE_PRIVATE)
                .edit()
                .putString(spKey_history_category_item, json)
                .apply();
    }

    /**
     * 取出历史 item 的 json
     *
     * @param c context
     */
    public static String getHistoryCategoriesJson(Context c) {
        if (c == null) return "";
        return c.getSharedPreferences(spName, Context.MODE_PRIVATE)
                .getString(spKey_history_category_item, "");
    }
}
