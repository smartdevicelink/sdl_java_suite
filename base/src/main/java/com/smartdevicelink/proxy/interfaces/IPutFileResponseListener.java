package com.smartdevicelink.proxy.interfaces;

import com.smartdevicelink.proxy.rpc.PutFileResponse;

public interface IPutFileResponseListener {
	public void onPutFileResponse(PutFileResponse response);
	
	public void onPutFileStreamError(Exception e, String info);
}
