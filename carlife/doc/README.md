# MTP vs iAP2


   type        | MTP     |     iAP2
-------------- | ------- | -------------------
  package      | packages/providers/MediaProvider  | packages/apps/Settings/src/com/android/settings/ethernet/EthernetSettings.java
  s            | -                     | -
  DataTrack    | - | frameworks/base/core/java/android/net/ethernet/EthernetDataTracker.java
  s            | -                     | -
  Service      | frameworks/base/media | frameworks/base/services/java/com/android/server
  Service      | MtpServer.java        | Iap2Service.java
  s            | -                     | -
  JNI          | -       | frameworks/base/services/core/jni
  JNI          | -       | com_android_server_iap2_Service.cpp
  s            | -       | -
 *native*      |    -    |  frameworks/av/media/mtp 
 *native*      | MtpServer.cpp | Iap2Server.cpp
  

----------------------------------------------------
###### Reference

* [Android JNI初步☞Java方法和native方法关联](https://blog.csdn.net/x13945/article/details/51938345)


* [I.MX6Q(TQIMX6Q/TQE9)学习笔记——新版BSP之USB HOST移植](https://blog.csdn.net/girlkoo/article/details/45652209)





----------------------------------------------------
