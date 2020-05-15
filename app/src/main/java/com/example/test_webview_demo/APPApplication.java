package com.example.test_webview_demo;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

public class APPApplication extends Application {

    private static final String TAG = "XX55";

    @Override
    public void onCreate() {
        super.onCreate();
        initListener();
    }

    private void initListener() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
            @Override
            public void onViewInitFinished(boolean success) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d(TAG, "onViewInitFinished");
                Log.d(TAG, "x5内核加载 " + (success ? "成功" : "失败"));
            }

            @Override
            public void onCoreInitFinished() {
                Log.d(TAG, "onCoreInitFinished");
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

}
