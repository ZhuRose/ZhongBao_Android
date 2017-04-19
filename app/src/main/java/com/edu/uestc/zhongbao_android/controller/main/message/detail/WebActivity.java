package com.edu.uestc.zhongbao_android.controller.main.message.detail;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.edu.uestc.zhongbao_android.R;
import com.edu.uestc.zhongbao_android.application.Constant;
import com.edu.uestc.zhongbao_android.controller.base.BaseActivity;
import com.edu.uestc.zhongbao_android.controller.base.BaseSwipeActivity;

import butterknife.BindView;

/**
 * Created by zhu on 17/4/1.
 */

public class WebActivity extends BaseActivity {
    String url;

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int loadLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initData() {
        super.initData();
        url = (String) getIntent().getExtras().get("url");
        webView.loadUrl("http://"+url);
        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                Log.v("web", "finish");
                super.onPageFinished(view, url);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView.destroy();
    }

    @Override
    protected void initView() {
        super.initView();

    }
}
