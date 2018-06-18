package com.pythoncat.totoro.service;

import com.pythoncat.totoro.model.History;
import com.pythoncat.totoro.model.HistoryItem;
import com.pythoncat.totoro.model.MyDate;
import com.pythoncat.totoro.utils.DateUtil;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by pythonCat on 2016/5/8.
 */
public class GankApi {

    private static final String BASE_URL = "http://gank.io/api/";

    public Observable<History> queryHistory() {
        //"history"
        //1.创建Retrofit对象

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL).build();
        //2.创建访问API的请求
        GankService gankService = retrofit.create(GankService.class);
        Observable<History> history = gankService.history("history");
        return history;
    }

    /**
     * @param date format{yyyy-MM-dd}
     * @return
     */
    public Observable<HistoryItem> queryHistoryItem(String date) {

        MyDate myDate = DateUtil.transDate2Bean(date);
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL).build();
        //2.创建访问API的请求
        GankService gankService = retrofit.create(GankService.class);
        Observable<HistoryItem> historyItem = gankService.historyItem(myDate.year, myDate.mouth, myDate.daily);
        return historyItem;
    }
}
