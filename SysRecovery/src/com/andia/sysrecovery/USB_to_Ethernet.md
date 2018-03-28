# USB to Ethernet

* [深入MountService、vold（五） MountService中通信（NativeDaemonConnector）(and5.1)](https://blog.csdn.net/kc58236582/article/details/47447153)

## NetworkManagementService class
* Constructs a new __NetworkManagementService__ instance.  
_frameworks/base/services/core/java/com/android/server/NetworkManagementService.java_   
* __NetdCallbackReceiver()__ is registered here.
```java
        mConnector = new NativeDaemonConnector(new NetdCallbackReceiver(), socket, 
                10, NETD_TAG, 160, wl,
                FgThread.get().getLooper());
        mThread = new Thread(mConnector, NETD_TAG);

        mDaemonHandler = new Handler(FgThread.get().getLooper());

        // Add ourself to the Watchdog monitors.
        Watchdog.getInstance().addMonitor(this);
```
------------------------------------------------------------------------
## NativeDaemonConnector CTOR  
* __frameworks/base/services/core/java/com/android/server/NativeDaemonConnector.java__  
```java
    NativeDaemonConnector(INativeDaemonConnectorCallbacks callbacks, String socket,
            int responseQueueSize, String logTag, int maxLogSize, PowerManager.WakeLock wl,
            Looper looper) {
        mCallbacks = callbacks;
        mSocket = socket;
        mResponseQueue = new ResponseQueue(responseQueueSize);
        mWakeLock = wl;
        if (mWakeLock != null) {
            mWakeLock.setReferenceCounted(true);
        }
        mLooper = looper;
        mSequenceNumber = new AtomicInteger(0);
        TAG = logTag != null ? logTag : "NativeDaemonConnector";
        mLocalLog = new LocalLog(maxLogSize);
    }
```

# Others
## Good diagram.
* [Android热插拔事件处理流程--Vold](https://blog.csdn.net/myarrow/article/details/8246716)

