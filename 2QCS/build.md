
# build

- [ ] How to incremental build?


# 有幾個 .config?
如果只 build kernel, 改 config 不一定會生效.    
用 CONFIG_TCG_TPM 測試.    
* .config
* drivers/char/tpm/Kconfig
* defconfig 跟 .config 用法不同!

```
Vendor/kernel_platform/out/...
Vendor/device/qcom/Kalama-kernel/.config
```

== END ==