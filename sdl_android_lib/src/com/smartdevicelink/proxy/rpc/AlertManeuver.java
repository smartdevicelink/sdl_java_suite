package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * This will bring up an alert with information related to the next navigation maneuver including potential voice
 * navigation instructions. Shown information will be taken from the ShowConstantTBT function
 * <p>
 * Function Group: Navigation
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 * @see ShowConstantTbt
 */
public class AlertManeuver extends RPCRequest{

    public static final String KEY_TTS_CHUNKS   = "ttsChunks";
    public static final String KEY_SOFT_BUTTONS = "softButtons";

    /**
     * Constructs a new AlertManeuver object
     */
    public AlertManeuver(){
        super(FunctionID.ALERT_MANEUVER.toString());
    }

    /**
     * <p>
     * Constructs a new AlertManeuver object indicated by the Hashtable parameter
     * </p>
     * 
     * @param hash
     *            The Hashtable to use
     */
    public AlertManeuver(Hashtable<String, Object> hash){
        super(hash);
    }

    /**
     * Gets the SoftButton List object
     * 
     * @return List<SoftButton> -a List<SoftButton> representing the List object
     * @since SmartDeviceLink 2.0
     */
    @SuppressWarnings("unchecked")
    public List<SoftButton> getSoftButtons(){
        if(parameters.get(KEY_SOFT_BUTTONS) instanceof List<?>){
            List<?> list = (List<?>) parameters.get(KEY_SOFT_BUTTONS);
            if(list != null && list.size() > 0){

	        	List<SoftButton> softButtonList  = new ArrayList<SoftButton>();

	        	boolean flagRaw  = false;
	        	boolean flagHash = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw SoftButton and a Hashtable value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof SoftButton) {
	        			if (flagHash) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof Hashtable) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagHash = true;
	        			softButtonList.add(new SoftButton((Hashtable<String, Object>) obj));

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<SoftButton>) list;
	        	} else if (flagHash) {
	        		return softButtonList;
	        	}
            }
        }
        return null;
    }

    /**
     * Sets the SoftButtons
     * 
     * @param softButtons
     *            a List<SoftButton> value
     *            <p>
     *            <b>Notes: </b><br/>
     *            <ul>
     *            <li>If omitted on supported displays, the alert will not have any SoftButton</li>
     *            <li>ArrayMin: 0</li>
     *            <li>ArrayMax: 4</li>
     *            </ul>
     * @since SmartDeviceLink 2.0
     */

    public void setSoftButtons(List<SoftButton> softButtons){

    	boolean valid = true;
    	
    	for ( SoftButton item : softButtons ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (softButtons != null) && (softButtons.size() > 0) && valid) {
            parameters.put(KEY_SOFT_BUTTONS, softButtons);
        }
        else{
            parameters.remove(KEY_SOFT_BUTTONS);
        }
    }

    /**
     * Gets TTSChunk[], the Array of type TTSChunk which, taken together, specify what is to be spoken to the user
     * 
     * @return List -a List<TTSChunk> value specify what is to be spoken to the user
     */
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTtsChunks(){
        if(parameters.get(KEY_TTS_CHUNKS) instanceof List<?>){
            List<?> list = (List<?>) parameters.get(KEY_TTS_CHUNKS);
            if(list != null && list.size() > 0){

            	List<TTSChunk> ttsChunkList  = new ArrayList<TTSChunk>();

            	boolean flagRaw  = false;
            	boolean flagHash = false;
            	
            	for ( Object obj : list ) {
            		
            		// This does not currently allow for a mixing of types, meaning
            		// there cannot be a raw TTSChunk and a Hashtable value in the
            		// same same list. It will not be considered valid currently.
            		if (obj instanceof TTSChunk) {
            			if (flagHash) {
            				return null;
            			}

            			flagRaw = true;

            		} else if (obj instanceof Hashtable) {
            			if (flagRaw) {
            				return null;
            			}

            			flagHash = true;
            			ttsChunkList.add(new TTSChunk((Hashtable<String, Object>) obj));

            		} else {
            			return null;
            		}

            	}

            	if (flagRaw) {
            		return (List<TTSChunk>) list;
            	} else if (flagHash) {
            		return ttsChunkList;
            	}
            }
        }
        return null;
    }

    /**
     * Sets array of type TTSChunk which, taken together, specify what is to be spoken to the user
     * 
     * @param ttsChunks
     *            <p>
     *            <b>Notes: </b>Array must have a least one element
     */
    public void setTtsChunks(List<TTSChunk> ttsChunks){

    	boolean valid = true;
    	
    	for ( TTSChunk item : ttsChunks ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (ttsChunks != null) && (ttsChunks.size() > 0) && valid) {
            parameters.put(KEY_TTS_CHUNKS, ttsChunks);
        }
        else{
            parameters.remove(KEY_TTS_CHUNKS);
        }
    }

}
