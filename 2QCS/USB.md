
# USB

## 測項
 - [x] 是否偵測到 GenesysLogic Chip? FW 版本正確嗎?
 - [ ] 接上 USB Mouse, 是否會動作?

-----------------------------------------------------------------------
## 變因
- dr_mode

## DTS
 - kernel_platform/msm-kernel/arch/arm64/boot/dts/vendor/qcom/kalamap-rb5-gen2.dtsi

------------------------------
## 2025-Nov-10
 - [x] 改了 (kalama-usb.dtsi) dr_mode, 怎樣加log 才能夠從 adb or serial log 看得出來?
 - [ ] 

 - 若沒接 micro USB to HOST, 常常無法開機.
 - 問題:  若 dr_mode = "host" 會一直異常 reboot

------------------------------
## 2025-Nov-09
add tag for testing
 - kernel_platform/common/init/main.c
 - kernel_platform/msm-kernel/drivers/usb/redriver


 == END ==