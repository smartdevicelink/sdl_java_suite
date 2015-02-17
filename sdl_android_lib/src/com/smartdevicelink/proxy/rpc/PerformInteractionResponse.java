package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.util.JsonUtils;

/**
 * PerformInteraction Response is sent, when PerformInteraction has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class PerformInteractionResponse extends RPCResponse {
    public static final String KEY_MANUAL_TEXT_ENTRY = "manualTextEntry";
    public static final String KEY_TRIGGER_SOURCE = "triggerSource";
    public static final String KEY_CHOICE_ID = "choiceID";

    private Integer choiceId;
    private String manualTextEntry;
    private String triggerSource; // represents TriggerSource enum
    
	/**
	 * Constructs a new PerformInteractionResponse object
	 */
    public PerformInteractionResponse() {
        super(FunctionID.PERFORM_INTERACTION);
    }
    
    /**
     * Creates a PerformInteractionResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public PerformInteractionResponse(JSONObject jsonObject){
        super(SdlCommand.PERFORM_INTERACTION, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.choiceId = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_CHOICE_ID);
            this.manualTextEntry = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MANUAL_TEXT_ENTRY);
            this.triggerSource = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TRIGGER_SOURCE);
            break;
        }
    }
    
    /**
     * Gets the application-scoped identifier that uniquely identifies this choice.
     * @return choiceID Min: 0  Max: 65535
     */   
    public Integer getChoiceID() {
        return this.choiceId;
    }
    
    /**
     * Sets the application-scoped identifier that uniquely identifies this choice.
     * @param choiceID Min: 0  Max: 65535
     */ 
    public void setChoiceID( Integer choiceID ) {
        this.choiceId = choiceID;
    }
    
    /**
     * <p>Returns a <I>TriggerSource</I> object which will be shown in the HMI</p>    
     * @return TriggerSource a TriggerSource object
     */    
    public TriggerSource getTriggerSource() {
        return TriggerSource.valueForJsonName(this.triggerSource, sdlVersion);
    }
    
    /**
     * <p>Sets TriggerSource<br/>
     * Indicates whether command was selected via VR or via a menu selection (using the OK button).</p>    
     * @param triggerSource a TriggerSource object
     */    
    public void setTriggerSource( TriggerSource triggerSource ) {
        this.triggerSource = (triggerSource == null) ? null : triggerSource.getJsonName(sdlVersion);
    }
    
    public void setManualTextEntry(String manualTextEntry) {
        this.manualTextEntry = manualTextEntry;
    }
    
    public String getManualTextEntry() {
        return this.manualTextEntry;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_CHOICE_ID, this.choiceId);
            JsonUtils.addToJsonObject(result, KEY_MANUAL_TEXT_ENTRY, this.manualTextEntry);
            JsonUtils.addToJsonObject(result, KEY_TRIGGER_SOURCE, this.triggerSource);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((choiceId == null) ? 0 : choiceId.hashCode());
		result = prime * result + ((manualTextEntry == null) ? 0 : manualTextEntry.hashCode());
		result = prime * result + ((triggerSource == null) ? 0 : triggerSource.hashCode());
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
		PerformInteractionResponse other = (PerformInteractionResponse) obj;
		if (choiceId == null) {
			if (other.choiceId != null) { 
				return false;
			}
		} 
		else if (!choiceId.equals(other.choiceId)) { 
			return false;
		}
		if (manualTextEntry == null) {
			if (other.manualTextEntry != null) { 
				return false;
			}
		} 
		else if (!manualTextEntry.equals(other.manualTextEntry)) { 
			return false;
		}
		if (triggerSource == null) {
			if (other.triggerSource != null) { 
				return false;
			}
		}
		else if (!triggerSource.equals(other.triggerSource)) { 
			return false;
		}
		return true;
	}
}
