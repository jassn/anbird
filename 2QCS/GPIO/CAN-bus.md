# CAN bus

- [ ] can0 and can1, 因為 CAN1_SPI_MISO @884000 先被 probe, 系統指定為 can0.
- [x] cansend can0, 然後 ifconfig can0 會有一堆 ERROR, 可能是接線不正確, 導致訊號一直反射; 改用 waveshare USB dongle, 接線正確後, 還要開啟 	Windows上的接收模式, 才不會出現 errors.
- [ ] 測試 aliases in kalama.dtsi, 對 spi0 / spi1 有效, 但 can0 / can1 仍無法改變.


參考資料:
- [ ] 80-88500-1, SPI in QUP
- [ ] 80-70023-8, CAN analyzer - PCAN-USB FD
- [ ] 80-70023-251, CAN_H, MCP2518



## CAN0 - Schematic
CRB800, page 14 (SD_CAN)
* CAN0_TXD

SMC-800, page 36
* (SPI) CAN_SDO [U27]
* CAN_SPI_MISO  [U28] CAN_SDO
* [U1000J] GPIO_68,AF3 ...... CAN_SPI_MISO

Above CAN0 SPI0 (GPIO 68,69,70,71) QUP_11


## CAN1
* CAN1_TXD    
Above CAN1 (GPIO 60,61,62,63) QUP_9



------------------------------------------------------------
# Test with Waveshare Dongle
Wait for data receiving ...
```
kalama#
ip link set can0 up type can bitrate 1000000
can dumpcan0
```

Windows PC sending data ...
* Using USBCAN V2.12
* "Must be Configured --> click Open"
* 'Manually Send'


## 線路連接
CRB 電路圖 - CON1402 - 接到 DB 9

ifconfig can0
* Pin3 ... CAN1_H ... 3母
* Pin5 ... CAN1_L ... 5母

ifconfig can1
* Pin7 ... CAN0_H ... 7母
* Pin2 ... CAN0_L ... 2母


------------------------------------------------------------
# Hardware

[Waveshare Wiki](
https://www.waveshare.com/wiki/USB-CAN-A?srsltid=AfmBOoqXhCEwVydY3PUHXg53etHQigRitgezQ0kIktUBqgkHweXI96gQ
)


[祥昌 USB to CAN](
https://www.sconline.com.tw/tw/product/show.php?num=32449&page=1&kind1=&kw=can
)


[PEAK PCAN-USB FD](
https://www.peak-system.com/PCAN-USB-FD.365.0.html?&L=1)



------------------------------------------------------------
# References
* 深入解析 CAN bus     
https://hongtronics.com/article-can-and-can-fd-bus-10-common-fault/

* MCP2518FD 外部CAN-FD 的调试方法 (RK3562J)    
https://www.forlinx.com/article-new-c22/1323.html


## 80-70023-8
* candump


## 80-Y8950-108
* cansend



-------------------------------------------------------------
# Troubleshooting
## 接線與硬體常見原因

沒有正確終端電阻（兩端各 120 Ω）
* 沒有終端、只有一端有終端，或擺錯位置，會導致反射、波形劣化、錯誤率高，TX/RX errors 逐漸增加，最後 BUS‑OFF。
​

CANH / CANL 接錯或短路
* H/L 對調、CANH 或 CANL 落空、或兩線短在一起，都會造成嚴重 bit error，馬上 ERROR‑PASSIVE / BUS‑OFF。
​

Bus 上只有你一個節點在「正常模式」發送
* 標準 CAN 需要另一個節點回 ACK bit，若 bus 上沒有任何 ACK，某些控制器會把這當成錯誤狀態，重傳到 error counter 累積，進入 BUS‑OFF，ifconfig 的 TX errors 會上升。
​

接線太長、線徑不適合或雜訊太大
* 超過建議長度、用非雙絞線、靠近強干擾源（馬達、電源線），也會造成隨機 bit error，反映為 RX/TX errors 增加。
​

收發器/線材硬體故障
* 壞掉的收發器、焊接不良、接頭鬆，會造成間歇性 error，錯誤狀態在 ERROR‑ACTIVE、ERROR‑PASSIVE、BUS‑OFF 之間跳。
​


== END ==
