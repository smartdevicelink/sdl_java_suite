package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.util.DebugTool;
/**
 * Sets value(s) for the specified global property(ies)
 * <p>
 * Function Group: Base <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 * @see ResetGlobalProperties
 */
public class SetGlobalProperties extends RPCRequest {
	public static final String KEY_VR_HELP_TITLE = "vrHelpTitle";
	public static final String KEY_MENU_TITLE = "menuTitle";
	public static final String KEY_MENU_ICON = "menuIcon";
	public static final String KEY_KEYBOARD_PROPERTIES = "keyboardProperties";
	public static final String KEY_HELP_PROMPT = "helpPrompt";
	public static final String KEY_TIMEOUT_PROMPT = "timeoutPrompt";
	public static final String KEY_VR_HELP = "vrHelp";
	/**
	 * Constructs a new SetGlobalProperties object
	 */
    public SetGlobalProperties() {
        super(FunctionID.SET_GLOBAL_PROPERTIES.toString());
    }
	/**
	 * Constructs a new SetGlobalProperties object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */    
    public SetGlobalProperties(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Gets a List<TTSChunk> for Help Prompt representing Array of one or more
	 * TTSChunk elements specifying the help prompt used in an interaction
	 * started by PTT
	 * 
	 * @return List<TTSChunk> -an Array of one or more TTSChunk elements
	 *         specifying the help prompt used in an interaction started by PTT
	 */    
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getHelpPrompt() {
    	if (parameters.get(KEY_HELP_PROMPT) instanceof List<?>) {
    		List<?> list = (List<?>)parameters.get(KEY_HELP_PROMPT);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (List<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<TTSChunk> newList = new ArrayList<TTSChunk>();
	                for (Object hashObj : list) {
	                    newList.add(new TTSChunk((Hashtable<String, Object>)hashObj));
	                }
	                return newList;
	            }
	        }
    	}
	    return null;
    }
	/**
	 * Sets a List<TTSChunk> for Help Prompt that Array of one or more
	 * TTSChunk elements specifying the help prompt used in an interaction
	 * started by PTT
	 * 
	 * @param helpPrompt
	 *            a List<TTSChunk> of one or more TTSChunk elements
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Array must have at least one element</li>
	 *            <li>Only optional it timeoutPrompt has been specified</li>
	 *            </ul>
	 */    
    public void setHelpPrompt(List<TTSChunk> helpPrompt) {
        if (helpPrompt != null) {
            parameters.put(KEY_HELP_PROMPT, helpPrompt);
        } else {
            parameters.remove(KEY_HELP_PROMPT);
        }
    }
	/**
	 * Gets a List<TTSChunk> for Timeout Prompt representing Array of one or
	 * more TTSChunk elements specifying the help prompt used in an interaction
	 * started by PTT
	 * 
	 * @return List<TTSChunk> -an Array of one or more TTSChunk elements
	 *         specifying the help prompt used in an interaction started by PTT
	 */    
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTimeoutPrompt() {
        if (parameters.get(KEY_TIMEOUT_PROMPT) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_TIMEOUT_PROMPT);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (List<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<TTSChunk> newList = new ArrayList<TTSChunk>();
	                for (Object hashObj : list) {
	                    newList.add(new TTSChunk((Hashtable<String, Object>)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
	/**
	 * Sets a List<TTSChunk> for Timeout Prompt representing Array of one or
	 * more TTSChunk elements specifying the help prompt used in an interaction
	 * started by PTT
	 * 
	 */    
    public void setTimeoutPrompt(List<TTSChunk> timeoutPrompt) {
        if (timeoutPrompt != null) {
            parameters.put(KEY_TIMEOUT_PROMPT, timeoutPrompt);
        } else {
            parameters.remove(KEY_TIMEOUT_PROMPT);
        }
    }

	/**
	 * Gets a voice recognition Help Title
	 * 
	 * @return String - a String value representing the text, which is shown as
	 *         title of the VR help screen used in an interaction started by PTT
	 * @since SmartDeviceLink 2.0
	 */
    public String getVrHelpTitle() {
        return (String) parameters.get(KEY_VR_HELP_TITLE);
    }

	/**
	 * Sets a voice recognition Help Title
	 * 
	 * @param vrHelpTitle
	 *            a String value representing a voice recognition Help Title
	 *            <p>
	 *            <b>Notes: </b><br/>
	 *            <ul>
	 *            <li>If omitted on supported displays, the default SDL help
	 *            title will be used</li>
	 *            <li>If omitted and one or more vrHelp items are provided, the
	 *            request will be rejected.</li>
	 *            <li>String Maxlength = 500</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public void setVrHelpTitle(String vrHelpTitle) {
        if (vrHelpTitle != null) {
            parameters.put(KEY_VR_HELP_TITLE, vrHelpTitle);
        } else {
        	parameters.remove(KEY_VR_HELP_TITLE);
        }
    }

	/**
	 * Gets items listed in the VR help screen used in an interaction started by
	 * PTT
	 * 
	 * @return List<VrHelpItem> - a List value representing items listed in
	 *         the VR help screen used in an interaction started by PTT
	 * @since SmartDeviceLink 2.0
	 */
    @SuppressWarnings("unchecked")
    public List<VrHelpItem> getVrHelp() {
        if (parameters.get(KEY_VR_HELP) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_VR_HELP);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof VrHelpItem) {
	                return (List<VrHelpItem>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<VrHelpItem> newList = new ArrayList<VrHelpItem>();
	                for (Object hashObj : list) {
	                    newList.add(new VrHelpItem((Hashtable<String, Object>)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }

	/**
	 * Sets the items listed in the VR help screen used in an interaction
	 * started by PTT
	 * 
	 * @param vrHelp
	 *            a List value representing items listed in the VR help screen
	 *            used in an interaction started by PTT
	 *            <p>
	 *            <b>Notes: </b><br/>
	 *            <ul>
	 *            <li>If omitted on supported displays, the default SmartDeviceLink VR
	 *            help / What Can I Say? screen will be used</li>
	 *            <li>If the list of VR Help Items contains nonsequential
	 *            positions (e.g. [1,2,4]), the RPC will be rejected</li>
	 *            <li>If omitted and a vrHelpTitle is provided, the request
	 *            will be rejected</li>
	 *            <li>Array Minsize: = 1</li>
	 *            <li>Array Maxsize = 100</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public void setVrHelp(List<VrHelpItem> vrHelp) {
        if (vrHelp != null) {
            parameters.put(KEY_VR_HELP, vrHelp);
        } else {
        	parameters.remove(KEY_VR_HELP);
        }
    }
    
    public String getMenuTitle() {
        return (String) parameters.get(KEY_MENU_TITLE);
    }

    public void setMenuTitle(String menuTitle) {
        if (menuTitle != null) {
            parameters.put(KEY_MENU_TITLE, menuTitle);
        } else {
        	parameters.remove(KEY_MENU_TITLE);
        }
    }

    public void setMenuIcon(Image menuIcon) {
        if (menuIcon != null) {
            parameters.put(KEY_MENU_ICON, menuIcon);
        } else {
        	parameters.remove(KEY_MENU_ICON);
        }
    }

    @SuppressWarnings("unchecked")
    public Image getMenuIcon() {
    	Object obj = parameters.get(KEY_MENU_ICON);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new Image((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_MENU_ICON, e);
            }
        }
        return null;
    }
    
    public void setKeyboardProperties(KeyboardProperties keyboardProperties) {
        if (keyboardProperties != null) {
            parameters.put(KEY_KEYBOARD_PROPERTIES, keyboardProperties);
        } else {
        	parameters.remove(KEY_KEYBOARD_PROPERTIES);
        }
    }

    @SuppressWarnings("unchecked")
    public KeyboardProperties getKeyboardProperties() {
    	Object obj = parameters.get(KEY_KEYBOARD_PROPERTIES);
        if (obj instanceof KeyboardProperties) {
            return (KeyboardProperties) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new KeyboardProperties((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_KEYBOARD_PROPERTIES, e);
            }
        }
        return null;
    }    
    
}
