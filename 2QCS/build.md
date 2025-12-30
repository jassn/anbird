
# build

- [ ] How to incremental build?
- [ ] 改 config 後, build all vs build kernel 有何差別?


# 有幾個 .config?
如果只 build kernel, 改 config 不一定會生效.    
用 CONFIG_TCG_TPM 測試.    
* .config
* drivers/char/tpm/Kconfig
* defconfig 跟 .config 用法不同!

Only build kernel, 可能不會改到下列檔案...    
這些檔案, 也不在 git server 上.


```
Vendor/kernel_platform/.config
Vendor/kernel_platform/msm-kernel/arch/arm64/configs/vendor/kalama-gki_defconfig
```

and (need verify...)
```
Vendor/kernel_platform/out/...
Vendor/device/qcom/Kalama-kernel/.config
```

== END ==