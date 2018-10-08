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
 * 			<td><ul><li>minvalue="0"</li><li>maxvalue="255"</li></ul></td>
 * 			<td>SmartDeviceLink 5.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>green</td>
 * 			<td>Integer</td>
 * 			<td>Y</td>
 * 			<td><ul><li>minvalue="0"</li><li>maxvalue="255"</li></ul></td>
 * 			<td>SmartDeviceLink 5.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>blue</td>
 * 			<td>Integer</td>
 * 			<td>Y</td>
 * 			<td><ul><li>minvalue="0"</li><li>maxvalue="255"</li></ul></td>
 * 			<td>SmartDeviceLink 5.0</td>
 * 		</tr>
 * </table>
 * @since SmartDeviceLink 5.0
 */
public class RGBColor extends RPCStruct{
    public static final String KEY_RED = "red";
    public static final String KEY_GREEN = "green";
    public static final String KEY_BLUE = "blue";
    private static final Integer MIN_VALUE = 0, MAX_VALUE = 255;

    /**
     * Constructs a new RGBColor object
     */
    public RGBColor(){
        this(MIN_VALUE, MIN_VALUE, MIN_VALUE);
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
        if (red != null && red >= MIN_VALUE && red <= MAX_VALUE) {
            hash.put(KEY_RED, red);
        } else {
            hash.put(KEY_RED, MIN_VALUE);
        }
        if (green != null && green >= MIN_VALUE && green <= MAX_VALUE) {
            hash.put(KEY_GREEN, green);
        } else {
            hash.put(KEY_GREEN, MIN_VALUE);
        }
        if (blue != null && blue >= MIN_VALUE && blue <= MAX_VALUE) {
            hash.put(KEY_BLUE, blue);
        } else {
            hash.put(KEY_BLUE, MIN_VALUE);
        }
        this.store = hash;
    }

    /**
     * Sets the red value of the color object
     * @param color red value - min: 0; max: 255
     */
    public void setRed(Integer color) {
        if (color != null && color >= MIN_VALUE && color <= MAX_VALUE) {
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
        if (color != null && color >= MIN_VALUE && color <= MAX_VALUE) {
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
        if (color != null && color >= MIN_VALUE && color <= MAX_VALUE) {
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
