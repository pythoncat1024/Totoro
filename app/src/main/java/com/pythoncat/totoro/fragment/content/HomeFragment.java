package com.pythoncat.totoro.fragment.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pythoncat.totoro.R;
import com.pythoncat.totoro.adapter.fragmentadapter.HomeItemPageAdapter;
import com.pythoncat.totoro.fragment.item.HomeItemFragment;

import java.util.ArrayList;

/**
 * 主页面的Fragment
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {



    public static final String Content_Param1_Key = "Content_Param1_Key";
    public static final String Content_Param2_Key = "Content_Param2_Key";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(Content_Param1_Key, param1);
        args.putString(Content_Param2_Key, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        mLoadListener.empty();
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_title_layout_home_fragment);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_pager_home_fragment);
        ArrayList<HomeItemFragment> fragments = new ArrayList<>();
        {
            HomeItemFragment object = HomeItemFragment.newInstance("妹纸", "history");
            fragments.add(object);
        }
        {
            HomeItemFragment object = HomeItemFragment.newInstance("pythonCat", null);
            fragments.add(object);
        }
        {
            HomeItemFragment object = HomeItemFragment.newInstance("github", null);
            fragments.add(object);
        }
        mViewPager.setAdapter(new HomeItemPageAdapter(getActivity().getSupportFragmentManager(), fragments));
        mTabLayout.setupWithViewPager(mViewPager);
//        mLoadListener.success();
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
