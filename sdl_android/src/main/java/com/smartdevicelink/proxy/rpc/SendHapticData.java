package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

public class SendHapticData extends RPCRequest {
	public static final String KEY_HAPTIC_SPATIAL_DATA = "hapticSpatialData";
	/**
	 * Constructs a new SendHapticData object
	 */
	public SendHapticData(){
		super(FunctionID.SEND_HAPTIC_DATA.toString());
	}

	/**
	 * <p>
	 * Constructs a new SendHapticData object indicated by the Hashtable parameter
	 * </p>
	 *
	 * @param hash The Hashtable to use
	 */
	public SendHapticData(Hashtable<String, Object> hash){
		super(hash);
	}

	public void setHapticSpatialData(Rectangle hapticSpatialData) {
		setParameters(KEY_HAPTIC_SPATIAL_DATA, hapticSpatialData);
	}

	public Rectangle getHapticSpatialData() {
		return (Rectangle) getObject(Rectangle.class, KEY_HAPTIC_SPATIAL_DATA);
	}

}
