package com.smartdevicelink.transport;

import android.content.Context;
import android.hardware.usb.UsbAccessory;

public class UsbTransportConfig extends BaseTransportConfig {
	
	private Context mainActivity = null;
	private UsbAccessory usbAccessory = null;
	
	public UsbTransportConfig (Context mainActivity) {
		this.mainActivity = mainActivity;
	}
	
	public UsbTransportConfig (Context mainActivity, UsbAccessory usbAccessory) {
		this.mainActivity = mainActivity;
		this.usbAccessory = usbAccessory;
	}
	
	public Context getUSBContext () {
		return mainActivity;
	}
	
	public UsbAccessory getUsbAccessory () {
		return usbAccessory;
	}
	
	public TransportType getTransportType() {
		return TransportType.USB;
	}
}