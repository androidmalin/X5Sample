package com.example.test_webview_demo;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.test_webview_demo.utils.WebViewJavaScriptFunction;
import com.example.test_webview_demo.utils.X5WebView;

public class FullScreenActivity extends Activity {

    /**
     * 用于演示X5WebView实现视频的全屏播放功能 其中注意 X5的默认全屏方式 与 android 系统的全屏方式
     */
    private X5WebView mWebView;
    private static final String LOCAL_URL = "file:///android_asset/webpage/fullscreenVideo.html";
    private static final String REMOTE_URL = "https://v-cdn.zjol.com.cn/276984.mp4";
    private static final String REMOTE_URL2 = "https://3g.163.com/news/article/FCGL5PTP000189FH.html?clickfrom=channel2018_news_newsList#offset=0";
    private static final String TAG = FullScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_webview_layout);
        initWindow();
        initView();
        initData();
        initListener();
        initDefault();
    }

    private void initDefault() {
        enablePageVideoFunc();
    }

    private void initWindow() {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    private void initView() {
        mWebView = findViewById(R.id.wv_common);
        mWebView.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
    }

    private void initData() {
        mWebView.loadUrl(REMOTE_URL2);
    }

    private void initListener() {
        mWebView.addJavascriptInterface(new WebViewJavaScriptFunction() {

            @Override
            public void onJsFunctionCalled(String tag) {
                Log.d(TAG, "onJsFunctionCalled:" + tag);
            }

            @JavascriptInterface
            public void onX5ButtonClicked() {
                enableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onCustomButtonClicked() {
                disableX5FullscreenFunc();
            }

            @JavascriptInterface
            public void onLiteWndButtonClicked() {
                enableLiteWndFunc();
            }

            @JavascriptInterface
            public void onPageVideoClicked() {
                enablePageVideoFunc();
            }
        }, "Android");
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "横屏", Toast.LENGTH_SHORT).show();
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "竖屏", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 开启X5全屏播放模式
     * 向WebView发出信息
     */
    private void enableX5FullscreenFunc() {
        if (mWebView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启X5全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", false);// true表示标准全屏,false表示X5全屏;不设置默认false,
            data.putBoolean("supportLiteWnd", false);// false:关闭小窗;true:开启小窗;不设置默认true,
            data.putInt("DefaultVideoScreen", 2);// 1:以页面内开始播放,2:以全屏开始播放;不设置默认:1
            mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }

    private void disableX5FullscreenFunc() {
        if (mWebView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "恢复webkit初始状态", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", true);
            data.putBoolean("supportLiteWnd", false);
            data.putInt("DefaultVideoScreen", 2);
            mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }

    private void enableLiteWndFunc() {
        if (mWebView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "开启小窗模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", false);
            data.putBoolean("supportLiteWnd", true);
            data.putInt("DefaultVideoScreen", 2);
            mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }

    private void enablePageVideoFunc() {
        if (mWebView.getX5WebViewExtension() != null) {
            Toast.makeText(this, "页面内全屏播放模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();
            data.putBoolean("standardFullScreen", false);
            data.putBoolean("supportLiteWnd", false);
            data.putInt("DefaultVideoScreen", 1);
            mWebView.getX5WebViewExtension().invokeMiscMethod("setVideoParams", data);
        }
    }
}
