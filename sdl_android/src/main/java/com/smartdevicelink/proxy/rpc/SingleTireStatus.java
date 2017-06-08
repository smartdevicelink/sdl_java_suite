package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;

import java.util.Hashtable;

/**
 * Tire pressure status of a single tire.
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>status</td>
 * 			<td>ComponentVolumeStatus</td>
 * 			<td>Describes the volume status of a single tire
 * 					See {@linkplain ComponentVolumeStatus}
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class SingleTireStatus extends RPCStruct {
	public static final String KEY_STATUS = "status";

	/**
	 * Constructs a newly allocated SingleTireStatus object
	 */
    public SingleTireStatus() { }
    
    /**
     * Constructs a newly allocated SingleTireStatus object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public SingleTireStatus(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    /**
     *  set the volume status of a single tire
     * @param status  the volume status of a single tire
     */
    public void setStatus(ComponentVolumeStatus status) {
    	setValue(KEY_STATUS, status);
    }
    
    /**
     * get  the volume status of a single tire
     * @return  the volume status of a single tire
     */
    public ComponentVolumeStatus getStatus() {
        return (ComponentVolumeStatus) getObject(ComponentVolumeStatus.class, KEY_STATUS);
    }
}
