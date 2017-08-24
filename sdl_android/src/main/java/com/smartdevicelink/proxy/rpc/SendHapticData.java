package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;
import java.util.List;

public class SendHapticData extends RPCRequest {
	public static final String KEY_HAPTIC_RECT_DATA = "hapticRectData";
	/**
	 * Constructs a new SendHapticData object
	 */
	public SendHapticData(){
		super(FunctionID.SEND_HAPTIC_DATA.toString());
	}

	/**
	 * <p>
	 * Send the spatial data gathered from SDLCarWindow or VirtualDisplayEncoder to the HMI.
	 * This data will be utilized by the HMI to determine how and when haptic events should occur
	 * </p>
	 *
	 * @param hash The Hashtable to use
	 */
	public SendHapticData(Hashtable<String, Object> hash){
		super(hash);
	}

	/**
	 * Array of spatial data structures that represent the locations of all user controls present on the HMI.
	 * This data should be updated if/when the application presents a new screen.
     * When a request is sent, if successful, it will replace all spatial data previously sent through RPC.
	 * If an empty array is sent, the existing spatial data will be cleared
	 */
	public void setHapticRectData(List<HapticRect> hapticRectData) {
		setParameters(KEY_HAPTIC_RECT_DATA, hapticRectData);
	}

	@SuppressWarnings("unchecked")
	public List<HapticRect> getHapticRectData() {
		return (List<HapticRect>) getObject(HapticRect.class, KEY_HAPTIC_RECT_DATA);
	}

}
