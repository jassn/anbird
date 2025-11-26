
# QCS8550

## work - reboot issue
- [ ] Slot _a is unbootable, trying alternate slot.


------------------------------------------------------------------------------
## DTS
* See 80-50450-1


------------------------------------------------------------------------------
## modify dr_mode = "host"
Path: kernel_platform/qcom/proprietary/devicetree/qcom/kalama-usb.dtsi


## build kernel driver
* 若修改了 kernel_platform/common/init/main.c
* 刪除以下目錄, 再下指令 build code, 就可以看到 new log from both adb and serial console.
```
cd qcm8850
rm -rf Vendor/device/qcom/kalama-kernel
rm -rf Vendor/kernel_platform/out
rm -rf Vendor/out/msm-kernel-kalama-consolidate
```
* 進入 docker
```
docker_run.sh -s
```

* 然後用 docker build, 
```
/dev_bin/build_tool/la_build.sh -b all
```
如果只修改 driver code, 大約要等一個小時,
如果有修改 DTS, 可能要等兩三個小時.

(已測試下列檔案...)   
- kernel_platform/msm-kernel/drivers/usb/redriver/nb7vpq904m.c


------------------------------------------------------------------------------
## 80-79054-200 
- QCS8550.UBUN.1.0_Linux_Ubuntu_Software_Programming_Guide.pdf
- 3.3.3  Linux device driver     
  To add a new driver in the kernel, the workspace is in <workspace>/src/kernel-5.15/kernel_platform/msm-kernel.

## 80-88500-1
- 2.5 USB Hub


== END ==
