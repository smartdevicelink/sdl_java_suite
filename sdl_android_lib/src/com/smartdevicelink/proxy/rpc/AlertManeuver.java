package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

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

    public static final String KEY_TTS_CHUNKS = "ttsChunks";
    public static final String KEY_SOFT_BUTTONS = "softButtons";
    
    private List<TTSChunk> ttsChunks;
    private List<SoftButton> softButtons;
    
    /**
     * Constructs a new AlertManeuver object
     */
    public AlertManeuver() {
        super(FunctionID.ALERT_MANEUVER);
    }
    
    /**
     * Creates an AlertManeuver object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public AlertManeuver(JSONObject jsonObject){
        super(SdlCommand.ALERT_MANEUVER, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            List<JSONObject> ttsChunkObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_TTS_CHUNKS);
            if(ttsChunkObjs != null){
                this.ttsChunks = new ArrayList<TTSChunk>(ttsChunkObjs.size());
                for(JSONObject ttsChunkObj : ttsChunkObjs){
                    this.ttsChunks.add(new TTSChunk(ttsChunkObj));
                }
            }
            
            List<JSONObject> softButtonObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_SOFT_BUTTONS);
            if(softButtonObjs != null){
                this.softButtons = new ArrayList<SoftButton>(softButtonObjs.size());
                for(JSONObject softButtonObj : softButtonObjs){
                    this.softButtons.add(new SoftButton(softButtonObj));
                }
            }
            
            break;
        }
    }

    /**
     * Gets TTSChunk[], the Array of type TTSChunk which, taken together, 
     * specify what is to be spoken to the user
     * 
     * @return List -a List<TTSChunk> value specify what is to be spoken to the user
     */
    public List<TTSChunk> getTtsChunks(){
        return ttsChunks;
    }

    /**
     * Sets array of type TTSChunk which, taken together, specify what is to be spoken to the user
     * 
     * @param ttsChunks
     *            <p>
     *            <b>Notes: </b>Array must have a least one element
     */
    public void setTtsChunks(List<TTSChunk> ttsChunks){
        this.ttsChunks = ttsChunks;
    }

    /**
     * Gets the SoftButton List object
     * 
     * @return List<SoftButton> -a List<SoftButton> representing the List object
     * @since SmartDeviceLink 2.0
     */
    public List<SoftButton> getSoftButtons(){
        return softButtons;
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
        this.softButtons = softButtons;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_TTS_CHUNKS, (this.ttsChunks == null) ? null : 
                JsonUtils.createJsonArrayOfJsonObjects(this.ttsChunks, sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_SOFT_BUTTONS, (this.softButtons == null) ? null : 
                JsonUtils.createJsonArrayOfJsonObjects(this.softButtons, sdlVersion));
            break;
        }
        
        return result;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( softButtons == null ) ? 0 : softButtons.hashCode() );
        result = prime * result + ( ( ttsChunks == null ) ? 0 : ttsChunks.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        AlertManeuver other = (AlertManeuver) obj;
        if(softButtons == null){
            if(other.softButtons != null){
                return false;
            }
        }
        else if(!softButtons.equals(other.softButtons)){
            return false;
        }
        if(ttsChunks == null){
            if(other.ttsChunks != null){
                return false;
            }
        }
        else if(!ttsChunks.equals(other.ttsChunks)){
            return false;
        }
        return true;
    }
    
}
