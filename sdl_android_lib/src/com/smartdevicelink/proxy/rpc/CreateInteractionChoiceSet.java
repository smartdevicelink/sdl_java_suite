package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;

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

	/**
	 * Constructs a new CreateInteractionChoiceSet object
	 */    
	public CreateInteractionChoiceSet() {
        super("CreateInteractionChoiceSet");
    }
	/**
	 * Constructs a new CreateInteractionChoiceSet object indicated by the
	 * Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */	
    public CreateInteractionChoiceSet(Hashtable hash) {
        super(hash);
    }
	/**
	 * Gets the Choice Set unique ID
	 * 
	 * @return Integer -an Integer representing the Choice Set ID
	 */    
    public Integer getInteractionChoiceSetID() {
        return (Integer) parameters.get( Names.interactionChoiceSetID );
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
            parameters.put(Names.interactionChoiceSetID, interactionChoiceSetID );
        }
    }
	/**
	 * Gets Choice Set Array of one or more elements
	 * 
	 * @return Vector<Choice> -a Vector<Choice> representing the array of one or
	 *         more elements
	 */    
    public Vector<Choice> getChoiceSet() {
        if (parameters.get(Names.choiceSet) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)parameters.get(Names.choiceSet);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof Choice) {
	                return (Vector<Choice>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<Choice> newList = new Vector<Choice>();
	                for (Object hashObj : list) {
	                    newList.add(new Choice((Hashtable)hashObj));
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
	 *            a Vector<Choice> representing the array of one or more
	 *            elements
	 *            <p>
	 *            <b>Notes: </b>Min Value: 1; Max Value: 100
	 */    
    public void setChoiceSet( Vector<Choice> choiceSet ) {
        if (choiceSet != null) {
            parameters.put(Names.choiceSet, choiceSet );
        }
    }
}
