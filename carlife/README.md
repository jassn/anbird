## Reference

* [自娱自乐5之Linux gadget驱动4（接受发送，complete函数，setup函数）](https://blog.csdn.net/xxxxxlllllxl/article/details/10459721)

* [Android驱动之MTP框架与流程](https://blog.csdn.net/stoic163/article/details/75043934)

* [Android f_rndis 分析笔记](https://blog.csdn.net/cfy_phonex/article/details/22684005)

* [android USB绑定功能实现（framework）](https://blog.csdn.net/kondykuang/article/details/7621791)

* [Android USB Tethering的实现以及代码流程](https://blog.csdn.net/tianshiyalin/article/details/17319627)

* [Android的USB系统简单分析之一](https://www.jianshu.com/p/b267c5cedfa9)

* [Android USB 主机（Host）和配件（Accessory）](https://blog.csdn.net/kjunchen/article/details/52244617)

* [Android 使用 Usb Accessory 模式与 linux 下位机进行通信](https://blog.csdn.net/lj402159806/article/details/69940628)

* [安卓USB开发教程 <三> USB Accessory](https://blog.csdn.net/JAZZSOLDIER/article/details/73849057)





-------------------------------------------------------
## Study MTP Service

### when switch USB OTG to device mode
adb logcat will show
*starting MTP server in MTP mode*

* source code at 
packages/providers/MediaProvider/src/com/android/providers/media

```java
    /**
     * Manage {@link #mServer}, creating only when running as the current user.
     */
    private void manageServiceLocked(StorageVolume primary, String[] subdirs) {
        final boolean isCurrentUser = UserHandle.myUserId() == ActivityManager.getCurrentUser();
        if (mServer == null && isCurrentUser) {
            Log.d(TAG, "starting MTP server in " + (mPtpMode ? "PTP mode" : "MTP mode"));
            mDatabase = new MtpDatabase(this, MediaProvider.EXTERNAL_VOLUME,
                    primary.getPath(), subdirs);
            mServer = new MtpServer(mDatabase, mPtpMode);
            mDatabase.setServer(mServer);
            if (!mMtpDisabled) {
                addStorageDevicesLocked();
            }
            mServer.start();
        } 
    }
```


* [Android之 MTP框架和流程分析 (3)](https://blog.csdn.net/u011279649/article/details/40950799)   
  android_mtp_MtpServer_setup  
  uevent

* [Android USB/MTP相关实现](https://blog.csdn.net/kv110/article/details/39934319)


----------------------------------------------------------
## Create my own USB gadget driver

### usb_ep_autoconfig
- [ ] usb_ep_autoconfig is also used in f_serial.c




----------------------------------------------------------
### How to let UDC-ISR like to my MFi driver?
- [ ] Is **android_work** common for all gadget functions?
- [ ] add my driver to `func_list`.
- [ ] **usb_add_function** ?
```cpp
    list_for_each_entry(f, &cfg->func_list, list) {
        pr_info("js3n . . . %s:%d: name=%s.\n", __func__, __LINE__, f->name);
        if (f->fi == fi) {
            ret = -EEXIST;
            goto out;
        }
    }
```



-----------------------------------------------------------
### config_usb_cfg_link
- [x] **config_usb_cfg_link** is called when adding a link in filesystem.

See root/init.usb.configfs.rc   
source at system/core/rootdir/init.usb.configfs.rc

```
symlink /config/usb_gadget/g1/functions/mfi.gs6 /config/usb_gadget/g1/configs/b.1/f3
```
or 
```
  cd /config/usb_gadget/g1
  ln -s functions/mfi.gs6 configs/b.1/f3
```



-----------------------------------------------------------
### Who calls make_group?
- [ ] Who calls make_group?    
  mkdir _dir_ under **/config/usb_gadget/g1/functions** will enter **function_make**
  (kernel_imx/drivers/usb/gadget/configfs.c)
```cpp

static struct config_group *function_make(
        struct config_group *group,
        const char *name)
{
}
```


* similar to below commands in **root/init.freescale.usb.rc**    
  source at **device/fsl/imx6/etc/init.usb.rc**  
```
    mkdir /config/usb_gadget/g1/functions/ffs.adb
    mkdir /config/usb_gadget/g1/functions/mtp.gs0
    mkdir /config/usb_gadget/g1/functions/ptp.gs1
    mkdir /config/usb_gadget/g1/functions/accessory.gs2
    mkdir /config/usb_gadget/g1/functions/audio_source.gs3
    mkdir /config/usb_gadget/g1/functions/rndis.gs4
    mkdir /config/usb_gadget/g1/functions/midi.gs5
```

* [linux之configfs简介和编程入门](https://blog.csdn.net/liumangxiong/article/details/12154865)  
定义了 configfs_group_operations，这里定义了 make_group 函数，在子系统下 mkdir 就会调用这个函数。

* [Make you own USB gadget(pdf)](https://events.static.linuxfound.org/sites/events/files/slides/LinuxConNA-Make-your-own-USB-gadget-Andrzej.Pietrasiewicz.pdf)





-----------------------------------------------------------
## Tasks to do:
- [ ] Examine what is written from adbd to configfs.
- [ ] usbmon doesn't work on i.mx6 yet.
- [ ] Study T3 CarPlay to help imx6 Carlife.
- [ ] How to report endpoints to iPhone?
- [ ] rewrite adb service to another program.
- [ ] Study AOA working flow; then apply to iPhone.
- [x] adb_open, adb_write are defined as inline.
- [ ] usbmon (USB sniffer)
- [ ] Apple Tool for debug (CarPlay)
- [ ] USB VID and PID from MFi
- [x] ADB over Wi-Fi.

* [ADB over Wi-Fi TCP](https://stackoverflow.com/questions/2604727/how-can-i-connect-to-android-with-adb-over-tcp?noredirect=1&lq=1)

  
  

----------------------------------------------------------
## adb log trace

* [android系统中log机制](https://blog.csdn.net/prike/article/details/50214973)  
(6）c/c++域使用LOG


* [android usb adb流程](https://blog.csdn.net/xiaojsj111/article/details/18599653)  
  trace_mask   
  (system/core/adb) apacket   
  adb 出问题时的调试方法

----------------------------------------------------------
## android_work

* [android下usb框架系列文章---(7)android-kernel gadget框架](https://blog.csdn.net/u011279649/article/details/17420321)  
  kobject_uevent_env  
  USB_STATE_MATCH   



----------------------------------------------------------
## usbmon for debug

* [linux下类似Bus Hound的工具](https://blog.csdn.net/liuqz2009/article/details/7886461)

* [linux下usb抓包：wireshark+usbmon](https://blog.csdn.net/jfleecumt/article/details/51944457)

* [wireshark 擷取 USB 訊號](http://twlinuxnotes.blogspot.tw/2013/01/wireshark-usb.html)


----------------------------------------------------------
## usb gadget driver

* [USB gadget driver: adb](https://blog.csdn.net/u011279649/article/details/11241783)

* [分析android的usb-gadget](https://blog.csdn.net/rain0993/article/details/8461697)




----------------------------------------------------------
## Enable adb trace


* [ ADB (Android Debug Bridge)](http://imsardine.simplbug.com/note/android/adb/adb.html)  
setprop persist.adb.trace_mask 1  
cat /data/adb/adb-1970-01-01-00-00-05-224  
source code at **system/core/adb**   

./init.freescale.usb.rc
```
on boot
    write /config/usb_gadget/g1/strings/0x409/serialnumber ${ro.serialno}
    write /config/usb_gadget/g1/strings/0x409/manufacturer ${ro.product.manufacturer}
    write /config/usb_gadget/g1/strings/0x409/product ${ro.product.model}
......
on property:sys.usb.ffs.ready=1 && property:sys.usb.config=mtp,adb && property:sys.usb.configfs=1
    write /config/usb_gadget/g1/functions/mtp.gs0/os_desc/interface.MTP/compatible_id "MTP"
    write /config/usb_gadget/g1/os_desc/use 1
    write /config/usb_gadget/g1/idProduct 0x4ee2
    write /config/usb_gadget/g1/idVendor 0x18d1
```

* [Linux USB gadget configured through configfs](https://www.kernel.org/doc/Documentation/usb/gadget_configfs.txt)




### /dev/usb-ffs/adb

* [Linaro - How to enable Android ConfigFS gadgets](https://wiki.linaro.org/LMG/Kernel/AndroidConfigFSGadgets)

* [usb ffs](http://bbs.chinaunix.net/thread-4160536-1-1.html)


* [Android USB gadget](https://blog.csdn.net/u012719256/article/details/52611036)






----------------------------------------------------------
## Android_work

* [枚举 - Android之 MTP框架和流程分析 (3)](https://blog.csdn.net/u011279649/article/details/40950799)

* [USB枚举详细过程剖析](https://blog.csdn.net/u011279649/article/details/41779767)



## ADB
* [ADB模块源码分析（二）——adb server的启动](https://blog.csdn.net/xiaoyida11/article/details/51322193)

* [ADB 源码分析(一) ——ADB模块简述](http://www.apkbus.com/blog-50331-54609.html)

* [ADB 源码分析(三) —— adbd daemon](http://www.apkbus.com/blog-50331-54627.html)




## Android MTP

* [android的USB MTP](https://blog.csdn.net/zhandoushi1982/article/details/7563702)  
“composite”是此层的关键字




## Android USB

* [Series - Linux那些事儿 之 戏说USB(28)设备的生命线(十一)](https://blog.csdn.net/zhqh100/article/details/44652431)

* [Linux那些事儿 之 戏说USB(12)接口是设备的接口(一)](https://blog.csdn.net/zhqh100/article/details/44593981)  
  Configuration vs Setting  
  device file = 设备文件  
  USB_DEVICE_MAJOR  
  cat /proc/devices  

* [android usb解析（一）UsbDeviceManager(and5.1)](https://blog.csdn.net/kc58236582/article/details/47810987)



## google: android gadget serial driver

* [Linux USB gadget解析（1）](https://blog.csdn.net/a350203223/article/details/40618431)

* [Linux USB gadget解析（2）](https://blog.csdn.net/a350203223/article/details/40618457)

* [Linux USB gadget解析（3）](https://blog.csdn.net/a350203223/article/details/40618759)

* [Linux USB gadget解析（4）](https://blog.csdn.net/a350203223/article/details/40618779)


* [USB gadget设备驱动解析（1） [精华]](https://blog.csdn.net/wujiangguizhen/article/details/16863639)

* [USB gadget设备驱动解析（1）——功能体验](https://blog.csdn.net/aleon_liao/article/details/8573254)

* [usb gadget虚拟串口](https://blog.csdn.net/luckywang1103/article/details/61917916)


* [Linux Gadget的一点研究之U盘和USB虚拟串口](https://blog.csdn.net/a350203223/article/details/40618901)

* [自己对usb otg gadget的理解](https://blog.csdn.net/wujiangguizhen/article/details/17167061)


# Reference

* [連上手機的車生活，百度CarLife體驗報告](https://read01.com/0GO34R.html#.WuLoPNYRWzc)

