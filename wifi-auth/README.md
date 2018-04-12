


## Adding debug log for wpa_supplicant
```cpp
wpa_printf(MSG_WARNING, "debug string);
```

------------------------------------
## Download modifications to target device

### download executable for test
cd out/target/product/sabresd_6dq/system/bin/
adb remount  
adb push wpa_supplicant /system/bin  


### download executable for framework
cd out/target/product/sabresd_6dq/system/framework  
adb remount  
adb push wifi-service.jar /system/framework  


### download apk for test
adb push <Luxg...apk> /system/app


--------------------------------------
## Reboot target and running

### check log
adb reboot
adb logcat -v | grep SupplicantState

```
01-01 00:07:39.523 I/SupplicantState( 1101): ERROR_AUTHENTICATING
```



-------------------------------------

## Set timeout to 3 seconds, then hotspot of mobile phone can send ERROR_AUTHENTICATING.

_external/wpa_supplicant_8/wpa_supplicant/wpa_supplicant.c_


* wpa_supplicant_rx_eapol
* One solution is to set _timeout_ to 4 seconds or shorter as below ...

```cpp
    timeout=3;
    wpa_supplicant_req_auth_timeout(wpa_s, timeout, 0);
```


Take a look at **wpa_supplicant_rx_eapol** partly.

```cpp
void wpa_supplicant_rx_eapol(void *ctx, const u8 *src_addr,
                 const u8 *buf, size_t len)
{
    struct wpa_supplicant *wpa_s = ctx;

    wpa_dbg(wpa_s, MSG_DEBUG, "RX EAPOL from " MACSTR, MAC2STR(src_addr));
    wpa_hexdump(MSG_MSGDUMP, "RX EAPOL", buf, len);
    
    /* code omitted ...... */
    
    
    if (wpa_s->eapol_received == 0 &&
        (!(wpa_s->drv_flags & WPA_DRIVER_FLAGS_4WAY_HANDSHAKE) ||
         !wpa_key_mgmt_wpa_psk(wpa_s->key_mgmt) ||
         wpa_s->wpa_state != WPA_COMPLETED) &&
        (wpa_s->current_ssid == NULL ||
         wpa_s->current_ssid->mode != IEEE80211_MODE_IBSS)) {
        /* Timeout for completing IEEE 802.1X and WPA authentication */
        int timeout = 10;

        if (wpa_key_mgmt_wpa_ieee8021x(wpa_s->key_mgmt) ||
            wpa_s->key_mgmt == WPA_KEY_MGMT_IEEE8021X_NO_WPA ||
            wpa_s->key_mgmt == WPA_KEY_MGMT_WPS) {
            /* Use longer timeout for IEEE 802.1X/EAP */
            timeout = 70;
        }

        wpa_printf(MSG_WARNING, "js3n.3354 ........ timeout = %d", timeout);
        wpa_supplicant_req_auth_timeout(wpa_s, timeout, 0);
    }

}

```

* wpa_supplicant_req_auth_timeout

* `wpa_supplicant_timeout` callback was registered.

```cpp
void wpa_supplicant_req_auth_timeout(struct wpa_supplicant *wpa_s,
                     int sec, int usec)
{
    if (wpa_s->conf->ap_scan == 0 &&
        (wpa_s->drv_flags & WPA_DRIVER_FLAGS_WIRED))
        return;

    wpa_dbg(wpa_s, MSG_INFO, "Setting authentication timeout: %d sec "
        "%d usec", sec, usec);


    eloop_cancel_timeout(wpa_supplicant_timeout, wpa_s, NULL);
    eloop_register_timeout(sec, usec, wpa_supplicant_timeout, wpa_s, NULL);
}
```


-----------------------------------



## Trace ...... ERROR_AUTHENTICATING

_frameworks/opt/net_

```java
    private void sendSupplicantStateChangedBroadcast(SupplicantState state, boolean failedAuth) {
        int supplState;
        
        ......

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

----------------------------------

* [Android API - ERROR_AUTHENTICATING](https://developer.android.com/reference/android/net/wifi/WifiManager.html#SUPPLICANT_STATE_CHANGED_ACTION)



* [Linux Netlink基本使用](http://www.android5.online/Android/androidjc/androidkf/gykf/201603/10274.html)




#### external/libnl  

* [etlink 库 -- 官方开发者教程中文版](http://blog.guorongfei.com/2015/02/15/libnl-translation-part7/)

















