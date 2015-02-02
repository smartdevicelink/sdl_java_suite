package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

/**
 * Specifies the version number of the SDL V4 interface. This is used by both the application and SDL to declare what interface version each is using.
 * <p><b> Parameter List
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>majorVersion</td>
 * 			<td>Int16</td>
 * 			<td>
 * 					<ul>
 * 					<li>minvalue="1"</li>
 * 				    <li>maxvalue="10"</li>
 *					</ul>
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>minorVersion</td>
 * 			<td>Int16</td>
 * 			<td>
 * 					<ul>
 * 					<li>minvalue="0"</li>
 * 				    <li>maxvalue="1000"</li>
 *					</ul>
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * </table> 
 * @since SmartDeviceLink 1.0
 */
public class SdlMsgVersion extends RPCObject {
	public static final String KEY_MAJOR_VERSION = "majorVersion";
	public static final String KEY_MINOR_VERSION = "minorVersion";

	private Integer majorVersion, minorVersion;
	
	/**
	 * Constructs a newly allocated SdlMsgVersion object
	 */
	public SdlMsgVersion() { }
    
    /**
     * Creates a SdlMsgVersion object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SdlMsgVersion(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.majorVersion = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_MAJOR_VERSION);
            this.minorVersion = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_MINOR_VERSION);
            break;
        }
    }
	
    /**
     * Get major version
     * 					<ul>
     * 					<li>minvalue="1"</li>
     * 				    <li>maxvalue="10"</li>
     *					</ul>
     * @return the major version
     */	
    public Integer getMajorVersion() {
        return this.majorVersion;
    }
    
    /**
     * Set major version
     * 					<ul>
     * 					<li>minvalue="1"</li>
     * 				    <li>maxvalue="10"</li>
     *					</ul>
     * @param majorVersion minvalue="1" and maxvalue="10" 
     */    
    public void setMajorVersion( Integer majorVersion ) {
        this.majorVersion = majorVersion;
    }
    
    /**
     * Get minor version
     * 					<ul>
     * 					<li>minvalue="0"</li>
     * 				    <li>maxvalue="1000"</li>
     *					</ul>
     * @return the minor version
     */    
    public Integer getMinorVersion() {
        return this.minorVersion;
    }
    
    /**
     * Set minor version
     * 					<ul>
     * 					<li>minvalue="0"</li>
     * 				    <li>maxvalue="1000"</li>
     *					</ul>
     * @param minorVersion min: 0; max: 1000
     */
    public void setMinorVersion( Integer minorVersion ) {
        this.minorVersion = minorVersion;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_MAJOR_VERSION, this.majorVersion);
            JsonUtils.addToJsonObject(result, KEY_MINOR_VERSION, this.minorVersion);
            break;
        }
        
        return result;
    }
}
