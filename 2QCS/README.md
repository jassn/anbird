
# QCS8550

## DTS
* 80-50450-1

## build kernel driver
若修改了 kernel_platform/common/init/main.c 刪除以下目錄, 就可以看到 new log from both adb and serial console.
```
rm -rf Vendor/device/qcom/kalama-kernel
```

但若修改了 kernel_platform/msm-kernel/drivers/usb/redriver/nb7vpq904m.c, 要如何能看到 new log?

== END ==
