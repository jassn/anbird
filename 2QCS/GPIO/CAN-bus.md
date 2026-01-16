# CAN bus

- [ ] can0 and can1 有接反嗎?
- [ ] cansend can0, 然後 ifconfig can0 會有一堆 ERROR
- [ ] 測試 aliases in kalama.dtsi


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
# References
* 深入解析 CAN bus     
https://hongtronics.com/article-can-and-can-fd-bus-10-common-fault/


# 80-70023-8
* candump


# 80-Y8950-108
* cansend




== END ==
