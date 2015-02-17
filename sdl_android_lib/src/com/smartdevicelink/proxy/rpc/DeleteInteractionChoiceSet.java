package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * Deletes an existing Choice Set identified by the parameter
 * interactionChoiceSetID. If the specified interactionChoiceSetID is currently
 * in use by an active <i> {@linkplain PerformInteraction}</i> this call to
 * delete the Choice Set will fail returning an IN_USE resultCode
 * <p>
 * Function Group: Base
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUD</b><br/>
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 * @see CreateInteractionChoiceSet
 * @see PerformInteraction
 */
public class DeleteInteractionChoiceSet extends RPCRequest {
	public static final String KEY_INTERACTION_CHOICE_SET_ID = "interactionChoiceSetID";

	private Integer interactionChoiceSetId;
	
	/**
	 * Constructs a new DeleteInteractionChoiceSet object
	 */
    public DeleteInteractionChoiceSet() {
        super(FunctionID.DELETE_INTERACTION_CHOICE_SET);
    }

    /**
     * Creates a DeleteInteractionChoiceSet object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public DeleteInteractionChoiceSet(JSONObject jsonObject) {
        super(SdlCommand.DELETE_INTERACTION_CHOICE_SET, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.interactionChoiceSetId = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_INTERACTION_CHOICE_SET_ID);
            break;
        }
    }
    
	/**
	 * Gets a unique ID that identifies the Choice Set
	 * @return Integer -an Integer value representing the unique Choice Set ID
	 */    
    public Integer getInteractionChoiceSetID() {
        return this.interactionChoiceSetId;
    }
    
	/**
	 * Sets a unique ID that identifies the Choice Set
	 * @param interactionChoiceSetID a unique ID that identifies the Choice Set
	 * <p>
	 * <b>Notes: </b>Min Value: 0; Max Value: 2000000000
	 */    
    public void setInteractionChoiceSetID( Integer interactionChoiceSetID ) {
        this.interactionChoiceSetId = interactionChoiceSetID;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_INTERACTION_CHOICE_SET_ID, this.interactionChoiceSetId);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((interactionChoiceSetId == null) ? 0 : interactionChoiceSetId.hashCode());
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
		DeleteInteractionChoiceSet other = (DeleteInteractionChoiceSet) obj;
		if (interactionChoiceSetId == null) {
			if (other.interactionChoiceSetId != null) { 
				return false;
			}
		} 
		else if (!interactionChoiceSetId.equals(other.interactionChoiceSetId)) { 
			return false;
		}
		return true;
	}
}
