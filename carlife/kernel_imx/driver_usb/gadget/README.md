## i.mx6 USB driver

* [iMX6 USB OTG功能开发与测试](https://blog.csdn.net/freeman1975/article/details/46364319)

* [随笔分类 - imx6](http://www.cnblogs.com/helloworldtoyou/category/813842.html)




-----------------------------------------------


## USB Gadget

* [Kernel USB Gadget Configfs Interface](https://events.static.linuxfound.org/sites/events/files/slides/USB%20Gadget%20Configfs%20API_0.pdf)

* [linux之configfs简介和编程入门](https://blog.csdn.net/liumangxiong/article/details/12154865)


* [关于配置android configfs gadgets的补充说明](https://blog.csdn.net/csdn66_2016/article/details/79655787)

* [Linux usb gadget configfs用法](https://blog.csdn.net/hktkfly6/article/details/72846218)  
  /sys/class/udc/ci_hdrc.0/  
  out/target/product/sabresd_6dq/root/init.freescale.usb.rc

* [Linux USB configfs How-to](https://blog.csdn.net/zoosenpin/article/details/38224149)  
  gadget_strings_langid_attrs


* [Android USB/MTP相关实现 - qualcomm chip](https://blog.csdn.net/kv110/article/details/39934319)

* [android下usb框架系列文章---(7)android-kernel gadget框架](https://blog.csdn.net/u011279649/article/details/17420321)




-------------------------------------------------------
## when connecting i.mx6 USB OTG (device) to Ubuntu USB Host.

[  269.645343] configfs-gadget gadget: unbind function 'mtp'/d8f2a600   
[  269.645416] configfs-gadget gadget: unbind function 'Function FS Gadget'/c8e436e4   
[  269.646103] jsn . . . . . . . 10:58 May18, configfs_composite_bind:1363   

[  269.646161] adding 'mtp'/d8f2a600 to config 'b'/d8f2a498   
[  269.646226] adding 'Function FS Gadget'/c940e1a4 to config 'b'/d8f2a498   


```cpp
static int
ffs_epfile_open(struct inode *inode, struct file *file)
{
    struct ffs_epfile *epfile = inode->i_private;
    if (WARN_ON(epfile->ffs->state != FFS_ACTIVE))
        return -ENODEV;

    file->private_data = epfile;
    ffs_data_opened(epfile->ffs);

    return 0;
}
```



--------------------------------------------------------

## Examine what is written from adbd to configfs.




```cpp
static struct dentry *
ffs_fs_mount(struct file_system_type *t, int flags,
          const char *dev_name, void *opts)
{
    ......
    rv = mount_nodev(t, flags, &data, ffs_sb_fill);
    if (IS_ERR(rv) && data.ffs_data) {
        ffs_release_dev(data.ffs_data);
        ffs_data_put(data.ffs_data);
    }
    return rv;
}

static int ffs_sb_fill(struct super_block *sb, void *_data, int silent)
{
    ......
    /* EP0 file */
    if (unlikely(!ffs_sb_create_file(sb, "ep0", ffs,
                     &ffs_ep0_operations)))
        return -ENOMEM;
}
```
-------------------------------------------
## ffs_ep0_write

```cpp
static const struct file_operations ffs_ep0_operations = {
    .llseek =   no_llseek,

    .open =     ffs_ep0_open,
    .write =    ffs_ep0_write,
    .read =     ffs_ep0_read,
    .release =  ffs_ep0_release,
    .unlocked_ioctl =   ffs_ep0_ioctl,
    .poll =     ffs_ep0_poll,
};
```


-------------------------------------------
## struct gadget_info

```cpp
struct gadget_info {
    struct config_group group;
    struct config_group functions_group;
    struct config_group configs_group;
    struct config_group strings_group;
    struct config_group os_desc_group;
    struct config_group *default_groups[5];

    struct mutex lock;
    struct usb_gadget_strings *gstrings[MAX_USB_STRING_LANGS + 1];
    struct list_head string_list;
    struct list_head available_func;

    const char *udc_name;
    struct usb_composite_driver composite;
    struct usb_composite_dev cdev;
    bool use_os_desc;
    char b_vendor_code;
    char qw_sign[OS_STRING_QW_SIGN_LEN];
#ifdef CONFIG_USB_CONFIGFS_UEVENT
    bool connected;
    bool sw_connected;
    struct work_struct work;
    struct device *dev;
#endif
};
```

