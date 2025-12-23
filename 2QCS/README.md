
# QCS8550

## work - reboot issue
- [ ] Slot _a is unbootable, trying alternate slot.
- [ ] fastboot
- [ ] How to modify/flash bootloader and take effect?
- [ ] DUT-8550 跟 PC 接 Ethernet 互通.
- [ ] 如何用 adb over ethernet?
- [ ] DTBO 在哪一個 block device? partition?

* [Qualcomm Linux 1.6](https://docs.qualcomm.com/bundle/publicresource/topics/80-70022-115/qualcomm-linux-docs-home.html)
* [Kernel documentation 80-70022-3](https://docs.qualcomm.com/bundle/publicresource/topics/80-70022-3)
* [Boot documentation 80-70022-4](https://docs.qualcomm.com/doc/80-70022-4/topic/landing-page.html)


------------------------------------------------------------------------------
# RTC
Keywords:
* kernel_platform/msm-kernel/arch/arm64/boot/dts/vendor/qcom/pmk8550.dtsi
* dtbo_a

Official Kernel    
https://github.com/torvalds/linux/blame/master/drivers/rtc/rtc-pm8xxx.c


## 80-50445-63 PMK8550
PM8550 provides VREG_COIN to PMK8550

### PMIC Software Drivers

### SPMI
The PMIC arbiter hardware on the QCS8550 now includes two SPMI buses: Primary and Secondary SPMI.

RPM - Resource Power Manager


## 80-50445-64 PMIC Overview
* Linux HLOS Software Stack


## 80-50445-100
19.3 Each PMIC has its own unique SID(Slave ID), which can be used for PMIC register access.

```
vbat-thd-rtc-pon = <3600>;
```

## 80-PT831-64
Question: fail to write RTC.


```
$ hwclock -w
hwclock: ioctl 4024700a: Permission denied
```

RTC-name
```
$ cat /sys/class/rtc/rtc0/name
rtc-pm8xxx c42d000.qcom,spmi:qcom,pmk8550@0:rtc@6100
```


------------------------------------------------------------------------------
# TMP1075
Temperature sensor
- [ ] /dev/i2c4 如何跟 dts 對應 qupv3_hub_i2c5?

```
$ i2cdetect -a 4

$ i2cdump 4 0x49
```


------------------------------------------------------------------------------

# Thermal
glmark2
- [ ] 80-70022-19
- [ ] 80-70022-10A
- [ ] 若 stress-ng 跑 100% 會發生降頻。
- [ ] QPS615 到 98度.
- [ ] 高到幾度會開始降頻?


* [Thermal testing 80-70015-251](https://docs.qualcomm.com/bundle/publicresource/topics/80-70015-251/thermal_testing.html)
* 80-50430-800 Chipset System Guide
* [stress-ng](https://github.com/ColinIanKing/stress-ng)
* [NEWS-compute intensive](https://www.qualcomm.com/news/onq/2023/04/qualcomm-qcm8550-and-qcs8550-processors-for-compute-intensive-apps)


## 80-50445-35
Thermal Mitigation Software Concept Architecture

Cooling Devices in Thermal Framework for Kernel – Sysfs Node



## 80-88970-100
24.1.4 Read the thermistor
```
cat /sys/devices/virtual/thermal/...
```


## 80-70022-30
[Customize a thermal zone](https://docs.qualcomm.com/doc/80-70022-30/topic/thermalzone.html)


Below: Provides the names of thermal zones.
```
cat /sys/class/thermal/thermal_zone<x>/type
```


Reads the existing mitigation state of the cooling device
```
cat /sys/class/thermal/cooling_device<x>/cur_state
```

## 80-P9301-88
Linux Android PMIC GPIO Software
User Guide

- SPMI


------------------------------------------------------------------------------
# DTS
* See 80-50450-1

```
$ cd /sys/firmware/devicetree/base
    or
$ cd /sys/class/i2c-dev
```

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

------------------------------------------------------------------------------
# 進入 docker
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
