package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;
import com.smartdevicelink.util.DebugTool;
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
        Object obj = store.get(KEY_DISPLAY_TYPE);
        if (obj instanceof DisplayType) {
            return (DisplayType) obj;
        } else if (obj instanceof String) {
            DisplayType theCode = null;
            try {
                theCode = DisplayType.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_DISPLAY_TYPE, e);
            }
            return theCode;
        }
        return null;
    }
    /**
     * Set the type of display
     * @param displayType the display type
     */    
    public void setDisplayType( DisplayType displayType ) {
        if (displayType != null) {
            store.put(KEY_DISPLAY_TYPE, displayType );
        } else {
        	store.remove(KEY_DISPLAY_TYPE);
        }
    }
    /**
     *Get an array of TextField structures, each of which describes a field in the HMI which the application can write to using operations such as <i>{@linkplain Show}</i>, <i>{@linkplain SetMediaClockTimer}</i>, etc. 
     *	 This array of TextField structures identify all the text fields to which the application can write on the current display (identified by DisplayType ).
     * @return the List of textFields
     */    
    @SuppressWarnings("unchecked")
    public List<TextField> getTextFields() {
        if (store.get(KEY_TEXT_FIELDS) instanceof List<?>) {
        	List<?> list = (List<?>)store.get(KEY_TEXT_FIELDS);
	        if (list != null && list.size() > 0) {

	        	List<TextField> textFieldList  = new ArrayList<TextField>();

	        	boolean flagRaw  = false;
	        	boolean flagHash = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw TextField and a Hashtable value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof TextField) {
	        			if (flagHash) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof Hashtable) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagHash = true;
	        			textFieldList.add(new TextField((Hashtable<String, Object>) obj));

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<TextField>) list;
	        	} else if (flagHash) {
	        		return textFieldList;
	        	}
	        }
        }
        return null;
    }
    /**
     * Set an array of TextField structures, each of which describes a field in the HMI which the application can write to using operations such as <i>{@linkplain Show}</i>, <i>{@linkplain SetMediaClockTimer}</i>, etc. 
     *	 This array of TextField structures identify all the text fields to which the application can write on the current display (identified by DisplayType ).
     * @param textFields the List of textFields
     */    
    public void setTextFields( List<TextField> textFields ) {

    	boolean valid = true;
    	
    	for ( TextField item : textFields ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (textFields != null) && (textFields.size() > 0) && valid) {
            store.put(KEY_TEXT_FIELDS, textFields );
        } else {
        	store.remove(KEY_TEXT_FIELDS);
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    public List<ImageField> getImageFields() {
        if (store.get(KEY_IMAGE_FIELDS) instanceof List<?>) {
            List<?> list = (List<?>)store.get(KEY_IMAGE_FIELDS);
	        if (list != null && list.size() > 0) {

	        	List<ImageField> imageFieldList  = new ArrayList<ImageField>();

	        	boolean flagRaw  = false;
	        	boolean flagHash = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw ImageField and a Hashtable value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof ImageField) {
	        			if (flagHash) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof Hashtable) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagHash = true;
	        			imageFieldList.add(new ImageField((Hashtable<String, Object>) obj));

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<ImageField>) list;
	        	} else if (flagHash) {
	        		return imageFieldList;
	        	}
	        }
        }
        return null;
    }
  
    public void setImageFields( List<ImageField> imageFields ) {

    	boolean valid = true;
    	
    	for ( ImageField item : imageFields ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (imageFields != null) && (imageFields.size() > 0) && valid) {
            store.put(KEY_IMAGE_FIELDS, imageFields );
        }
        else
        {
        	store.remove(KEY_IMAGE_FIELDS);
        }
    }    
    
    public Integer getNumCustomPresetsAvailable() {
        return (Integer) store.get(KEY_NUM_CUSTOM_PRESETS_AVAILABLE);
    }
 
    public void setNumCustomPresetsAvailable(Integer numCustomPresetsAvailable) {
        if (numCustomPresetsAvailable != null) {
            store.put(KEY_NUM_CUSTOM_PRESETS_AVAILABLE, numCustomPresetsAvailable);
        }
        else
        {
        	store.remove(KEY_NUM_CUSTOM_PRESETS_AVAILABLE);
        }
    }
      
    /**
     * Get an array of MediaClockFormat elements, defining the valid string formats used in specifying the contents of the media clock field
     * @return the Veotor of mediaClockFormat
     */    
    @SuppressWarnings("unchecked")
    public List<MediaClockFormat> getMediaClockFormats() {
        if (store.get(KEY_MEDIA_CLOCK_FORMATS) instanceof List<?>) {
        	List<?> list = (List<?>)store.get(KEY_MEDIA_CLOCK_FORMATS);
	        if (list != null && list.size() > 0) {
	            
	        	List<MediaClockFormat> mediaClockFormatList  = new ArrayList<MediaClockFormat>();

	        	boolean flagRaw  = false;
	        	boolean flagHash = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw MediaClockFormat and a String value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof MediaClockFormat) {
	        			if (flagHash) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof String) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagHash = true;
	        			String strFormat = (String) obj;
	                    MediaClockFormat toAdd = null;
	                    try {
	                        toAdd = MediaClockFormat.valueForString(strFormat);
	                    } catch (Exception e) {
	                        DebugTool.logError("Failed to parse MediaClockFormat from " + getClass().getSimpleName() + "." + KEY_MEDIA_CLOCK_FORMATS, e);
	                    }
	                    if (toAdd != null) {
	                    	mediaClockFormatList.add(toAdd);
	                    }

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<MediaClockFormat>) list;
	        	} else if (flagHash) {
	        		return mediaClockFormatList;
	        	}
	        }
        }
        return null;
    }
    /**
     * Set an array of MediaClockFormat elements, defining the valid string formats used in specifying the contents of the media clock field
     * @param mediaClockFormats the List of MediaClockFormat
     */    
    public void setMediaClockFormats( List<MediaClockFormat> mediaClockFormats ) {

    	boolean valid = true;
    	
    	for ( MediaClockFormat item : mediaClockFormats ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (mediaClockFormats != null) && (mediaClockFormats.size() > 0) && valid) {
            store.put(KEY_MEDIA_CLOCK_FORMATS, mediaClockFormats );
        } else {
        	store.remove(KEY_MEDIA_CLOCK_FORMATS);
        }
    }
    
    /**
     * set the display's persistent screen supports.
     * @param graphicSupported
     * @since SmartDeviceLink 2.0
     */
    public void setGraphicSupported(Boolean graphicSupported) {
    	if (graphicSupported != null) {
    		store.put(KEY_GRAPHIC_SUPPORTED, graphicSupported);
    	} else {
    		store.remove(KEY_GRAPHIC_SUPPORTED);
    	}
    }
    
    /**
     * Get the display's persistent screen supports.
     * @return Boolean get the value of graphicSupported
     * @since SmartDeviceLink 2.0
     */
    public Boolean getGraphicSupported() {
    	return (Boolean) store.get(KEY_GRAPHIC_SUPPORTED);
    }
    
    @SuppressWarnings("unchecked")
    public List<String> getTemplatesAvailable() {
        if (store.get(KEY_TEMPLATES_AVAILABLE) instanceof List<?>) {
        	List<?> list = (List<?>)store.get( KEY_TEMPLATES_AVAILABLE);
        	if (list != null && list.size() > 0) {
        		for( Object obj : list ) {
        			if (!(obj instanceof String)) {
        				return null;
        			}
        		}
        		return (List<String>) list;
        	}
        }
        return null;
    }   
    
    public void setTemplatesAvailable(List<String> templatesAvailable) {

    	boolean valid = true;
    	
    	for ( String item : templatesAvailable ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (templatesAvailable != null) && (templatesAvailable.size() > 0) && valid) {
            store.put(KEY_TEMPLATES_AVAILABLE, templatesAvailable);
        }
        else
        {
        	store.remove(KEY_TEMPLATES_AVAILABLE);
        }        
    }
        
    public void setScreenParams(ScreenParams screenParams) {
        if (screenParams != null) {
            store.put(KEY_SCREEN_PARAMS, screenParams);
        } else {
            store.remove(KEY_SCREEN_PARAMS);
        }
    }

    @SuppressWarnings("unchecked")
    public ScreenParams getScreenParams() {
        Object obj = store.get(KEY_SCREEN_PARAMS);
        if (obj instanceof ScreenParams) {
            return (ScreenParams) obj;
        } else if (obj instanceof Hashtable) {
            try {
                return new ScreenParams((Hashtable<String, Object>) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_SCREEN_PARAMS, e);
            }
        }
        return null;
    }     
}
