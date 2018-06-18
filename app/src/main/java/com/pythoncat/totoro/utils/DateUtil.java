package com.pythoncat.totoro.utils;

import com.pythoncat.totoro.model.MyDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by pythonCat on 2016/5/8.
 */
public class DateUtil {

    /**
     * http://gank.io/api/day/2016/04/28
     * 2016-05-06 -- > {2016,05,06}
     *
     * @param date
     * @return
     */
    public static String[] transDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = simpleDateFormat.parse(date);

            simpleDateFormat = new SimpleDateFormat("yyyy");
            String year = simpleDateFormat.format(parse);

            simpleDateFormat = new SimpleDateFormat("MM");
            String mouth = simpleDateFormat.format(parse);
            simpleDateFormat = new SimpleDateFormat("dd");
            String day = simpleDateFormat.format(parse);

            String[] dateArray = {year, mouth, day};
            return dateArray;
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("date format is not yyyy-MM-dd");
        }
    }

    public static MyDate transDate2Bean(String date) {
        String[] transDate = transDate(date);
        if (transDate.length != 3) {
            throw new IllegalArgumentException("字符串数组转化错误:" + Arrays.toString(transDate));
        }
        MyDate myDate = new MyDate();
        myDate.year = transDate[0];
        myDate.mouth = transDate[1];
        myDate.daily = transDate[2];
        return myDate;
    }


    public static void main(String[] args) {

        String[] date = transDate("2016-05-06");

        String s = Arrays.toString(date);

        System.out.println(s);
    }
}
