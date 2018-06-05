### Notice:
Modification before building project and otapackage.   

* device/fsl/sabresd_6dq/build_id.mk
```
export BUILD_NUMBER=20170227
```

* device/fsl/sabresd_6dq/BoardConfig.mk
```
TARGET_BOARD_DTS_CONFIG := imx6q:imx6q-sabresd.dtb
```

---------------------------------------------------------------
### Tips
* It'll be convenient to prepare SysRecovery.apk to USB Storage.


---------------------------------------------------------------
### Trace
* **BUILT_TARGET_FILES_PACKAGE** in build/core/Makefile
```
out/target/product/sabresd_6dq/obj/PACKAGING/target_files_intermediates/sabresd_6dq-target_files.zip
```



* [wandbord bsp and freescale official bsp](http://highaltitudeoolong.blogspot.com/2015/12/)   
  TARGET_BOARD_DTS_CONFIG

* [Android Recovery OTA升级（一）—— make otapackage](https://www.cnblogs.com/cxchanpin/p/7020315.html)    
  BUILT_TARGET_FILES_PACKAGE   
  INTERNAL_OTA_PACKAGE_TARGET

* [Android Recovery OTA升级（一）—— make otapackage](https://blog.csdn.net/wzy_1988/article/details/47056035)

* [Android OTA升级（一）之Makefile文件分析](https://blog.csdn.net/wanshilun/article/details/77852486)
  Illustration
  
* [Android OTA系统升级---原理三](https://blog.csdn.net/darwinlong/article/details/78795912)

* [Android下编译OTA升级包](https://blog.csdn.net/llping2011/article/details/9471913)




---------------------------------------------------------------

### How to test
1. no USB inserted, then start app.
2. use different USB disks.
3. verify the version number after OTA update.



---------------------------------------------------------------
### Android API
* [RecoverySystem API](https://developer.android.com/reference/android/os/RecoverySystem.html)



### Task List
- [x] Use any USB Disks for update.
- [ ] why cannot enter RECOVERY MODE using "adb reboot recovery"??
- [ ] What happens if supplying an incorrect file?




### Incremental update
* [Android OTA升级原理](http://blog.csdn.net/ylyuanlu/article/details/44457691)

* ./build.sh otapackage  

* cd out/target/product/sabresd_6dq/obj/PACKAGING/target_files_intermediates


* build/tools/releasetools/ota_from_target_files.py \
-i sabresd_6dq-target_files-20180317.zip \
sabresd_6dq-target_files-20180318.zip \
incre-ota.zip




### code from i.MX






### Reference
* [Slide Share - Android OTA updates](https://www.slideshare.net/gibsson/android-ota-updates)


* packages/apps/fsl_imx_demo/FSLOta=1;	



* [Android OTA升级原理和流程分析](http://blog.csdn.net/ylyuanlu/article/details/44457605)


* [Android 5.x OTA Update官方文檔](https://read01.com/zh-tw/yy7ny2.html#.Wqs6px9fizc)

* [Android Bootloader - Main system - Recovery](http://blog.csdn.net/llping2011/article/details/9499029)

* [Android进入Recovery模式流程分析](http://ljgabc.github.io/2014/11/14/2014-11-14-Android%E8%BF%9B%E5%85%A5Recovery%E6%A8%A1%E5%BC%8F%E6%B5%81%E7%A8%8B%E5%88%86%E6%9E%90/)


* [Android Recovery升级原理](https://blog.csdn.net/q1183345443/article/details/78042785)


-----------------------------------------------------
### git usage

* [Git Checkout](https://www.atlassian.com/git/tutorials/using-branches/git-checkout)

* [Git Branch 的操作與基本工作流程](https://gogojimmy.net/2012/01/21/how-to-use-git-2-basic-usage-and-worflow/)



-----------------------------------------------------
### Others

* [Using SWUpdate to upgrade your system](https://boundarydevices.com/using-swupdate-upgrade-system/)



