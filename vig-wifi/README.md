# source 

```cpp
static void wpa_supplicant_event_disassoc(struct wpa_supplicant *wpa_s,
                      u16 reason_code,
                      int locally_generated)
{
    ......
    if (!is_zero_ether_addr(bssid) ||
        wpa_s->wpa_state >= WPA_AUTHENTICATING) {
        wpa_msg(wpa_s, MSG_INFO, WPA_EVENT_DISCONNECTED "bssid=" MACSTR
            " reason=%d%s",
            MAC2STR(bssid), reason_code,
            locally_generated ? " locally_generated=1" : "");
    }
}
```

# Error log

04-27 07:51:39.085 I/wpa_supplicant(  728): wlan0: CTRL-EVENT-DISCONNECTED bssid=10:d0:7a:be:47:37 reason=0
04-27 07:51:39.130 D/WifiNative-HAL(  510): stopRssiMonitoring, cmdId 0



