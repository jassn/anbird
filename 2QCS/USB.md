
# USB

## 測項
 - [x] adb 是否偵測到 GenesysLogic Chip? FW 版本正確嗎?
 - [x] 可以偵測 USB 3.2 (Sandisk)
 - [x] 接上 USB Mouse, 是否會動作? *USB2.1*
 - [ ] 異常 reboot, 解決了嗎?
 - [x] 每次都能順利開機嗎? 要長按 power button 才行.
 - [ ] yavta, 沒權限 V4L2 STREAM_ON.


# APK for Camera Test
https://f-droid.org/zh_Hant/packages/humer.uvc_camera/


------------------------------------------------------------------
# 問題
- 我有一個 USB type-C DTS file , 要如何改成 USB Type-A?
- 在高通平台上 extcon 用於 USB OTG 的實作細節

https://docs.qualcomm.com/bundle/publicresource/topics/80-88500-3/66_Configure_AI_ML_modules.html

-----------------------------------------------------------------------
## 變因
- dr_mode

## DTS
 - kernel_platform/msm-kernel/arch/arm64/boot/dts/vendor/qcom/kalamap-rb5-gen2.dtsi


-----------------------------------------------------------------------
## 2025-Nov-21
test usb camera    
camx-hal3-test, 80-42028-300 QRB5156    
build yavta, 80-50450-2 QCS8550 LE     
build yavta, 80-79054-200 QCS8550 UBUNTU     
yavta, 80-70022-8 Qualcomm Linux Interfaces Guide 1.6  
yavta, 80-88500-3



-----------------------------------------------------------------------
## 2025-Nov-20
Solve USB2.1 by fix hardware switch from SoC to GL3590 Hub.
- [x] hw-22
- [x] hw-13(24), no reboot, jack fw
- [x] hw-11
- [x] hw-10(12), reboot, jack fw
- [x] hw-8



-----------------------------------------------------------------------
## 2025-Nov-13
 - qusb_dci.c
 - [NG] la_build.sh -c boot
 - la_build.sh -b boot




------------------------------
## 2025-Nov-10
 - [x] 改了 (kalama-usb.dtsi) dr_mode, 怎樣加log 才能夠從 adb or serial log 看得出來?
 - 若沒接 micro USB to ADB HOST, 常常無法開機.
 - 問題:  若 dr_mode = "host" 會一直異常 reboot

------------------------------
## 2025-Nov-09
add tag for testing
 - kernel_platform/common/init/main.c
 - kernel_platform/msm-kernel/drivers/usb/redriver


-----------------------------------------------------------------------
# Document
## 80-35348-100
- USB Power Delivery
## SP80-PT831-4
- Build Android     

== END ==