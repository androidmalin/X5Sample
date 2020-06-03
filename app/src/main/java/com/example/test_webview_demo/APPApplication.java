package com.example.test_webview_demo;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

/**
 * 由于第三方首次启动时加载x5会消耗时间，为了使第三方app运行流畅，Tbs会在app首次启动时加载系统内核，待第三方app再次启动时就会加载X5内核了。
 * 首次安装APP后第一次启动时X5内核总是加载失败（手机中已经安装了微信和QQ也不行，直接运行官网的demo也是首次加载失败），kill掉程序后再次启动就好了（这个问题有人已经在官网反馈了，但是官方没有给出解决方案）
 * https://www.jianshu.com/p/da19da46fb20
 * https://www.jianshu.com/p/e5ea44673870
 */
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
