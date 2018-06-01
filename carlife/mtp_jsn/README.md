
## How to add interface string to EA Native Transport?

   type        | NCM                  | MTP                | MFI
-------------- | -------------------- | -------------------|---------------
 *usb_string*  |  ncm_string_defs     |   mtp_string_defs  | mfi_string_defs
 struct usb_interface_descriptor | ncm_control_intf | - | mfi_data_run_intf


* common functions:
  **usb_gstrings_attach**
```cpp
  struct usb_string *usb_gstrings_attach(struct usb_composite_dev *cdev,
        struct usb_gadget_strings **sp, unsigned n_strings)
```

* common functions:
  **usb_ep_autoconfig**
```cpp
int usb_assign_descriptors(struct usb_function *f,
        struct usb_descriptor_header **fs,
        struct usb_descriptor_header **hs,
        struct usb_descriptor_header **ss)
```

* Test on Ubuntu   
  lsusb -v -d 18d1:

- [ ] How to change **bInterfaceNumber**?  

* [NCM - ARM Keil](https://www.keil.com/pack/doc/mw/USB/html/_c_d_c.html)

----------------------------------------------------------
- [x] How to send two USB interfaces?

* see f_ncm.c for `.bAlternateSetting`.  
* common functions:    
  **usb_interface_id**.   
  **usb_assign_descriptors**   

----------------------------------------------------------


   pp   | NCM                  | MTP                   | MFI
------- | -------------------- | ----------------------|---------------
  A1    | ncm_bind             |  mtp_function_bind    | mfi_function_bind
  A2    |   struct f_ncm *ncm  | struct mtp_dev  *dev  | struct mtp_dev  *dev
  A3    | ncm_iad_desc.        | **mtp_interface_desc**    | mfi_data_run_intf.
  A4    | bFirstInterface      | bInterfaceNumber      | bInterfaceNumber 


* [iAP2 USB support with iMX6 Nitrogen](https://community.nxp.com/thread/341689)

* [USB On-The-Go (OTG)](http://trac.gateworks.com/wiki/linux/OTG)




----------------------------------------------------------
## USB Packet Header

```cpp
static struct usb_interface_descriptor mtp_interface_desc = {
    .bLength                = USB_DT_INTERFACE_SIZE,
    .bDescriptorType        = USB_DT_INTERFACE,
    .bInterfaceNumber       = 0,
    .bNumEndpoints          = 3,
    .bInterfaceClass        = 0x51,
    .bInterfaceSubClass     = 0x52,
    .bInterfaceProtocol     = 0,
};
```



----------------------------------------------------------
## USB Packet String  

* ?? probe_device_descriptor ?? in external/libmtp?
* kernel_imx/drivers/usb/gadget/function/f_mtp.c   
  Change "MTP" to other strings, then examine USB packets

```cpp
static struct usb_string mtp_string_defs[] = {
    /* Naming interface "MTP" so libmtp will recognize us */
    [INTERFACE_STRING_INDEX].s  = "MTP",
    {  },   /* end of list */
};
```

----------------------------------------------------------


## Who send USB_CONFIGURED ?

* [MTP协议开发入门](https://blog.csdn.net/coroutines/article/details/44341417)   
  lsusb -v -d 18d1:




----------------------------------------------------------

## modify from MTP

- [x] How to receive broadcast from **android_work**?  
      `mFilter.addAction("android.hardware.usb.action.USB_STATE");`

* USB_FUNCTION_MTP  
  _frameworks/base/core/java/android/hardware/usb/UsbManager.java_   
  _frameworks/base/services/usb_

* packages/providers/MediaProvider/src/com/android/providers/media

* [枚举 - Android之 MTP框架和流程分析 (3)](https://blog.csdn.net/u011279649/article/details/40950799)

* [Android系统中MTP的一些相关知识](http://www.cnblogs.com/skywang12345/p/3474206.html)

* [『BroadcastReceiver』- 廣播接收器的基本用法 by 賽肥膩膩](https://xnfood.com.tw/android-broadcastreceiver/)


----------------------------------------------------------
### Android Service Activity 通信
* [Android Service与Activity之间通信的几种方式](https://blog.csdn.net/xiaanming/article/details/9750689)





----------------------------------------------------------
### porting of adb, usb gadgets

* [Adb移植（一）简单分析](https://blog.csdn.net/mirkerson/article/details/32306955)

* [ADB模块源码分析（二）——adb server的启动](http://www.apkbus.com/blog-50331-54621.html)

* [ADB模块源码分析（二）——adb server的启动](https://blog.csdn.net/xiaoyida11/article/details/51322193)   
(with illustration)

* [Linux USB Gadget 实现我们自己的ADB，Linux下高级调试功能（一）](https://blog.csdn.net/ShuoWangLiangXian/article/details/38363151)

* [Linux-USB Gadget : Part 4: 最简单的 gadget驱动：g_zero](https://blog.csdn.net/zjujoe/article/details/2675095)







----------------------------------------------------------
### libusbg, configfs and usb gadget

* [怎样配置android configfs gadgets](https://blog.csdn.net/csdn66_2016/article/details/79614807)

* [PATCH 30 usb/gadget: the start of the configfs interface](https://www.spinics.net/lists/linux-usb/msg76388.html)

* [Kernel USB Gadget Configfs Interface](https://events.static.linuxfound.org/sites/events/files/slides/USB%20Gadget%20Configfs%20API_0.pdf)

* [Android O新增的关于usb的三个属性 sys.usb.configfs，sys.usb.ffs.ready，sys.usb.ffs.mtp.ready](https://blog.csdn.net/u014135607/article/details/80011192)

* [usb gadget usb host数据传输](https://blog.csdn.net/weixin_38123672/article/details/75126375)







----------------------------------------------------------
### workqueue and usb gadget

* [android usb 分析笔记](https://blog.csdn.net/cfy_phonex/article/details/22654439)

* [Android4.0 USB掛載內核驅動層流程分析（一）](https://www.dayexie.com/detail652694.html)



----------------------------------------------------------
### workqueue, isr, interrupt  
init calls `INIT_WORK(&gi->work, android_work);`  
ISR calls `schedule_work(&gi->work);`  
* [kernel driver interrupt ISR](https://www.ptt.cc/bbs/LinuxDev/M.1489760401.A.565.html)

* [LDDP-Book 十一、中斷](http://silverfoxkkk.pixnet.net/blog/post/45000257-lddp%3A%E5%8D%81%E4%B8%80%E3%80%81%E4%B8%AD%E6%96%B7)

* [中斷處理的工作隊列機制－原來如此](http://blog.xuite.net/tzeng015/twblog/113271950-%E4%B8%AD%E6%96%B7%E8%99%95%E7%90%86%E7%9A%84%E5%B7%A5%E4%BD%9C%E9%9A%8A%E5%88%97%E6%A9%9F%E5%88%B6%EF%BC%8D%E5%8E%9F%E4%BE%86%E5%A6%82%E6%AD%A4)

* [linux中的中断处理方法](http://blog.51cto.com/11674570/1951161)

* [i.MX_Linux_Reference_Manual.pdf](file:///home/jason/Downloads/i.MX_Linux_Reference_Manual.pdf)

* [linux工作队列编程](https://blog.csdn.net/scottgly/article/details/6846824)

* [Linux 的并发可管理工作队列机制探讨](https://www.ibm.com/developerworks/cn/linux/l-cn-cncrrc-mngd-wkq/)

* [理解 linux 工作队列](https://blog.csdn.net/sinat_30545941/article/details/72871596)   
(with illustration)

* [linux中断下半部分——工作队列](https://blog.csdn.net/u013686805/article/details/21003329)






----------------------------------------------------------
### UDC and gadget drivers

* [USB gadget设备驱动解析（3） 作者：华清远见](http://emb.hqyj.com/Column/Column141.htm)

* [Qseecom 8916平台的usb gadget解读（1）](https://blog.csdn.net/u013308744/article/details/52368739)

* [Android USB驱动源码分析（-）](https://blog.csdn.net/weijory/article/details/75500697)



----------------------------------------------------------
