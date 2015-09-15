package com.smartdevicelink.abstraction.rpc;

import com.smartdevicelink.abstraction.listener.SystemRequestListener;
import com.smartdevicelink.proxy.rpc.SystemRequest;

public class SystemRequestWithListener extends SystemRequest {
	
	private SystemRequestListener mListener;

	public SystemRequestListener getListener() {
		return mListener;
	}

	public void setListener(SystemRequestListener listener) {
		this.mListener = listener;
	}

}
