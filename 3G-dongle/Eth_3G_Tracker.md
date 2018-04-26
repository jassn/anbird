# Ethernet on Android-7.1.1


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


```
