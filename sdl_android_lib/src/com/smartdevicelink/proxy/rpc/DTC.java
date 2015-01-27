package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

/**
 * String containing hexadecimal identifier as well as other common names.
 * <p><b>Parameter List
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>statusByte</td>
 * 			<td>String</td>
 * 			<td>Hexadecimal byte string
 *				 <ul>
 *					<li>Maxlength = 500</li>
 *				 </ul> 
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class DTC extends RPCObject {
	public static final String KEY_IDENTIFIER = "identifier";
	public static final String KEY_STATUS_BYTE = "statusByte";
	
	private String id, statusByte;
	
	/**
	 * Constructs a newly allocated DTC object
	 */
    public DTC() { }
    
    /**
     * Creates a DTC object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public DTC(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.id = JsonUtils.readStringFromJsonObject(jsonObject, KEY_IDENTIFIER);
            this.statusByte = JsonUtils.readStringFromJsonObject(jsonObject, KEY_STATUS_BYTE);
            break;
        }
    }
    
    /**
     * set identifier
     * @param identifier
     */
    public void setIdentifier(String identifier) {
    	this.id = identifier;
    }
    
    /**
     * get identifier
     * @return identifier
     */
    public String getIdentifier() {
    	return this.id;
    }
    
    /**
     * set Hexadecimal byte string
     * @param statusByte Hexadecimal byte string
     */
    public void setStatusByte(String statusByte) {
    	this.statusByte = statusByte;
    }
    
    /**
     * get Hexadecimal byte string
     * @return Hexadecimal byte string
     */
    public String getStatusByte() {
    	return this.statusByte;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_IDENTIFIER, this.id);
            JsonUtils.addToJsonObject(result, KEY_STATUS_BYTE, this.statusByte);
            break;
        }
        
        return result;
    }
}
