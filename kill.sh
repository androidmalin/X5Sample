#!/bin/bash
name="com.example.test_webview_demo"
adb shell ps | grep $name | awk '{print $2}' | xargs adb shell run-as $name kill
