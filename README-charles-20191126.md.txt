 README.md
Prepare for build
Download ti-uim-master.tar.gz from http://10.140.96.8/T-BOX/mpu-imx6solo/uploads/588bb67e1d1072bd45899fd940b03831/ti-uim-master.tar.gz
move it to buildroot_dl directory
How to build
Totally build
cd buildroot
make liteon_imx6_defconfig
make
Build uboot only
cd buildroot
make uboot-rebuild
make
Build kernel&dts only
cd buildroot
make linux-rebuild
make
Build package only
cd buildroot
make <package>-rebuild
make
How to build WL1807 wifi driver
When you modify the kernel config file, you may have to recompile the WL1807 wifi driver, otherwise the WL1807 wifi driver load may not be successful, mistakes like ¡G wlcore_sdio: Unknown symbol sdio_memcpy_toio (err -22)

reference doc: http://processors.wiki.ti.com/index.php/WL18xx_System_Build_Scripts#Troubleshoot

clone build-utilites.git
modify setup-env
./build_wl18xx.sh init
you have to download package from ti git server manually, if you can not access ti git server by git@//

build kernel zImage and modules first

copy kernel directory modules.order and modules.builtin to fs/lib/modules/3.14.52-1.1.0_ga/ directory

./build_wl18xx.sh update R8.7_SP3

overite rootfs-overlay modules

T-BOX Packages and API
RILD Package
rild pakcage path: ==mpu-imx6solo/buildroot/rild directory==.

RILD interact with modem uart port by AT commands.

RILD package consists of three parts: rild daemon process¡Blibrildapi.so library¡B monitor_datacall application.

rild daemon process
rild daemon process is launched by startup script.

rild interact with modem by /dev/ttyUSB2 port

rild interact with librildapi.so by zmq REQ and zmq PUB socket.

rild use protobuf as commnication protocal, proto file is defined by ~/mpu-imx6solo/buildroot/rild/librild/inc/rild_interface.proto

librildapi.so library
Application should use librildapi.so to visit rild service.

librildapi.so api function is defined in

~/mpu-imx6solo/buildroot/rild/librild/inc/rild_api.h

typedef struct
{
	void (*onCallRing)(void *arg);  // called when an incoming call
	void (*onNewSms)(char *phone_num, char *scts, char *sms);  // called when new message come
}RIL_CallBacks;

/* connect rild service, the first function should be called */
int RIL_register(RIL_CallBacks *callbacks);

/* disconnect rild service */
int unregister_ril(void);

/* poweron modem */
int set_radio_power(int onoff);

int get_sim_status(void);

int get_iccid(char *iccid);

int get_imsi(char *imsi);

int get_imei(char *imei);

int set_phone_num(char *phone_num);

int get_phone_num(char *phone_num);

int get_signal_strength(int *rssi, int *ber);

int request_dial(char *phone_number);

int request_send_sms(char *phone_num, char *sms);

int setup_data_call(void);

int deactive_data_call(void);
monitor_datacall process
monitor_datacall process monitor internet connection status. If connection is broken, monitor_datacall will deactive previous datacall, and try to resetup data call.

build command
cd ~/mpu-imx6solo/buildroot
make liteon_imx6_defconfig
make rild-dirclean
make rild-rebuild
MCU_PROXY package
MCU Proxy is responsible for communicating with MCU, mapping the MCU function to MPU.

MCU Proxy package is located in ~/mpu-imx6solo/buildroot/mcu_proxy directory

There are peripherals such as ADC, GSENSOR and CAN on MCU, and the functions provided by MCU Proxy are related to these peripherals.

The MCU Proxy function consists of two parts. mcu_proxyd is the service process.

mcuproxyd is launched automatically in the startup script.

libmcuproxy.so is a dynamic library for App to access mcu_proxyd.

libmcuproxy.so API
#define EVENT_UNKNOWN  0
#define EVENT_SOS  1
#define EVENT_GENSOR  2
#define EVENT_CAN  3
#define EVENT_MODEM 4
#define EVENT_ACCON 5
#define EVENT_ACCOFF 6
#define EVENT_GSENSOR 7  // every 50 microseconds

typedef struct
{
	void (*onRecvEvent)(int event_id,  void *event_arg);
} McuProxyCallBacks;


/* BCD format*/
typedef struct
{
	unsigned char year;  //2018¦~? 0x18
	unsigned char month; //0x05
	unsigned char day;   //0x16
	unsigned char hour;  //0x17
	unsigned char minute; //0x30
	unsigned char second; //0x40
} RtcDateTime;

typedef struct
{
	unsigned short xx;  //2018¦~? 0x12
	unsigned short xy;
	unsigned short xz;
	unsigned short gx;
	unsigned short gy;
	unsigned short gz;
} GsensorData;



int connect_mcu_proxy(McuProxyCallBacks *call_backs);
int close_mcu_proxy(void);
int read_battery_voltage(unsigned int *voltage);
int read_backup_battery_voltage(unsigned int *voltage);
int get_date_time(RtcDateTime *datatime);
int set_date_time(RtcDateTime *datatime);

/*
0: Normal mode
1: sleep1 mode
2: sleep2 mode
3: shutdown mode
*/
int enter_sleep_mode(int mode);
int close_mcu_can(void);


/* PIN20 of 28PIN connector  */
int read_adc1_voltage(unsigned int *voltage);

/*PIN23 of 28PIN connector*/
int read_adc2_voltage(unsigned int *voltage);

/*frame : 4 bytes can_id + 8 bytes can frame*/
int read_mcu_canframe(unsigned char *can_frm_buffer,  unsigned int *frame_num,  int *flag);


/*
ionum:
0: ACC  PIN4
1: SOS1 PIN5
2: SOS2 PIN6
3: PWM1 PIN16
4: PWM2 PIN17
*/
int read_iostate(int io_num, int *state);


/*cycle : microseconds */
int set_gensor_data_cycle(unsigned int cycle);

/*
mcu will wake up mpu after cycle seconds,
cycle should greater than 30 seconds
*/
int enable_periodic_wake(unsigned int cycle);

/*
disable periodic wakeup 
*/
int disable_periodic_wake();
T-BOX UTILS package
T-BOX UTILS includes WiFi operation API, system parameter read-write API, firmware updatation API

T-BOX UTILS package is located in ~/mpu-imx6solo/buildroot/tbox_utils directory

T-BOX UTILS API
int read_device_type(char *type);

int read_device_sn(char *device_sn);

int read_device_id(char *device_id);

int read_hw_version(char *hw_version);

int read_vendor_id(int *vendor_id);

int query_runing_sysversion(char *version);

int query_backup_sysversion(char *version);

int start_wifi(void);

int stop_wifi(void);


/* password at least 8 characters long*/
int change_wifi_ssid_passwd(char *ssid, char *passwd);

int get_wifi_ssid_passwd(char *ssid, char *passwd);

int get_wifi_active_user(int *number);

int lock_powerkey(void);

int release_powerkey(void);

/*key value length should be less than 32 */
int set_system_parameter(char *name, char *value);

int get_system_parameter(char *name, char *value);

int system_upgrade_start(char *upgrade_file);
build command
cd ~/mpu-imx6solo/buildroot
make liteon_imx6_defconfig
make mcu_proxy-dirclean
make mcu_proxy-rebuild
build ubuntu 18.04 rootfs
copy rootfs_base.tar into build work_dir
cp ../rootfs_base.tar