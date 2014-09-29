package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
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
	/**
	 * Constructs a newly allocated DisplayCapabilities object
	 */
    public DisplayCapabilities() { }
    /**
     * Constructs a newly allocated DisplayCapabilities object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */    
    public DisplayCapabilities(Hashtable hash) {
        super(hash);
    }
    /**
     * Get the type of display
     * @return the type of display
     */    
    public DisplayType getDisplayType() {
        Object obj = store.get(Names.displayType);
        if (obj instanceof DisplayType) {
            return (DisplayType) obj;
        } else if (obj instanceof String) {
            DisplayType theCode = null;
            try {
                theCode = DisplayType.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.displayType, e);
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
            store.put(Names.displayType, displayType );
        }
    }
    /**
     *Get an array of TextField structures, each of which describes a field in the HMI which the application can write to using operations such as <i>{@linkplain Show}</i>, <i>{@linkplain SetMediaClockTimer}</i>, etc. 
     *	 This array of TextField structures identify all the text fields to which the application can write on the current display (identified by DisplayType ).
     * @return the Vector of textFields
     */    
    public Vector<TextField> getTextFields() {
        if (store.get(Names.textFields) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)store.get(Names.textFields);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TextField) {
	                return (Vector<TextField>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<TextField> newList = new Vector<TextField>();
	                for (Object hashObj : list) {
	                    newList.add(new TextField((Hashtable)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
    /**
     * Set an array of TextField structures, each of which describes a field in the HMI which the application can write to using operations such as <i>{@linkplain Show}</i>, <i>{@linkplain SetMediaClockTimer}</i>, etc. 
     *	 This array of TextField structures identify all the text fields to which the application can write on the current display (identified by DisplayType ).
     * @param textFields the Vector of textFields
     */    
    public void setTextFields( Vector<TextField> textFields ) {
        if (textFields != null) {
            store.put(Names.textFields, textFields );
        }
    }
    
    
    
    public Vector<TextField> getImageFields() {
        if (store.get(Names.imageFields) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)store.get(Names.imageFields);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TextField) {
	                return (Vector<TextField>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<TextField> newList = new Vector<TextField>();
	                for (Object hashObj : list) {
	                    newList.add(new TextField((Hashtable)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
  
    public void setImageFields( Vector<TextField> imageFields ) {
        if (imageFields != null) {
            store.put(Names.imageFields, imageFields );
        }
        else
        {
        	store.remove(Names.imageFields);
        }
    }    
    
    public Integer getNumCustomPresetsAvailable() {
        return (Integer) store.get(Names.numCustomPresetsAvailable);
    }
 
    public void setNumCustomPresetsAvailable(Integer numCustomPresetsAvailable) {
        if (numCustomPresetsAvailable != null) {
            store.put(Names.numCustomPresetsAvailable, numCustomPresetsAvailable);
        }
        else
        {
        	store.remove(Names.numCustomPresetsAvailable);
        }
    }
      
    /**
     * Get an array of MediaClockFormat elements, defining the valid string formats used in specifying the contents of the media clock field
     * @return the Veotor of mediaClockFormat
     */    
    public Vector<MediaClockFormat> getMediaClockFormats() {
        if (store.get(Names.mediaClockFormats) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)store.get(Names.mediaClockFormats);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof MediaClockFormat) {
	                return (Vector<MediaClockFormat>) list;
	            } else if (obj instanceof String) {
	                Vector<MediaClockFormat> newList = new Vector<MediaClockFormat>();
	                for (Object hashObj : list) {
	                    String strFormat = (String)hashObj;
	                    MediaClockFormat toAdd = null;
	                    try {
	                        toAdd = MediaClockFormat.valueForString(strFormat);
	                    } catch (Exception e) {
	                        DebugTool.logError("Failed to parse MediaClockFormat from " + getClass().getSimpleName() + "." + Names.mediaClockFormats, e);
	                    }
	                    if (toAdd != null) {
	                        newList.add(toAdd);
	                    }
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
    /**
     * Set an array of MediaClockFormat elements, defining the valid string formats used in specifying the contents of the media clock field
     * @param mediaClockFormats the Vector of MediaClockFormat
     */    
    public void setMediaClockFormats( Vector<MediaClockFormat> mediaClockFormats ) {
        if (mediaClockFormats != null) {
            store.put(Names.mediaClockFormats, mediaClockFormats );
        }
    }
    
    /**
     * set the display's persistent screen supports.
     * @param graphicSupported
     * @since SmartDeviceLink 2.0
     */
    public void setGraphicSupported(Boolean graphicSupported) {
    	if (graphicSupported != null) {
    		store.put(Names.graphicSupported, graphicSupported);
    	} else {
    		store.remove(Names.graphicSupported);
    	}
    }
    
    /**
     * Get the display's persistent screen supports.
     * @return Boolean get the value of graphicSupported
     * @since SmartDeviceLink 2.0
     */
    public Boolean getGraphicSupported() {
    	return (Boolean) store.get(Names.graphicSupported);
    }
    
    public Vector<String> getTemplatesAvailable() {
        if (store.get(Names.templatesAvailable) instanceof Vector<?>) {
        	Vector<?> list = (Vector<?>)store.get( Names.templatesAvailable);
        	if (list != null && list.size() > 0) {
        		Object obj = list.get(0);
        		if (obj instanceof String) {
                	return (Vector<String>) list;        			
        		}
        	}
        }
        return null;
    }   
    
    public void setTemplatesAvailable(Vector<String> templatesAvailable) {
        if (templatesAvailable != null) {
            store.put(Names.templatesAvailable, templatesAvailable);
        }
        else
        {
        	store.remove(Names.templatesAvailable);
        }        
    }
        
    public void setScreenParams(ScreenParams screenParams) {
        if (screenParams != null) {
            store.put(Names.screenParams, screenParams);
        } else {
            store.remove(Names.screenParams);
        }
    }

    public ScreenParams getScreenParams() {
        Object obj = store.get(Names.screenParams);
        if (obj instanceof ScreenParams) {
            return (ScreenParams) obj;
        } else if (obj instanceof Hashtable) {
            try {
                return new ScreenParams((Hashtable) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.screenParams, e);
            }
        }
        return null;
    }     
}
