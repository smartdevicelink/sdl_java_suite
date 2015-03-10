package com.smartdevicelink.proxy.view;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCRequest;

public class SDLView extends RPCRequest {

	public SDLView(String functionName) {
		super(functionName);
	}
	
	public SDLView(Hashtable<String, Object> hash) {
		super(hash);
	}
	
	//TODO call when the view is sent.
	public void onInitialize(){};

	//TODO call when the view is successful
	public void onShown(){};
	
	//TODO call when the view is sent to background.
	public void onBackground(){};
	
	//TODO call when the view is replaced by another view.
	public void onDestroyed(){}; 

}
