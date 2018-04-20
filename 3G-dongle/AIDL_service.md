# The Calling Sequence

File  | Location             | Remark
----- | -------------------- | --------------
**EthernetManager.java** | frameworks/base/core/java/android/net/ethernet
**EthAskeyService.java** | frameworks/base/services/java/com/android/server | getState

-------------------------------------------------------
## IEthernetManager.aidl
**IEthAskeyManager.aidl** - frameworks/base/core/java/android/net/ethernet/
```java
interface IEthAskeyManager
{
    List<EthernetDevInfo> getDeviceNameList();
    void setState(int state);
    int getState( );
    ......
}
```

**Android.mk** - framework/base
```java
core/java/android/net/ethernet/IEthAskeyManager.aidl
```



-------------------------------------------------------

## SystemServer.java

frameworks/base/services/java/com/android/server
```java
   private static final String ETHERNET_SERVICE_CLASS =
            "com.android.server.ethernet.EthernetService";
   ......
   mSystemServiceManager.startService(ETHERNET_SERVICE_CLASS);
```

References
==========

google "aidl null object reference"  

* [unable to start service intent](https://stackoverflow.com/questions/3439356/unable-to-start-service-intent)


* [AIDL初识](https://www.jianshu.com/p/c1a5dd10ad78)


* [Android：学习AIDL，这一篇文章就够了(上)](https://blog.csdn.net/luoyanglizi/article/details/51980630)




