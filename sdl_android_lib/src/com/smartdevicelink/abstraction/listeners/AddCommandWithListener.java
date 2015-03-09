package com.smartdevicelink.abstraction.listeners;

import java.util.Hashtable;

import com.smartdevicelink.abstraction.listeners.OnCommandListener;
import com.smartdevicelink.proxy.rpc.AddCommand;

public class AddCommandWithListener extends AddCommand{
	
	private OnCommandListener mListener;
    public AddCommandWithListener() { }

    public AddCommandWithListener(Hashtable hash) {
        super(hash);
    }
	public void setListener(OnCommandListener listener){
		mListener = listener;
	}
	
	public OnCommandListener getListener() {
		return mListener;
	}

}
