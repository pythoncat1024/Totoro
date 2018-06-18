package com.pythoncat.totoro.activity.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.pythoncat.totoro.R;
import com.pythoncat.totoro.adapter.recycleradapter.CategoryAdapter;
import com.pythoncat.totoro.utils.ToastUtil;

/**
 * Created by pythonCat on 2016/5/10.
 * 详情页的展示界面
 */
public class DetailActivity extends AppCompatActivity {

    private WebView mWebView;
    private ImageView mIv;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_detail);
        String receivedUrl = getIntent().getStringExtra(CategoryAdapter.sendKey);

        pb = (ProgressBar) findViewById(R.id.progressBar_detail_activity);
        mWebView = (WebView) findViewById(R.id.webView_detail_activity);
        mIv = (ImageView) findViewById(R.id.iv_detail_activity);

        if (receivedUrl.endsWith(".png") || receivedUrl.endsWith(".jpg") || receivedUrl.endsWith(".gif")) {
            mIv.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
            Glide.with(this).load(receivedUrl).into(mIv);
        } else {
            mIv.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);

            // Let's display the progress in the activity title bar, like the
            // browser app does.


            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {
                    // Activities and WebViews measure progress with different scales.
                    // The progress meter will automatically disappear when we reach 100%
                    pb.setProgress(progress);
                }

            });
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                    super.onReceivedError(view, request, error);
                    ToastUtil.showToastShort("Oh no! " + request.toString());
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
            });
            mWebView.loadUrl(receivedUrl);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }
}
