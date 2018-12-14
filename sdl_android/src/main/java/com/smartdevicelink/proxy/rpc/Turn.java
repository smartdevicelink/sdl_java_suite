package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;

import java.util.Hashtable;

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
        setValue(KEY_NAVIGATION_TEXT, navigationText);
    }

    /**
     * get the text to describe the turn (e.g. streetname)
     * 
     * @return the text to describe the turn (e.g. streetname)
     */
    public String getNavigationText(){
        return getString(KEY_NAVIGATION_TEXT);
    }

    /**
     * set Image to be shown for a turn
     * 
     * @param turnIcon
     *            the image to be shown for a turn
     */
    public void setTurnIcon(Image turnIcon){
        setValue(KEY_TURN_IMAGE, turnIcon);
    }

    /**
     * get the image to be shown for a turn
     * 
     * @return the image to be shown for a turn
     */
    @SuppressWarnings("unchecked")
    public Image getTurnIcon(){
        return (Image) getObject(Image.class, KEY_TURN_IMAGE);
    }

}
