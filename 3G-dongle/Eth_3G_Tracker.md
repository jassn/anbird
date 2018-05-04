# Ethernet on Android-7.1.1

## interfaceLinkStateChanged
```java
    public void interfaceLinkStateChanged(String iface, boolean up) {
        updateInterfaceState(iface, up);
    }

    private void updateInterfaceState(String iface, boolean up) {
        if (!mIface.equals(iface)) {
            return;
        }
        Log.d(TAG, "updateInterface: " + iface + " link " + (up ? "up" : "down"));

        synchronized(this) {
            mLinkUp = up;
            mNetworkInfo.setIsAvailable(up);
            if (!up) {
                // Tell the agent we're disconnected. It will call disconnect().
                mNetworkInfo.setDetailedState(DetailedState.DISCONNECTED, null, mHwAddr);
                stopIpProvisioningThreadLocked();
            }
            updateAgent();
            // set our score lower than any network could go
            // so we get dropped.  TODO - just unregister the factory
            // when link goes down.
            mFactory.setScoreFilter(up ? NETWORK_SCORE : -1);
        }
    }
```
















------------------------------
## interfaceAdded

```java

    private class InterfaceObserver extends BaseNetworkObserver {
        @Override
        public void interfaceAdded(String iface) {
            maybeTrackInterface(iface);
        }
    }

    private boolean maybeTrackInterface(String iface) {
        // If we don't already have an interface, and if this interface matches
        // our regex, start tracking it.

        if (!iface.matches(mIfaceMatch) || isTrackingInterface())
            return false;

        try {
            setInterfaceUp(iface);
        } catch (IllegalStateException e) {
            Log.e(TAG, e.toString() + " (Ethernet IF abnormal)");
            return false;
        }
        return true;
    }

    private void setInterfaceUp(String iface) {
        // Bring up the interface so we get link status indications.
        try {
            mNMService.setInterfaceUp(iface);
            String hwAddr = null;
            InterfaceConfiguration config = mNMService.getInterfaceConfig(iface);

            if (config == null) {
                Log.e(TAG, "Null iterface config for " + iface + ". Bailing out.");
                return;
            }

            synchronized (this) {
                if (!isTrackingInterface()) {
                    setInterfaceInfoLocked(iface, config.getHardwareAddress());
                    mNetworkInfo.setIsAvailable(true);
                    mNetworkInfo.setExtraInfo(mHwAddr);
                } else {
                    Log.e(TAG, "Interface unexpectedly changed from " + iface + " to " + mIface);
                    mNMService.setInterfaceDown(iface);
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error upping interface " + mIface + ": " + e);
        }
    }

```


------------------------------------------
## startMonitoring
grep **Started tracking interface** in
frameworks/opt/net/ethernet/java/com/android/server/ethernet


```java
    /**
     * Begin monitoring connectivity
     */
    public synchronized void start(Context context, Handler target) {
        // The services we use.
        IBinder b = ServiceManager.getService(Context.NETWORKMANAGEMENT_SERVICE);
        mNMService = INetworkManagementService.Stub.asInterface(b);
        mEthernetManager = (EthernetManager) context.getSystemService(Context.ETHERNET_SERVICE);

        // Interface match regex.
        mIfaceMatch = context.getResources().getString(
                com.android.internal.R.string.config_ethernet_iface_regex);

        // Create and register our NetworkFactory.
        mFactory = new LocalNetworkFactory(NETWORK_TYPE, context, target.getLooper());
        mFactory.setCapabilityFilter(mNetworkCapabilities);
        mFactory.setScoreFilter(-1); // this set high when we have an iface
        mFactory.register();

        mContext = context;

        // Start tracking interface change events.
        mInterfaceObserver = new InterfaceObserver();
        try {
            mNMService.registerObserver(mInterfaceObserver);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not register InterfaceObserver " + e);
        }


```


