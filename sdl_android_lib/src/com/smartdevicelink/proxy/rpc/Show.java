package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.util.DebugTool;

/**
 * Updates the application's display text area, regardless of whether or not
 * this text area is visible to the user at the time of the request. The
 * application's display text area remains unchanged until updated by subsequent
 * calls to Show
 * <p></p>
 * The content of the application's display text area is visible to the user
 * when the application's {@linkplain com.smartdevicelink.proxy.rpc.enums.HMILevel}
 * is FULL or LIMITED, and the
 * {@linkplain com.smartdevicelink.proxy.rpc.enums.SystemContext}=MAIN and no
 * {@linkplain Alert} is in progress
 * <p></p>
 * The Show operation cannot be used to create an animated scrolling screen. To
 * avoid distracting the driver, Show commands cannot be issued more than once
 * every 4 seconds. Requests made more frequently than this will be rejected
 * 
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 *  
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>mainField1</td>
 * 			<td>String</td>
 * 			<td>Text to be displayed in a single-line display, or in the upper display line in a two-line display.</td>
 *                 <td>N</td>
 * 			<td>If this parameter is omitted, the text of mainField1 does not change. If this parameter is an empty string, the field will be cleared. <p>Maxlength = 500</p></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>mainField2</td>
 * 			<td>String</td>
 * 			<td>Text to be displayed on the second display line of a two-line display.</td>
 *                 <td>N</td>
 * 			<td><p>If this parameter is omitted, the text of mainField2 does not change. </p> <p>If this parameter is an empty string, the field will be cleared.</p><p>If provided and the display is a single-line display, the parameter is ignored.</p>Maxlength = 500</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>mainField3</td>
 * 			<td>String</td>
 * 			<td>Text to be displayed on the first display line of the second page.</td>
 *                 <td>N</td>
 * 			<td><p>If this parameter is omitted, the text of mainField3 does not change. </p><p>If this parameter is an empty string, the field will be cleared.</p><p>If provided and the display is a single-line display, the parameter is ignored.</p> Maxlength = 500</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>mainField4</td>
 * 			<td>String</td>
 * 			<td>Text to be displayed on the second display line of the second page.</td>
 *                 <td>N</td>
 * 			<td><p>If this parameter is omitted, the text of mainField4 does not change. </p><p>If this parameter is an empty string, the field will be cleared.</p><p>If provided and the display is a single-line display, the parameter is ignored.</p>Maxlength = 500</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>alignment</td>
 * 			<td>TextAlignment</td>
 * 			<td>Specifies how mainField1 and mainField2 text should be aligned on display.</td>
 *                 <td>N</td>
 * 			<td><p>Applies only to mainField1 and mainField2 provided on this call, not to what is already showing in display.</p><p>If this parameter is omitted, text in both mainField1 and mainField2 will be centered. </p>Has no effect with navigation display</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>statusBar</td>
 * 			<td>String</td>
 * 			<td>The text is placed in the status bar area.</td>
 *                 <td>N</td>
 * 			<td><p>Note: The status bar only exists on navigation displays</p><p>If this parameter is omitted, the status bar text will remain unchanged.</p><p>If this parameter is an empty string, the field will be cleared.</p><p>If provided and the display has no status bar, this parameter is ignored.</p>Maxlength = 500</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>mediaClock</td>
 * 			<td>String</td>
 * 			<td><p>Text value for MediaClock field.</p> <p>Has to be properly formatted by Mobile App according to SDL capabilities.</p>If this text is set, any automatic media clock updates previously set with SetMediaClockTimer will be stopped.</td>
 *                 <td>N</td>
 * 			<td><p>Must be properly formatted as described in the MediaClockFormat enumeration. </p><p>If a value of five spaces is provided, this will clear that field on the display (i.e. the media clock timer field will not display anything) </p>Maxlength = 500</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>mediaTrack</td>
 * 			<td>String</td>
 * 			<td>Array of one or more TTSChunk elements specifying the help prompt used in an interaction started by PTT.</td>
 *                 <td>N</td>
 * 			<td><p>If parameter is omitted, the track field remains unchanged.</p><p>If an empty string is provided, the field will be cleared.</p><p>This field is only valid for media applications on navigation displays.</p>Maxlength = 500</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>graphic</td>
 * 			<td>Image</td>
 * 			<td>Image to be shown on supported displays.</td>
 *                 <td>N</td>
 * 			<td>If omitted on supported displays, the displayed graphic shall not change.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>secondaryGraphic</td>
 * 			<td>Image</td>
 * 			<td>	<p>Image struct determining whether static or dynamic secondary image to display in app.</p>If omitted on supported displays, the displayed secondary graphic shall not change.</td>
 *                 <td>N</td>
 * 			<td> </td>
 * 			<td>SmartDeviceLink 2.3.2</td>
 * 		</tr>
 * 		<tr>
 * 			<td>softButtons</td>
 * 			<td>SoftButton</td>
 * 			<td>Soft buttons as defined by the App</td>
 *                 <td>N</td>
 * 			<td><p>If omitted on supported displays, the currently displayed SoftButton values will not change.</p>Array Minsize: 0; Array Maxsize: 8</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>customPresets</td>
 * 			<td>String</td>
 * 			<td>Custom presets as defined by the App.</td>
 *                 <td>N</td>
 * 			<td><p>If omitted on supported displays, the presets will be shown as not defined.</p>Minsize: 0; Maxsize: 6</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 *  </table>
 *<p><b>Response </b></p>
 *
 *<p><b> Non-default Result Codes: </b></p>
 *	 <p>SUCCESS </p>
 *	 <p>INVALID_DATA</p>
 *	 <p>OUT_OF_MEMORY</p>
 *    <p> TOO_MANY_PENDING_REQUESTS</p>
 *     <p>APPLICATION_NOT_REGISTERED</p>
 *    <p> GENERIC_ERROR</p>
 *    <p>  REJECTED</p>
 *    <p>  DISALLOWED</p>
 * <p> UNSUPPORTED_RESOURCE </p>     
 *  <p>ABORTED</p>
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
	/**
	 * Constructs a new Show object
	 */
	public Show() {
        super(FunctionID.SHOW.toString());
    }
	/**
	 * Constructs a new Show object indicated by the Hashtable parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public Show(Hashtable<String, Object> hash) {
        super(hash);
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
        return (String) parameters.get(KEY_MAIN_FIELD_1);
    }
	/**
	 * Sets the text displayed in a single-line display, or in the upper display
	 * line in a two-line display
	 * 
	 * @param mainField1
	 *            the String value representing the text displayed in a
	 *            single-line display, or in the upper display line in a
	 *            two-line display
	 *            <p></p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>If this parameter is omitted, the text of mainField1 does
	 *            not change</li>
	 *            <li>If this parameter is an empty string, the field will be
	 *            cleared</li>
	 *            </ul>
	 */    
    public void setMainField1(String mainField1) {
        if (mainField1 != null) {
            parameters.put(KEY_MAIN_FIELD_1, mainField1);
        } else {
        	parameters.remove(KEY_MAIN_FIELD_1);
        }
    }
	/**
	 * Gets the text displayed on the second display line of a two-line display
	 * 
	 * @return String -a String value representing the text displayed on the
	 *         second display line of a two-line display
	 */    
    public String getMainField2() {
        return (String) parameters.get(KEY_MAIN_FIELD_2);
    }
	/**
	 * Sets the text displayed on the second display line of a two-line display
	 * 
	 * @param mainField2
	 *            the String value representing the text displayed on the second
	 *            display line of a two-line display
	 *            <p></p>
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
        if (mainField2 != null) {
            parameters.put(KEY_MAIN_FIELD_2, mainField2);
        } else {
        	parameters.remove(KEY_MAIN_FIELD_2);
        }
    }

	/**
	 * Gets the text displayed on the first display line of the second page
	 * 
	 * @return String -a String value representing the text displayed on the
	 *         first display line of the second page
	 * @since SmartDeviceLink 2.0
	 */
    public String getMainField3() {
        return (String) parameters.get(KEY_MAIN_FIELD_3);
    }

	/**
	 * Sets the text displayed on the first display line of the second page
	 * 
	 * @param mainField3
	 *            the String value representing the text displayed on the first
	 *            display line of the second page
	 *            <p></p>
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
        if (mainField3 != null) {
            parameters.put(KEY_MAIN_FIELD_3, mainField3);
        } else {
        	parameters.remove(KEY_MAIN_FIELD_3);
        }
    }

	/**
	 * Gets the text displayed on the second display line of the second page
	 * 
	 * @return String -a String value representing the text displayed on the
	 *         first display line of the second page
	 * @since SmartDeviceLink 2.0
	 */
    public String getMainField4() {
        return (String) parameters.get(KEY_MAIN_FIELD_4);
    }

	/**
	 * Sets the text displayed on the second display line of the second page
	 * 
	 * @param mainField4
	 *            the String value representing the text displayed on the second
	 *            display line of the second page
	 *            <p></p>
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
        if (mainField4 != null) {
            parameters.put(KEY_MAIN_FIELD_4, mainField4);
        } else {
        	parameters.remove(KEY_MAIN_FIELD_4);
        }
    }
	/**
	 * Gets the alignment that Specifies how mainField1 and mainField2 text
	 * should be aligned on display
	 * 
	 * @return TextAlignment -an Enumeration value
	 */    
    public TextAlignment getAlignment() {
        Object obj = parameters.get(KEY_ALIGNMENT);
        if (obj instanceof TextAlignment) {
            return (TextAlignment) obj;
        } else if (obj instanceof String) {
            return TextAlignment.valueForString((String) obj);
        }
        return null;
    }
	/**
	 * Sets the alignment that Specifies how mainField1 and mainField2 text
	 * should be aligned on display
	 * 
	 * @param alignment
	 *            an Enumeration value
	 *            <p></p>
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
        if (alignment != null) {
            parameters.put(KEY_ALIGNMENT, alignment);
        } else {
        	parameters.remove(KEY_ALIGNMENT);
        }
    }
	/**
	 * Gets text in the Status Bar
	 * 
	 * @return String -the value in the Status Bar
	 */    
    public String getStatusBar() {
        return (String) parameters.get(KEY_STATUS_BAR);
    }
	/**
	 * Sets text in the Status Bar
	 * 
	 * @param statusBar
	 *            a String representing the text you want to add in the Status
	 *            Bar
	 *            <p></p>
	 *            <b>Notes: </b><i>The status bar only exists on navigation
	 *            displays</i>
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
        if (statusBar != null) {
            parameters.put(KEY_STATUS_BAR, statusBar);
        } else {
        	parameters.remove(KEY_STATUS_BAR);
        }
    }
	/**
	 * Gets the String value of the MediaClock
	 * 
	 * @return String -a String value of the MediaClock
	 */ 
	@Deprecated	 
    public String getMediaClock() {
        return (String) parameters.get(KEY_MEDIA_CLOCK);
    }
	/**
	 * Sets the value for the MediaClock field using a format described in the
	 * MediaClockFormat enumeration
	 * 
	 * @param mediaClock
	 *            a String value for the MdaiaClock
	 *            <p></p>
	 *            <b>Notes: </b>
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
        if (mediaClock != null) {
            parameters.put(KEY_MEDIA_CLOCK, mediaClock);
        } else {
        	parameters.remove(KEY_MEDIA_CLOCK);
        }
    }
	/**
	 * Gets the text in the track field
	 * 
	 * @return String -a String displayed in the track field
	 */    
    public String getMediaTrack() {
        return (String) parameters.get(KEY_MEDIA_TRACK);
    }
	/**
	 * Sets the text in the track field
	 * 
	 * @param mediaTrack
	 *            a String value disaplayed in the track field
	 *            <p></p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>If parameter is omitted, the track field remains unchanged</li>
	 *            <li>If an empty string is provided, the field will be cleared</li>
	 *            <li>This field is only valid for media applications on navigation displays</li>
	 *            </ul>
	 */    
    public void setMediaTrack(String mediaTrack) {
        if (mediaTrack != null) {
            parameters.put(KEY_MEDIA_TRACK, mediaTrack);
        } else {
        	parameters.remove(KEY_MEDIA_TRACK);
        }
    }

	/**
	 * Sets an image to be shown on supported displays
	 * 
	 * @param graphic
	 *            the value representing the image shown on supported displays
	 *            <p></p>
	 *            <b>Notes: </b>If omitted on supported displays, the displayed
	 *            graphic shall not change
	 * @since SmartDeviceLink 2.0
	 */
    public void setGraphic(Image graphic) {
        if (graphic != null) {
            parameters.put(KEY_GRAPHIC, graphic);
        } else {
        	parameters.remove(KEY_GRAPHIC);
        }
    }

	/**
	 * Gets an image to be shown on supported displays
	 * 
	 * @return Image -the value representing the image shown on supported
	 *         displays
	 * @since SmartDeviceLink 2.0
	 */
    @SuppressWarnings("unchecked")
    public Image getGraphic() {
    	Object obj = parameters.get(KEY_GRAPHIC);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new Image((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_GRAPHIC, e);
            }
        }
        return null;
    }

    
    public void setSecondaryGraphic(Image secondaryGraphic) {
        if (secondaryGraphic != null) {
            parameters.put(KEY_SECONDARY_GRAPHIC, secondaryGraphic);
        } else {
        	parameters.remove(KEY_SECONDARY_GRAPHIC);
        }
    }


    @SuppressWarnings("unchecked")
    public Image getSecondaryGraphic() {
    	Object obj = parameters.get(KEY_SECONDARY_GRAPHIC);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new Image((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_SECONDARY_GRAPHIC, e);
            }
        }
        return null;
    }    
    
    
	/**
	 * Gets the Soft buttons defined by the App
	 * 
	 * @return List<SoftButton> -a List value representing the Soft buttons
	 *         defined by the App
	 * @since SmartDeviceLink 2.0
	 */
    @SuppressWarnings("unchecked")
    public List<SoftButton> getSoftButtons() {
        if (parameters.get(KEY_SOFT_BUTTONS) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_SOFT_BUTTONS);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof SoftButton) {
	                return (List<SoftButton>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<SoftButton> newList = new ArrayList<SoftButton>();
	                for (Object hashObj : list) {
	                    newList.add(new SoftButton((Hashtable<String, Object>)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }

	/**
	 * Sets the the Soft buttons defined by the App
	 * 
	 * @param softButtons
	 *            a List value represemting the Soft buttons defined by the
	 *            App
	 *            <p></p>
	 *            <b>Notes: </b>
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
        if (softButtons != null) {
            parameters.put(KEY_SOFT_BUTTONS, softButtons);
        } else {
        	parameters.remove(KEY_SOFT_BUTTONS);
        }
    }

	/**
	 * Gets the Custom Presets defined by the App
	 * 
	 * @return List<String> - a List value representing the Custom presets
	 *         defined by the App
	 * @since SmartDeviceLink 2.0
	 */
    @SuppressWarnings("unchecked")
    public List<String> getCustomPresets() {
    	if (parameters.get(KEY_CUSTOM_PRESETS) instanceof List<?>) {
    		List<?> list = (List<?>)parameters.get(KEY_CUSTOM_PRESETS);
    		if (list != null && list.size()>0) {
    			Object obj = list.get(0);
    			if (obj instanceof String) {
    				return (List<String>) list;
    			}
    		}
    	}
        return null;
    }

	/**
	 * Sets the Custom Presets defined by the App
	 * 
	 * @param customPresets
	 *            a List value representing the Custom Presets defined by the
	 *            App
	 *            <p></p>
	 *            <ul>
	 *            <li>If omitted on supported displays, the presets will be shown as not defined</li>
	 *            <li>Array Minsize: 0</li>
	 *            <li>Array Maxsize: 6</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public void setCustomPresets(List<String> customPresets) {
        if (customPresets != null) {
            parameters.put(KEY_CUSTOM_PRESETS, customPresets);
        } else {
        	parameters.remove(KEY_CUSTOM_PRESETS);
        }
    }
}
