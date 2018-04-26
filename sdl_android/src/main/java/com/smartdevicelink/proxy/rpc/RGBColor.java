package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import java.util.Hashtable;

/**
 *  A color class that stores RGB values
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 		    <th>Reg.</th>
 * 		    <th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>red</td>
 * 			<td>Integer</td>
 * 			<td>Y</td>
 * 			<td>
 * 					<ul>
 * 					<li>minvalue="0"</li>
 * 				    <li>maxvalue="255"</li>
 *					</ul>
 *			</td>
 * 			<td>SmartDeviceLink 4.7</td>
 * 		</tr>
 * 		<tr>
 * 			<td>green</td>
 * 			<td>Integer</td>
 * 			<td>Y</td>
 * 			<td>
 * 					<ul>
 * 					<li>minvalue="0"</li>
 * 				    <li>maxvalue="255"</li>
 *					</ul>
 *			</td>
 * 			<td>SmartDeviceLink 4.7</td>
 * 		</tr>
 * 		<tr>
 * 			<td>blue</td>
 * 			<td>Integer</td>
 * 			<td>Y</td>
 * 			<td>
 * 					<ul>
 * 					<li>minvalue="0"</li>
 * 				    <li>maxvalue="255"</li>
 *					</ul>
 *			</td>
 * 			<td>SmartDeviceLink 4.7</td>
 * 		</tr>
 * </table>
 * @since SmartDeviceLink 4.7
 */
public class RGBColor extends RPCStruct{
    public static final String KEY_RED = "red";
    public static final String KEY_GREEN = "green";
    public static final String KEY_BLUE = "blue";

    public RGBColor(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new RGB object
     * @param red red value
     * @param green green value
     * @param blue blue value
     */
    public RGBColor(Integer red, Integer green, Integer blue) {
        Hashtable<String, Object> hash = new Hashtable<>();
        if (red != null) {
            hash.put(KEY_RED, red);
        }
        if (green != null) {
            hash.put(KEY_GREEN, green);
        }
        if (blue != null) {
            hash.put(KEY_BLUE, blue);
        }
        this.store = hash;
    }

    /**
     * Sets the red value of the color object
     * @param color red value
     */
    public void setRed(Integer color) {
        setValue(KEY_RED, color);
    }

    /**
     * Gets the red value of the color
     * @return red value
     */
    public Integer getRed() {
        return getInteger(KEY_RED);
    }

    /**
     * Sets the green value of the color object
     * @param color green value
     */
    public void setGreen(Integer color) {
        setValue(KEY_GREEN, color);
    }

    /**
     * Gets the green value of the color
     * @return green value
     */
    public Integer getGreen() {
        return getInteger(KEY_GREEN);
    }

    /**
     * Sets the blue value of the color object
     * @param color blue value
     */
    public void setBlue(Integer color) {
        setValue(KEY_BLUE, color);
    }

    /**
     * Gets the green value of the color
     * @return green value
     */
    public Integer getBlue() {
        return getInteger(KEY_BLUE);
    }
}
