package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * <p>This will bring up an alert with information related to the next navigation maneuver including potential voice
 * navigation instructions. Shown information will be taken from the ShowConstantTBT function
 * </p>
 * <p>Function Group: Navigation</p>
 * 
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 * 
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
     * 
     * <p>Constructs a new AlertManeuver object indicated by the Hashtable parameter</p>
     * 
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
                Object obj = list.get(0);
                if(obj instanceof SoftButton){
                    return (List<SoftButton>) list;
                }
                else if(obj instanceof Hashtable){
                    List<SoftButton> newList = new ArrayList<SoftButton>();
                    for(Object hashObj : list){
                        newList.add(new SoftButton((Hashtable<String, Object>) hashObj));
                    }
                    return newList;
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
     *            <b>Notes: </b></p>
     *            <ul>
     *            <li>If omitted on supported displays, the alert will not have any SoftButton</li>
     *            <li>ArrayMin: 0</li>
     *            <li>ArrayMax: 4</li>
     *            </ul>
     * @since SmartDeviceLink 2.0
     */

    public void setSoftButtons(List<SoftButton> softButtons){
        if(softButtons != null){
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
                Object obj = list.get(0);
                if(obj instanceof TTSChunk){
                    return (List<TTSChunk>) list;
                }
                else if(obj instanceof Hashtable){
                    List<TTSChunk> newList = new ArrayList<TTSChunk>();
                    for(Object hashObj : list){
                        newList.add(new TTSChunk((Hashtable<String, Object>) hashObj));
                    }
                    return newList;
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
     *            <b>Notes: </b></p>Array must have a least one element
     */
    public void setTtsChunks(List<TTSChunk> ttsChunks){
        if(ttsChunks != null){
            parameters.put(KEY_TTS_CHUNKS, ttsChunks);
        }
        else{
            parameters.remove(KEY_TTS_CHUNKS);
        }
    }

}
