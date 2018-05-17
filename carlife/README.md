## Tasks to do:
- [ ] How to report endpoints to iPhone?
- [x] adb_open, adb_write are defined as inline.
- [ ] usbmon (USB sniffer)
- [ ] Apple Tool for debug (CarPlay)
- [ ] USB VID and PID from MFi
- [x] ADB over Wi-Fi.

* [ADB over Wi-Fi TCP](https://stackoverflow.com/questions/2604727/how-can-i-connect-to-android-with-adb-over-tcp?noredirect=1&lq=1)

----------------------------------------------------------
## usbmon for debug

* [linux下类似Bus Hound的工具](https://blog.csdn.net/liuqz2009/article/details/7886461)

* [linux下usb抓包：wireshark+usbmon](https://blog.csdn.net/jfleecumt/article/details/51944457)

* [wireshark 擷取 USB 訊號](http://twlinuxnotes.blogspot.tw/2013/01/wireshark-usb.html)


----------------------------------------------------------
## usb gadget driver

* [USB gadget driver: adb](https://blog.csdn.net/u011279649/article/details/11241783)




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

