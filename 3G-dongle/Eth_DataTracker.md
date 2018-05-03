# Ethernet Data Tracker on Android-4.2

- [ ] **startMonitoring** is called from somewhere of framework. 
- [ ] How to register **interfaceAdded** of EthernetDataTracker ?
- [x] Who call **interfaceAdded** ?




------------------------------------------
## Who send broadcast? and when ?

* interfaceAdded call reconnect
* **reconnect** call ConnectNetwork
* ConnectNetwork call runDhcp.
* **runDhcp** call sendStateBroadcast.
```java
    private void runDhcp() {
        Thread dhcpThread = new Thread(new Runnable() {
            public void run() {
                synchronized(this) {
                       sendStateBroadcast(EthAskeyManager.EVENT_CONFIGURATION_SUCCEEDED);
                       return;
                }
            } // run
        }, "ETH_DHCP");
        dhcpThread.start();
    }
```

* **sendStateBroadcast** sends NETWORK_STATE_CHANGED_ACTION.
```java
    private void sendStateBroadcast(int event) {
        Intent intent = new Intent(EthAskeyManager.NETWORK_STATE_CHANGED_ACTION);
        intent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT
                                    | Intent.FLAG_RECEIVER_REPLACE_PENDING);
        intent.putExtra(EthAskeyManager.EXTRA_NETWORK_INFO, mNetworkInfo);
        intent.putExtra(EthAskeyManager.EXTRA_LINK_PROPERTIES,
                            new LinkProperties (mLinkProperties));
        intent.putExtra(EthAskeyManager.EXTRA_ETHERNET_STATE, event);
        mContext.sendStickyBroadcast(intent);
    }
```

* **handleReceive** receives `NETWORK_STATE_CHANGED_ACTION` from broadcast.  
* then call `mEthStateReceiver.onReceive`. 
```java
    public EthAskeyService(Context context) {
        mContext = context;
        mDeviceMap = new HashMap<String, EthernetDevInfo>();

        IBinder b = ServiceManager.getService(Context.NETWORKMANAGEMENT_SERVICE);
        mNMService = INetworkManagementService.Stub.asInterface(b);
        mTracker = EthernetDataTracker.getInstance();

        mEthStateReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
                handleReceive(context, intent);
            }
        };
        mFilter = new IntentFilter();
        mFilter.addAction(EthAskeyManager.NETWORK_STATE_CHANGED_ACTION);

        context.registerReceiver(mEthStateReceiver, mFilter);
    }
```
* and the message should be registered in
frameworks/base/core/res/AndroidManifest.xml
```java
<protected-broadcast android:name="android.net.ethernet.STATE_CHANGE" />
```









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

    /**
     * Begin monitoring connectivity
     */
    public void startMonitoring(Context context, Handler target) {
        mContext = context;
        mCsHandler = target;

        // register for notifications from NetworkManagement Service
        IBinder b = ServiceManager.getService(Context.NETWORKMANAGEMENT_SERVICE);
        mNMService = INetworkManagementService.Stub.asInterface(b);

        mEthManage = EthernetManager.getInstance();
        
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
