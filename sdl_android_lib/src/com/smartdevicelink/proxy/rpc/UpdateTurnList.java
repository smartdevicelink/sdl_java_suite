package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * Updates the list of next maneuvers, which can be requested by the user pressing the softbutton "Turns" on the
 * Navigation base screen. Three softbuttons are predefined by the system: Up, Down, Close
 * <p>
 * Function Group: Navigation
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 * @see ShowConstantTbt
 */
public class UpdateTurnList extends RPCRequest{
    public static final String KEY_TURN_LIST = "turnList";
    public static final String KEY_SOFT_BUTTONS = "softButtons";

    private List<Turn> turnList;
    private List<SoftButton> softButtons;
    
    /**
     * Constructs a new UpdateTurnList object
     */
    public UpdateTurnList() {
        super(FunctionID.UPDATE_TURN_LIST);
    }
    
    /**
     * Creates an UpdateTurnList object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public UpdateTurnList(JSONObject jsonObject){
        super(SdlCommand.UPDATE_TURN_LIST, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            List<JSONObject> turnListObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_TURN_LIST);
            if(turnListObjs != null){
                this.turnList = new ArrayList<Turn>(turnListObjs.size());
                for(JSONObject turnListObj : turnListObjs){
                    this.turnList.add(new Turn(turnListObj));
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
     * Sets a list of turns to be shown to the user
     * 
     * @param turnList
     *            a List<Turn> value representing a list of turns to be shown to the user
     *            <p>
     *            <b>Notes: </b>Minsize=1; Maxsize=100
     */
    public void setTurnList(List<Turn> turnList){
        this.turnList = turnList;
    }
    
    /**
     * Gets a list of turns to be shown to the user
     * 
     * @return List<Turn> -a List value representing a list of turns
     */
    public List<Turn> getTurnList(){
        return this.turnList;
    }

    /**
     * Gets the SoftButton List object
     * 
     * @return List<SoftButton> -a List<SoftButton> representing the List object
     * @since SmartDeviceLink 2.0
     */
    public List<SoftButton> getSoftButtons(){
        return this.softButtons;
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
            JsonUtils.addToJsonObject(result, KEY_TURN_LIST, (this.turnList == null) ? null : 
                JsonUtils.createJsonArrayOfJsonObjects(this.turnList, sdlVersion));
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
        result = prime * result + ( ( turnList == null ) ? 0 : turnList.hashCode() );
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
        UpdateTurnList other = (UpdateTurnList) obj;
        if(softButtons == null){
            if(other.softButtons != null){
                return false;
            }
        }
        else if(!softButtons.equals(other.softButtons)){
            return false;
        }
        if(turnList == null){
            if(other.turnList != null){
                return false;
            }
        }
        else if(!turnList.equals(other.turnList)){
            return false;
        }
        return true;
    }

}
