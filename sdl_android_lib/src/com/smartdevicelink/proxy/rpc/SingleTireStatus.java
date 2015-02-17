package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.util.JsonUtils;

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
public class SingleTireStatus extends RPCObject {
	public static final String KEY_STATUS = "status";

	private String status; // represents ComponentVolumeStatus enum
	
	/**
	 * Constructs a newly allocated SingleTireStatus object
	 */
    public SingleTireStatus() { }
    
    /**
     * Creates a SingleTireStatus object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SingleTireStatus(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.status = JsonUtils.readStringFromJsonObject(jsonObject, KEY_STATUS);
            break;
        }
    }
    
    /**
     *  set the volume status of a single tire
     * @param status  the volume status of a single tire
     */
    public void setStatus(ComponentVolumeStatus status) {
    	this.status = (status == null) ? null : status.getJsonName(sdlVersion);
    }
    
    /**
     * get  the volume status of a single tire
     * @return  the volume status of a single tire
     */
    public ComponentVolumeStatus getStatus() {
        return ComponentVolumeStatus.valueForJsonName(this.status, sdlVersion);
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_STATUS, this.status);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		SingleTireStatus other = (SingleTireStatus) obj;
		if (status == null) {
			if (other.status != null) { 
				return false;
			}
		}
		else if (!status.equals(other.status)) { 
			return false;
		}
		return true;
	}
}
