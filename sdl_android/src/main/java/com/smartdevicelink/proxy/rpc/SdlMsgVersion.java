package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;

/**
 * Specifies the version number of the SDL V4 interface. This is used by both the application and SDL to declare what interface version each is using.
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>majorVersion</td>
 * 			<td>Integer</td>
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
 * 			<td>Integer</td>
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
public class SdlMsgVersion extends RPCStruct {
	public static final String KEY_MAJOR_VERSION = "majorVersion";
	public static final String KEY_MINOR_VERSION = "minorVersion";

	/**
	 * Constructs a newly allocated SdlMsgVersion object
	 */
	public SdlMsgVersion() { }
    /**
     * Constructs a newly allocated SdlMsgVersion object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */    
	public SdlMsgVersion(Hashtable<String, Object> hash) {
        super(hash);
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
        return (Integer) store.get( KEY_MAJOR_VERSION );
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
        if (majorVersion != null) {
            store.put(KEY_MAJOR_VERSION, majorVersion );
        } else {
        	store.remove(KEY_MAJOR_VERSION);
        }
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
        return (Integer) store.get( KEY_MINOR_VERSION );
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
        if (minorVersion != null) {
            store.put(KEY_MINOR_VERSION, minorVersion );
        } else {
        	store.remove(KEY_MINOR_VERSION);
        }
    }
}
