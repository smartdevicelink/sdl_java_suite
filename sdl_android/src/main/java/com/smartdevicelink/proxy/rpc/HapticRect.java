package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Created by brettywhite on 8/24/17.
 */

public class HapticRect extends RPCStruct {
	public static final String KEY_ID = "id";
	public static final String KEY_RECT = "rect";

	public HapticRect() {}
	public HapticRect(Hashtable<String, Object> hash) {
		super(hash);
	}


}
