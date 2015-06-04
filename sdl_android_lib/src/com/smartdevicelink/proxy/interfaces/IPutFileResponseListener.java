package com.smartdevicelink.proxy.interfaces;

import com.smartdevicelink.rpc.responses.PutFileResponse;

public interface IPutFileResponseListener {
	public void onPutFileResponse(PutFileResponse response);
	
	public void onPutFileStreamError(Exception e, String info);
}
