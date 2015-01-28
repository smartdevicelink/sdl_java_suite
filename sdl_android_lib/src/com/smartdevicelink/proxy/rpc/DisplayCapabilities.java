package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;
import com.smartdevicelink.util.JsonUtils;
/**
 * Contains information about the display for the SDL system to which the application is currently connected.
  * <p><b> Parameter List
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
 */
public class DisplayCapabilities extends RPCObject {
	public static final String KEY_DISPLAY_TYPE = "displayType";
	public static final String KEY_MEDIA_CLOCK_FORMATS = "mediaClockFormats";
	public static final String KEY_TEXT_FIELDS = "textFields";
	public static final String KEY_IMAGE_FIELDS = "imageFields";
    public static final String KEY_GRAPHIC_SUPPORTED = "graphicSupported";
    public static final String KEY_SCREEN_PARAMS = "screenParams";
    public static final String KEY_TEMPLATES_AVAILABLE = "templatesAvailable";
    public static final String KEY_NUM_CUSTOM_PRESETS_AVAILABLE = "numCustomPresetsAvailable";
	
    private String displayType; // represents DisplayType enum
    private ScreenParams screenParams;
    
    private List<TextField> textFields;
    private List<ImageField> imageFields;
    private List<String> mediaClockFormats; // represents MediaClockFormat enum
    private List<String> templatesAvailable;
    private Boolean graphicSupported;
    private Integer numCustomPresetsAvailable;
    
    /**
	 * Constructs a newly allocated DisplayCapabilities object
	 */
    public DisplayCapabilities() { }
    
    /**
     * Creates a DisplayCapabilities object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */    
    public DisplayCapabilities(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.displayType = JsonUtils.readStringFromJsonObject(jsonObject, KEY_DISPLAY_TYPE);
            this.graphicSupported = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_GRAPHIC_SUPPORTED);
            this.numCustomPresetsAvailable = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_NUM_CUSTOM_PRESETS_AVAILABLE);
            
            this.mediaClockFormats = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_MEDIA_CLOCK_FORMATS);
            this.templatesAvailable = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_TEMPLATES_AVAILABLE);
            
            JSONObject screenParamsObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_SCREEN_PARAMS);
            if(screenParamsObj != null){
                this.screenParams = new ScreenParams(screenParamsObj);
            }
            
            List<JSONObject> textFieldObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_TEXT_FIELDS);
            if(textFieldObjs != null){
                this.textFields = new ArrayList<TextField>(textFieldObjs.size());
                for(JSONObject textFieldObj : textFieldObjs){
                    this.textFields.add(new TextField(textFieldObj));
                }
            }
            
            List<JSONObject> imageFieldObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_IMAGE_FIELDS);
            if(imageFieldObjs != null){
                this.imageFields = new ArrayList<ImageField>(imageFieldObjs.size());
                for(JSONObject imageFieldObj : imageFieldObjs){
                    this.imageFields.add(new ImageField(imageFieldObj));
                }
            }
            
            break;
        }
    }
    /**
     * Get the type of display
     * @return the type of display
     */    
    public DisplayType getDisplayType() {
        return DisplayType.valueForJsonName(this.displayType, sdlVersion);
    }
    
    /**
     * Set the type of display
     * @param displayType the display type
     */    
    public void setDisplayType( DisplayType displayType ) {
        this.displayType = displayType.getJsonName(sdlVersion);
    }
    
    /**
     *Get an array of TextField structures, each of which describes a field in the HMI which the application can write to using operations such as <i>{@linkplain Show}</i>, <i>{@linkplain SetMediaClockTimer}</i>, etc. 
     *	 This array of TextField structures identify all the text fields to which the application can write on the current display (identified by DisplayType ).
     * @return the List of textFields
     */
    public List<TextField> getTextFields() {
        return this.textFields;
    }
    
    /**
     * Set an array of TextField structures, each of which describes a field in the HMI which the application can write to using operations such as <i>{@linkplain Show}</i>, <i>{@linkplain SetMediaClockTimer}</i>, etc. 
     *	 This array of TextField structures identify all the text fields to which the application can write on the current display (identified by DisplayType ).
     * @param textFields the List of textFields
     */    
    public void setTextFields( List<TextField> textFields ) {
        this.textFields = textFields;
    }
    
    public List<ImageField> getImageFields() {
        return this.imageFields;
    }
  
    public void setImageFields( List<ImageField> imageFields ) {
        this.imageFields = imageFields;
    }    
    
    public Integer getNumCustomPresetsAvailable() {
        return this.numCustomPresetsAvailable;
    }
 
    public void setNumCustomPresetsAvailable(Integer numCustomPresetsAvailable) {
        this.numCustomPresetsAvailable = numCustomPresetsAvailable;
    }
      
    /**
     * Get an array of MediaClockFormat elements, defining the valid string formats used in specifying the contents of the media clock field
     * @return the List of mediaClockFormat
     */
    public List<MediaClockFormat> getMediaClockFormats() {
        if(this.mediaClockFormats == null){
            return null;
        }
        
        // This is not ideal and will be quite slow, but the benefit of doing it this way is to avoid
        // doing it on creation of the object and converting to strings on creation of JSON object.
        // This method should be called less than constructor and JSON methods; overall, should be fine.
        List<MediaClockFormat> result = new ArrayList<MediaClockFormat>(this.mediaClockFormats.size());
        for(String str : this.mediaClockFormats){
            result.add(MediaClockFormat.valueForJsonName(str, sdlVersion));
        }
        return result;
    }
    
    /**
     * Set an array of MediaClockFormat elements, defining the valid string formats used in specifying the contents of the media clock field
     * @param mediaClockFormats the List of MediaClockFormat
     */    
    public void setMediaClockFormats( List<MediaClockFormat> mediaClockFormats ) {
        if(mediaClockFormats == null){
            this.mediaClockFormats = null;
        }
        else{
            this.mediaClockFormats = new ArrayList<String>(mediaClockFormats.size());
            for(MediaClockFormat mcFormat : mediaClockFormats){
                this.mediaClockFormats.add(mcFormat.getJsonName(sdlVersion));
            }
        }
    }
    
    /**
     * set the display's persistent screen supports.
     * @param graphicSupported
     * @since SmartDeviceLink 2.0
     */
    public void setGraphicSupported(Boolean graphicSupported) {
    	this.graphicSupported = graphicSupported;
    }
    
    /**
     * Get the display's persistent screen supports.
     * @return Boolean get the value of graphicSupported
     * @since SmartDeviceLink 2.0
     */
    public Boolean getGraphicSupported() {
    	return this.graphicSupported;
    }
    
    public List<String> getTemplatesAvailable() {
        return this.templatesAvailable;
    }   
    
    public void setTemplatesAvailable(List<String> templatesAvailable) {
        this.templatesAvailable = templatesAvailable;
    }
        
    public void setScreenParams(ScreenParams screenParams) {
        this.screenParams = screenParams;
    }

    public ScreenParams getScreenParams() {
        return this.screenParams;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_DISPLAY_TYPE, this.displayType);
            JsonUtils.addToJsonObject(result, KEY_GRAPHIC_SUPPORTED, this.graphicSupported);
            JsonUtils.addToJsonObject(result, KEY_NUM_CUSTOM_PRESETS_AVAILABLE, this.numCustomPresetsAvailable);

            JsonUtils.addToJsonObject(result, KEY_TEMPLATES_AVAILABLE, 
                    (this.templatesAvailable == null) ? null : JsonUtils.createJsonArray(this.templatesAvailable));
            JsonUtils.addToJsonObject(result, KEY_MEDIA_CLOCK_FORMATS, 
                    (this.mediaClockFormats == null) ? null : JsonUtils.createJsonArray(this.mediaClockFormats));
            
            JsonUtils.addToJsonObject(result, KEY_TEXT_FIELDS, 
                    (this.textFields == null) ? null : JsonUtils.createJsonArrayOfJsonObjects(this.textFields, sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_IMAGE_FIELDS,
                    (this.imageFields == null) ? null : JsonUtils.createJsonArrayOfJsonObjects(this.imageFields, sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_SCREEN_PARAMS, 
                    (this.screenParams == null) ? null : this.screenParams.getJsonParameters(sdlVersion));
            break;
        }
        
        return result;
    }
}
