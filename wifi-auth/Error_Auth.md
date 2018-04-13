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


*wpa_msg* call wpa_msg_cb, which is `wpa_supplicant_ctrl_iface_msg_cb`.   

```cpp
/*  wpa_supplicant_8/src/utils/wpa_debug.c  */
void wpa_msg(void *ctx, int level, const char *fmt, ...)
{
    if (wpa_msg_cb)
        wpa_msg_cb(ctx, level, WPA_MSG_PER_INTERFACE, buf, len);
}

static void wpa_supplicant_ctrl_iface_msg_cb(void *ctx, int level,
                         enum wpa_msg_type type,
                         const char *txt, size_t len)
{

            wpa_supplicant_ctrl_iface_send(
                wpa_s,
                type != WPA_MSG_PER_INTERFACE ?
                NULL : wpa_s->ifname,
                gpriv->sock, &gpriv->ctrl_dst, level,
                txt, len, NULL, gpriv);

	/*  ......  or  ......  **/

            wpa_supplicant_ctrl_iface_send(wpa_s, NULL, priv->sock,
                               &priv->ctrl_dst, level,
                               txt, len, priv, NULL);

}

```




then
```cpp
static void wpa_supplicant_ctrl_iface_send(struct wpa_supplicant *wpa_s,
                       const char *ifname, int sock,
                       struct dl_list *ctrl_dst,
                       int level, const char *buf,
                       size_t len,
                       struct ctrl_iface_priv *priv,
                       struct ctrl_iface_global_priv *gp)
{
}
```


---------------------------------

## Java Framework

frameworks/opt/net/wifi/service/
java/com/android/server/wifi/WifiMonitor.java   

```java
    /* EAP authentication timeout events */
    private static final String AUTH_EVENT_PREFIX_STR = "Authentication with";
    private static final String AUTH_TIMEOUT_STR = "timed out.";
```



MonitorThread::run() call **dispatchEvent**

```java
    private class MonitorThread extends Thread {
        public void run() {
            //noinspection InfiniteLoopStatement
            for (;;) {
                String eventStr = mWifiNative.waitForEvent();

                if (dispatchEvent(eventStr)) {
                    if (DBG) Log.d(TAG, "Disconnecting from the supplicant, no more events");
                    break;
                }
            }
        }
    }

```


WifiMonitor send message **AUTHENTICATION_FAILURE_EVENT**

```java
    private synchronized boolean dispatchEvent(String eventStr) {
        String iface;
        // IFNAME=wlan0 ANQP-QUERY-DONE addr=18:cf:5e:26:a4:88 result=SUCCESS
        if (eventStr.startsWith("IFNAME=")) {
            int space = eventStr.indexOf(' ');
            if (space != -1) {
                iface = eventStr.substring(7, space);
                eventStr = eventStr.substring(space + 1);
            }
        }

        if (dispatchEvent(eventStr, iface)) {
            mConnected = false;
            return true;
        }
        return false;
    }

```


Dispatching event to interface

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
