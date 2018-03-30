# 3G Dongle - Status List


## Trace code 

* packages/apps/Settings/res/xml/settings_headers.xml
* call __EthernetSettings__ here.  

```xml 
  <!-- Ethernet -->
  <header
       android:id="@+id/ethernet_settings"
       android:fragment="com.android.settings.ethernet.EthernetSettings"
       android:title="@string/eth_radio_ctrl_title"
       android:icon="@drawable/ic_settings_ethernet" />
```

__[What]__ is the usage of __id/ethernet_settings__ ?


## File list
--------------------------------------

/build/core/pathmap.mk

/device/nvidia/vcm30t30/init.vcm30t30.rc
/device/nvidia/vcm30t30/start_cdc
/device/nvidia/vcm30t30/vcm30t30.mk

--------------------------------------

* frameworks/base/Android.mk  
__IEthernetManager.aidl__ already there.


/frameworks/base/core/java/android/content/Context.java
/frameworks/base/core/java/android/preference/PreferenceActivity.java
/frameworks/base/core/java/android/provider/Settings.java
/frameworks/base/ethernet/java/android/net/ethernet/EthernetDataTracker.java
/frameworks/base/ethernet/java/android/net/ethernet/EthernetDevInfo.aidl
/frameworks/base/ethernet/java/android/net/ethernet/EthernetDevInfo.java
/frameworks/base/ethernet/java/android/net/ethernet/EthernetManager.java
/frameworks/base/ethernet/java/android/net/ethernet/IEthernetManager.aidl

* frameworks/base/services/java/com/android/server/ConnectivityService.java  
__ConnectivityService.java__ was moved to _frameworks/base/services/core/java/com/android/server_


* frameworks/base/services/java/com/android/server/EthernetService.java  
__EthernetService.java__ was moved to _frameworks/opt/net/ethernet/java/com/android/server/ethernet_


/frameworks/base/services/java/com/android/server/SystemServer.java


/kernel/arch/arm/configs/tegra_vcm30t30_android_defconfig



---------------------------------------------
## Add a new item to Settings

/packages/apps/Settings/res/drawable/ic_settings_ethernet.xml  
/packages/apps/Settings/res/drawable/ic_settings_ethernet_img.png  


* packages/apps/Settings/res/layout/ethernet_dialog.xml  
This file depends on strings.xml


* packages/apps/Settings/res/values/strings.xml  
__[Done]__  

* packages/apps/Settings/res/xml/ethernet_settings.xml  
__[Done]__ Just copy this file to the same folder.  
ref by AndroidManifest.xml  



* packages/apps/Settings/res/xml/settings_headers.xml
this file was removed in Android 7.1  
__ethernet_settings__ was put after WirelessSrttings and before DEVICE category.  
Compare to 7.1 --> _packages/apps/Settings/AndroidManifest.xml_  

__[What]__ is the usage of __ethernet_settings__ ?


----------------------------------------------

/packages/apps/Settings/src/com/android/settings/Settings.java  
/packages/apps/Settings/src/com/android/settings/ethernet/EthernetDialog.java  
/packages/apps/Settings/src/com/android/settings/ethernet/EthernetSettings.java  


* system/core/libnetutils/Android.mk  
__[Done]__ - do nothing on i.mx6  


* system/core/libnetutils/dhcp_utils.c  
This file was removed in Android 7.1  




