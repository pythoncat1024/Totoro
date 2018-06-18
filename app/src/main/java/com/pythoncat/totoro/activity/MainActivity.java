package com.pythoncat.totoro.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.github.johnpersano.supertoasts.SuperToast;
import com.jakewharton.rxbinding.view.RxView;
import com.pythoncat.totoro.App;
import com.pythoncat.totoro.R;
import com.pythoncat.totoro.fragment.content.HistoryFragment;
import com.pythoncat.totoro.fragment.content.HomeFragment;
import com.pythoncat.totoro.fragment.content.SlideShowFragment;
import com.pythoncat.totoro.fragment.content.ToolsFragment;
import com.pythoncat.totoro.utils.ToastUtil;
import com.pythoncat.totoro.utils.UriUtil;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private HomeFragment homeFragment;
    private HistoryFragment historyFragment;

    private Map<Integer, Fragment> mFragmentSet;

    private int curentId;
    private SlideShowFragment slideShowFragment;
    private Toolbar toolbar;
    private ToolsFragment toolsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentSet = new TreeMap<>();
//        toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        assert fab != null;
        fab.setOnClickListener((view) ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", (v) -> {
                            SuperToast.cancelAllSuperToasts();
                            SuperToast.create(App.get(), "我是Action",
                                    SuperToast.Duration.MEDIUM).show();
                        }).show()
        );

//        drawer
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
//navigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
//        navigationView head
            View headerView = navigationView.getHeaderView(0);
            ImageView ivNavHead = (ImageView) headerView.findViewById(R.id.nav_head_img);
            LogUtils.e(ivNavHead);
            LogUtils.e("ivNavHead ===========");
            Glide.with(this).load(UriUtil.cat).into(ivNavHead);
            RxView.clicks(headerView)
                    .throttleLast(500, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe((aVoid) -> {
                        //侧边栏头布局的点击事件
                        startActivity(new Intent(this, PersonalActivity.class));
                        ToastUtil.showToastShort("打开个人信息详情页");
                    });
            // default content ui
            navigationView.setCheckedItem(R.id.nav_item_home);//默认选中 主页！！
        }

        //content
        FrameLayout contentContainer = (FrameLayout) findViewById(R.id.main_content_container);
        LogUtils.i(contentContainer);
        // -->但是，这个不会调用 onNavigationItemSelected 方法，所以需要设置默认ui
        homeUi();
    }

    @Override
    protected void onResume() {
        super.onResume();
// Calculate ActionBar height
        toolbar.measure(0, 0);
        int height = toolbar.getHeight();
        System.out.println("AAAAAAAAA = = = =" + height);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (drawer != null && toggle != null) {
            drawer.removeDrawerListener(toggle);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private HomeFragment homeUi() {
        if (homeFragment == null)
            homeFragment = HomeFragment.newInstance(null, null);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId(), homeFragment)
                .commit();
        return homeFragment;
    }

    private HistoryFragment historyUi() {
        if (historyFragment == null)
            historyFragment = HistoryFragment.newInstance(null, null);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId(), historyFragment)
                .commit();
        return historyFragment;
    }

    private SlideShowFragment slideshowUi() {
        if (slideShowFragment == null) {
            slideShowFragment = new SlideShowFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId(), slideShowFragment)
                .commit();
        return slideShowFragment;
    }

    private ToolsFragment toolsUi() {
        if (toolsFragment == null) {
            toolsFragment = new ToolsFragment();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId(), toolsFragment)
                .commit();
        return toolsFragment;
    }

    private void hideUi(int currentId) {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(mFragmentSet.get(currentId))
                .commit();
    }

    int containerViewId() {
        return R.id.main_content_container;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_item_home) {
            // Handle the camera action
            curentId = id;
            mFragmentSet.put(id, homeUi());
        } else if (id == R.id.nav_history) {
            curentId = id;
            mFragmentSet.put(id, historyUi());
        } else if (id == R.id.nav_slideshow) {
            curentId = id;
            mFragmentSet.put(id, slideshowUi());
        } else if (id == R.id.nav_manage) {
            curentId = id;
            mFragmentSet.put(id, toolsUi());
        } else if (id == R.id.nav_share) {
            hideUi(curentId);
        } else if (id == R.id.nav_send) {
            hideUi(curentId);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
