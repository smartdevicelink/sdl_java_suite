/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.transport;

import android.content.Context;
import android.hardware.usb.UsbAccessory;

import com.smartdevicelink.transport.enums.TransportType;

/**
 * <b>NOTE: </b> This should no longer be used. See the MultplexTransportConfig and guides to
 * understand how to implement USB multiplexing. This class and method of USB connection will be
 * removed in the next major release. If a router service is available to handle multiplexing of the
 * usb transport it will be used, and this app will connect to whatever router service hosts the USB
 * connection.
 * @see MultiplexTransportConfig
 */
@Deprecated
public class USBTransportConfig extends BaseTransportConfig {
	
	private Context mainActivity = null;
	private UsbAccessory usbAccessory = null;
	private Boolean queryUsbAcc = true;

	/**
	 * <b>NOTE: </b> This should no longer be used. See the MultplexTransportConfig and guides to
	 * understand how to implement USB multiplexing. This class and method of USB connection will be
	 * removed in the next major release. If a router service is available to handle multiplexing of the
	 *  usb transport it will be used, and this app will connect to whatever router service hosts the USB
	 *  connection.
	 * @param mainActivity context used to start USB transport
	 * @see MultiplexTransportConfig
	 */
	public USBTransportConfig (Context mainActivity) {
		this.mainActivity = mainActivity;
	}

	/**
	 * <b>NOTE: </b> This should no longer be used. See the MultplexTransportConfig and guides to
	 * understand how to implement USB multiplexing. This class and method of USB connection will be
	 * removed in the next major release. If a router service is available to handle multiplexing of the
	 * usb transport it will be used, and this app will connect to whatever router service hosts the USB
	 * connection.
	 * @param mainActivity context used to start USB transport
	 * @param usbAccessory the accessory that was given to this app
	 * @see MultiplexTransportConfig
	 */
	public USBTransportConfig (Context mainActivity, UsbAccessory usbAccessory) {
		this.mainActivity = mainActivity;
		this.usbAccessory = usbAccessory;
	}

	/**
	 * <b>NOTE: </b> This should no longer be used. See the MultplexTransportConfig and guides to
	 * understand how to implement USB multiplexing. This class and method of USB connection will be
	 * removed in the next major release. If a router service is available to handle multiplexing of the
	 * usb transport it will be used, and this app will connect to whatever router service hosts the USB
	 * connection.
	 * @param mainActivity context used to start USB transport
	 * @param shareConnection enable other sessions on this app to use this USB connection
	 * @param queryUsbAcc attempt to query the USB accessory if none is provided
	 * @see MultiplexTransportConfig
	 */
	public USBTransportConfig (Context mainActivity, boolean shareConnection, boolean queryUsbAcc) {
		this.mainActivity = mainActivity;
		this.queryUsbAcc = queryUsbAcc;
		super.shareConnection = shareConnection;
	}

	/**
	 * <b>NOTE: </b> This should no longer be used. See the MultplexTransportConfig and guides to
	 * understand how to implement USB multiplexing. This class and method of USB connection will be
	 * removed in the next major release. If a router service is available to handle multiplexing of the
	 * usb transport it will be used, and this app will connect to whatever router service hosts the USB
	 * connection.
	 * @param mainActivity context used to start USB transport
	 * @param usbAccessory the accessory that was given to this app
	 * @param shareConnection enable other sessions on this app to use this USB connection
	 * @param queryUsbAcc attempt to query the USB accessory if none is provided
	 * @see MultiplexTransportConfig
	 */
	public USBTransportConfig (Context mainActivity, UsbAccessory usbAccessory, boolean shareConnection, boolean queryUsbAcc) {
		this.mainActivity = mainActivity;
		this.queryUsbAcc = queryUsbAcc;
		this.usbAccessory = usbAccessory;
		super.shareConnection = shareConnection;
	}
	
	public Boolean getQueryUsbAcc () {
		return queryUsbAcc;
	}
	
	public Context getUSBContext () {
		return mainActivity;
	}
	
	public UsbAccessory getUsbAccessory () {
		return usbAccessory;
	}

	public void setUsbAccessory (UsbAccessory value) { usbAccessory = value; }

	public TransportType getTransportType() {
		return TransportType.USB;
	}
}