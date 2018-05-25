## modify from MTP

* [枚举 - Android之 MTP框架和流程分析 (3)](https://blog.csdn.net/u011279649/article/details/40950799)


----------------------------------------------------------
### workqueue, isr, interrupt  
init calls `INIT_WORK(&gi->work, android_work);`  
ISR calls `schedule_work(&gi->work);`  
* [kernel driver interrupt ISR](https://www.ptt.cc/bbs/LinuxDev/M.1489760401.A.565.html)

* [LDDP-Book 十一、中斷](http://silverfoxkkk.pixnet.net/blog/post/45000257-lddp%3A%E5%8D%81%E4%B8%80%E3%80%81%E4%B8%AD%E6%96%B7)

* [中斷處理的工作隊列機制－原來如此](http://blog.xuite.net/tzeng015/twblog/113271950-%E4%B8%AD%E6%96%B7%E8%99%95%E7%90%86%E7%9A%84%E5%B7%A5%E4%BD%9C%E9%9A%8A%E5%88%97%E6%A9%9F%E5%88%B6%EF%BC%8D%E5%8E%9F%E4%BE%86%E5%A6%82%E6%AD%A4)

* [linux中的中断处理方法](http://blog.51cto.com/11674570/1951161)

* [i.MX_Linux_Reference_Manual.pdf](file:///home/jason/Downloads/i.MX_Linux_Reference_Manual.pdf)

* [linux工作队列编程](https://blog.csdn.net/scottgly/article/details/6846824)

* [Linux 的并发可管理工作队列机制探讨](https://www.ibm.com/developerworks/cn/linux/l-cn-cncrrc-mngd-wkq/)

* [理解 linux 工作队列](https://blog.csdn.net/sinat_30545941/article/details/72871596)   
(with illustration)




----------------------------------------------------------
### UDC and gadget drivers

* [USB gadget设备驱动解析（3） 作者：华清远见](http://emb.hqyj.com/Column/Column141.htm)

* [Qseecom 8916平台的usb gadget解读（1）](https://blog.csdn.net/u013308744/article/details/52368739)




----------------------------------------------------------
