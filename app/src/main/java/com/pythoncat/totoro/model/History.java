package com.pythoncat.totoro.model;

import java.util.List;

/**
 * Created by pythonCat on 2016/5/8.
 */
public class History {

    /**
     * error : false
     * results : ["2016-05-06","2016-05-05","2016-05-04","2016-05-03",...
     */

    public boolean error;
    public List<String> results;

    @Override
    public String toString() {
        return "History{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
