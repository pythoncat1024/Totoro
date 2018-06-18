package com.pythoncat.totoro.model.factory;

import com.pythoncat.totoro.model.AdapterBean;
import com.pythoncat.totoro.model.HistoryItem;

import java.util.List;

import rx.Observable;

/**
 * Created by pythonCat on 2016/5/10.
 */
public class DataFactory {

    /**
     * 根据分类 category 获取对应的数据集合
     *
     * @param category {android，ios,福利，瞎推荐，拓展资源}
     * @param bean     分类数据源
     * @return list of adapterBean
     */
    public static List<AdapterBean> getCategoryData(String category, HistoryItem.ResultsBean bean) {
        List<AdapterBean> single;
        if ("iOS".equalsIgnoreCase(category)) {
            single = Observable.from(bean.getIOS())
                    .filter((check)-> check!=null)
                    .map((map) -> ModelFactory.adapter(map))
                    .toList()
                    .toBlocking()
                    .single();

        } else if ("休息视频".equalsIgnoreCase(category)) {
            single = Observable.from(bean.get休息视频())
                    .filter((check)-> check!=null)
                    .map((map) -> ModelFactory.adapter(map))
                    .toList()
                    .toBlocking()
                    .single();
        } else if ("拓展资源".equalsIgnoreCase(category)) {
            single = Observable.from(bean.get拓展资源())
                    .filter((check)-> check!=null)
                    .map((map) -> ModelFactory.adapter(map))
                    .toList()
                    .toBlocking()
                    .single();
        } else if ("Android".equalsIgnoreCase(category)) {
            single = Observable.from(bean.getAndroid())
                    .filter((check)-> check!=null)
                    .map((map) -> ModelFactory.adapter(map))
                    .toList()
                    .toBlocking()
                    .single();
        } else if ("福利".equalsIgnoreCase(category)) {
            single = Observable.from(bean.get福利())
                    .filter((check)-> check!=null)
                    .map((map) -> ModelFactory.adapter(map))
                    .toList()
                    .toBlocking()
                    .single();
        } else {
            // default
            single = Observable.from(bean.get福利())
                    .filter((check)-> check!=null)
                    .map((map) -> ModelFactory.adapter(map))
                    .toList()
                    .toBlocking()
                    .single();
        }
        return single;
    }

}
