package com.pythoncat.totoro.model.factory;

import com.pythoncat.totoro.model.AdapterBean;
import com.pythoncat.totoro.model.HistoryItem;

import rx.Observable;

/**
 * Created by pythonCat on 2016/5/10.
 * 将同种类型的bean转换为同一个bean
 */
public class ModelFactory {

    public static AdapterBean adapter(HistoryItem.ResultsBean.AndroidBean bean) {
        return Observable.just(bean)
                .firstOrDefault(null,
                        (select) -> select != null)
//                .filter((temp)-> temp != null)
                .map((map) -> {
                    AdapterBean adapterBean = new AdapterBean();
                    adapterBean._id = map.get_id();
                    adapterBean.createdAt = map.getCreatedAt();
                    adapterBean.desc = map.getDesc();
                    adapterBean.publishedAt = map.getPublishedAt();
                    adapterBean.source = map.getSource();
                    adapterBean.type = map.getType();
                    adapterBean.url = map.getUrl();
                    adapterBean.used = map.isUsed();
                    adapterBean.who = map.getWho();
                    return adapterBean;
                }).toBlocking().single();
    }

    public static AdapterBean adapter(HistoryItem.ResultsBean.IOSBean bean) {
        return Observable.just(bean)
                .firstOrDefault(null,
                        (select) -> select != null)
//                .filter((temp)-> temp != null)
                .map((map) -> {
                    AdapterBean adapterBean = new AdapterBean();
                    adapterBean._id = map.get_id();
                    adapterBean.createdAt = map.getCreatedAt();
                    adapterBean.desc = map.getDesc();
                    adapterBean.publishedAt = map.getPublishedAt();
                    adapterBean.source = map.getSource();
                    adapterBean.type = map.getType();
                    adapterBean.url = map.getUrl();
                    adapterBean.used = map.isUsed();
                    adapterBean.who = map.getWho();
                    return adapterBean;
                }).toBlocking().single();
    }


    public static AdapterBean adapter(HistoryItem.ResultsBean.休息视频Bean bean) {
        return Observable.just(bean)
                .firstOrDefault(null,
                        (select) -> select != null)
//                .filter((temp)-> temp != null)
                .map((map) -> {
                    AdapterBean adapterBean = new AdapterBean();
                    adapterBean._id = map.get_id();
                    adapterBean.createdAt = map.getCreatedAt();
                    adapterBean.desc = map.getDesc();
                    adapterBean.publishedAt = map.getPublishedAt();
                    adapterBean.source = map.getSource();
                    adapterBean.type = map.getType();
                    adapterBean.url = map.getUrl();
                    adapterBean.used = map.isUsed();
                    adapterBean.who = map.getWho();
                    return adapterBean;
                }).toBlocking().single();
    }

    public static AdapterBean adapter(HistoryItem.ResultsBean.拓展资源Bean bean) {
        return Observable.just(bean)
                .firstOrDefault(null,
                        (select) -> select != null)
//                .filter((temp)-> temp != null)
                .map((map) -> {
                    AdapterBean adapterBean = new AdapterBean();
                    adapterBean._id = map.get_id();
                    adapterBean.createdAt = map.getCreatedAt();
                    adapterBean.desc = map.getDesc();
                    adapterBean.publishedAt = map.getPublishedAt();
                    adapterBean.source = map.getSource();
                    adapterBean.type = map.getType();
                    adapterBean.url = map.getUrl();
                    adapterBean.used = map.isUsed();
                    adapterBean.who = map.getWho();
                    return adapterBean;
                }).toBlocking().single();
    }

    public static AdapterBean adapter(HistoryItem.ResultsBean.瞎推荐Bean bean) {
        return Observable.just(bean)
                .firstOrDefault(null,
                        (select) -> select != null)
//                .filter((temp)-> temp != null)
                .map((map) -> {
                    AdapterBean adapterBean = new AdapterBean();
                    adapterBean._id = map.get_id();
                    adapterBean.createdAt = map.getCreatedAt();
                    adapterBean.desc = map.getDesc();
                    adapterBean.publishedAt = map.getPublishedAt();
                    adapterBean.source = map.getSource();
                    adapterBean.type = map.getType();
                    adapterBean.url = map.getUrl();
                    adapterBean.used = map.isUsed();
                    adapterBean.who = map.getWho();
                    return adapterBean;
                }).toBlocking().single();
    }

    public static AdapterBean adapter(HistoryItem.ResultsBean.福利Bean bean) {
        return Observable.just(bean)
                .firstOrDefault(null,
                        (select) -> select != null)
//                .filter((temp)-> temp != null)
                .map((map) -> {
                    AdapterBean adapterBean = new AdapterBean();
                    adapterBean._id = map.get_id();
                    adapterBean.createdAt = map.getCreatedAt();
                    adapterBean.desc = map.getDesc();
                    adapterBean.publishedAt = map.getPublishedAt();
                    adapterBean.source = map.getSource();
                    adapterBean.type = map.getType();
                    adapterBean.url = map.getUrl();
                    adapterBean.used = map.isUsed();
                    adapterBean.who = map.getWho();
                    return adapterBean;
                }).toBlocking().single();
    }
}
