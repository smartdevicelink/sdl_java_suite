package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;

/**
 * Describes a navigation turn including an optional icon
 * 
 * <p><b>Parameter List</p>
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
public class Turn extends RPCStruct{
    public static final String KEY_NAVIGATION_TEXT = "navigationText";
    public static final String KEY_TURN_IMAGE = "turnIcon";
    
    public Turn() { }
    public Turn(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * set the text to describe the turn (e.g. streetname)
     * 
     * @param navigationText
     *            the text to describe the turn (e.g. streetname)
     */
    public void setNavigationText(String navigationText){
        if(navigationText != null){
            store.put(KEY_NAVIGATION_TEXT, navigationText);
        }
        else{
            store.remove(KEY_NAVIGATION_TEXT);
        }
    }

    /**
     * get the text to describe the turn (e.g. streetname)
     * 
     * @return the text to describe the turn (e.g. streetname)
     */
    public String getNavigationText(){
        return (String) store.get(KEY_NAVIGATION_TEXT);
    }

    /**
     * set Image to be shown for a turn
     * 
     * @param turnIcon
     *            the image to be shown for a turn
     */
    public void setTurnIcon(Image turnIcon){
        if (turnIcon != null) {
            store.put(KEY_TURN_IMAGE, turnIcon);
        } else {
            store.remove(KEY_TURN_IMAGE);
        }
    }

    /**
     * get the image to be shown for a turn
     * 
     * @return the image to be shown for a turn
     */
    @SuppressWarnings("unchecked")
    public Image getTurnIcon(){
        Object obj = store.get(KEY_TURN_IMAGE);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
            return new Image((Hashtable<String, Object>) obj);
        }
        return null;
    }

}
