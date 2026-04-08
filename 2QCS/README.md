
# QCS8550

# QIMSDK
- [ ] LE, 如何加大 partition? 

# RS232
- [x] (2026/3/11) baud 57600 有問題...
- [ ]  How? Debug UART issues of 80-70023-8
- [ ]  io_expander, TCAL6416, 為何每次編號不一樣?
- [x] 目標: 可以看到 /dev/ttyHS*
- [x] gpioset 操作後, 為什麼 ttyHS5 不見了? (後面沒再出現問題)
- [ ] adb logcat, a88000.uart, ~/RB5/uart/jan13pm1620.
- [x] 實測 RS232, 用兩台 DUT 互傳?
- [x] 實測 RS485 / RS422

## work - reboot issue
- [ ] Slot _a is unbootable, trying alternate slot.
- [ ] fastboot
- [ ] How to modify/flash bootloader and take effect?
- [ ] DUT-8550 跟 PC 接 Ethernet 互通.
- [ ] 如何用 adb over ethernet?
- [ ] DTBO 在哪一個 block device? partition?

* [Qualcomm Linux 2.0](https://docs.qualcomm.com/doc/80-80020-115)
* [Qualcomm Linux 1.7](https://docs.qualcomm.com/doc/80-70023-115/topic/qualcomm-linux-docs-home.html)
* [Qualcomm Linux 1.6](https://docs.qualcomm.com/bundle/publicresource/topics/80-70022-115/qualcomm-linux-docs-home.html)
* [Kernel documentation 80-70022-3](https://docs.qualcomm.com/bundle/publicresource/topics/80-70022-3)
* [Boot documentation 80-70022-4](https://docs.qualcomm.com/doc/80-70022-4/topic/landing-page.html)


------------------------------------------------------------------------------
# Partition
- [ ] 80-70023-27
- [ ] 80-PT831-20

UFS Provisioning Layout
- Customers can configure the last two LUNs, 
  but they must create all partitions at the same time.
- LUN: Logical Unit Numbers.

80-80021-27
* Manage partitions in Qualcomm Linux
* Partition tool (Ptool)


------------------------------------------------------------------------
# Fastboot
From the LA release note
```
adb reboot bootloader
```

[CSDN - ](https://blog.csdn.net/weixin_29306571/article/details/158903856?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-0-158903856-blog-140344941.235^v43^pc_blog_bottom_relevance_base7&spm=1001.2101.3001.4242.1&utm_relevant_index=2)


------------------------------------------------------------------------------
# RTC
- [ ] I2C RTC, 電池也可由 PMIC 提供嗎?
- [ ] 有哪些外接 5G modem 可以用?
- [ ] /dev/rtc0 or /dev/rtc1?
- [ ] 80-ND960-1 Time Service
- [ ] persistent wall clock time.
 
Keywords:
* kernel_platform/msm-kernel/arch/arm64/boot/dts/vendor/qcom/pmk8550.dtsi
* dtbo_a

Official Kernel    
https://github.com/torvalds/linux/blame/master/drivers/rtc/rtc-pm8xxx.c

## 80-ND960-1 Time Service
RTC write is disabled in the kernel by policy.     
To maintain the persistent time across reboots, a proprietary solution is provided.
```
ANDROID_ALARM_SET_RTC
fd = open("/dev/alarm", O_RDWR);
```
As long as the files are present and contain the correct values, the system time is preserved after system reboot. See vendor/qcom/proprietary/time-services/time_daemon_qmi.c.



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


## 80-P0157-1
time_daemon




------------------------------------------------------------------------------
# TMP1075
I2C Temperature Sensor.

```
$ i2cdetect -a 4

$ i2cdump 4 0x49

$ i2cget 4 0x49 0
```


/dev/i2c4 如何跟 DTS 對應 qupv3_hub_i2c5?

```
ls -al /sys/class/i2c-dev

.../devices/platform/soc/9c0000.qcom,qupv3_i2c_geni_se/994000.i2c/i2c-4/i2c-dev/i2c-4
```
然後到 DTS directory, grep `994000`:
```
./kalama-qupv3.dtsi:  qupv3_hub_i2c5: i2c@994000 {
```






------------------------------------------------------------------------------

# Thermal
glmark2 (for Linux)
- [ ] 80-70022-19
- [ ] 80-70022-10A
- [ ] 若 stress-ng 跑 100% 會發生降頻。
- [ ] QPS615 到 98度.
- [ ] 高到幾度會開始降頻?


## LMH (80-88500-4)    
- [Thermal core framework](https://docs.qualcomm.com/doc/80-88500-4/topic/54_Thermal_core_framework.html)
- 

The thermal core emergency frequency mitigation is handled by limits management hardware (LMH).


```
adb shell "cd /data/; nohup stress-ng --cpu 0 --cpu-method=all -vm 4 --vm-bytes 75% --hdd 3 --hdd-bytes 15G -t 172800 &"
```


* [Thermal testing 80-70015-251](https://docs.qualcomm.com/bundle/publicresource/topics/80-70015-251/thermal_testing.html)
* 80-50430-800 Chipset System Guide
* [stress-ng](https://github.com/ColinIanKing/stress-ng)
* [NEWS-compute intensive](https://www.qualcomm.com/news/onq/2023/04/qualcomm-qcm8550-and-qcs8550-processors-for-compute-intensive-apps)


## HDMI Resolution
Let icons look smaller.
```
adb shell wm density 200
```


## 80-50445-35
Thermal Mitigation Software Concept Architecture

Cooling Devices in Thermal Framework for Kernel – Sysfs Node



## 80-88970-100
24.1.4 Read the thermistor
```
cat /sys/devices/virtual/thermal/...
```


## 80-70022-10



### Configure GPU
Watch GPU usage:
```
cat /sys/class/kgsl/kgsl-3d0/gpu_busy_percentage
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

Regulate temperature with fan controller
* PWM fan controller


## 80-P9301-88
Linux Android PMIC GPIO Software
User Guide

- SPMI
- PWM


------------------------------------------------------------------------------
# DTS
* See 80-50450-1

```
$ cd /sys/firmware/devicetree/base
    or
$ cd /sys/class/i2c-dev
```

## fixed-regulator    
https://wiki.st.com/stm32mpu/wiki/Regulator_overview

https://elixir.bootlin.com/linux/v6.18.3/source/Documentation/devicetree/bindings/regulator/fixed-regulator.yaml


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
