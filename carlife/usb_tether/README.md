## USB Tethering





## usb_ep_queue
* kernel_imx/include/linux/usb/gadget.h
```cpp
static inline int usb_ep_queue(struct usb_ep *ep,
                   struct usb_request *req, gfp_t gfp_flags)
{
    return ep->ops->queue(ep, req, gfp_flags);
}
```
* struct usb_ep_ops (kernel_imx/drivers/usb/chipidea/udc.c)
```cpp
static const struct usb_ep_ops usb_ep_ops = {
    .enable        = ep_enable,
    .disable       = ep_disable,
    .alloc_request = ep_alloc_request,
    .free_request  = ep_free_request,
    .queu2e        = ep_queue,
    .dequeue       = ep_dequeue,
};
```
* ep_queue
```cpp
static int ep_queue(struct usb_ep *ep, struct usb_request *req,
            gfp_t __maybe_unused gfp_flags)
{
}
```

--------------------------------------------------------------

* [USB gadget: mass_storage's android layer](http://www.dayexie.com/detail1824395.html)   
android_work,     
Listens for uevent messages from the kernel to monitor the USB state




--------------------------------------------------------------

* [Android USB Tethering的实现以及代码流程](https://blog.csdn.net/seuduck/article/details/11178859)

* [android下usb框架系列文章---(5)Usb setting 中tethering 设置流程](https://blog.csdn.net/u011279649/article/details/17420355)  
manager 和service有一个对应关系，固定的规则。manager是为了sdk诞生的，方便app开发者调用。其实可以直接调用service，如mountservice是没有mountmanager的。   
service是在系统起来是就被android系统启动的，而manager是后期有需要时实例化起来的。   
Service的目录在：/frameworks/base/services/java/com/android/server/   
manager的目录在：frameworks/base/core/java/android   


* [Android的USB系统简单分析之一](https://www.jianshu.com/p/b267c5cedfa9)




--------------------------------------------------------------
## Wifi tethering

* [Android8.0 WIFI ap Tethering 相关知识](https://blog.csdn.net/Aaron121314/article/details/78538852)



