package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import java.util.Hashtable;

/**
 *  A color scheme for all display layout templates.
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 		    <th>Reg.</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>primaryColor</td>
 * 			<td>RGBColor</td>
 * 			<td>The primary "accent" color</td>
 * 			<td>N</td>
 * 			<td>SmartDeviceLink 4.6</td>
 * 		</tr>
 * 		<tr>
 * 			<td>secondaryColor</td>
 * 			<td>RGBColor</td>
 * 			<td>The secondary "accent" color</td>
 * 			<td>N</td>
 * 			<td>SmartDeviceLink 4.6</td>
 * 		</tr>
 * 		<tr>
 * 			<td>backgroundColor</td>
 * 			<td>RGBColor</td>
 * 			<td>The color of the background</td>
 * 			<td>N</td>
 * 			<td>SmartDeviceLink 4.6</td>
 * 		</tr>
 * </table>
 * @since SmartDeviceLink 4.6
 */
public class TemplateColorScheme extends RPCStruct {

    public static final String KEY_PRIMARY_COLOR = "primaryColor";
    public static final String KEY_SECONDARY_COLOR = "secondaryColor";
    public static final String KEY_BACKGROUND_COLOR = "backgroundColor";

    /**
     * Constructs a new TemplateColorScheme object
     */
    public TemplateColorScheme(){
    }

    /**
     * Constructs a new TemplateColorScheme object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public TemplateColorScheme(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the primaryColor of the scheme
     * @param color an RGBColor object representing the primaryColor
     */
    public void setPrimaryColor(RGBColor color) {
        setValue(KEY_PRIMARY_COLOR, color);
    }

    /**
     * Gets the primaryColor of the scheme
     * @return an RGBColor object representing the primaryColor
     */
    public RGBColor getPrimaryColor() {
        return (RGBColor) getObject(RGBColor.class, KEY_PRIMARY_COLOR);
    }

    /**
     * Sets the secondaryColor of the scheme
     * @param color an RGBColor object representing the secondaryColor
     */
    public void setSecondaryColor(RGBColor color) {
        setValue(KEY_SECONDARY_COLOR, color);
    }

    /**
     * Gets the secondaryColor of the scheme
     * @return an RGBColor object representing the secondaryColor
     */
    public RGBColor getSecondaryColor() {
        return (RGBColor) getObject(RGBColor.class, KEY_SECONDARY_COLOR);
    }

    /**
     * Sets the backgroundColor of the scheme
     * @param color an RGBColor object representing the backgroundColor
     */
    public void setBackgroundColor(RGBColor color) {
        setValue(KEY_BACKGROUND_COLOR, color);
    }

    /**
     * Gets the backgroundColor of the scheme
     * @return an RGBColor object representing the backgroundColor
     */
    public RGBColor getBackgroundColor() {
        return (RGBColor) getObject(RGBColor.class, KEY_BACKGROUND_COLOR);
    }
}



