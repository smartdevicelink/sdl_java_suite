package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.util.JsonUtils;

/**
 * Creates a Choice Set which can be used in subsequent <i>
 * {@linkplain PerformInteraction}</i> Operations.
 * <p>
 * Function Group: Base 
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 * @see DeleteInteractionChoiceSet
 * @see PerformInteraction
 */
public class CreateInteractionChoiceSet extends RPCRequest {
	public static final String KEY_CHOICE_SET = "choiceSet";
	public static final String KEY_INTERACTION_CHOICE_SET_ID = "interactionChoiceSetID";

	private Integer interactionChoiceSetId;
	private List<Choice> choiceSet;
	
	/**
	 * Constructs a new CreateInteractionChoiceSet object
	 */    
	public CreateInteractionChoiceSet() {
        super(FunctionID.CREATE_INTERACTION_CHOICE_SET);
    }
	
	/**
     * Creates a CreateInteractionChoiceSet object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public CreateInteractionChoiceSet(JSONObject jsonObject) {
        super(jsonObject);
        
        switch(sdlVersion){
        default:
            this.interactionChoiceSetId = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_INTERACTION_CHOICE_SET_ID);
            
            List<JSONObject> choiceSetListJson = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_CHOICE_SET);
            if(choiceSetListJson != null){
                this.choiceSet = new ArrayList<Choice>(choiceSetListJson.size());
                for(JSONObject choiceJson : choiceSetListJson){
                    this.choiceSet.add(new Choice(choiceJson));
                }
            }
            break;
        }
    }
    
	/**
	 * Gets the Choice Set unique ID
	 * 
	 * @return Integer -an Integer representing the Choice Set ID
	 */    
    public Integer getInteractionChoiceSetID() {
        return this.interactionChoiceSetId;
    }
    
	/**
	 * Sets a unique ID that identifies the Choice Set
	 * 
	 * @param interactionChoiceSetID
	 *            an Integer value representing the Choice Set ID
	 *            <p>
	 *            <b>Notes: </b>Min Value: 0; Max Value: 2000000000
	 */    
    public void setInteractionChoiceSetID( Integer interactionChoiceSetID ) {
        this.interactionChoiceSetId = interactionChoiceSetID;
    }
    
	/**
	 * Gets Choice Set Array of one or more elements
	 * 
	 * @return List<Choice> -a List<Choice> representing the array of one or
	 *         more elements
	 */
    public List<Choice> getChoiceSet() {
        return this.choiceSet;
    }
    
	/**
	 * Sets a Choice Set that is an Array of one or more elements
	 * 
	 * @param choiceSet
	 *            a List<Choice> representing the array of one or more
	 *            elements
	 *            <p>
	 *            <b>Notes: </b>Min Value: 1; Max Value: 100
	 */    
    public void setChoiceSet( List<Choice> choiceSet ) {
        this.choiceSet = choiceSet;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_INTERACTION_CHOICE_SET_ID, this.interactionChoiceSetId);
            JsonUtils.addToJsonObject(result, KEY_CHOICE_SET, 
                    JsonUtils.createJsonArrayOfJsonObjects(this.choiceSet, sdlVersion));
            break;
        }
        
        return result;
    }
}
