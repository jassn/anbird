


## trace ERROR_AUTHENTICATING



```java

    private void sendSupplicantStateChangedBroadcast(SupplicantState state, boolean failedAuth) {
        int supplState;
        switch (state) {
            case DISCONNECTED: supplState = BatteryStats.WIFI_SUPPL_STATE_DISCONNECTED; break;
            case INTERFACE_DISABLED:
                supplState = BatteryStats.WIFI_SUPPL_STATE_INTERFACE_DISABLED; break;
            case INACTIVE: supplState = BatteryStats.WIFI_SUPPL_STATE_INACTIVE; break;
            case SCANNING: supplState = BatteryStats.WIFI_SUPPL_STATE_SCANNING; break;
            case AUTHENTICATING: supplState = BatteryStats.WIFI_SUPPL_STATE_AUTHENTICATING; break;
            case ASSOCIATING: supplState = BatteryStats.WIFI_SUPPL_STATE_ASSOCIATING; break;
            case ASSOCIATED: supplState = BatteryStats.WIFI_SUPPL_STATE_ASSOCIATED; break;
            case FOUR_WAY_HANDSHAKE:
                supplState = BatteryStats.WIFI_SUPPL_STATE_FOUR_WAY_HANDSHAKE; break;
            case GROUP_HANDSHAKE: supplState = BatteryStats.WIFI_SUPPL_STATE_GROUP_HANDSHAKE; break;
            case COMPLETED: supplState = BatteryStats.WIFI_SUPPL_STATE_COMPLETED; break;
            case DORMANT: supplState = BatteryStats.WIFI_SUPPL_STATE_DORMANT; break;
            case UNINITIALIZED: supplState = BatteryStats.WIFI_SUPPL_STATE_UNINITIALIZED; break;
            case INVALID: supplState = BatteryStats.WIFI_SUPPL_STATE_INVALID; break;
            default:
                Slog.w(TAG, "Unknown supplicant state " + state);
                supplState = BatteryStats.WIFI_SUPPL_STATE_INVALID;
                break;
        }
        try {
            mBatteryStats.noteWifiSupplicantStateChanged(supplState, failedAuth);
        } catch (RemoteException e) {
            // Won't happen.
        }
        Intent intent = new Intent(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION);
        intent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT
                | Intent.FLAG_RECEIVER_REPLACE_PENDING);
        intent.putExtra(WifiManager.EXTRA_NEW_STATE, (Parcelable) state);
        if (failedAuth) {
            intent.putExtra(
                WifiManager.EXTRA_SUPPLICANT_ERROR,
                WifiManager.ERROR_AUTHENTICATING);
        }
        mContext.sendStickyBroadcastAsUser(intent, UserHandle.ALL);
    }

```
