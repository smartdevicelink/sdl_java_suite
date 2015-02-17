package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * Non periodic vehicle data read request. This is an RPC to get diagnostics
 * data from certain vehicle modules. DIDs of a certain module might differ from
 * vehicle type to vehicle type
 * <p>
 * Function Group: ProprietaryData
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 */
public class ReadDID extends RPCRequest {
	public static final String KEY_ECU_NAME = "ecuName";
	public static final String KEY_DID_LOCATION = "didLocation";

	private Integer ecuName;
	private List<Integer> didLocation;
	
	/**
	 * Constructs a new ReadDID object
	 */
    public ReadDID() {
        super(FunctionID.READ_DID);
    }

    /**
     * Creates a ReadDID object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ReadDID(JSONObject jsonObject){
        super(SdlCommand.READ_DIDS, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.ecuName = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_ECU_NAME);
            this.didLocation = JsonUtils.readIntegerListFromJsonObject(jsonObject, KEY_DID_LOCATION);
            break;
        }
    }

	/**
	 * Sets an ID of the vehicle module
	 * 
	 * @param ecuName
	 *            an Integer value representing the ID of the vehicle module
	 *            <p>
	 *            <b>Notes: </b>Minvalue:0; Maxvalue:65535
	 */
    public void setEcuName(Integer ecuName) {
    	this.ecuName = ecuName;
    }

	/**
	 * Gets the ID of the vehicle module
	 * 
	 * @return Integer -an Integer value representing the ID of the vehicle
	 *         module
	 */
    public Integer getEcuName() {
    	return this.ecuName;
    }

	/**
	 * Sets raw data from vehicle data DID location(s)
	 * 
	 * @param didLocation
	 *            a List<Integer> value representing raw data from vehicle
	 *            data DID location(s)
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Minvalue:0; Maxvalue:65535</li>
	 *            <li>ArrayMin:0; ArrayMax:1000</li>
	 *            </ul>
	 */
    public void setDidLocation(List<Integer> didLocation) {
    	this.didLocation = didLocation;
    }

	/**
	 * Gets raw data from vehicle data DID location(s)
	 * 
	 * @return List<Integer> -a List<Integer> value representing raw data
	 *         from vehicle data DID location(s)
	 */
    public List<Integer> getDidLocation() {
        return this.didLocation;
    }
    
    @Override
	public JSONObject getJsonParameters(int sdlVersion){
	    JSONObject result = super.getJsonParameters(sdlVersion);
	    
	    switch(sdlVersion){
	    default:
	        JsonUtils.addToJsonObject(result, KEY_ECU_NAME, this.ecuName);
	        JsonUtils.addToJsonObject(result, KEY_DID_LOCATION, (this.didLocation == null) ? null : 
	            JsonUtils.createJsonArray(this.didLocation));
	        break;
	    }
	    
	    return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((didLocation == null) ? 0 : didLocation.hashCode());
		result = prime * result + ((ecuName == null) ? 0 : ecuName.hashCode());
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
		ReadDID other = (ReadDID) obj;
		if (didLocation == null) {
			if (other.didLocation != null) { 
				return false;
			}
		} else if (!didLocation.equals(other.didLocation)) { 
			return false;
		}
		if (ecuName == null) {
			if (other.ecuName != null) { 
				return false;
			}
		} else if (!ecuName.equals(other.ecuName)) { 
			return false;
		}
		return true;
	}
}
