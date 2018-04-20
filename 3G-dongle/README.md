
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
Content from cell 1 | Content from cell 2
Content in the first column | Content in the second column



-----------------------------------------------------
## String rename because they are conflict with AOSP

Items | Android-7.1 Ethernet | New 3G Dongle 
----- | -------------------- | --------------
var | ETHERNET_SERVICE | ETHASKEY_SERVICE
file | IEthernetManager.aidl | IEthAskeytManager.aidl
interface | EthernetManager | EthAskeyManager




-----------------------------------------------------

## How to add a new item to Settings?

* [Android-7.0 Settings中添加一个菜单选项](https://blog.csdn.net/qq_25804863/article/details/50229461)


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



