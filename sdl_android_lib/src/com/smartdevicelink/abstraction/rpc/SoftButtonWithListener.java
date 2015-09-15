package com.smartdevicelink.abstraction.rpc;

import com.smartdevicelink.abstraction.listener.ButtonListener;
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
