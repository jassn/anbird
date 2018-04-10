# How does ERROR_AUTHENTICATING get noticed ?

## trace ... Authentication with   

frameworks/opt/net/wifi/service/
java/com/android/server/wifi/WifiMonitor.java   

```java
private static final String AUTH_EVENT_PREFIX_STR = "Authentication with";
```
















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
