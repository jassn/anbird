
## Work of 2018-4-30
- [ ] **sendStateBroadcast** should be added in proper places to send broadcast.
- [x] **onReceive** is test OK if use "android.net.wifi.STATE_CHANGE" in EthAskeyService.java.
- [ ] Look for pppoe which was added by annett.
- [ ] pppoe
- [ ] EVENT_STATE_CHANGED ??


-----------------------------------------------------
## Work of 2018-4-25
- [x] run EthernetDataTracker.getInstance.
- [ ] run EthernetDataTracker.interfaceAdded, interfaceRemoved, interfaceLinkStateChanged

-----------------------------------------------------
## Todo List
- [ ] addressRemoved of EthernetDataTracker.java
- [ ] ppp-related.
- [ ] no this file on the target: **/system/bin/dhcpcd**.  
- [ ] dhcp (libnet), how to wait for cdc property to complete?  
Look for the equivalence of **wait_for_property** in *frameworks/base/service/net*.
- [ ] add **EthernetDataTracker.java** to 
frameworks/base/core/java/android/net/ethernet ??  
many error messages!  
See if this java source can be found in /qwrk??  
- [ ] How to start AIDL service gracefully? like wifi-service?
- [ ] iperf.
- [ ] bluetooth: no sound.
- [x] getState().

-----------------------------------------------------
## wait for property

* see also: system/netd

```cpp
/*
 * Wait for a system property to be assigned a specified value.
 * If desired_value is NULL, then just wait for the property to
 * be created with any value. maxwait is the maximum amount of
 * time in seconds to wait before giving up.
 */
static int wait_for_property(const char *name, const char *desired_value, int maxwait)
{
    char value[PROPERTY_VALUE_MAX] = {'\0'};
    int maxnaps = (maxwait * 1000) / NAP_TIME;

    if (maxnaps < 1) {
        maxnaps = 1;
    }

    while (maxnaps-- > 0) {
        usleep(NAP_TIME * 1000);
        if (property_get(name, value, NULL)) {
            if (desired_value == NULL ||
                    strcmp(value, desired_value) == 0) {
                return 0;
            }
        }
    }
    return -1; /* failure */
}
```

-----------------------------------------------------

## String rename because they are conflict with AOSP
frameworks/base/services/java/com/android/server

Items | Android-7.1 Ethernet | New 3G Dongle 
----- | -------------------- | --------------
file | EthernetService.java | EthAskeyService.java






## Comparison of Android ethernet service with Ethernet askey 3G.

File | Android-4.4 Askey | Android-7.1 Ethernet | 3G Dongle 
----- | ---------------- | --------- | ----------
EthernetManager.java  | framework/base/ethernet/java/android/net/ethernet | frameworks/base/core/java/android/net
EthernetService.java  | frameworks/base/services/java/com/android/server | frameworks/opt/net/ethernet | frameworks/base/services/java
IEthernetManager.aidl | framework/base/ethernet | frameworks/base/core/java/android/net | ~IEthAskeyManager.aidl~



-----------------------------------------------------
## String rename because they are conflict with AOSP

Items | Android-7.1 Ethernet | New 3G Dongle 
----- | -------------------- | --------------
var | ETHERNET_SERVICE | ETHASKEY_SERVICE
file | IEthernetManager.aidl | IEthAskeytManager.aidl
interface | EthernetManager | EthAskeyManager




-----------------------------------------------------

## [2018-4-17]
google: ethpreference

https://blog.csdn.net/shan0xiao0xi/article/details/17116667
https://github.com/hongyunwu/android4.4-settings/blob/master/src/com/android/settings/ethernet/EthernetSettings.java





-----------------------------------------------------
## [2018-4-17]

ninja: Entering directory `.'
[  0% 5/1250] Aidl: framework <= frameworks/base/ethernet/java/android/net/ethernet/IEthernetManager.aidl
FAILED: /bin/bash -c "out/host/linux-x86/bin/aidl -dout/target/common/obj/JAVA_LIBRARIES/framework_intermediates/src/ethernet/java/android/net/ethernet/IEthernetManager.P -b  -Iframeworks/base -Iframeworks/base/src -Isystem/update_engine/binder_bindings -Iframeworks/base/core/java -Iframeworks/base/graphics/java -Iframeworks/base/location/java -Iframeworks/base/media/java -Iframeworks/base/media/mca/effect/java -Iframeworks/base/media/mca/filterfw/java -Iframeworks/base/media/mca/filterpacks/java -Iframeworks/base/drm/java -Iframeworks/base/opengl/java -Iframeworks/base/sax/java -Iframeworks/base/telecomm/java -Iframeworks/base/telephony/java -Iframeworks/base/wifi/java -Iframeworks/base/keystore/java -Iframeworks/base/rs/java -Iframeworks/native/aidl/binder -Iframeworks/av/camera/aidl -Iframeworks/native/aidl/gui -Isystem/netd/server/binder -Iframeworks/base/core/java -Iframeworks/base/graphics/java -Iframeworks/base/location/java -Iframeworks/base/media/java -Iframeworks/base/media/mca/effect/java -Iframeworks/base/media/mca/filterfw/java -Iframeworks/base/media/mca/filterpacks/java -Iframeworks/base/drm/java -Iframeworks/base/opengl/java -Iframeworks/base/sax/java -Iframeworks/base/telecomm/java -Iframeworks/base/telephony/java -Iframeworks/base/wifi/java -Iframeworks/base/keystore/java -Iframeworks/base/rs/java frameworks/base/ethernet/java/android/net/ethernet/IEthernetManager.aidl out/target/common/obj/JAVA_LIBRARIES/framework_intermediates/src/ethernet/java/android/net/ethernet/IEthernetManager.java"

frameworks/base/ethernet/java/android/net/ethernet/IEthernetManager.aidl:19: couldn't find import for class android.net.ethernet.EthernetDevInfo

## Fixed by adding "ethernet" to 
/nmopt/Antec_imx6dl/build/core/pathmap.mk

-----------------------------------------------------






## How to add a new item to Settings?

* [Android-7.0 Settings中添加一个菜单选项](https://blog.csdn.net/qq_25804863/article/details/50229461)



-----------------------------------------------------
## TODO

* how to develop without flashing all images?





-----------------------------------------------------


## older Android versions


* [在settings中添加一个类似于wlan/ethernet的模块](https://blog.csdn.net/ice__bingo/article/details/56666806)


* [android-2.3.1](https://blog.csdn.net/jdsjlzx/article/details/22077661)


-------------------------------------


## others android settings

https://blog.csdn.net/ice__bingo/article/details/56666806

https://blog.csdn.net/jdsjlzx/article/details/22077661

https://blog.csdn.net/w690333243/article/details/59112468

https://blog.csdn.net/wzy_1988/article/details/50556113

https://blog.csdn.net/qq_25804863/article/details/50229461

https://blog.csdn.net/world_kun/article/details/51735732

https://blog.csdn.net/qq_31530015/article/details/53507968

https://blog.csdn.net/zhudaozhuan/article/details/50816086

------------------------------------

## ethernet service



