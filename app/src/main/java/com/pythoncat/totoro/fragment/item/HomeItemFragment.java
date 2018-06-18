package com.pythoncat.totoro.fragment.item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pythoncat.totoro.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 子页面的Fragment {contains in view pager}
 */
public class HomeItemFragment extends Fragment {


    public static final String ARG_TITLE = "arg_title";
    public static final String ARG_PARAM2 = "arg_param2";

    public HomeItemFragment() {
        // Required empty public constructor
    }

    public static HomeItemFragment newInstance(String title, String param2) {
        HomeItemFragment fragment = new HomeItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
    }
}
