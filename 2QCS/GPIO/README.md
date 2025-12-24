
# GPIO
- [ ] /sys/class/i2c-dev 不應出現 984000(qupv3_hub_i2c1) 及 988000(qupv3_hub_i2c2).
- [ ] SP80-PT831-6, qupv3_se5_2uart
- [ ] 80-88500-1, Debug UART in kernel


----------------------------------------------------------
# 80-50445-21


----------------------------------------------------------
# 80-70022-3
## 10 pinctrl driver
10.1 Configure the GPIO usage
* Mux: function name
* Configuration: drive strength and bias property

The driver code must use generic APIs to select and register their GPIO configurations within the pinctrl configurations.

GPIO as interrupt request (IRQ)

10.3 Configure GPIOS from the user space

----------------------------------------------------------
# 80-70022-8
- [ ] How to test?
- [ ] gpiochip? where?



----------------------------------------------------------
# 80-88500-1
Qualcomm universal peripheral (QUP)
qupv3fw

## Verify QUP firmware
```
Non-HLOS/common/core_qupv3fw/kailua/qupv3fw.elf
```


----------------------------------------------------------
# 80-79054-200
## 3.3.2 Merge DTB and DTBO

## 3.3.3 Linux device driver

3.3.3.1 Add a driver

3.3.3.2 Configure the pin control

Most GPIO pins have an alternative function.
- kernel_platform/msm-kernel/drivers/pinctrl/qcom/
- **qcom/pinctrl-kalama.c** defines the pin names and groups for the QCS8550 device.
- To find an exact alternative function names
```
struct msm_function kalama_functions[] = {
    FUCNTION(gpio),
    ......
}
```
1. Find out the pin controls.
2. Define the pin control set. Each pin set structure contains mux and config subnodes.


- Sample for configuring pins on the UART


## 3.3.4 Configure defconfig

Do not modify `gki_defconfig` directly.



-------------------------------------------------------
# SP80-PT831-6 QCS6490
Linux Android Software Porting User Guide

## 5.2 UART
5.2.1 Configure UART in the boot loader

5.2.2 Configure low-speed UART in the kernel

Code modification
* Device tree - `kalama-qupv3.dtsi`
* GPIO - `kalama-pinctrl.dtsi`





-------------------------------------------------------

# Others
如果 PCAT 找不到 devices, 重新安裝 qud.
```
qpm-cli --install qud
```

* [git pull error - divergent branch](https://www.git-tower.com/learn/git/faq/you-have-divergent-branches#:~:text=The%20message%20%22You%20have%20divergent%20branches%20and,a%20merge%2C%20which%20creates%20a%20merge%20commit)


== END ==
