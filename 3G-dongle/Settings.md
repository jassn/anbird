# Settings of Android 7.1

## Trace ...... setEnabled
This will go to `reconnect` or `teardown` of EthernetTracker.java  
packages/apps/Settings/src/com/android/settings/ethernet
* **setEthEnabled** is controlled from GUI.
```java
    private void setEthEnabled(final boolean enable) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... unused){
                try {
                    if ((mEthManager.isConfigured() != true) && (enable == true)){
                        publishProgress();
                    }else{
                        mEthManager.setEnabled(enable);
                    }
                    Thread.sleep(500);
                }catch(Exception e){
                }
                return null;
            }
        }
    }
```
EthernetManager.java
```java
    public void setEnabled(boolean enable) {
        try {
            mService.setState(enable ? ETHERNET_STATE_ENABLED:ETHERNET_STATE_DISABLED);
        } catch (RemoteException e) {
            Slog.i(TAG,"Can not set new state");
        }
    }
```
EthernetService.java
```java
    public synchronized void setState(int state) {
        if (mEthState != state) {
            mEthState = state;
            if (state == EthAskeyManager.ETHERNET_STATE_DISABLED) {
                persistEnabled(false);
                mTracker.teardown();
            } else if (state == EthAskeyManager.ETHERNET_STATE_ENABLED){
                persistEnabled(true);
                mTracker.reconnect();
            }
        }
    }
```


## Four header categories:
* Wireless & networks
* Devices
* Personal 
* System  
_packages/apps/Settings/AndroidManifest.xml_
```xml

        <activity android:name=".Settings$WirelessSettings"
            android:label="@string/header_category_wireless_networks">
            <intent-filter android:priority="4">
                <action android:name="com.android.settings.category.wireless" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>

        <activity android:name=".Settings$DeviceSettings"
            android:label="@string/header_category_device">
            <intent-filter android:priority="3">
                <action android:name="com.android.settings.category.device" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>

        <activity android:name=".Settings$PersonalSettings"
            android:label="@string/header_category_personal">
            <intent-filter android:priority="2">
                <action android:name="com.android.settings.category.personal" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>

        <activity android:name=".SystemSettings"
            android:label="@string/header_category_system">
            <intent-filter android:priority="1">
                <action android:name="com.android.settings.category.system" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>


```
-------------------------------


Reference
=========

* [Settings中添加一个菜单选项](https://blog.csdn.net/qq_25804863/article/details/50229461)


* [Android 7.0 settings 总结](https://blog.csdn.net/w690333243/article/details/58603571)

* [飞行模式下打开wifi](https://blog.csdn.net/lfx_xianxian/article/details/50847455)

* [Settings 裡面新增一個類別選項](http://mf99coding.logdown.com/posts/175706-how-to-add-an-option-in-the-settings-page-along-with-a-switch)


* [积跬步至千里系列之九--Android系统设置(二)](https://blog.csdn.net/sdjzyuxinburen/article/details/50652003)

* [Android Settings 中添加item 解决方案](https://www.jianshu.com/p/5318da3aced4)

* [Android Activity加载Fragment的一般简易方法](https://blog.csdn.net/zhangphil/article/details/49942519)



