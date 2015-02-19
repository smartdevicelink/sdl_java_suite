package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * This RPC is used to update the user with navigation information for the constantly shown screen (base screen), but
 * also for the alert type screen
 * <p>
 * Function Group: Navigation
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 * @see AlertManeuver
 * @see UpdateTurnList
 */
public class ShowConstantTbt extends RPCRequest{

    public static final String KEY_TEXT1                   = "navigationText1";
    public static final String KEY_TEXT2                   = "navigationText2";
    public static final String KEY_ETA                     = "eta";
    public static final String KEY_TOTAL_DISTANCE          = "totalDistance";
    public static final String KEY_MANEUVER_DISTANCE       = "distanceToManeuver";
    public static final String KEY_MANEUVER_DISTANCE_SCALE = "distanceToManeuverScale";
    public static final String KEY_MANEUVER_IMAGE          = "turnIcon";
    public static final String KEY_NEXT_MANEUVER_IMAGE     = "nextTurnIcon";
    public static final String KEY_MANEUVER_COMPLETE       = "maneuverComplete";
    public static final String KEY_SOFT_BUTTONS            = "softButtons";
    public static final String KEY_TIME_TO_DESTINATION     = "timeToDestination";

    private String navigationText1, navigationText2, eta, totalDistance, timeToDestination;
    private Double distanceToManeuver, distanceToManeuverScale;
    private Boolean maneuverComplete;
    private Image turnIcon, nextTurnIcon;
    private List<SoftButton> softButtons;
    
    /**
     * Constructs a new ShowConstantTbt object
     */
    public ShowConstantTbt(){
        super(FunctionID.SHOW_CONSTANT_TBT);
    }
    
    public ShowConstantTbt(JSONObject jsonObject){
        super(SdlCommand.ADD_COMMAND, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.navigationText1 = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TEXT1);
            this.navigationText2 = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TEXT1);
            this.eta = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TEXT1);
            this.totalDistance = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TEXT1);
            this.timeToDestination = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TEXT1);
            this.distanceToManeuver = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_TEXT1);
            this.distanceToManeuverScale = JsonUtils.readDoubleFromJsonObject(jsonObject, KEY_TEXT1);
            this.maneuverComplete = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_TEXT1);
            
            JSONObject imageObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_MANEUVER_IMAGE);
            if(imageObj != null){
                this.turnIcon = new Image(imageObj);
            }
            
            imageObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_NEXT_MANEUVER_IMAGE);
            if(imageObj != null){
                this.nextTurnIcon = new Image(imageObj);
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
     * Sets a text for navigation text field 1
     * 
     * @param navigationText1
     *            a String value representing a text for navigation text field 1
     *            <p>
     *            <b>Notes: </b>Maxlength=500
     */
    public void setNavigationText1(String navigationText1){
        this.navigationText1 = navigationText1;
    }

    /**
     * Gets a text for navigation text field 1
     * 
     * @return String -a String value representing a text for navigation text field 1
     */
    public String getNavigationText1(){
        return this.navigationText1;
    }

    /**
     * Sets a text for navigation text field 2
     * 
     * @param navigationText2
     *            a String value representing a text for navigation text field 2
     *            <p>
     *            <b>Notes: </b>Maxlength=500
     */
    public void setNavigationText2(String navigationText2){
        this.navigationText2 = navigationText2;
    }

    /**
     * Gets a text for navigation text field 2
     * 
     * @return String -a String value representing a text for navigation text field 2
     */
    public String getNavigationText2(){
        return this.navigationText2;
    }

    /**
     * Sets a text field for estimated time of arrival
     * 
     * @param eta
     *            a String value representing a text field for estimated time of arrival
     *            <p>
     *            <b>Notes: </b>Maxlength=500
     */
    public void setEta(String eta){
        this.eta = eta;
    }

    /**
     * Gets a text field for estimated time of arrival
     * 
     * @return String -a String value representing a text field for estimated time of arrival
     */
    public String getEta(){
        return eta;
    }

    /**
     * Sets a text field for total distance
     * 
     * @param totalDistance
     *            a String value representing a text field for total distance
     *            <p>
     *            <b>Notes: </b>Maxlength=500
     */
    public void setTotalDistance(String totalDistance){
        this.totalDistance = totalDistance;
    }

    /**
     * Gets a text field for total distance
     * 
     * @return String -a String value representing a text field for total distance
     */
    public String getTotalDistance(){
        return this.totalDistance;
    }

    /**
     * Sets an Image for turnicon
     * 
     * @param turnIcon
     *            an Image value
     */
    public void setTurnIcon(Image turnIcon){
        this.turnIcon = turnIcon;
    }

    /**
     * Gets an Image for turnicon
     * 
     * @return Image -an Image value representing an Image for turnicon
     */
    public Image getTurnIcon(){
        return this.turnIcon;
    }

    /**
     * Sets an Image for nextTurnIcon
     * 
     * @param nextTurnIcon
     *            an Image value
     */
    public void setNextTurnIcon(Image nextTurnIcon){
        this.nextTurnIcon = nextTurnIcon;
    }

    /**
     * Gets an Image for nextTurnIcon
     * 
     * @return Image -an Image value representing an Image for nextTurnIcon
     */
    public Image getNextTurnIcon(){
        return this.nextTurnIcon;
    }

    /**
     * Sets a Fraction of distance till next maneuver
     * 
     * @param distanceToManeuver
     *            a Double value representing a Fraction of distance till next maneuver
     *            <p>
     *            <b>Notes: </b>Minvalue=0; Maxvalue=1000000000
     */
    public void setDistanceToManeuver(Double distanceToManeuver){
        this.distanceToManeuver = distanceToManeuver;
    }

    /**
     * Gets a Fraction of distance till next maneuver
     * 
     * @return Double -a Double value representing a Fraction of distance till next maneuver
     */
    public Double getDistanceToManeuver(){
        return this.distanceToManeuver;
    }

    /**
     * Sets a Distance till next maneuver (starting from) from previous maneuver
     * 
     * @param distanceToManeuverScale
     *            a Double value representing a Distance till next maneuver (starting from) from previous maneuver
     *            <p>
     *            <b>Notes: </b>Minvalue=0; Maxvalue=1000000000
     */
    public void setDistanceToManeuverScale(Double distanceToManeuverScale){
        this.distanceToManeuverScale = distanceToManeuverScale;
    }

    /**
     * Gets a Distance till next maneuver (starting from) from previous maneuver
     * 
     * @return Double -a Double value representing a Distance till next maneuver (starting from) from previous maneuver
     */
    public Double getDistanceToManeuverScale(){
        return this.distanceToManeuverScale;
    }

    /**
     * Sets a maneuver complete flag. If and when a maneuver has completed while an AlertManeuver is active, the app
     * must send this value set to TRUE in order to clear the AlertManeuver overlay<br/>
     * If omitted the value will be assumed as FALSE
     * <p>
     * 
     * @param maneuverComplete
     *            a Boolean value
     */
    public void setManeuverComplete(Boolean maneuverComplete){
        this.maneuverComplete = maneuverComplete;
    }

    /**
     * Gets a maneuver complete flag
     * 
     * @return Boolean -a Boolean value
     */
    public Boolean getManeuverComplete(){
        return this.maneuverComplete;
    }

    /**
     * Sets Three dynamic SoftButtons available (first SoftButton is fixed to "Turns"). If omitted on supported
     * displays, the currently displayed SoftButton values will not change
     * <p>
     * <b>Notes: </b>Minsize=0; Maxsize=3
     * 
     * @param softButtons
     *            a List<SoftButton> value
     */
    public void setSoftButtons(List<SoftButton> softButtons){
        this.softButtons = softButtons;
    }

    /**
     * Gets Three dynamic SoftButtons available (first SoftButton is fixed to "Turns"). If omitted on supported
     * displays, the currently displayed SoftButton values will not change
     * 
     * @return Vector<SoftButton> -a Vector<SoftButton> value
     */
    public List<SoftButton> getSoftButtons(){
        return this.softButtons;
    }

    public void setTimeToDestination(String timeToDestination){
        this.timeToDestination = timeToDestination;
    }

    public String getTimeToDestination(){
        return this.timeToDestination;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_TEXT1, this.navigationText1);
            JsonUtils.addToJsonObject(result, KEY_TEXT2, this.navigationText2);
            JsonUtils.addToJsonObject(result, KEY_ETA, this.eta);
            JsonUtils.addToJsonObject(result, KEY_TOTAL_DISTANCE, this.totalDistance);
            JsonUtils.addToJsonObject(result, KEY_MANEUVER_DISTANCE, this.distanceToManeuver);
            JsonUtils.addToJsonObject(result, KEY_MANEUVER_DISTANCE_SCALE, this.distanceToManeuverScale);
            JsonUtils.addToJsonObject(result, KEY_MANEUVER_COMPLETE, this.maneuverComplete);
            JsonUtils.addToJsonObject(result, KEY_TIME_TO_DESTINATION, this.timeToDestination);
            
            JsonUtils.addToJsonObject(result, KEY_MANEUVER_IMAGE, (this.turnIcon == null) ? null :
                this.turnIcon.getJsonParameters(sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_NEXT_MANEUVER_IMAGE, (this.nextTurnIcon == null) ? null :
                this.nextTurnIcon.getJsonParameters(sdlVersion));
            
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
        result = prime * result + ( ( distanceToManeuver == null ) ? 0 : distanceToManeuver.hashCode() );
        result = prime * result + ( ( distanceToManeuverScale == null ) ? 0 : distanceToManeuverScale.hashCode() );
        result = prime * result + ( ( eta == null ) ? 0 : eta.hashCode() );
        result = prime * result + ( ( maneuverComplete == null ) ? 0 : maneuverComplete.hashCode() );
        result = prime * result + ( ( navigationText1 == null ) ? 0 : navigationText1.hashCode() );
        result = prime * result + ( ( navigationText2 == null ) ? 0 : navigationText2.hashCode() );
        result = prime * result + ( ( nextTurnIcon == null ) ? 0 : nextTurnIcon.hashCode() );
        result = prime * result + ( ( softButtons == null ) ? 0 : softButtons.hashCode() );
        result = prime * result + ( ( timeToDestination == null ) ? 0 : timeToDestination.hashCode() );
        result = prime * result + ( ( totalDistance == null ) ? 0 : totalDistance.hashCode() );
        result = prime * result + ( ( turnIcon == null ) ? 0 : turnIcon.hashCode() );
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
        ShowConstantTbt other = (ShowConstantTbt) obj;
        if(distanceToManeuver == null){
            if(other.distanceToManeuver != null){
                return false;
            }
        }
        else if(!distanceToManeuver.equals(other.distanceToManeuver)){
            return false;
        }
        if(distanceToManeuverScale == null){
            if(other.distanceToManeuverScale != null){
                return false;
            }
        }
        else if(!distanceToManeuverScale.equals(other.distanceToManeuverScale)){
            return false;
        }
        if(eta == null){
            if(other.eta != null){
                return false;
            }
        }
        else if(!eta.equals(other.eta)){
            return false;
        }
        if(maneuverComplete == null){
            if(other.maneuverComplete != null){
                return false;
            }
        }
        else if(!maneuverComplete.equals(other.maneuverComplete)){
            return false;
        }
        if(navigationText1 == null){
            if(other.navigationText1 != null){
                return false;
            }
        }
        else if(!navigationText1.equals(other.navigationText1)){
            return false;
        }
        if(navigationText2 == null){
            if(other.navigationText2 != null){
                return false;
            }
        }
        else if(!navigationText2.equals(other.navigationText2)){
            return false;
        }
        if(nextTurnIcon == null){
            if(other.nextTurnIcon != null){
                return false;
            }
        }
        else if(!nextTurnIcon.equals(other.nextTurnIcon)){
            return false;
        }
        if(softButtons == null){
            if(other.softButtons != null){
                return false;
            }
        }
        else if(!softButtons.equals(other.softButtons)){
            return false;
        }
        if(timeToDestination == null){
            if(other.timeToDestination != null){
                return false;
            }
        }
        else if(!timeToDestination.equals(other.timeToDestination)){
            return false;
        }
        if(totalDistance == null){
            if(other.totalDistance != null){
                return false;
            }
        }
        else if(!totalDistance.equals(other.totalDistance)){
            return false;
        }
        if(turnIcon == null){
            if(other.turnIcon != null){
                return false;
            }
        }
        else if(!turnIcon.equals(other.turnIcon)){
            return false;
        }
        return true;
    }

}
