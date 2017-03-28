package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * <p>This RPC is used to update the user with navigation information for the constantly shown screen (base screen), but
 * also for the alert type screen</p>
 * 
 * <p>Function Group: Navigation</p>
 * 
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 * 
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

    /**
     * Constructs a new ShowConstantTbt object
     */
    public ShowConstantTbt(){
        super(FunctionID.SHOW_CONSTANT_TBT.toString());
    }

    /**
     * Constructs a new ShowConstantTbt object indicated by the Hashtable parameter
     * <p>
     * 
     * @param hash
     *            The Hashtable to use
     */
    public ShowConstantTbt(Hashtable<String, Object> hash){
        super(hash);
    }

    /**
     * Sets a text for navigation text field 1
     * 
     * @param navigationText1
     *            a String value representing a text for navigation text field 1
     *            <p></p>
     *            <b>Notes: </b>Maxlength=500
     */
    public void setNavigationText1(String navigationText1){
        if(navigationText1 != null){
            parameters.put(KEY_TEXT1, navigationText1);
        }
        else{
            parameters.remove(KEY_TEXT1);
        }
    }

    /**
     * Gets a text for navigation text field 1
     * 
     * @return String -a String value representing a text for navigation text field 1
     */
    public String getNavigationText1(){
        return (String) parameters.get(KEY_TEXT1);
    }

    /**
     * Sets a text for navigation text field 2
     * 
     * @param navigationText2
     *            a String value representing a text for navigation text field 2
     *            <p></p>
     *            <b>Notes: </b>Maxlength=500
     */
    public void setNavigationText2(String navigationText2){
        if(navigationText2 != null){
            parameters.put(KEY_TEXT2, navigationText2);
        }
        else{
            parameters.remove(KEY_TEXT2);
        }
    }

    /**
     * Gets a text for navigation text field 2
     * 
     * @return String -a String value representing a text for navigation text field 2
     */
    public String getNavigationText2(){
        return (String) parameters.get(KEY_TEXT2);
    }

    /**
     * Sets a text field for estimated time of arrival
     * 
     * @param eta
     *            a String value representing a text field for estimated time of arrival
     *            <p></p>
     *            <b>Notes: </b>Maxlength=500
     */
    public void setEta(String eta){
        if(eta != null){
            parameters.put(KEY_ETA, eta);
        }
        else{
            parameters.remove(KEY_ETA);
        }
    }

    /**
     * Gets a text field for estimated time of arrival
     * 
     * @return String -a String value representing a text field for estimated time of arrival
     */
    public String getEta(){
        return (String) parameters.get(KEY_ETA);
    }

    /**
     * Sets a text field for total distance
     * 
     * @param totalDistance
     *            a String value representing a text field for total distance
     *            <p></p>
     *            <b>Notes: </b>Maxlength=500
     */
    public void setTotalDistance(String totalDistance){
        if(totalDistance != null){
            parameters.put(KEY_TOTAL_DISTANCE, totalDistance);
        }
        else{
            parameters.remove(KEY_TOTAL_DISTANCE);
        }
    }

    /**
     * Gets a text field for total distance
     * 
     * @return String -a String value representing a text field for total distance
     */
    public String getTotalDistance(){
        return (String) parameters.get(KEY_TOTAL_DISTANCE);
    }

    /**
     * Sets an Image for turnicon
     * 
     * @param turnIcon
     *            an Image value
     */
    public void setTurnIcon(Image turnIcon){
        if(turnIcon != null){
            parameters.put(KEY_MANEUVER_IMAGE, turnIcon);
        }
        else{
            parameters.remove(KEY_MANEUVER_IMAGE);
        }
    }

    /**
     * Gets an Image for turnicon
     * 
     * @return Image -an Image value representing an Image for turnicon
     */
    @SuppressWarnings("unchecked")
    public Image getTurnIcon(){
        Object obj = parameters.get(KEY_MANEUVER_IMAGE);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
            return new Image((Hashtable<String, Object>) obj);
        }
        return null;
    }

    /**
     * Sets an Image for nextTurnIcon
     * 
     * @param nextTurnIcon
     *            an Image value
     */
    public void setNextTurnIcon(Image nextTurnIcon){
        if(nextTurnIcon != null){
            parameters.put(KEY_NEXT_MANEUVER_IMAGE, nextTurnIcon);
        }
        else{
            parameters.remove(KEY_NEXT_MANEUVER_IMAGE);
        }
    }

    /**
     * Gets an Image for nextTurnIcon
     * 
     * @return Image -an Image value representing an Image for nextTurnIcon
     */
    @SuppressWarnings("unchecked")
    public Image getNextTurnIcon(){
        Object obj = parameters.get(KEY_NEXT_MANEUVER_IMAGE);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
            return new Image((Hashtable<String, Object>) obj);
        }
        return null;
    }

    /**
     * Sets a Fraction of distance till next maneuver
     * 
     * @param distanceToManeuver
     *            a Double value representing a Fraction of distance till next maneuver
     *            <p></p>
     *            <b>Notes: </b>Minvalue=0; Maxvalue=1000000000
     */
    public void setDistanceToManeuver(Double distanceToManeuver){
        if(distanceToManeuver != null){
            parameters.put(KEY_MANEUVER_DISTANCE, distanceToManeuver);
        }
        else{
            parameters.remove(KEY_MANEUVER_DISTANCE);
        }
    }

    /**
     * Gets a Fraction of distance till next maneuver
     * 
     * @return Double -a Double value representing a Fraction of distance till next maneuver
     */
    public Double getDistanceToManeuver(){
        return (Double) parameters.get(KEY_MANEUVER_DISTANCE);
    }

    /**
     * Sets a Distance till next maneuver (starting from) from previous maneuver
     * 
     * @param distanceToManeuverScale
     *            a Double value representing a Distance till next maneuver (starting from) from previous maneuver
     *            <p></p>
     *            <b>Notes: </b>Minvalue=0; Maxvalue=1000000000
     */
    public void setDistanceToManeuverScale(Double distanceToManeuverScale){
        if(distanceToManeuverScale != null){
            parameters.put(KEY_MANEUVER_DISTANCE_SCALE, distanceToManeuverScale);
        }
        else{
            parameters.remove(KEY_MANEUVER_DISTANCE_SCALE);
        }
    }

    /**
     * Gets a Distance till next maneuver (starting from) from previous maneuver
     * 
     * @return Double -a Double value representing a Distance till next maneuver (starting from) from previous maneuver
     */
    public Double getDistanceToManeuverScale(){
        return (Double) parameters.get(KEY_MANEUVER_DISTANCE_SCALE);
    }

    /**
     * <p>Sets a maneuver complete flag. If and when a maneuver has completed while an AlertManeuver is active, the app
     * must send this value set to TRUE in order to clear the AlertManeuver overlay
     * If omitted the value will be assumed as FALSE</p>
     * 
     * 
     * @param maneuverComplete
     *            a Boolean value
     */
    public void setManeuverComplete(Boolean maneuverComplete){
        if(maneuverComplete != null){
            parameters.put(KEY_MANEUVER_COMPLETE, maneuverComplete);
        }
        else{
            parameters.remove(KEY_MANEUVER_COMPLETE);
        }
    }

    /**
     * Gets a maneuver complete flag
     * 
     * @return Boolean -a Boolean value
     */
    public Boolean getManeuverComplete(){
        return (Boolean) parameters.get(KEY_MANEUVER_COMPLETE);
    }

    /**
     * <p>Sets Three dynamic SoftButtons available (first SoftButton is fixed to "Turns"). If omitted on supported
     * displays, the currently displayed SoftButton values will not change</p>
     * 
     * <p><b>Notes: </b>Minsize=0; Maxsize=3</p>
     * 
     * @param softButtons
     *            a List<SoftButton> value
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
     * Gets Three dynamic SoftButtons available (first SoftButton is fixed to "Turns"). If omitted on supported
     * displays, the currently displayed SoftButton values will not change
     * 
     * @return Vector<SoftButton> -a Vector<SoftButton> value
     */
    @SuppressWarnings("unchecked")
    public List<SoftButton> getSoftButtons(){
        if (parameters.get(KEY_SOFT_BUTTONS) instanceof List<?>) {
            List<?> list = (List<?>)parameters.get(KEY_SOFT_BUTTONS);
            if (list != null && list.size() > 0) {
                Object obj = list.get(0);
                if (obj instanceof SoftButton) {
                    return (List<SoftButton>) list;
                } else if (obj instanceof Hashtable) {
                    List<SoftButton> newList = new ArrayList<SoftButton>();
                    for (Object hashObj : list) {
                        newList.add(new SoftButton((Hashtable<String, Object>)hashObj));
                    }
                    return newList;
                }
            }
        }
        return null;
    }

    public void setTimeToDestination(String timeToDestination){
        if(timeToDestination != null){
            parameters.put(KEY_TIME_TO_DESTINATION, timeToDestination);
        }
        else{
            parameters.remove(KEY_TIME_TO_DESTINATION);
        }
    }

    public String getTimeToDestination(){
        return (String) parameters.get(KEY_TIME_TO_DESTINATION);
    }
}
