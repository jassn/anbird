# USB Storage Notification

* [USB UMS MTP设置过程 （一）, 2012](https://blog.csdn.net/muojie/article/details/8315666)

---------------------------------------
## StorageManager
frameworks/base/core/java/android/os/storage/StorageManager.java

```java
    public void registerListener(StorageEventListener listener) {
        synchronized (mDelegates) {
            final StorageEventListenerDelegate delegate = new StorageEventListenerDelegate(listener,
                    mLooper);
            try {
                mMountService.registerListener(delegate);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
            mDelegates.add(delegate);
        }
    }
```

mListeners ...

```java
    public StorageManager(Context context, Looper looper) {
        mMountService = IMountService.Stub.asInterface(ServiceManager.getService("mount"));
        if (mMountService == null) {
            throw new IllegalStateException("Failed to find running mount service");
        }
    }

```


-----------------------------------------------------
# Mount Service
* [Android-7.0 Vold, mounted by kernel](https://blog.csdn.net/qq_31530015/article/details/53325101)


-----------------------------------------------------
## References
* [Android USB Host 使用详解（U盘）](https://blog.csdn.net/glouds/article/details/40260805)
