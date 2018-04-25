# Ethernet Data Tracker

- [ ] who call **interfaceAdded** ?

----------------------------------------------
## trace android-4.2

frameworks/base/services/java/com/android/server
```java
    private NetworkManagementService(Context context) {
        mContext = context;

        if ("simulator".equals(SystemProperties.get("ro.product.device"))) {
            return;
        }

        mConnector = new NativeDaemonConnector(
                new NetdCallbackReceiver(), "netd", 10, NETD_TAG, 160);
        mThread = new Thread(mConnector, NETD_TAG);

        // Add ourself to the Watchdog monitors.
        Watchdog.getInstance().addMonitor(this);
    }
```



## NetworkUtils

a| Android-4.2 | Android 7.1
----|---------------|----------
