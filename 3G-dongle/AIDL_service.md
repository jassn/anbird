# The Calling Sequence

File  | Location             | Remark
----- | -------------------- | --------------
**EthernetManager.java** | frameworks/base/core/java/android/net/ethernet
**EthAskeyService.java** | frameworks/base/services/java/com/android/server | getState

-------------------------------------------------------

# SystemServer.java

frameworks/base/services/java/com/android/server
```java
   private static final String ETHERNET_SERVICE_CLASS =
            "com.android.server.ethernet.EthernetService";
   ......
   mSystemServiceManager.startService(ETHERNET_SERVICE_CLASS);
```


