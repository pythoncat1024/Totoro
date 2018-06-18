package com.pythoncat.totoro.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.pythoncat.totoro.App;
import com.pythoncat.totoro.R;
import com.pythoncat.totoro.utils.RxJavaUtil;
import com.pythoncat.totoro.utils.UriUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

public class LauncherActivity extends AppCompatActivity {

    private RelativeLayout launcherLayout;
    private RelativeLayout skipLayout;
    private ImageView ivAd;
    private TextView skipTv;
    private Subscription gotoMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        init(false);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        closeTimerDelay();
        startMain();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Picasso.with(this).cancelRequest(ivAd);
        closeTimerDelay();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private Activity getActivity() {
        return this;
    }

    /**
     * 初始化启动页
     *
     * @param removeAd 是否移除广告！
     */
    private void init(boolean removeAd) {
        launcherLayout = (RelativeLayout) findViewById(R.id.launcher_layout);
        skipLayout = (RelativeLayout) findViewById(R.id.skip_layout);
        skipTv = (TextView) findViewById(R.id.tv_skip);
        ivAd = (ImageView) findViewById(R.id.img_ad);
        ivAd.setEnabled(false);
        String adImg = UriUtil.impl().img();

        RxView.clicks(ivAd)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribe((aVoid) -> {
                    closeTimerDelay();
                    Uri uri = Uri.parse(UriUtil.impl().bt(adImg));
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                });
        Callback callback = new Callback() {
            @Override
            public void onSuccess() {
                ivAd.setEnabled(true);
                LogUtils.i("Picasso onSuccess");
                setBackgroundWhenLoad();
                gotoMain = timerDelay(3, () ->
                        {
                            LogUtils.w("@@@ ………………打开主界面");
                            startMain();
                        }
                        , (aLong) -> {
                            long time = 3 - aLong;
                            if (time > 0) {
                                skipTv.setText(App.getString(R.string.goto_main, time));
                            } else {
                                skipTv.setText(R.string.goto_main2);
                            }
                        });
            }

            @Override
            public void onError() {
                LogUtils.e("Picasso onError");
                ivAd.setEnabled(false);
                //失败就直接打开主界面吧
                startMain();
            }
        };
        if (removeAd) {
            startMain();
        } else {
            LogUtils.e("广告链接： " + adImg);
            // adImg = "http://img3.cache.netease.com/photo/0008/2016-05-09/BMLGK0BB2EI00008.jpg"
            Picasso.with(this).load(adImg).fit()
//                .memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE)
                    .centerCrop()
                    .config(Bitmap.Config.ARGB_8888)
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.small_yellow)
                    .into(ivAd, callback);
        }

        skipLayout.setOnClickListener((v) -> {
            closeTimerDelay();
            startMain();
        });
    }

    private void closeTimerDelay() {
        LogUtils.e("### 停止延时打开主界面");
        RxJavaUtil.close(gotoMain);
    }

    /**
     * count 秒之后，执行 action
     *
     * @param count  秒数
     * @param action 结束时，执行的行为
     * @param onNext 每隔一秒 执行的行为
     */
    private Subscription timerDelay(int count, Action0 action, Action1<Long> onNext) {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count) //3 次，每次1秒间隔的定时器
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(action)
                .subscribe(onNext);
    }

    private void setBackgroundWhenLoad() {
        launcherLayout.setBackgroundResource(R.color.colorPrimary);
    }

    private Subscription startMainDelay(long delay) {
        return Observable.timer(delay, TimeUnit.SECONDS).subscribe((aLong) -> {
            startMain();
            closeTimerDelay();
        });
    }

    private void startMain() {
        Intent it = new Intent(App.get(), MainActivity.class);
        startActivity(it);
        getActivity().finish();
        closeTimerDelay();
    }
}
