package com.smartdevicelink.abstraction;

import com.smartdevicelink.abstraction.listeners.ButtonListener;
import com.smartdevicelink.proxy.rpc.SoftButton;

public class SoftButtonWithListener extends SoftButton {
	
	private ButtonListener mListener;

	public void setListener(ButtonListener listener){
		mListener = listener;
	}
	
	public ButtonListener getListener() {
		return mListener;
	}
}
