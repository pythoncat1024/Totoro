package com.pythoncat.totoro.activity.historyitem;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.totoro.R;
import com.pythoncat.totoro.adapter.fragmentadapter.CategoryItemPageAdapter;
import com.pythoncat.totoro.fragment.content.HistoryFragment;
import com.pythoncat.totoro.fragment.historycategory.CategoryFragment;
import com.pythoncat.totoro.model.HistoryItem;
import com.pythoncat.totoro.service.GankApi;
import com.pythoncat.totoro.utils.RxJavaUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HistoryItemActivity extends AppCompatActivity {
    private View mLoadingLayout;
    private View mErrorLayout;
    private View mEmptyLayout;
    private TextView titleView;
    private LinearLayout mContentLayout;
    private TabLayout mCategoryTabs;
    private ViewPager mTabPagers;
    private Subscription dataSubscription;
    private CategoryItemPageAdapter adapter;
    private ArrayList<CategoryFragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_item);
        // get intent
        String itemDate = getIntent().getStringExtra(HistoryFragment.ITEM_KEY);
        //title 没有数据的时候显示
        titleView = (TextView) findViewById(R.id.tv_title_activity_item_history);
        //loading
        mLoadingLayout = findViewById(R.id.layout_loading);
        mLoadingLayout.setOnClickListener(v -> {
            loadData(itemDate, false);
        });
        //error
        mErrorLayout = findViewById(R.id.layout_error);
        mErrorLayout.setOnClickListener(v -> {
            loadData(itemDate, false);
        });
        // empty
        mEmptyLayout = findViewById(R.id.layout_empty);
        mEmptyLayout.setOnClickListener(v -> {
            loadData(itemDate, false);
        });
        //content
        mContentLayout = (LinearLayout) findViewById(R.id.content_layout_activity_item_history);
        mCategoryTabs = (TabLayout) findViewById(R.id.tab_title_layout_history_item_activity);
        mTabPagers = (ViewPager) findViewById(R.id.vp_pager_history_item_activity);
        LogUtils.w("加载数据中................");
        loadData(itemDate, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxJavaUtil.close(dataSubscription);
    }

    /**
     * 加载数据 net/local
     *
     * @param itemDate 当前Item的数据 { e.g "2016-06-15"}
     * @param first    进入界面时的加载为first，其他时候刷新不是
     */
    private void loadData(String itemDate, boolean first) {
        loadingUi();
        RxJavaUtil.close(dataSubscription);
        if (itemDate == null) {
            LogUtils.w("当前日期没有数据 ~_~ .");
            emptyUi();
        } else {
            Action1<HistoryItem> onNext = (historyItem) -> {
                dataUi();
                LogUtils.e("获取到" + HistoryFragment.ITEM_KEY + "的数据：");
                LogUtils.d(historyItem);
                List<String> categoryList = historyItem.getCategory();
                LogUtils.e(categoryList);
                LogUtils.w("=============================00");
                logData(historyItem);
//                test
                if (first) {
                    fragments = new ArrayList<>();
                    for (int i = 0; i < categoryList.size(); i++) {
                        CategoryFragment object = CategoryFragment.newInstance(categoryList.get(i), itemDate);
                        fragments.add(object);
                    }
                    adapter = new CategoryItemPageAdapter(getSupportFragmentManager(), fragments);
                    mTabPagers.setAdapter(adapter);
                } else {
                    fragments.clear();
                    for (String cat : categoryList) {
                        CategoryFragment object = CategoryFragment.newInstance(cat, null);
                        fragments.add(object);
                    }
                    adapter.notifyDataSetChanged();//TODO 这里待测试
                }
                mCategoryTabs.setupWithViewPager(mTabPagers);
                //£ #######################
            };
            Action1<Throwable> onError = (e) -> {
                LogUtils.e(e);
                errorUi();
            };
            dataSubscription = new GankApi().queryHistoryItem(itemDate).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(onNext, onError);
        }
    }

    /**
     * 打印一下数据
     *
     * @param historyItem bean
     */
    private void logData(HistoryItem historyItem) {
        HistoryItem.ResultsBean results = historyItem.getResults();
        List<HistoryItem.ResultsBean.AndroidBean> android = results.getAndroid();
        List<HistoryItem.ResultsBean.IOSBean> ios = results.getIOS();
        List<HistoryItem.ResultsBean.休息视频Bean> xiuxi = results.get休息视频();
        List<HistoryItem.ResultsBean.拓展资源Bean> tuozhan = results.get拓展资源();
        List<HistoryItem.ResultsBean.瞎推荐Bean> xia = results.get瞎推荐();
        List<HistoryItem.ResultsBean.福利Bean> fuli = results.get福利();

        LogUtils.e(android);
        LogUtils.w("=============================00");
        LogUtils.e(ios);
        LogUtils.w("=============================00");
        LogUtils.e(xiuxi);
        LogUtils.w("=============================00");
        LogUtils.e(tuozhan);
        LogUtils.w("=============================00");
        LogUtils.e(xia);
        LogUtils.w("=============================00");
        LogUtils.e(fuli);
        LogUtils.w("=============================00");
        //£ #######################
    }


    private void emptyUi() {
        titleView.setVisibility(View.VISIBLE);
        mEmptyLayout.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.GONE);
    }

    private void errorUi() {
        titleView.setVisibility(View.VISIBLE);
        mErrorLayout.setVisibility(View.VISIBLE);
        mEmptyLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.GONE);
    }

    private void loadingUi() {
        titleView.setVisibility(View.VISIBLE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        mEmptyLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.GONE);
    }

    private void dataUi() {
        titleView.setVisibility(View.GONE);
        mContentLayout.setVisibility(View.VISIBLE);
        mEmptyLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
        mErrorLayout.setVisibility(View.GONE);
    }
}
