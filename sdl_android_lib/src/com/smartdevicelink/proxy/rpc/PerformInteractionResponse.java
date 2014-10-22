package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.util.DebugTool;

/**
 * PerformInteraction Response is sent, when PerformInteraction has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class PerformInteractionResponse extends RPCResponse {
    public static final String manualTextEntry = "manualTextEntry";
    public static final String triggerSource = "triggerSource";
    public static final String choiceID = "choiceID";

	/**
	 * Constructs a new PerformInteractionResponse object
	 */
    public PerformInteractionResponse() {
        super("PerformInteraction");
    }

	/**
	 * Constructs a new PerformInteractionResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public PerformInteractionResponse(Hashtable hash) {
        super(hash);
    }
    /**
     * Gets the application-scoped identifier that uniquely identifies this choice.
     * @return choiceID Min: 0  Max: 65535
     */   
    public Integer getChoiceID() {
        return (Integer) parameters.get( PerformInteractionResponse.choiceID );
    }
    /**
     * Sets the application-scoped identifier that uniquely identifies this choice.
     * @param choiceID Min: 0  Max: 65535
     */ 
    public void setChoiceID( Integer choiceID ) {
        if (choiceID != null) {
            parameters.put(PerformInteractionResponse.choiceID, choiceID );
        } else {
            parameters.remove(PerformInteractionResponse.choiceID);
        }
    }
    /**
     * <p>Returns a <I>TriggerSource</I> object which will be shown in the HMI</p>    
     * @return TriggerSource a TriggerSource object
     */    
    public TriggerSource getTriggerSource() {
        Object obj = parameters.get(PerformInteractionResponse.triggerSource);
        if (obj instanceof TriggerSource) {
            return (TriggerSource) obj;
        } else if (obj instanceof String) {
            TriggerSource theCode = null;
            try {
                theCode = TriggerSource.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + PerformInteractionResponse.triggerSource, e);
            }
            return theCode;
        }
        return null;
    }
    /**
     * <p>Sets TriggerSource<br/>
     * Indicates whether command was selected via VR or via a menu selection (using the OK button).</p>    
     * @param triggerSource a TriggerSource object
     */    
    public void setTriggerSource( TriggerSource triggerSource ) {
        if (triggerSource != null) {
            parameters.put(PerformInteractionResponse.triggerSource, triggerSource );
        } else {
            parameters.remove(PerformInteractionResponse.triggerSource);
        }
    }
    
    public void setManualTextEntry(String manualTextEntry) {
        if (manualTextEntry != null) {
            parameters.put(PerformInteractionResponse.manualTextEntry, manualTextEntry);
        } else {
            parameters.remove(PerformInteractionResponse.manualTextEntry);
        }
    }
    public String getManualTextEntry() {
        return (String) parameters.get(PerformInteractionResponse.manualTextEntry);
    }    
}
