package com.pythoncat.totoro.adapter.fragmentadapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pythoncat.totoro.fragment.historycategory.CategoryFragment;

import java.util.List;

/**
 * Created by pythonCat on 2016/5/9.
 */
public class CategoryItemPageAdapter extends FragmentPagerAdapter {
    private final List<CategoryFragment> fragments;

    public CategoryItemPageAdapter(FragmentManager fm, List<CategoryFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (fragments == null) {
            throw new IllegalArgumentException("the fragment adapter's data can't be null !");
        }
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getArguments().getString(CategoryFragment.ARG_TITLE);
    }
}
