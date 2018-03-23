

### Android OTA升级原理和流程分析

* [Android系统的三种启动模式](http://blog.csdn.net/ylyuanlu/article/details/44457691)

###
* [make otapackage - voidcn](http://www.voidcn.com/article/p-uwwrguaj-tv.html)


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




