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
 * 			<th>Reg.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>red</td>
 * 			<td>Integer</td>
 * 			<td>Y</td>
 * 			<td>
 *                 <ul>
 *                 <li>minvalue="0"</li>
 *                 <li>maxvalue="255"</li>
 *                 </ul>
 * 			</td>
 * 			<td>SmartDeviceLink 4.6</td>
 * 		</tr>
 * 		<tr>
 * 			<td>green</td>
 * 			<td>Integer</td>
 * 			<td>Y</td>
 * 			<td>
 *                 <ul>
 *                 <li>minvalue="0"</li>
 *                 <li>maxvalue="255"</li>
 *                 </ul>
 * 			</td>
 * 			<td>SmartDeviceLink 4.6</td>
 * 		</tr>
 * 		<tr>
 * 			<td>blue</td>
 * 			<td>Integer</td>
 * 			<td>Y</td>
 * 			<td>
 *                 <ul>
 *                 <li>minvalue="0"</li>
 *                 <li>maxvalue="255"</li>
 *                 </ul>
 * 			</td>
 * 			<td>SmartDeviceLink 4.6</td>
 * 		</tr>
 * </table>
 * @since SmartDeviceLink 4.6
 */
public class RGBColor extends RPCStruct{
    public static final String KEY_RED = "red";
    public static final String KEY_GREEN = "green";
    public static final String KEY_BLUE = "blue";
    private static Integer minValue = 0;
    private static Integer maxValue = 255;


    /**
     * Constructs a new RGBColor object
     */
    public RGBColor(){
        this(minValue, minValue, minValue);
    }

    /**
     * Constructs a new RGBColor object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public RGBColor(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new RGB object
     * @param red red value - min: 0; max: 255
     * @param green green value - min: 0; max: 255
     * @param blue blue value - min: 0; max: 255
     */
    public RGBColor(Integer red, Integer green, Integer blue) {
        Hashtable<String, Object> hash = new Hashtable<>();
        if (red != null && red >= minValue && red <= maxValue) {
            hash.put(KEY_RED, red);
        } else {
            hash.put(KEY_RED, minValue);
        }
        if (green != null && green >= minValue && green <= maxValue) {
            hash.put(KEY_GREEN, green);
        } else {
            hash.put(KEY_GREEN, minValue);
        }
        if (blue != null && blue >= minValue && blue <= maxValue) {
            hash.put(KEY_BLUE, blue);
        } else {
            hash.put(KEY_BLUE, minValue);
        }
        this.store = hash;
    }

    /**
     * Sets the red value of the color object
     * @param color red value - min: 0; max: 255
     */
    public void setRed(Integer color) {
        if (color != null && color >= minValue && color <= maxValue) {
            setValue(KEY_RED, color);
        }
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
     * @param color green value - min: 0; max: 255
     */
    public void setGreen(Integer color) {
        if (color != null && color >= minValue && color <= maxValue) {
            setValue(KEY_GREEN, color);
        }
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
     * @param color blue value - min: 0; max: 255
     */
    public void setBlue(Integer color) {
        if (color != null && color >= minValue && color <= maxValue) {
            setValue(KEY_BLUE, color);
        }
    }

    /**
     * Gets the green value of the color
     * @return green value
     */
    public Integer getBlue() {
        return getInteger(KEY_BLUE);
    }
}
