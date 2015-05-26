package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

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

	/**
	 * Constructs a new DeleteInteractionChoiceSet object
	 */
    public DeleteInteractionChoiceSet() {
        super(FunctionID.DELETE_INTERACTION_CHOICE_SET.toString());
    }
	/**
	 * Constructs a new DeleteInteractionChoiceSet object indicated by the
	 * Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */    
    public DeleteInteractionChoiceSet(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Gets a unique ID that identifies the Choice Set
	 * @return Integer -an Integer value representing the unique Choice Set ID
	 */    
    public Integer getInteractionChoiceSetID() {
        return (Integer) parameters.get( KEY_INTERACTION_CHOICE_SET_ID );
    }
	/**
	 * Sets a unique ID that identifies the Choice Set
	 * @param interactionChoiceSetID a unique ID that identifies the Choice Set
	 * <p>
	 * <b>Notes: </b>Min Value: 0; Max Value: 2000000000
	 */    
    public void setInteractionChoiceSetID( Integer interactionChoiceSetID ) {
        if (interactionChoiceSetID != null) {
            parameters.put(KEY_INTERACTION_CHOICE_SET_ID, interactionChoiceSetID );
        } else {
            parameters.remove(KEY_INTERACTION_CHOICE_SET_ID);
        }
    }
}
