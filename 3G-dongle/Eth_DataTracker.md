# Ethernet Data Tracker

- [ ] How to register **interfaceAdded** of EthernetDataTracker ?
- [x] Who call **interfaceAdded** ?


------------------------------------

## Who call *interfaceAdded* ?
When 3G dongle is plugged in,
`onEvent` receives **usb0** for `notifyInterfaceAdded`.

* frameworks/base/services/java/com/android/server/
NetworkManagementService.java

```java
        @Override
        public boolean onEvent(int code, String raw, String[] cooked) {
            switch (code) {
            case NetdResponseCode.InterfaceChange:
                    /*
                     * a network interface change occured
                     * Format: "NNN Iface added <name>"
                     *         "NNN Iface removed <name>"
                     *         "NNN Iface changed <name> <up/down>"
                     *         "NNN Iface linkstatus <name> <up/down>"
                     */
                    if (cooked.length < 4 || !cooked[1].equals("Iface")) {
                        throw new IllegalStateException(
                                String.format("Invalid event from daemon (%s)", raw));
                    }
                    if (cooked[2].equals("added")) {
                        notifyInterfaceAdded(cooked[3]);
                        return true;
```

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
