# Ethernet Data Tracker on Android-4.2

- [ ] **startMonitoring** is called from somewhere of framework. 
- [ ] How to register **interfaceAdded** of EthernetDataTracker ?
- [x] Who call **interfaceAdded** ?

------------------------------------------
## interfaceAdded

```java
   private static class InterfaceObserver extends INetworkManagementEventObserver.Stub {
        private EthernetDataTracker mTracker;

        public void interfaceAdded(String iface) {
            mTracker.interfaceAdded(iface);
        }
    }
    
    private void interfaceAdded(String iface) {
        /**  It will add an new interface to EthernetService and check it.  **/
        if(!mEthManage.addInterfaceToService(iface))
            return;

        /**  The first adding interface will be reconnect.  **/
        synchronized(mIface) {
            if(!mIface.isEmpty())
                return;
            mIface = iface;
        }

        NetworkUtils.enableInterface(mIface);
        reconnect();
    }

```



------------------------------------------
## maybeTrackInterface
* /frameworks/opt/net/ethernet/java/com/android/server





------------------------------------------
## startMonitoring - Begin monitoring connectivity

Items | Android-4.2  | Android-7.1.1
----- | -------------------- | --------------
class | EthernetDataTracker | EthernetNetworkFactory
method | startMonitoring | start



------------------------------------------
* Reference to Android-7.1.1
grep **Started tracking interface** in
frameworks/opt/net/ethernet/java/com/android/server/ethernet


```java
    public void startMonitoring(Context context, Handler target) {
        mContext = context;
        mCsHandler = target;

        // register for notifications from NetworkManagement Service
        IBinder b = ServiceManager.getService(Context.NETWORKMANAGEMENT_SERVICE);
        mNMService = INetworkManagementService.Stub.asInterface(b);

        mEthManage = EthernetManager.getInstance();

        if (mEthManage == null)
            Log.e("SHUGE", "mEthManage is nulllllllllllllllllllllllllllllllllllll");
        else
            Log.e("SHUGE", "mEthManage is OKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK");
        mInterfaceObserver = new InterfaceObserver(this);
```


------------------------------------
## NetworkStateTracker
* framework/base/core

```java
public interface NetworkStateTracker {
```



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
