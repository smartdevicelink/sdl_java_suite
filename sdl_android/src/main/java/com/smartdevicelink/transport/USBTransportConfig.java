package com.smartdevicelink.transport;

import com.smartdevicelink.transport.enums.TransportType;

import android.content.Context;
import android.hardware.usb.UsbAccessory;

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