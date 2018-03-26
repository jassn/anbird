# SD Mount on Android
* [Android SDCard Mount流程分析 - gangyanliang, old version](https://blog.csdn.net/gangyanliang/article/details/8254478)


### device node (target device)
$ ls -al /dev/block/vold  
brw------- 1 root root 8,   0 1970-01-01 00:00 disk:8,0  
brw------- 1 root root 8,   1 1970-01-01 00:00 public:8:1  

***

### USB Serial on Linux
* [Linux的USB-Serial驱动 - gangyanliang](https://blog.csdn.net/gangyanliang/article/details/8276978)  

#### Linux系统初始化阶段
1. code in (kernel)/drivers/usb/core/usb.c
```c
    static int __init usb_init(void)
    {
        retval = bus_register(&usb_bus_type);
    }    
```
