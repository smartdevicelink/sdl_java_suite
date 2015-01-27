package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.util.JsonUtils;

/**
 * This RPC allows to request diagnostic module trouble codes from a certain
 * vehicle module
 * <p>
 * Function Group: ProprietaryData
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * <p>
 */
public class GetDTCs extends RPCRequest {
	public static final String KEY_DTC_MASK = "dtcMask";
	public static final String KEY_ECU_NAME = "ecuName";

	private Integer dtcMask, ecuName;
	
	/**
	 * Constructs a new GetDTCs object
	 */
    public GetDTCs() {
        super(FunctionID.GET_DTCS);
    }

    /**
     * Creates a GetDTCs object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public GetDTCs(JSONObject jsonObject) {
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.dtcMask = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_DTC_MASK);
            this.ecuName = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_ECU_NAME);
            break;
        }
    }

	/**
	 * Sets a name of the module to receive the DTC form
	 * 
	 * @param ecuName
	 *            an Integer value representing a name of the module to receive
	 *            the DTC form
	 *            <p>
	 *            <b>Notes: </b>Minvalue:0; Maxvalue:65535
	 */
    public void setEcuName(Integer ecuName) {
    	this.ecuName = ecuName;
    }

	/**
	 * Gets a name of the module to receive the DTC form
	 * 
	 * @return Integer -an Integer value representing a name of the module to
	 *         receive the DTC form
	 */
    public Integer getEcuName() {
    	return this.ecuName;
    }
    
    public void setDtcMask(Integer dtcMask) {
    	this.dtcMask = dtcMask;
    }
    
    public Integer getDtcMask() {
    	return this.dtcMask;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_DTC_MASK, this.dtcMask);
            JsonUtils.addToJsonObject(result, KEY_ECU_NAME, this.ecuName);
            break;
        }
        
        return result;
    }
}
