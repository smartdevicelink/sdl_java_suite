package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.util.JsonUtils;

/**
 * Updates the application's display text area, regardless of whether or not
 * this text area is visible to the user at the time of the request. The
 * application's display text area remains unchanged until updated by subsequent
 * calls to Show
 * <p>
 * The content of the application's display text area is visible to the user
 * when the application's {@linkplain com.smartdevicelink.proxy.rpc.enums.HMILevel}
 * is FULL or LIMITED, and the
 * {@linkplain com.smartdevicelink.proxy.rpc.enums.SystemContext}=MAIN and no
 * {@linkplain Alert} is in progress
 * <p>
 * The Show operation cannot be used to create an animated scrolling screen. To
 * avoid distracting the driver, Show commands cannot be issued more than once
 * every 4 seconds. Requests made more frequently than this will be rejected
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 * @see Alert
 * @see SetMediaClockTimer
 */
public class Show extends RPCRequest {
	public static final String KEY_GRAPHIC = "graphic";
	public static final String KEY_CUSTOM_PRESETS = "customPresets";
	public static final String KEY_MAIN_FIELD_1 = "mainField1";
	public static final String KEY_MAIN_FIELD_2 = "mainField2";
	public static final String KEY_MAIN_FIELD_3 = "mainField3";
	public static final String KEY_MAIN_FIELD_4 = "mainField4";
	public static final String KEY_STATUS_BAR = "statusBar";
	public static final String KEY_MEDIA_CLOCK = "mediaClock";
	public static final String KEY_ALIGNMENT = "alignment";
	public static final String KEY_MEDIA_TRACK = "mediaTrack";
	public static final String KEY_SECONDARY_GRAPHIC = "secondaryGraphic";
	public static final String KEY_SOFT_BUTTONS = "softButtons";
	
	private String mainField1, mainField2, mainField3, mainField4, statusBar,
	    mediaClock, mediaTrack;
	private String alignment; // represents TextAlignment enum
	private Image graphic, secondaryGraphic;
	private List<String> customPresets;
	private List<SoftButton> softButtons;
	
	/**
	 * Constructs a new Show object
	 */
	public Show() {
        super(FunctionID.SHOW);
    }
	
    /**
     * Creates a Show object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public Show(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.mainField1 = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MAIN_FIELD_1);
            this.mainField2 = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MAIN_FIELD_2);
            this.mainField3 = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MAIN_FIELD_3);
            this.mainField4 = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MAIN_FIELD_4);
            this.statusBar = JsonUtils.readStringFromJsonObject(jsonObject, KEY_STATUS_BAR);
            this.mediaClock = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MEDIA_CLOCK);
            this.mediaTrack = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MEDIA_TRACK);
            this.alignment = JsonUtils.readStringFromJsonObject(jsonObject, KEY_ALIGNMENT);
            this.customPresets = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_CUSTOM_PRESETS);
            
            JSONObject graphicObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_GRAPHIC);
            if(graphicObj != null){
                this.graphic = new Image(graphicObj);
            }
            
            JSONObject secondaryGraphicObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_SECONDARY_GRAPHIC);
            if(secondaryGraphicObj != null){
                this.secondaryGraphic = new Image(secondaryGraphicObj);
            }
            
            List<JSONObject> softButtonObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_SOFT_BUTTONS);
            if(softButtonObjs != null){
                this.softButtons = new ArrayList<SoftButton>(softButtonObjs.size());
                for(JSONObject softButtonObj : softButtonObjs){
                    this.softButtons.add(new SoftButton(softButtonObj));
                }
            }
            break;
        }
    }
    
	/**
	 * Gets the text displayed in a single-line display, or in the upper display
	 * line in a two-line display
	 * 
	 * @return String -a String value representing the text displayed in a
	 *         single-line display, or in the upper display line in a two-line
	 *         display
	 */    
    public String getMainField1() {
        return this.mainField1;
    }
    
	/**
	 * Sets the text displayed in a single-line display, or in the upper display
	 * line in a two-line display
	 * 
	 * @param mainField1
	 *            the String value representing the text displayed in a
	 *            single-line display, or in the upper display line in a
	 *            two-line display
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>If this parameter is omitted, the text of mainField1 does
	 *            not change</li>
	 *            <li>If this parameter is an empty string, the field will be
	 *            cleared</li>
	 *            </ul>
	 */    
    public void setMainField1(String mainField1) {
        this.mainField1 = mainField1;
    }
    
	/**
	 * Gets the text displayed on the second display line of a two-line display
	 * 
	 * @return String -a String value representing the text displayed on the
	 *         second display line of a two-line display
	 */    
    public String getMainField2() {
        return this.mainField2;
    }
    
	/**
	 * Sets the text displayed on the second display line of a two-line display
	 * 
	 * @param mainField2
	 *            the String value representing the text displayed on the second
	 *            display line of a two-line display
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>If this parameter is omitted, the text of mainField2 does
	 *            not change</li>
	 *            <li>If this parameter is an empty string, the field will be
	 *            cleared</li>
	 *            <li>If provided and the display is a single-line display, the
	 *            parameter is ignored</li>
	 *            <li>Maxlength = 500</li>
	 *            </ul>
	 */    
    public void setMainField2(String mainField2) {
        this.mainField2 = mainField2;
    }

	/**
	 * Gets the text displayed on the first display line of the second page
	 * 
	 * @return String -a String value representing the text displayed on the
	 *         first display line of the second page
	 * @since SmartDeviceLink 2.0
	 */
    public String getMainField3() {
        return this.mainField3;
    }

	/**
	 * Sets the text displayed on the first display line of the second page
	 * 
	 * @param mainField3
	 *            the String value representing the text displayed on the first
	 *            display line of the second page
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>If this parameter is omitted, the text of mainField3 does
	 *            not change</li>
	 *            <li>If this parameter is an empty string, the field will be
	 *            cleared</li>
	 *            <li>If provided and the display is a single-line display, the
	 *            parameter is ignored</li>
	 *            <li>Maxlength = 500</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public void setMainField3(String mainField3) {
        this.mainField3 = mainField3;
    }

	/**
	 * Gets the text displayed on the second display line of the second page
	 * 
	 * @return String -a String value representing the text displayed on the
	 *         first display line of the second page
	 * @since SmartDeviceLink 2.0
	 */
    public String getMainField4() {
        return this.mainField4;
    }

	/**
	 * Sets the text displayed on the second display line of the second page
	 * 
	 * @param mainField4
	 *            the String value representing the text displayed on the second
	 *            display line of the second page
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>If this parameter is omitted, the text of mainField4 does
	 *            not change</li>
	 *            <li>If this parameter is an empty string, the field will be
	 *            cleared</li>
	 *            <li>If provided and the display is a single-line display, the
	 *            parameter is ignored</li>
	 *            <li>Maxlength = 500</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public void setMainField4(String mainField4) {
        this.mainField4 = mainField4;
    }
    
	/**
	 * Gets the alignment that Specifies how mainField1 and mainField2 text
	 * should be aligned on display
	 * 
	 * @return TextAlignment -an Enumeration value
	 */    
    public TextAlignment getAlignment() {
        return TextAlignment.valueForJsonName(this.alignment, sdlVersion);
    }
    
	/**
	 * Sets the alignment that Specifies how mainField1 and mainField2 text
	 * should be aligned on display
	 * 
	 * @param alignment
	 *            an Enumeration value
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Applies only to mainField1 and mainField2 provided on this
	 *            call, not to what is already showing in display</li>
	 *            <li>If this parameter is omitted, text in both mainField1 and
	 *            mainField2 will be centered</li>
	 *            <li>Has no effect with navigation display</li>
	 *            </ul>
	 */    
    public void setAlignment(TextAlignment alignment) {
        this.alignment = (alignment == null) ? null : alignment.getJsonName(sdlVersion);
    }
    
	/**
	 * Gets text in the Status Bar
	 * 
	 * @return String -the value in the Status Bar
	 */    
    public String getStatusBar() {
        return this.statusBar;
    }
    
	/**
	 * Sets text in the Status Bar
	 * 
	 * @param statusBar
	 *            a String representing the text you want to add in the Status
	 *            Bar
	 *            <p>
	 *            <b>Notes: </b><i>The status bar only exists on navigation
	 *            displays</i><br/>
	 *            <ul>
	 *            <li>If this parameter is omitted, the status bar text will
	 *            remain unchanged</li>
	 *            <li>If this parameter is an empty string, the field will be
	 *            cleared</li>
	 *            <li>If provided and the display has no status bar, this
	 *            parameter is ignored</li>
	 *            </ul>
	 */    
    public void setStatusBar(String statusBar) {
        this.statusBar = statusBar;
    }
    
	/**
	 * Gets the String value of the MediaClock
	 * 
	 * @return String -a String value of the MediaClock
	 */ 
	@Deprecated	 
    public String getMediaClock() {
        return this.mediaClock;
    }
	
	/**
	 * Sets the value for the MediaClock field using a format described in the
	 * MediaClockFormat enumeration
	 * 
	 * @param mediaClock
	 *            a String value for the MdaiaClock
	 *            <p>
	 *            <b>Notes: </b><br/>
	 *            <ul>
	 *            <li>Must be properly formatted as described in the
	 *            MediaClockFormat enumeration</li>
	 *            <li>If a value of five spaces is provided, this will clear
	 *            that field on the display (i.e. the media clock timer field
	 *            will not display anything)</li>
	 *            </ul>
	 */
	@Deprecated
    public void setMediaClock(String mediaClock) {
        this.mediaClock = mediaClock;
    }
	
	/**
	 * Gets the text in the track field
	 * 
	 * @return String -a String displayed in the track field
	 */    
    public String getMediaTrack() {
        return this.mediaTrack;
    }
    
	/**
	 * Sets the text in the track field
	 * 
	 * @param mediaTrack
	 *            a String value disaplayed in the track field
	 *            <p>
	 *            <b>Notes: </b><br/>
	 *            <ul>
	 *            <li>If parameter is omitted, the track field remains unchanged</li>
	 *            <li>If an empty string is provided, the field will be cleared</li>
	 *            <li>This field is only valid for media applications on navigation displays</li>
	 *            </ul>
	 */    
    public void setMediaTrack(String mediaTrack) {
        this.mediaTrack = mediaTrack;
    }

	/**
	 * Sets an image to be shown on supported displays
	 * 
	 * @param graphic
	 *            the value representing the image shown on supported displays
	 *            <p>
	 *            <b>Notes: </b>If omitted on supported displays, the displayed
	 *            graphic shall not change<br/>
	 * @since SmartDeviceLink 2.0
	 */
    public void setGraphic(Image graphic) {
        this.graphic = graphic;
    }

	/**
	 * Gets an image to be shown on supported displays
	 * 
	 * @return Image -the value representing the image shown on supported
	 *         displays
	 * @since SmartDeviceLink 2.0
	 */
    public Image getGraphic() {
    	return this.graphic;
    }

    
    public void setSecondaryGraphic(Image secondaryGraphic) {
        this.secondaryGraphic = secondaryGraphic;
    }

    public Image getSecondaryGraphic() {
    	return this.secondaryGraphic;
    }    
    
    
	/**
	 * Gets the Soft buttons defined by the App
	 * 
	 * @return List<SoftButton> -a List value representing the Soft buttons
	 *         defined by the App
	 * @since SmartDeviceLink 2.0
	 */
    public List<SoftButton> getSoftButtons() {
        return this.softButtons;
    }

	/**
	 * Sets the the Soft buttons defined by the App
	 * 
	 * @param softButtons
	 *            a List value represemting the Soft buttons defined by the
	 *            App
	 *            <p>
	 *            <b>Notes: </b><br/>
	 *            <ul>
	 *            <li>If omitted on supported displays, the currently displayed
	 *            SoftButton values will not change</li>
	 *            <li>Array Minsize: 0</li>
	 *            <li>Array Maxsize: 8</li>
	 *            </ul>
	 * 
	 * @since SmartDeviceLink 2.0
	 */
    public void setSoftButtons(List<SoftButton> softButtons) {
        this.softButtons = softButtons;
    }

	/**
	 * Gets the Custom Presets defined by the App
	 * 
	 * @return List<String> - a List value representing the Custom presets
	 *         defined by the App
	 * @since SmartDeviceLink 2.0
	 */
    public List<String> getCustomPresets() {
    	return this.customPresets;
    }

	/**
	 * Sets the Custom Presets defined by the App
	 * 
	 * @param customPresets
	 *            a List value representing the Custom Presets defined by the
	 *            App
	 *            <p>
	 *            <ul>
	 *            <li>If omitted on supported displays, the presets will be shown as not defined</li>
	 *            <li>Array Minsize: 0</li>
	 *            <li>Array Maxsize: 6</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public void setCustomPresets(List<String> customPresets) {
        this.customPresets = customPresets;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_MAIN_FIELD_1, this.mainField1);
            JsonUtils.addToJsonObject(result, KEY_MAIN_FIELD_2, this.mainField2);
            JsonUtils.addToJsonObject(result, KEY_MAIN_FIELD_3, this.mainField3);
            JsonUtils.addToJsonObject(result, KEY_MAIN_FIELD_4, this.mainField4);
            JsonUtils.addToJsonObject(result, KEY_STATUS_BAR, this.statusBar);
            JsonUtils.addToJsonObject(result, KEY_MEDIA_CLOCK, this.mediaClock);
            JsonUtils.addToJsonObject(result, KEY_MEDIA_TRACK, this.mediaTrack);
            JsonUtils.addToJsonObject(result, KEY_ALIGNMENT, this.alignment);
            
            JsonUtils.addToJsonObject(result, KEY_CUSTOM_PRESETS, (this.customPresets == null) ? null :
                JsonUtils.createJsonArray(this.customPresets));
            
            JsonUtils.addToJsonObject(result, KEY_SOFT_BUTTONS, (this.softButtons == null) ? null :
                JsonUtils.createJsonArrayOfJsonObjects(this.softButtons, sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_GRAPHIC, (this.graphic == null) ? null :
                this.graphic.getJsonParameters(sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_SECONDARY_GRAPHIC, (this.secondaryGraphic == null) ? null :
                this.secondaryGraphic.getJsonParameters(sdlVersion));
            break;
        }
        
        return result;
    }
}
