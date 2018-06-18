package com.pythoncat.totoro.service;

import com.pythoncat.totoro.model.History;
import com.pythoncat.totoro.model.HistoryItem;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by pythonCat on 2016/5/8.
 */
public interface GankService {

    /**
     * 获取历史数据的api
     * http://gank.io/api/day/history
     * @param what
     * @return
     */
    @GET("day/{what}")
    Observable<History> history(@Path("what") String what);

    /**
     * 获取历史数据中 某一天的api
     * http://gank.io/api/day/2016/05/06
     * @param year
     * @return
     */

    @GET("day/{year}/{mouth}/{daily}")
    Observable<HistoryItem> historyItem(@Path("year") String year, @Path("mouth") String mouth, @Path("daily") String daily);
}
