package com.example.test_webview_demo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.test_webview_demo.utils.X5WebView;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;

public class FileChooserActivity extends Activity {

    /**
     * 用于展示在web端<input type=text>的标签被选择之后，文件选择器的制作和生成
     */
    private X5WebView mWebView;
    private ValueCallback<Uri> mUploadFile;
    private ValueCallback<Uri[]> mUploadFiles;
    private static final String TAG = "FileChooser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_webview_layout);
        initView();
        initData();
        initListener();
    }

    private void initData() {
        mWebView.loadUrl("file:///android_asset/webpage/fileChooser.html");
    }

    private void initView() {
        mWebView = findViewById(R.id.wv_common);
    }

    private void initListener() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                Log.d(TAG, "openFileChooser 1");
                mUploadFile = uploadMsg;
                openFileChooseProcess();
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                Log.d(TAG, "openFileChooser 2");
                mUploadFile = uploadMsg;
                openFileChooseProcess();
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.d(TAG, "openFileChooser 3");
                mUploadFile = uploadMsg;
                openFileChooseProcess();
            }

            // For Android  >= 5.0
            public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             WebChromeClient.FileChooserParams fileChooserParams) {
                Log.d(TAG, "openFileChooser 4:" + filePathCallback.toString());
                mUploadFiles = filePathCallback;
                openFileChooseProcess();
                return true;
            }
        });
    }

    private void openFileChooseProcess() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(Intent.createChooser(i, TAG), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                if (null != mUploadFile) {
                    Uri result = data == null ? null : data.getData();
                    mUploadFile.onReceiveValue(result);
                    mUploadFile = null;
                }
                if (null != mUploadFiles) {
                    Uri result = data == null ? null : data.getData();
                    mUploadFiles.onReceiveValue(new Uri[]{result});
                    mUploadFiles = null;
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != mUploadFile) {
                mUploadFile.onReceiveValue(null);
                mUploadFile = null;
            }
        }
    }

    /**
     * 确保注销配置能够被释放
     */
    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }
}
