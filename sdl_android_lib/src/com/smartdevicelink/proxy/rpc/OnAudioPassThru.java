package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.BulkData;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;

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
public class OnAudioPassThru extends RPCNotification implements BulkData{
	/**
	*Constructs a newly allocated OnCommand object
	*/    
    public OnAudioPassThru() {
        super(FunctionID.ON_AUDIO_PASS_THRU);
    }
    
    private byte[] bulkData;

    /**
     * Creates an OnAudioPassThru object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public OnAudioPassThru(JSONObject jsonObject) {
        this(jsonObject, null);
    }

    /**
     * Creates an OnAudioPassThru object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     * @param bulkData The bulk data for this object
     */
    public OnAudioPassThru(JSONObject jsonObject, byte[] bulkData) {
        super(SdlCommand.ON_AUDIO_PASS_THRU, jsonObject);
        this.bulkData = bulkData;
    }
    
    @Deprecated
    public void setAPTData(byte[] aptData) {
        setBulkData(aptData);
    }
    
    @Deprecated
    public byte[] getAPTData() {
        return getBulkData();
    }

    @Override
    public byte[] getBulkData(){
        return this.bulkData;
    }

    @Override
    public void setBulkData(byte[] rawData){
        this.bulkData = rawData;
    }
}
