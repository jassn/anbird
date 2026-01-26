# 常用指令(複製/貼上)

cd qcm8550/Vendor
```
rm -rf device/qcom/kalama-kernel kernel_platform/out out/msm-kernel-kalama-consolidate
```


can0 - bring up
```
ip link set can0 up type can bitrate 1000000
```



-------------------------------------------------------------
# IM SDK
- [ ] 80-50450-1  AI/ML
- [ ] 80-70023-15B
- [ ] 80-70023-51  eSDK


$ cd qcs8550_le_1_0/LE.PRODUCT.2.1.r1/apps_proc
```
MACHINE=kalama DISTRO=qti-distro-rb-debug source poky/qti conf/set_bb_env.sh && bitbake qti-robotics-image
```




-------------------------------------------------------------
# Xdocker (monsong)


## 2026m01
- [ ] ./Xdocker_run.sh 進去後, .ssh 裏的東西會消失.


```
cd ~/Tools/Xdocker
### edit Dockerfile if necessary
```

可參考 JackLo Docker, in ~/AMD/docker





--------------------------------------------
# Camera
## camx-hal3-test
- [ ] Vendor/vendor/qcom/proprietary/



== END ==
