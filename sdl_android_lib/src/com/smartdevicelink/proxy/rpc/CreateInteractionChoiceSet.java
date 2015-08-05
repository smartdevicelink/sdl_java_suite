package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

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

	/**
	 * Constructs a new CreateInteractionChoiceSet object
	 */    
	public CreateInteractionChoiceSet() {
        super(FunctionID.CREATE_INTERACTION_CHOICE_SET.toString());
    }
	/**
	 * Constructs a new CreateInteractionChoiceSet object indicated by the
	 * Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */	
    public CreateInteractionChoiceSet(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Gets the Choice Set unique ID
	 * 
	 * @return Integer -an Integer representing the Choice Set ID
	 */    
    public Integer getInteractionChoiceSetID() {
        return (Integer) parameters.get( KEY_INTERACTION_CHOICE_SET_ID );
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
        if (interactionChoiceSetID != null) {
            parameters.put(KEY_INTERACTION_CHOICE_SET_ID, interactionChoiceSetID );
        } else {
        	parameters.remove(KEY_INTERACTION_CHOICE_SET_ID);
        }
    }
	/**
	 * Gets Choice Set Array of one or more elements
	 * 
	 * @return List<Choice> -a List<Choice> representing the array of one or
	 *         more elements
	 */   
    @SuppressWarnings("unchecked") 
    public List<Choice> getChoiceSet() {
        if (parameters.get(KEY_CHOICE_SET) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_CHOICE_SET);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof Choice) {
	                return (List<Choice>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<Choice> newList = new ArrayList<Choice>();
	                for (Object hashObj : list) {
	                    newList.add(new Choice((Hashtable<String, Object>)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
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
        if (choiceSet != null) {
            parameters.put(KEY_CHOICE_SET, choiceSet );
        } else {
        	parameters.remove(KEY_CHOICE_SET);
        }
    }
}
