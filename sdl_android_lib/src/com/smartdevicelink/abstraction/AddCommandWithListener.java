package com.smartdevicelink.abstraction;

import com.smartdevicelink.abstraction.listener.OnCommandListener;
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
