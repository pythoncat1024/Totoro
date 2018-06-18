package com.pythoncat.totoro.fragment.content;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.pythoncat.totoro.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SlideShowFragment extends Fragment {


    private LinearLayout showOrHideLayout;
    private Button btnControl;
    private int flag;
    private ScrollView scrollView;
    private TextView tvFirst;
    private int actionBarHeight;

    public SlideShowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slide_show, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TypedValue tv = new TypedValue();
        if (getActivity().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            System.out.println("CCCCCCCCCCCCC = = = =" + actionBarHeight);
        }

        scrollView = (ScrollView) view.findViewById(R.id.scroll_slide_layout);
        tvFirst = (TextView) view.findViewById(R.id.tv_first);
        showOrHideLayout = (LinearLayout) view.findViewById(R.id.show_or_hide_layout);
        showOrHideLayout.setVisibility(View.GONE);
        btnControl = (Button) view.findViewById(R.id.btn_show_or_hide_control);

        btnControl.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                float rawY = event.getRawY();
                float y = event.getY();

                System.out.println("rawY==" + rawY + " **** y  =" + y);
                System.out.println("rawY - y==" + (rawY - y));
                System.out.println("btnTop- y==" + btnControl.getTop());
                if (flag % 2 == 0) {
                    //to show
                    showLayout(showOrHideLayout, event);
                } else {
                    //to hide
                    hideLayout(showOrHideLayout, event);
                }
                flag++;
            }
            return true;
        });
    }

    private void hideLayout(LinearLayout showOrHideLayout, MotionEvent event) {
        LogUtils.e("隐藏 布局");
        showOrHideLayout.setVisibility(View.GONE);
        float rawY = event.getRawY();
        scrollView.smoothScrollTo(0, (int) -(rawY - actionBarHeight - btnControl.getHeight()));// >0 y up
    }

    private void showLayout(LinearLayout showOrHideLayout, MotionEvent event) {
        LogUtils.e("显示 布局");
        showOrHideLayout.setVisibility(View.VISIBLE);
        float rawY = event.getRawY();
        scrollView.smoothScrollBy(0, (int) (rawY - actionBarHeight - btnControl.getHeight()));// >0 y up
    }
}
















