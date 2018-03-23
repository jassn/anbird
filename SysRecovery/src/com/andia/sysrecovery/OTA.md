

### Android OTA升级原理和流程分析

* [Android编译系统详解 - cloudchou 2014/02/07](http://www.cloudchou.com/android/post-276.html)

* [Android系统的三种启动模式](http://blog.csdn.net/ylyuanlu/article/details/44457691)

###
* [make otapackage - voidcn](http://www.voidcn.com/article/p-uwwrguaj-tv.html)

###
* [深入理解：Android 編譯系統](https://read01.com/jjKzL.html#.WrN9zh9fizc)

* [深入理解：Android 編譯系統 - CSDN](http://blog.csdn.net/huangyabin001/article/details/36383031)

* [Android7.0 编译系统流程分析](https://blog.csdn.net/lizekun2010/article/details/52598105)

* [Android Recovery OTA升级（一）—— make otapackage](http://blog.csdn.net/wzy_1988/article/details/47056035#writefullotapackage)




### To generate incremental OTA from 0319 to 0320

jason@MSI-PE62-7RE:Antec_imx6dl$ build/tools/releasetools/ota_from_target_files.py -i ~/Pictures/dist-0319/sabresd_6dq-target_files-20180319.zip ~/Pictures/dist-0320/sabresd_6dq-target_files-20180320.zip ~/Pictures/sabresd_6dq-incre-20180320.zip

unzipping target target-files...
(using device-specific extensions from target_files)
unzipping source target-files...
loaded device-specific extensions from /tmp/targetfiles-54RoFH/META/releasetools.py
Loading target...
Loading source...
4 diffs to compute
    2.29 sec   162137 /   163247 bytes ( 99.32%) system/recovery-from-boot.p
    2.57 sec      252 /      572 bytes ( 44.06%) system/bin/install-recovery.sh
    2.59 sec      282 /     2520 bytes ( 11.19%) system/build.prop
    2.59 sec      210 /    63888 bytes (  0.33%) system/lib/lib_vpu_wrapper.so
using prebuilt boot.img from IMAGES...
using prebuilt boot.img from IMAGES...
using prebuilt recovery.img from IMAGES...
using prebuilt recovery.img from IMAGES...
boot      target: 10487808  source: 10487808  diff: 774
boot image changed; including.
recovery image changed; including as patch from boot.
done.




