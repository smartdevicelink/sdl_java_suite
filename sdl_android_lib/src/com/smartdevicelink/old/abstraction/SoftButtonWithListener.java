package com.smartdevicelink.old.abstraction;

import com.smartdevicelink.old.abstraction.listeners.ButtonListener;
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
