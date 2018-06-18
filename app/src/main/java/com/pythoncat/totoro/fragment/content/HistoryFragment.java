package com.pythoncat.totoro.fragment.content;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.totoro.R;
import com.pythoncat.totoro.activity.historyitem.HistoryItemActivity;
import com.pythoncat.totoro.adapter.recycleradapter.HistoryAdapter;
import com.pythoncat.totoro.listener.OnItemClickListener;
import com.pythoncat.totoro.model.History;
import com.pythoncat.totoro.service.GankApi;
import com.pythoncat.totoro.utils.RxJavaUtil;
import com.pythoncat.totoro.utils.ToastUtil;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    public static final String ITEM_KEY = "item_date";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView tvTitle;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Subscription dataSubcription;
    private RecyclerView mRecyclerView;
    private HistoryAdapter adapter;
    private View loadingLayout;
    private View errorLayout;
    private View emptyLayout;

    public HistoryFragment() {
    }

    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        tvTitle = (TextView) root.findViewById(R.id.tv_history_title);
        tvTitle.setText(R.string.history_title);
        //loading
        loadingLayout = root.findViewById(R.id.layout_loading);
        loadingLayout.setOnClickListener(v -> {
            loadData(false);
        });
        //error
        errorLayout = root.findViewById(R.id.layout_error);
        errorLayout.setOnClickListener(v -> {
            loadData(false);
        });
        // empty
        emptyLayout = root.findViewById(R.id.layout_empty);
        emptyLayout.setOnClickListener(v -> {
            loadData(false);
        });
        //content
        mRecyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_history);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_swipe_history);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            LogUtils.v("");
            loadData(false);
        });
        LogUtils.e("onViewCreated");
        loadData(true);
    }

    private void loadingUi() {
        loadingLayout.setVisibility(View.VISIBLE);
        emptyLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.GONE);
    }

    private void emptyUi() {
        emptyLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.GONE);
    }

    private void errorUi() {
        errorLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.GONE);
        mSwipeRefreshLayout.setVisibility(View.GONE);
    }

    private void dataUi() {
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        emptyLayout.setVisibility(View.GONE);
    }


    /**
     * @param first 进入界面时的加载为first，其他时候刷新不是
     */
    private void loadData(boolean first) {
        loadingUi();
        RxJavaUtil.close(dataSubcription);
        Action1<History> onNext = (history) -> {
            if (history == null || history.error) {
                errorUi();
            } else {
                dataUi();
                LogUtils.e(history.hashCode());
                List<String> results = history.results;
                showData(results, first);
                LogUtils.e("show data");
                if (!first) {
                    ToastUtil.showToastShort("刷新完成，更新数据!");
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        };
        Action1<Throwable> onError = (e) -> {
            errorUi();
            LogUtils.e(e);
        };
        dataSubcription = new GankApi().queryHistory().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    private void showData(List<String> results, boolean first) {
        LogUtils.e("获取到的数据 results   " + results);
        if (results == null) {
            errorUi();
            return;
        }
        if (results.size() <= 0) {
            emptyUi();
            return;
        }
        if (first) {
            adapter = new HistoryAdapter(getActivity(), new OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    LogUtils.w("这个View到底是什么呢？");
                    LogUtils.w(view);
                    Intent it = new Intent(getActivity(), HistoryItemActivity.class);

                    it.putExtra(ITEM_KEY, results.get(position));
                    startActivity(it);
                }
            });
            adapter.setData(results);
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
        RxJavaUtil.close(dataSubcription);
    }

}
