package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

/**
 * Binary data is in binary part of hybrid msg.
 *  <p>
 * </p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel:
 * <ul>
 * <li>BACKGROUND, FULL, LIMITED</li>
 * </ul>
 * AudioStreamingState:
 * <ul>
 * <li>TBD</li>
 * </ul>
 * SystemContext:
 * <ul>
 * <li>TBD</li>
 * </ul>
 * </ul>
 * <p>
 * <b>Parameter List:</b>
 * <table border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>Req</th>
 * <th>Notes</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * </table>
 * </p>
 *
 */
public class OnAudioPassThru extends RPCNotification {
	/**
	*Constructs a newly allocated OnCommand object
	*/    
    public OnAudioPassThru() {
        super(FunctionID.ON_AUDIO_PASS_THRU.toString());
    }
    /**
     *<p>Constructs a newly allocated OnAudioPassThru object indicated by the Hashtable parameter</p>
     *@param hash The Hashtable to use
     */ 
    public OnAudioPassThru(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setAPTData(byte[] aptData) {
        setBulkData(aptData);
    }
    public byte[] getAPTData() {
        return getBulkData();
    }
}
