# How does ERROR_AUTHENTICATING get noticed ?

## Trace ... Authentication with   

**wpa_supplicant_timeout** send message to upper layer.

```cpp
static void wpa_supplicant_timeout(void *eloop_ctx, void *timeout_ctx)
{
    wpa_msg(wpa_s, MSG_INFO, "Authentication with " MACSTR " timed out.",
        MAC2STR(bssid));
}
```
The remaining part of `wpa_supplicant_timeout` will change state to DISCONNECT directly, and set **locally_generated** to 1.  


---------------------------------

frameworks/opt/net/wifi/service/
java/com/android/server/wifi/WifiMonitor.java   

```java
    /* EAP authentication timeout events */
    private static final String AUTH_EVENT_PREFIX_STR = "Authentication with";
    private static final String AUTH_TIMEOUT_STR = "timed out.";
```
WifiMonitor send message **AUTHENTICATION_FAILURE_EVENT**
```java
private boolean dispatchEvent(String eventStr, String iface) {
        if (!eventStr.startsWith(EVENT_PREFIX_STR)) {
            if (eventStr.startsWith(WPS_SUCCESS_STR)) {
                sendMessage(iface, WPS_SUCCESS_EVENT);
            } else if (eventStr.startsWith(AUTH_EVENT_PREFIX_STR) &&
                    eventStr.endsWith(AUTH_TIMEOUT_STR)) {
                sendMessage(iface, AUTHENTICATION_FAILURE_EVENT);
	    } else {
                if (DBG) Log.w(TAG, "couldn't identify event type - " + eventStr);
            }
            eventLogCounter++;
            return false;
        }
}

```

Recv by SupplicantStateTracker.java  
and set **mAuthFailureInSupplicantBroadcast** to true.
```java
    class DefaultState extends State {
        @Override
        public boolean processMessage(Message message) {
            if (true) Log.d(TAG, getName() + message.toString() + "\n");
            switch (message.what) {
                case WifiMonitor.AUTHENTICATION_FAILURE_EVENT:
                    mAuthFailureInSupplicantBroadcast = true;
                    Slog.d(TAG, "jssn, mAuthFailureInSupplicantBroadcast ... true \n");
                    break;
            }
            return HANDLED;
        }
    }
```
Wait for next state change event, then call **sendSupplicantStateChangedBroadcast**

```java
case WifiMonitor.SUPPLICANT_STATE_CHANGE_EVENT:
    StateChangeResult stateChangeResult = (StateChangeResult) message.obj;
    SupplicantState state = stateChangeResult.state;

    sendSupplicantStateChangedBroadcast(state, mAuthFailureInSupplicantBroadcast);
    mAuthFailureInSupplicantBroadcast = false;
    transitionOnSupplicantStateChange(stateChangeResult);
    break;
```



----------------------------------------

NOTICE: following could be wrong.
=================================



## if comment out wpa_supplicant_mark_disassoc and wpas_event_deauth, 
reason become 6, and no ERROR_AUTH.






```java
    private void sendSupplicantStateChangedBroadcast(SupplicantState state, boolean failedAuth) {
        int supplState;

		    /* code omitted ...... */
        Intent intent = new Intent(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        intent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT
                | Intent.FLAG_RECEIVER_REPLACE_PENDING);
        intent.putExtra(WifiManager.EXTRA_NEW_STATE, (Parcelable) state);
        if (failedAuth) {
            Slog.d(TAG, "jsn, Line.205, failedAuth");
            intent.putExtra(
                WifiManager.EXTRA_SUPPLICANT_ERROR,
                WifiManager.ERROR_AUTHENTICATING);
        }
        mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
    }
```
