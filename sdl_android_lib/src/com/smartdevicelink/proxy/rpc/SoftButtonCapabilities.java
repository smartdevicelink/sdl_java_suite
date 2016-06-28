package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;

/**
 * <p>Contains information about a SoftButton's capabilities.</p>
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>shortPressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports a short press.
 *					Whenever the button is pressed short, onButtonPressed( SHORT) will be invoked.
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>longPressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports a LONG press.
 * 					Whenever the button is pressed long, onButtonPressed( LONG) will be invoked.
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>upDownAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports "button down" and "button up". Whenever the button is pressed, onButtonEvent( DOWN) will be invoked.
 *					Whenever the button is released, onButtonEvent( UP) will be invoked. * 			
 *			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>imageSupported</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports referencing a static or dynamic image.
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class SoftButtonCapabilities extends RPCStruct {
	public static final String KEY_IMAGE_SUPPORTED = "imageSupported";
	public static final String KEY_SHORT_PRESS_AVAILABLE = "shortPressAvailable";
	public static final String KEY_LONG_PRESS_AVAILABLE = "longPressAvailable";
	public static final String KEY_UP_DOWN_AVAILABLE = "upDownAvailable";

	/**
	 * Constructs a newly allocated SoftButtonCapabilities object
	 */
    public SoftButtonCapabilities() { }
    
    /**
     * Constructs a newly allocated SoftButtonCapabilities object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public SoftButtonCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    /**
     * set the button supports a short press.
     * @param shortPressAvailable whether the button supports a short press.
     */
    public void setShortPressAvailable(Boolean shortPressAvailable) {
        if (shortPressAvailable != null) {
            store.put(KEY_SHORT_PRESS_AVAILABLE, shortPressAvailable);
        } else {
        	store.remove(KEY_SHORT_PRESS_AVAILABLE);
        }
    }
    
    /**
     * get whether the button supports a short press.
     * @return whether the button supports a short press
     */
    public Boolean getShortPressAvailable() {
        return (Boolean) store.get( KEY_SHORT_PRESS_AVAILABLE);
    }
    
    /**
     * set the button supports a LONG press.
     * @param longPressAvailable whether the button supports a long press
     */
    public void setLongPressAvailable(Boolean longPressAvailable) {
        if (longPressAvailable != null) {
            store.put(KEY_LONG_PRESS_AVAILABLE, longPressAvailable);
        } else {
        	store.remove(KEY_LONG_PRESS_AVAILABLE);
        }
    }
    
    /**
     * get whether  the button supports a LONG press.
     * @return whether  the button supports a LONG press
     */
    public Boolean getLongPressAvailable() {
        return (Boolean) store.get( KEY_LONG_PRESS_AVAILABLE);
    }
    
    /**
     * set the button supports "button down" and "button up". 
     * @param upDownAvailable the button supports "button down" and "button up". 
     */
    public void setUpDownAvailable(Boolean upDownAvailable) {
        if (upDownAvailable != null) {
            store.put(KEY_UP_DOWN_AVAILABLE, upDownAvailable);
        } else {
        	store.remove(KEY_UP_DOWN_AVAILABLE);
        }
    }
    
    /**
     * get the button supports "button down" and "button up".
     * @return the button supports "button down" and "button up".
     */
    public Boolean getUpDownAvailable() {
        return (Boolean) store.get( KEY_UP_DOWN_AVAILABLE);
    }
    
    /**
     * set the button supports referencing a static or dynamic image.
     * @param imageSupported whether the button supports referencing a static or dynamic image.
     */
    public void setImageSupported(Boolean imageSupported) {
        if (imageSupported != null) {
            store.put(KEY_IMAGE_SUPPORTED, imageSupported);
        } else {
        	store.remove(KEY_IMAGE_SUPPORTED);
        }
    }
    
    /**
     * get the button supports referencing a static or dynamic image.
     * @return the button supports referencing a static or dynamic image.
     */
    public Boolean getImageSupported() {
        return (Boolean) store.get( KEY_IMAGE_SUPPORTED);
    }
}
