package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.util.DebugTool;

/**
 * Tire pressure status of a single tire.
 * <p><b>Parameter List
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
 * 					See ComponentVolumeStatus
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class SingleTireStatus extends RPCStruct {

	/**
	 * Constructs a newly allocated SingleTireStatus object
	 */
    public SingleTireStatus() { }
    
    /**
     * Constructs a newly allocated SingleTireStatus object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public SingleTireStatus(Hashtable hash) {
        super(hash);
    }
    
    /**
     *  set the volume status of a single tire
     * @param status  the volume status of a single tire
     */
    public void setStatus(ComponentVolumeStatus status) {
    	if (status != null) {
    		store.put(Names.status, status);
    	} else {
    		store.remove(Names.status);
    	}
    }
    
    /**
     * get  the volume status of a single tire
     * @return  the volume status of a single tire
     */
    public ComponentVolumeStatus getStatus() {
        Object obj = store.get(Names.status);
        if (obj instanceof ComponentVolumeStatus) {
            return (ComponentVolumeStatus) obj;
        } else if (obj instanceof String) {
        	ComponentVolumeStatus theCode = null;
            try {
                theCode = ComponentVolumeStatus.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.status, e);
            }
            return theCode;
        }
        return null;
    }
}
