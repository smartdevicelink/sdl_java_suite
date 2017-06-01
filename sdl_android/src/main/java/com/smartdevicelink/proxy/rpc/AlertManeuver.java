package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;
import java.util.List;

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
        return (List<SoftButton>) getObject(SoftButton.class, KEY_SOFT_BUTTONS);
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
        setParameter(KEY_SOFT_BUTTONS, softButtons);
    }

    /**
     * Gets TTSChunk[], the Array of type TTSChunk which, taken together, specify what is to be spoken to the user
     * 
     * @return List -a List<TTSChunk> value specify what is to be spoken to the user
     */
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTtsChunks(){
        return (List<TTSChunk>) getObject(TTSChunk.class, KEY_TTS_CHUNKS);
    }

    /**
     * Sets array of type TTSChunk which, taken together, specify what is to be spoken to the user
     * 
     * @param ttsChunks
     *            <p>
     *            <b>Notes: </b></p>Array must have a least one element
     */
    public void setTtsChunks(List<TTSChunk> ttsChunks){
        setParameter(KEY_TTS_CHUNKS, ttsChunks);
    }

}
