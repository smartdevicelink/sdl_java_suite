package com.smartdevicelink.abstraction;

import com.smartdevicelink.abstraction.listeners.ButtonListener;
import com.smartdevicelink.proxy.rpc.SubscribeButton;

public class SubscribeButtonWithListener extends SubscribeButton {
	
	private ButtonListener mListener;

	public ButtonListener getListener() {
		return mListener;
	}

	public void setListener(ButtonListener mListener) {
		this.mListener = mListener;
	}
}
