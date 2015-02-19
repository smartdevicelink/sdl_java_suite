package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

/**
 * Describes a navigation turn including an optional icon
 * <p>
 * <b>Parameter List
 * <table border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>SmartDeviceLink Ver. Available</th>
 * </tr>
 * <tr>
 * <td>navigationText</td>
 * <td>String</td>
 * <td>Text to describe the turn (e.g. streetname)
 * <ul>
 * <li>Maxlength = 500</li>
 * </ul>
 * </td>
 * <td>SmartDeviceLink 2.0</td>
 * </tr>
 * <tr>
 * <td>turnIcon</td>
 * <td>Image</td>
 * <td>Image to be shown for a turn</td>
 * <td>SmartDeviceLink 2.0</td>
 * </tr>
 * </table>
 * 
 * @since SmartDeviceLink 2.0
 */
public class Turn extends RPCObject{
    public static final String KEY_NAVIGATION_TEXT = "navigationText";
    public static final String KEY_TURN_IMAGE = "turnIcon";
    
    private String navigationText;
    private Image turnIcon;
    
    public Turn() { }
    
    /**
     * Creates a BeltStatus object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     * @param sdlVersion The version of SDL represented in the JSON
     */
    public Turn(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.navigationText = JsonUtils.readStringFromJsonObject(jsonObject, KEY_NAVIGATION_TEXT);
            
            JSONObject imageObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_TURN_IMAGE);
            if(imageObj != null){
                this.turnIcon = new Image(imageObj);
            }
            break;
        }
    }

    /**
     * set the text to describe the turn (e.g. streetname)
     * 
     * @param navigationText
     *            the text to describe the turn (e.g. streetname)
     */
    public void setNavigationText(String navigationText){
        this.navigationText = navigationText;
    }

    /**
     * get the text to describe the turn (e.g. streetname)
     * 
     * @return the text to describe the turn (e.g. streetname)
     */
    public String getNavigationText(){
        return this.navigationText;
    }

    /**
     * set Image to be shown for a turn
     * 
     * @param turnIcon
     *            the image to be shown for a turn
     */
    public void setTurnIcon(Image turnIcon){
        this.turnIcon = turnIcon;
    }

    /**
     * get the image to be shown for a turn
     * 
     * @return the image to be shown for a turn
     */
    public Image getTurnIcon(){
        return this.turnIcon;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion) {
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_NAVIGATION_TEXT, this.navigationText);
            JsonUtils.addToJsonObject(result, KEY_TURN_IMAGE, (this.turnIcon == null) ? null :
                this.turnIcon.getJsonParameters(sdlVersion));
            break;
        }
        
        return result;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( navigationText == null ) ? 0 : navigationText.hashCode() );
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
        Turn other = (Turn) obj;
        if(navigationText == null){
            if(other.navigationText != null){
                return false;
            }
        }
        else if(!navigationText.equals(other.navigationText)){
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
