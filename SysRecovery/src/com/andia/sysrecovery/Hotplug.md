# Hotplay and uevent

* [Android热插拔事件处理流程--Vold](https://blog.csdn.net/myarrow/article/details/8246716)

## 3. NetlinkListener::onDataAvailable

## 4. NetlinkHandler::onEvent

---------------------------------------------------
## 1.  NetlinkManager
* NetlinkManager communicate with kernel via PF_NETLINK.  
* main call NetlinkManager::start()  
_system/vold/main.cpp_

```cpp
int main(int argc, char** argv) {
    CommandListener *cl;
    CryptCommandListener *ccl;
    NetlinkManager *nm;

    if (!(nm = NetlinkManager::Instance())) {
        LOG(ERROR) << "Unable to create NetlinkManager";
        exit(1);
    }
 
    cl = new CommandListener();
    ccl = new CryptCommandListener();
    vm->setBroadcaster((SocketListener *) cl);
    nm->setBroadcaster((SocketListener *) cl);

    if (nm->start()) {
        PLOG(ERROR) << "Unable to start NetlinkManager";
        exit(1);
    }
}
```

## 2.  NetlinkManager::start()
_system/vold/NetlinkManager.cpp_

```cpp
int NetlinkManager::start() {
    if ((mSock = socket(PF_NETLINK, SOCK_DGRAM | SOCK_CLOEXEC,
            NETLINK_KOBJECT_UEVENT)) < 0) {
        SLOGE("Unable to create uevent socket: %s", strerror(errno));
        return -1;
    }
    ......
    mHandler = new NetlinkHandler(mSock);
    if (mHandler->start()) {
        SLOGE("Unable to start NetlinkHandler: %s", strerror(errno));
        goto out;
    }

    return 0;
}
```
