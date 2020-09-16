/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;
import com.smartdevicelink.util.Version;

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
 * 			<td>@Deprecated <s>displayType</s></td>
 * 			<td><s>DisplayType</s></td>
 * 			<td><s>The type of display</s>
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>displayName</td>
 * 			<td>String</td>
 * 			<td>The name of the display
 *			</td>
 * 			<td>SmartDeviceLink 5.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>textField</td>
 * 			<td>TextField[]</td>
 * 			<td>An array of TextField structures, each of which describes a field in the HMI which the application can write to using operations such as <i>{@linkplain Show}</i>, <i>{@linkplain SetMediaClockTimer}</i>, etc. 
 *					 This array of TextField structures identify all the text fields to which the application can write on the current display (identified by DisplayType ).
 * 			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>mediaClockFormats</td>
 * 			<td>MediaClockFormat[]</td>
 * 			<td>An array of MediaClockFormat elements, defining the valid string formats used in specifying the contents of the media clock field</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>graphicSupported</td>
 * 			<td>Boolean</td>
 * 			<td>The display's persistent screen supports referencing a static or dynamic image.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * </table>
 * @since SmartDeviceLink 1.0
 * @see DisplayType
 * @see MediaClockFormat
 * @see TextField
 * @see ImageField
 * 
 */
public class DisplayCapabilities extends RPCStruct {
	@Deprecated public static final String KEY_DISPLAY_TYPE = "displayType";
	public static final String KEY_DISPLAY_NAME = "displayName";
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
     * Constructs a newly allocated DisplayCapabilities object
     * @param displayType the display type
     * @param textFields the List of textFields
     * @param mediaClockFormats the List of MediaClockFormat
     * @param graphicSupported true if the display supports graphics, false if it does not
     */
    @Deprecated
    public DisplayCapabilities(@NonNull DisplayType displayType, @NonNull List<TextField> textFields, @NonNull List<MediaClockFormat> mediaClockFormats, @NonNull Boolean graphicSupported) {
        this();
        setDisplayType(displayType);
        setTextFields(textFields);
        setMediaClockFormats(mediaClockFormats);
        setGraphicSupported(graphicSupported);
    }
    /**
     * Constructs a newly allocated DisplayCapabilities object
     * @param displayName the display name (String)
     * @param textFields the List of textFields
     * @param mediaClockFormats the List of MediaClockFormat
     * @param graphicSupported true if the display supports graphics, false if it does not
     */
    public DisplayCapabilities(String displayName, @NonNull List<TextField> textFields, @NonNull List<MediaClockFormat> mediaClockFormats, @NonNull Boolean graphicSupported) {
        this();
        setDisplayName(displayName);
        setTextFields(textFields);
        setMediaClockFormats(mediaClockFormats);
        setGraphicSupported(graphicSupported);
    }

    @Override
    public void format(Version rpcVersion, boolean formatParams) {
        super.format(rpcVersion, formatParams);
        if(!store.containsKey(KEY_GRAPHIC_SUPPORTED)){
            // At some point this was added to the RPC spec as mandatory but at least in v1.0.0
            // it was not included.
            store.put(KEY_GRAPHIC_SUPPORTED, Boolean.FALSE);
        }
    }

    /**
     * Get the type of display
     * @return the type of display
     */
    @Deprecated
    public DisplayType getDisplayType() {
        return (DisplayType) getObject(DisplayType.class, KEY_DISPLAY_TYPE);
    }
    /**
     * Set the type of display
     * @param displayType the display type
     */
    @Deprecated
    public DisplayCapabilities setDisplayType(@NonNull DisplayType displayType) {
        setValue(KEY_DISPLAY_TYPE, displayType);
        return this;
    }
    /** Get the name of the display
     * @return the name of the display
     */
    public String getDisplayName() {
        return getString(KEY_DISPLAY_NAME);
    }
    /**
     * Set the name of the display
     * @param displayName the name of the display
     */
    public DisplayCapabilities setDisplayName( String displayName) {
        setValue(KEY_DISPLAY_NAME, displayName);
        return this;
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
    public DisplayCapabilities setTextFields(@NonNull List<TextField> textFields) {
        setValue(KEY_TEXT_FIELDS, textFields);
        return this;
    }
    
    
    
    @SuppressWarnings("unchecked")
    public List<ImageField> getImageFields() {
        return (List<ImageField>) getObject(ImageField.class, KEY_IMAGE_FIELDS);
    }
  
    public DisplayCapabilities setImageFields( List<ImageField> imageFields) {
        setValue(KEY_IMAGE_FIELDS, imageFields);
        return this;
    }
    
    public Integer getNumCustomPresetsAvailable() {
        return getInteger(KEY_NUM_CUSTOM_PRESETS_AVAILABLE);
    }
 
    public DisplayCapabilities setNumCustomPresetsAvailable( Integer numCustomPresetsAvailable) {
        setValue(KEY_NUM_CUSTOM_PRESETS_AVAILABLE, numCustomPresetsAvailable);
        return this;
    }
      
    /**
     * Get an array of MediaClockFormat elements, defining the valid string formats used in specifying the contents of the media clock field
     * @return the Vector of mediaClockFormat
     */    
    @SuppressWarnings("unchecked")
    public List<MediaClockFormat> getMediaClockFormats() {
        return (List<MediaClockFormat>) getObject(MediaClockFormat.class, KEY_MEDIA_CLOCK_FORMATS);
    }
    /**
     * Set an array of MediaClockFormat elements, defining the valid string formats used in specifying the contents of the media clock field
     * @param mediaClockFormats the List of MediaClockFormat
     */
    public DisplayCapabilities setMediaClockFormats(@NonNull List<MediaClockFormat> mediaClockFormats) {
        setValue(KEY_MEDIA_CLOCK_FORMATS, mediaClockFormats);
        return this;
    }
    
    /**
     * set the display's persistent screen supports.
     * @param graphicSupported true if the display supports graphics, false if it does not
     * @since SmartDeviceLink 2.0
     */
    public DisplayCapabilities setGraphicSupported(@NonNull Boolean graphicSupported) {
        setValue(KEY_GRAPHIC_SUPPORTED, graphicSupported);
        return this;
    }
    
    /**
     * Get the display's persistent screen supports.
     * @return true if the display supports graphics, false if it does not
     * @since SmartDeviceLink 2.0
     */
    public Boolean getGraphicSupported() {
    	return getBoolean(KEY_GRAPHIC_SUPPORTED);
    }
    
    @SuppressWarnings("unchecked")
    public List<String> getTemplatesAvailable() {
        return (List<String>) getObject(String.class, KEY_TEMPLATES_AVAILABLE);
    }   
    
    public DisplayCapabilities setTemplatesAvailable( List<String> templatesAvailable) {
        setValue(KEY_TEMPLATES_AVAILABLE, templatesAvailable);
        return this;
    }
        
    public DisplayCapabilities setScreenParams( ScreenParams screenParams) {
        setValue(KEY_SCREEN_PARAMS, screenParams);
        return this;
    }

    public ScreenParams getScreenParams() {
        return (ScreenParams) getObject(ScreenParams.class, KEY_SCREEN_PARAMS);
    }
}
