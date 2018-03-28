# USB to Ethernet

* [深入MountService、vold（五） MountService中通信（NativeDaemonConnector）(and5.1)](https://blog.csdn.net/kc58236582/article/details/47447153)


------------------------------------------------------------------------
## onEvent()



* Then look at __listenToSocket__ of NativeDaemonConnector  
_frameworks/base/services/core/java/com/android/server/NativeDaemonConnector.java_
* __sendMessage__ to NativeDaemonConnector::handleMessage
```java
private void listenToSocket() throws IOException {
                ......
                Message msg = mCallbackHandler.obtainMessage(
                        event.getCode(), uptimeMillisInt(), 0, event.getRawEvent());
                if (mCallbackHandler.sendMessage(msg)) {
                    releaseWl = false;
                }
}                
```

* NativeDaemonConnector::handleMessage call NetdCallbackReceiver::onEvent.  
_frameworks/base/services/core/java/com/android/server/NetworkManagementService.java_
```java
    public boolean handleMessage(Message msg) {
            if (!mCallbacks.onEvent(msg.what, event, NativeDaemonEvent.unescapeArgs(event))) {
                log(String.format("Unhandled event '%s'", event));
            }
    }
```

* __onEvent__ call notifyInterfaceAdded.  
```java
public boolean onEvent(int code, String raw, String[] cooked) {
        String errorMessage = String.format("Invalid event from daemon (%s)", raw);
        switch (code) {
        case NetdResponseCode.InterfaceChange:
                        if (cooked[2].equals("added")) {
                                notifyInterfaceAdded(cooked[3]);
                                return true;
                        }
        } // END switch
} // END onEvent
```

* __notifyInterfaceAdded__ notify our observers of an interface addition.  
* __interfaceAdded__ was called.
```java
    private void notifyInterfaceAdded(String iface) {
        final int length = mObservers.beginBroadcast();
        try {
            for (int i = 0; i < length; i++) {
                try {
                    mObservers.getBroadcastItem(i).interfaceAdded(iface);
                } catch (RemoteException | RuntimeException e) {
                }
            }
        } finally {
            mObservers.finishBroadcast();
        }
    }
```

* Guess
* __InterfaceObserver::interfaceAdded__ get notified.  
_frameworks/opt/net/ethernet/java/com/android/server/ethernet/EthernetNetworkFactory.java_
```java
public void interfaceAdded(String iface) {
    Log.d(TAG, "jsn, line.178");
    maybeTrackInterface(iface);
}
```


------------------------------------------------------------------------

## Constructs a new __NetworkManagementService__ instance.  
* __NetworkManagementService__ create a new NativeDaemonConnector.  
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

