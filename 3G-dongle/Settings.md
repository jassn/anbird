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

