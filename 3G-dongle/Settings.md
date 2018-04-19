# Settings of Android 7.1

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

