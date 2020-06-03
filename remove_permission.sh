#!/bin/bash
adb shell pm revoke com.example.test_webview_demo android.permission.READ_PHONE_STATE
adb shell pm revoke com.example.test_webview_demo android.permission.WRITE_EXTERNAL_STORAGE
adb shell pm revoke com.example.test_webview_demo android.permission.READ_EXTERNAL_STORAGE