# System UI

## Trace log when plug in USB Storage ...
D/UsbHostManager(  508): Added device UsbDevice[mName=/dev/bus/usb/001/003,mVendorId=1921,mProductId=21911,  
mClass=0,mSubclass=0,mProtocol=0,mManufacturerName=SanDisk,mProductName=Cruzer Glide 3.0,  
mVersion=2.16,mSerialNumber=4C530001181116102423,mConfigurations=[

* The log is printed from *frameworks/base/services/usb/java/com/android/server/usb*  
* [endUsbDeviceAdded](https://github.com/jassn/droid-7.1/blob/aca7f816112c15a136764d84396852adfe62f7ff/frameworks/base/services/usb/java/com/android/server/usb/UsbHostManager.java#L224)

* [android usb解析（二）UsbHostManager(and6.0)](https://blog.csdn.net/kc58236582/article/details/54691334)

**************************
* frameworks/base/services/usb/java/com/android/server/usb/UsbService.java
* Class **UsbService**
* systemReady call monitorUsbHostBus (by thread)
* monitorUsbHostBus call into JNI.

```java
public class UsbService extends IUsbManager.Stub {

    public static class Lifecycle extends SystemService {
        private UsbService mUsbService;

        @Override
        public void onBootPhase(int phase) {
            if (phase == SystemService.PHASE_ACTIVITY_MANAGER_READY) {
                mUsbService.systemReady();
            } else if (phase == SystemService.PHASE_BOOT_COMPLETED) {
                mUsbService.bootCompleted();
            }
        }
    }
}
```

**************************
* JNI call usb_host_run.  
* HAL of UsbHostManager.
* usb_device_added is registered as usb_device_added_cb.

```cpp
static void android_server_UsbHostManager_monitorUsbHostBus(JNIEnv* /* env */, jobject thiz)
{
    struct usb_host_context* context = usb_host_init();
    if (!context) {
        ALOGE("usb_host_init failed");
        return;
    }
    // this will never return so it is safe to pass thiz directly
    usb_host_run(context, usb_device_added, usb_device_removed, NULL, (void *)thiz);
}


```

**************************
* system/core/libusbhost/usbhost.c
* **usb_host_run** call usb_host_load

```cpp
void usb_host_run(struct usb_host_context *context,
                  usb_device_added_cb added_cb,
                  usb_device_removed_cb removed_cb,
                  usb_discovery_done_cb discovery_done_cb,
                  void *client_data)
{
    int done;

    done = usb_host_load(context, added_cb, removed_cb, discovery_done_cb, client_data);

    while (!done) {

        done = usb_host_read_event(context);
    }
} /* usb_host_run() */
```


**************************
* usb_host_load call find_existing_devices
* find_existing_devices call find_existing_devices_bus
* find_existing_devices_bus call added_cb()
* added_cb is actually **usb_device_added** in JNI.

```cpp
static int find_existing_devices_bus(char *busname,
                                     usb_device_added_cb added_cb,
                                     void *client_data)
{
    char devname[32];
    DIR *devdir;
    struct dirent *de;
    int done = 0;

    devdir = opendir(busname);
    if(devdir == 0) return 0;

    while ((de = readdir(devdir)) && !done) {
        if(badname(de->d_name)) continue;

        snprintf(devname, sizeof(devname), "%s/%s", busname, de->d_name);
        done = added_cb(devname, client_data);
    } // end of devdir while
    closedir(devdir);

    return done;
}
```
**************************

* **usb_device_added** call endUsbDeviceAdded().

```cpp
static int usb_device_added(const char *devname, void* client_data) {
    /*  code omitted ... */
    env->CallVoidMethod(thiz, method_endUsbDeviceAdded);
}
```

**************************
* **endUsbDeviceAdded** is a JNI callback.
* frameworks/base/services/usb/java/com/android/server/usb/UsbHostManager.java

```java
    /* Called from JNI in monitorUsbHostBus() to finish adding a new device */
    private void endUsbDeviceAdded() {
        synchronized (mLock) {
            /*  code omitted ... */
            if (mNewDevice != null) {
                mNewDevice.setConfigurations(
                        mNewConfigurations.toArray(new UsbConfiguration[mNewConfigurations.size()]));
                mDevices.put(mNewDevice.getDeviceName(), mNewDevice);
                Slog.d(TAG, "Added device " + mNewDevice);
                getCurrentSettings().deviceAttached(mNewDevice);
                mUsbAlsaManager.usbDeviceAdded(mNewDevice);
            } else {
                Slog.e(TAG, "mNewDevice is null in endUsbDeviceAdded");
            }
            mNewDevice = null;
            mNewConfigurations = null;
            mNewInterfaces = null;
            mNewEndpoints = null;
            mNewConfiguration = null;
            mNewInterface = null;
        }
    }

```

***********************************

## System UI
* [android 6.0 SystemUI源码分析](https://blog.csdn.net/zhudaozhuan/article/details/50816086)

frameworks/base/packages/SystemUI/src/com/android/systemui/usb/StorageNotification.java
```java
public class StorageNotification extends SystemUI {
    private static final String TAG = "StorageNotification";

}
```
