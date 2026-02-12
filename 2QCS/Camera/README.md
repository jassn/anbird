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
- [ ] Steps to build QIM SDK for QCS8550
- [ ] 80-63942-1  export PREBUILT_SRC_DIR="<APPS_ROOT>/prebuilt_HY11"
- [ ] RNO-251225223408_REV_1_00100.1_Release_Note_for_QCS8550.LE.1.0.pdf


## bitbake (80-70023-51)
$ cd qcs8550_le_1_0/LE.PRODUCT.2.1.r1/apps_proc

a. Set up the build environment:
```
export PREBUILT_SRC_DIR="$(pwd)/prebuilt_HY11"
```
then
```
MACHINE=kalama DISTRO=qti-distro-rb-debug source setup-environment
```

b. Run the bitbake command to generate the eSDK:
```
bitbake -fc populate_sdk_ext qti-robotics-image
```

c. Locate the generated eSDK:
```
.../apps_proc/build-qti-distro-rb-debug/tmp-glibc/deploy/sdk
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
