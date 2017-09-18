package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.util.DebugTool;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
/**
 * Contains information about the display for the SDL system to which the application is currently connected.
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>displayType</td>
 * 			<td>DisplayType</td>
 * 			<td>The type of display
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>textField</td>
 * 			<td>TextField[]</td>
 * 			<td>An array of TextField structures, each of which describes a field in the HMI which the application can write to using operations such as <i>{@linkplain Show}</i>, <i>{@linkplain SetMediaClockTimer}</i>, etc. 
 *					 This array of TextField structures identify all the text fields to which the application can write on the current display (identified by DisplayType ).
 * 			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *     <tr>
 * 			<td>mediaClockFormats</td>
 * 			<td>MediaClockFormat[]</td>
 * 			<td>An array of MediaClockFormat elements, defining the valid string formats used in specifying the contents of the media clock field</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *     <tr>
 * 			<td>graphicSupported</td>
 * 			<td>Boolean</td>
 * 			<td>The display's persistent screen supports referencing a static or dynamic image.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * </table>
 * @since SmartDeviceLink 1.0
 * @see DisplayType
 * @see MediaClockFormat
 * @see TextFieldName
 * @see ImageType
 * 
 */
public class DisplayCapabilities extends RPCStruct {
	public static final String KEY_DISPLAY_TYPE = "displayType";
	public static final String KEY_MEDIA_CLOCK_FORMATS = "mediaClockFormats";
	public static final String KEY_TEXT_FIELDS = "textFields";
	public static final String KEY_IMAGE_FIELDS = "imageFields";
    public static final String KEY_GRAPHIC_SUPPORTED = "graphicSupported";
    public static final String KEY_SCREEN_PARAMS = "screenParams";
    public static final String KEY_TEMPLATES_AVAILABLE = "templatesAvailable";
    public static final String KEY_NUM_CUSTOM_PRESETS_AVAILABLE = "numCustomPresetsAvailable";
	/**
	 * Constructs a newly allocated DisplayCapabilities object
	 */
    public DisplayCapabilities() { }
    /**
     * Constructs a newly allocated DisplayCapabilities object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */    
    public DisplayCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Get the type of display
     * @return the type of display
     */    
    public DisplayType getDisplayType() {
        return (DisplayType) getObject(DisplayType.class, KEY_DISPLAY_TYPE);
    }
    /**
     * Set the type of display
     * @param displayType the display type
     */    
    public void setDisplayType( DisplayType displayType ) {
        setValue(KEY_DISPLAY_TYPE, displayType);
    }
    /**
     *Get an array of TextField structures, each of which describes a field in the HMI which the application can write to using operations such as <i>{@linkplain Show}</i>, <i>{@linkplain SetMediaClockTimer}</i>, etc. 
     *	 This array of TextField structures identify all the text fields to which the application can write on the current display (identified by DisplayType ).
     * @return the List of textFields
     */    
    @SuppressWarnings("unchecked")
    public List<TextField> getTextFields() {
        return (List<TextField>) getObject(TextField.class, KEY_TEXT_FIELDS);
    }
    /**
     * Set an array of TextField structures, each of which describes a field in the HMI which the application can write to using operations such as <i>{@linkplain Show}</i>, <i>{@linkplain SetMediaClockTimer}</i>, etc. 
     *	 This array of TextField structures identify all the text fields to which the application can write on the current display (identified by DisplayType ).
     * @param textFields the List of textFields
     */    
    public void setTextFields( List<TextField> textFields ) {
        setValue(KEY_TEXT_FIELDS, textFields);
    }
    
    
    
    @SuppressWarnings("unchecked")
    public List<ImageField> getImageFields() {
        return (List<ImageField>) getObject(ImageField.class, KEY_IMAGE_FIELDS);
    }
  
    public void setImageFields( List<ImageField> imageFields ) {
        setValue(KEY_IMAGE_FIELDS, imageFields);
    }    
    
    public Integer getNumCustomPresetsAvailable() {
        return getInteger(KEY_NUM_CUSTOM_PRESETS_AVAILABLE);
    }
 
    public void setNumCustomPresetsAvailable(Integer numCustomPresetsAvailable) {
        setValue(KEY_NUM_CUSTOM_PRESETS_AVAILABLE, numCustomPresetsAvailable);
    }
      
    /**
     * Get an array of MediaClockFormat elements, defining the valid string formats used in specifying the contents of the media clock field
     * @return the Veotor of mediaClockFormat
     */    
    @SuppressWarnings("unchecked")
    public List<MediaClockFormat> getMediaClockFormats() {
        return (List<MediaClockFormat>) getObject(MediaClockFormat.class, KEY_MEDIA_CLOCK_FORMATS);
    }
    /**
     * Set an array of MediaClockFormat elements, defining the valid string formats used in specifying the contents of the media clock field
     * @param mediaClockFormats the List of MediaClockFormat
     */    
    public void setMediaClockFormats( List<MediaClockFormat> mediaClockFormats ) {
        setValue(KEY_MEDIA_CLOCK_FORMATS, mediaClockFormats);
    }
    
    /**
     * set the display's persistent screen supports.
     * @param graphicSupported
     * @since SmartDeviceLink 2.0
     */
    public void setGraphicSupported(Boolean graphicSupported) {
    	setValue(KEY_GRAPHIC_SUPPORTED, graphicSupported);
    }
    
    /**
     * Get the display's persistent screen supports.
     * @return Boolean get the value of graphicSupported
     * @since SmartDeviceLink 2.0
     */
    public Boolean getGraphicSupported() {
    	return getBoolean(KEY_GRAPHIC_SUPPORTED);
    }
    
    @SuppressWarnings("unchecked")
    public List<String> getTemplatesAvailable() {
        return (List<String>) getObject(String.class, KEY_TEMPLATES_AVAILABLE);
    }   
    
    public void setTemplatesAvailable(List<String> templatesAvailable) {
        setValue(KEY_TEMPLATES_AVAILABLE, templatesAvailable);
    }
        
    public void setScreenParams(ScreenParams screenParams) {
        setValue(KEY_SCREEN_PARAMS, screenParams);
    }

    @SuppressWarnings("unchecked")
    public ScreenParams getScreenParams() {
        return (ScreenParams) getObject(ScreenParams.class, KEY_SCREEN_PARAMS);
    }     
}
