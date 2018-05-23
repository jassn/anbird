/*
 * f_mfi.c -- USB for Baidu CarLife and Apple CarPlay
 *
 * Copyright (C) 2018 Antec Corporation
 * Contact: Jason Chang
 *
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 */

#include <linux/kernel.h>
#include <linux/module.h>
#include <linux/device.h>
#include <linux/etherdevice.h>
#include <linux/crc32.h>

#include <linux/usb/cdc.h>

#include "u_mfi.h"


struct f_mfi {
	//struct gether			port;
	u8				ctrl_id, data_id;
	bool				timer_stopping;
	struct usb_function     function;
};

#if 0
static inline struct f_mfi *func_to_mfi(struct usb_function *f)
{
	return container_of(f, struct f_mfi, port.func);
}
#endif







/* Configfs support *********************************************************/

#if 0
static inline struct f_fs_opts *to_ffs_opts(struct config_item *item)
{
	return container_of(to_config_group(item), struct f_fs_opts,
			    func_inst.group);
}
#endif

static void ffs_attr_release(struct config_item *item)
{
	//struct f_fs_opts *opts = to_ffs_opts(item);

	//usb_put_function_instance(&opts->func_inst);
}

static struct configfs_item_operations ffs_item_ops = {
	.release	= ffs_attr_release,
};

static struct config_item_type ffs_func_type = {
	.ct_item_ops	= &ffs_item_ops,
	.ct_owner	= THIS_MODULE,
};




static struct usb_function_instance *mfi_alloc_inst(void)
{
	struct f_mfi_opts *opts;

	opts = kzalloc(sizeof(*opts), GFP_KERNEL);
	if (!opts)
		return ERR_PTR(-ENOMEM);

	mutex_init(&opts->lock);

	//opts->func_inst.free_func_inst = ncm_free_inst;
	//opts->net = gether_setup_default();

	config_group_init_type_name(&opts->func_inst.group, "", &ffs_func_type);

	return &opts->func_inst;
}


/**
 * See ffs_alloc. (android adb)
 *
 * following are more complex.
 *   f_serial - based on gserial port.
 *   f_ncm    - based on ethernet.
 */
static struct usb_function *mfi_alloc(struct usb_function_instance *fi)
{
	struct f_mfi		*mfi;
	struct f_mfi_opts	*opts;

	//ENTER();

	/* allocate and initialize one new instance */
	mfi = kzalloc(sizeof(*mfi), GFP_KERNEL);
	if (unlikely(!mfi))
		return ERR_PTR(-ENOMEM);

	mfi->function.name    = "MFi CarLife";

	opts = container_of(fi, struct f_mfi_opts, func_inst);
	mutex_lock(&opts->lock);
	opts->refcnt++;

	return &mfi->function;
}




DECLARE_USB_FUNCTION_INIT(mfi, mfi_alloc_inst, mfi_alloc);
MODULE_LICENSE("GPL");
MODULE_AUTHOR("Jason Chang");
