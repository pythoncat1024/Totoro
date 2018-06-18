package com.pythoncat.totoro.listener;

/**
 * Created by pythonCat on 2016/5/8.
 * Fragment 数据加载完成的回调
 */
public interface OnFragmentLoad {

    void error(Throwable e);

    void success();

    void empty();
}
