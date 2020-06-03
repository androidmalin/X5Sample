#!/bin/bash
#1.find the first device
packageName="com.example.test_webview_demo"
first_device=`adb devices | awk  'NR==2' | awk  '{print $1}'`
echo "apk will install to "${first_device}

#2.uninstall this app
adb -s ${first_device} uninstall ${packageName} >/dev/null
echo "uninstall ${packageName} from "${first_device}
echo "install ${packageName} to "${first_device}

#3.compile and install
echo "gradle app:installDebug"
./gradlew -q app:installDebug -x lint --parallel --offline --continue &&

#4.start app
echo "start app"
adb -s ${first_device} shell am start ${packageName}/.MainActivity >/dev/null

#5.sleep
echo "sleep 3s"
sleep 3s

#6.set permission
echo "set permission start"
adb shell pm grant com.example.test_webview_demo android.permission.READ_PHONE_STATE
adb shell pm grant com.example.test_webview_demo android.permission.WRITE_EXTERNAL_STORAGE
adb shell pm grant com.example.test_webview_demo android.permission.READ_EXTERNAL_STORAGE
echo "set permission end"

#7.sleep
echo "sleep 3s"
sleep 3s

#8.kill pid
echo "kill app"
adb shell ps | grep ${packageName} | awk '{print $2}' | xargs adb shell run-as ${packageName} kill

#9.sleep
echo "sleep 3s"
sleep 3s

#10.restart
echo "restart app"
adb -s ${first_device} shell am start ${packageName}/.MainActivity >/dev/null
