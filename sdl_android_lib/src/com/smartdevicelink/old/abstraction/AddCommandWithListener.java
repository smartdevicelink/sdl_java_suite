package com.smartdevicelink.old.abstraction;

import com.smartdevicelink.old.abstraction.listeners.OnCommandListener;
import com.smartdevicelink.proxy.rpc.AddCommand;

public class AddCommandWithListener extends AddCommand{
	
	private OnCommandListener mListener;
	
	public void setListener(OnCommandListener listener){
		mListener = listener;
	}
	
	public OnCommandListener getListener() {
		return mListener;
	}

}