package com.pythoncat.totoro.fragment.historycategory;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.totoro.R;
import com.pythoncat.totoro.adapter.recycleradapter.CategoryAdapter;
import com.pythoncat.totoro.model.AdapterBean;
import com.pythoncat.totoro.model.HistoryItem;
import com.pythoncat.totoro.model.factory.DataFactory;
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
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 历史分类Item页面
 */
public class CategoryFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_TITLE = "arg_history_category_fragment_title";
    public static final String ARG_ITEM_DATE = "arg_history_category_fragment_item_date";

    private String mTitle;
    private String mItemData;
    private View loadingLayout;
    private View errorLayout;
    private View emptyLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private Subscription dataSubscription;
    private CategoryAdapter adapter;


    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param category Parameter 1.
     * @param itemDate Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    public static CategoryFragment newInstance(String category, String itemDate) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, category);
        args.putString(ARG_ITEM_DATE, itemDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_TITLE);
            mItemData = getArguments().getString(ARG_ITEM_DATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_category, container, false);
    }

    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
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
        mSwipeRefreshLayout = (SwipeRefreshLayout) root
                .findViewById(R.id.refresh_swipe_fragment_history_category);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            LogUtils.v("");
            loadData(false);
        });
        mRecyclerView = (RecyclerView) root
                .findViewById(R.id.recycler_view_fragment_history_category);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadData(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxJavaUtil.close(dataSubscription);
    }

    private void loadData(boolean first) {
        loadingUi();
        RxJavaUtil.close(dataSubscription);
        Action1<Throwable> onError = (e) -> {
            errorUi();
            LogUtils.e(e);
        };
        Action1<HistoryItem> onNext = (item) -> {
            dataUi();
            HistoryItem.ResultsBean bean = item.getResults();
            List<AdapterBean> dataList = DataFactory.getCategoryData(mTitle, bean);
            showData(dataList, first);
            LogUtils.e("show data");
            if (!first) {
                ToastUtil.showToastShort("刷新完成，更新数据!");
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };
        dataSubscription = new GankApi().queryHistoryItem(mItemData).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);

    }

    private void showData(List<AdapterBean> dataList, boolean first) {
        if (dataList == null) {
            errorUi();
            return;
        }
        if (dataList.size() == 0) {
            emptyUi();
            return;
        }
        if (first) {
            adapter = new CategoryAdapter(getActivity(), dataList);
            mRecyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
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
}
