# clipboard

* should search all occurence of   
eloop_register_timeout(sec, usec, wpa_supplicant_timeout, wpa_s, NULL);  
and   
eloop_cancel_timeout(wpa_supplicant_timeout, wpa_s, NULL);  

------------------------------------------
Other Todo
* re-compile gnome terminal to be indep applications.

* Geany - how to search by F3, F4?



------------------------------------------
# TODO List

* Why cannot build wpa_supplicant using mm as a single module?

* What's the details of kernel sending uevent to netlink?

* What's the detals from wpa_supplicant to java code?

* How to enable more debug messages?

- see src/utils/wpa_debug.c

```cpp
/*  wpa_supplicant/main.c  */
int main(int argc, char *argv[])
{
	params.wpa_debug_level = MSG_DEBUG;
}

```

