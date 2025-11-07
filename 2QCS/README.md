
# QCS8550

## DTS
* 80-50450-1

------------------------------------------------------------------------------
## build kernel driver
若修改了 kernel_platform/common/init/main.c 刪除以下目錄, 就可以看到 new log from both adb and serial console.
```
cd qcm8850
rm -rf Vendor/device/qcom/kalama-kernel
rm -rf Vendor/kernel_platform/out
rm -rf Vendor/out
```

然後用 docker build, 要等兩三個小時.
```
la_build -a all
```
但若修改了 kernel_platform/msm-kernel/drivers/usb/redriver/nb7vpq904m.c, 要如何能看到 new log?

## 80-79054-200 
- QCS8550.UBUN.1.0_Linux_Ubuntu_Software_Programming_Guide.pdf
- 3.3.3  Linux device driver     
  To add a new driver in the kernel, the workspace is in <workspace>/src/kernel-5.15/kernel_platform/msm-kernel.




== END ==
