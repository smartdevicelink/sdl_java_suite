package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Updates the list of next maneuvers, which can be requested by the user pressing the softbutton "Turns" on the
 * Navigation base screen. Three softbuttons are predefined by the system: Up, Down, Close
 * 
 * <p>Function Group: Navigation</p>
 * 
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 * 
 * 
 * @since SmartDeviceLink 2.0
 * @see ShowConstantTbt
 */
public class UpdateTurnList extends RPCRequest{
    public static final String KEY_TURN_LIST = "turnList";
    public static final String KEY_SOFT_BUTTONS = "softButtons";

    /**
     * Constructs a new UpdateTurnList object
     */
    public UpdateTurnList() {
        super(FunctionID.UPDATE_TURN_LIST.toString());
    }
    
    /**
    * <p>
    * Constructs a new UpdateTurnList object indicated by the Hashtable
    * parameter
    * </p>
    * 
    * @param hash
    *            The Hashtable to use
    */
    public UpdateTurnList(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    /**
     * Sets a list of turns to be shown to the user
     * 
     * @param turnList
     *            a List<Turn> value representing a list of turns to be shown to the user
     *            <p>
     *            <b>Notes: </b>Minsize=1; Maxsize=100</p>
     */
    public void setTurnList(List<Turn> turnList){
        if(turnList != null){
            parameters.put(KEY_TURN_LIST, turnList);
        }
        else{
            parameters.remove(KEY_TURN_LIST);
        }
    }
    
    /**
     * Gets a list of turns to be shown to the user
     * 
     * @return List<Turn> -a List value representing a list of turns
     */
    @SuppressWarnings("unchecked")
    public List<Turn> getTurnList(){
        if(parameters.get(KEY_TURN_LIST) instanceof List<?>){
            List<?> list = (List<?>) parameters.get(KEY_TURN_LIST);
            if(list != null && list.size() > 0){
                Object obj = list.get(0);
                if(obj instanceof Turn){
                    return (List<Turn>) list;
                }
                else if(obj instanceof Hashtable){
                    List<Turn> newList = new ArrayList<Turn>();
                    for(Object hashObj : list){
                        newList.add(new Turn((Hashtable<String, Object>) hashObj));
                    }
                    return newList;
                }
            }
        }
        return null;
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

}
