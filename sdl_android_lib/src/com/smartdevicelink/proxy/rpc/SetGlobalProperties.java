package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;
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
	
	private KeyboardProperties keyboardProperties;
	private Image menuIcon;
	private List<VrHelpItem> vrHelp;
	private List<TTSChunk> helpPrompt, timeoutPrompt;
	private String vrHelpTitle, menuTitle;
	
	/**
	 * Constructs a new SetGlobalProperties object
	 */
    public SetGlobalProperties() {
        super(FunctionID.SET_GLOBAL_PROPERTIES);
    }
    
    /**
     * Creates a SetGlobalProperties object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SetGlobalProperties(JSONObject jsonObject){
        super(SdlCommand.SET_GLOBAL_PROPERTIES, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.vrHelpTitle = JsonUtils.readStringFromJsonObject(jsonObject, KEY_VR_HELP_TITLE);
            this.menuTitle = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MENU_TITLE);
            
            JSONObject keyboardPropertiesObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_KEYBOARD_PROPERTIES);
            if(keyboardPropertiesObj != null){
                this.keyboardProperties = new KeyboardProperties(keyboardPropertiesObj);
            }
            
            JSONObject menuIconObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_MENU_ICON);
            if(menuIconObj != null){
                this.menuIcon = new Image(menuIconObj);
            }
            
            List<JSONObject> vrHelpObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_VR_HELP);
            if(vrHelpObjs != null){
                this.vrHelp = new ArrayList<VrHelpItem>(vrHelpObjs.size());
                for(JSONObject vrHelpObj : vrHelpObjs){
                    this.vrHelp.add(new VrHelpItem(vrHelpObj));
                }
            }
            
            List<JSONObject> helpPromptObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_HELP_PROMPT);
            if(helpPromptObjs != null){
                this.helpPrompt = new ArrayList<TTSChunk>(helpPromptObjs.size());
                for(JSONObject helpPromptObj : helpPromptObjs){
                    this.helpPrompt.add(new TTSChunk(helpPromptObj));
                }
            }
            
            List<JSONObject> timeoutPromptObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_TIMEOUT_PROMPT);
            if(timeoutPromptObjs != null){
                this.timeoutPrompt = new ArrayList<TTSChunk>(timeoutPromptObjs.size());
                for(JSONObject timeoutPromptObj : timeoutPromptObjs){
                    this.timeoutPrompt.add(new TTSChunk(timeoutPromptObj));
                }
            }
            break;
        }
    }
    
	/**
	 * Gets a List<TTSChunk> for Help Prompt representing Array of one or more
	 * TTSChunk elements specifying the help prompt used in an interaction
	 * started by PTT
	 * 
	 * @return List<TTSChunk> -an Array of one or more TTSChunk elements
	 *         specifying the help prompt used in an interaction started by PTT
	 */
    public List<TTSChunk> getHelpPrompt() {
        return this.helpPrompt;
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
        this.helpPrompt = helpPrompt;
    }
    
	/**
	 * Gets a List<TTSChunk> for Timeout Prompt representing Array of one or
	 * more TTSChunk elements specifying the help prompt used in an interaction
	 * started by PTT
	 * 
	 * @return List<TTSChunk> -an Array of one or more TTSChunk elements
	 *         specifying the help prompt used in an interaction started by PTT
	 */
    public List<TTSChunk> getTimeoutPrompt() {
        return this.timeoutPrompt;
    }
    
	/**
	 * Sets a List<TTSChunk> for Timeout Prompt representing Array of one or
	 * more TTSChunk elements specifying the help prompt used in an interaction
	 * started by PTT
	 * 
	 */    
    public void setTimeoutPrompt(List<TTSChunk> timeoutPrompt) {
        this.timeoutPrompt = timeoutPrompt;
    }

	/**
	 * Gets a voice recognition Help Title
	 * 
	 * @return String - a String value representing the text, which is shown as
	 *         title of the VR help screen used in an interaction started by PTT
	 * @since SmartDeviceLink 2.0
	 */
    public String getVrHelpTitle() {
        return this.vrHelpTitle;
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
        this.vrHelpTitle = vrHelpTitle;
    }

	/**
	 * Gets items listed in the VR help screen used in an interaction started by
	 * PTT
	 * 
	 * @return List<VrHelpItem> - a List value representing items listed in
	 *         the VR help screen used in an interaction started by PTT
	 * @since SmartDeviceLink 2.0
	 */
    public List<VrHelpItem> getVrHelp() {
        return this.vrHelp;
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
        this.vrHelp = vrHelp;
    }
    
    public String getMenuTitle() {
        return this.menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public void setMenuIcon(Image menuIcon) {
        this.menuIcon = menuIcon;
    }

    public Image getMenuIcon() {
    	return this.menuIcon;
    }
    
    public void setKeyboardProperties(KeyboardProperties keyboardProperties) {
        this.keyboardProperties = keyboardProperties;
    }

    public KeyboardProperties getKeyboardProperties() {
    	return this.keyboardProperties;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_MENU_TITLE, this.menuTitle);
            JsonUtils.addToJsonObject(result, KEY_VR_HELP_TITLE, this.vrHelpTitle);
            
            JsonUtils.addToJsonObject(result, KEY_KEYBOARD_PROPERTIES, (this.keyboardProperties == null) ? null : 
                this.keyboardProperties.getJsonParameters(sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_MENU_ICON, (this.menuIcon == null) ? null :
                this.menuIcon.getJsonParameters(sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_HELP_PROMPT, (this.helpPrompt == null) ? null :
                JsonUtils.createJsonArrayOfJsonObjects(this.helpPrompt, sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_TIMEOUT_PROMPT, (this.timeoutPrompt == null) ? null :
                JsonUtils.createJsonArrayOfJsonObjects(this.timeoutPrompt, sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_VR_HELP, (this.vrHelp == null) ? null :
                JsonUtils.createJsonArrayOfJsonObjects(this.vrHelp, sdlVersion));
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((helpPrompt == null) ? 0 : helpPrompt.hashCode());
		result = prime * result + ((keyboardProperties == null) ? 0 : keyboardProperties.hashCode());
		result = prime * result + ((menuIcon == null) ? 0 : menuIcon.hashCode());
		result = prime * result + ((menuTitle == null) ? 0 : menuTitle.hashCode());
		result = prime * result + ((timeoutPrompt == null) ? 0 : timeoutPrompt.hashCode());
		result = prime * result + ((vrHelp == null) ? 0 : vrHelp.hashCode());
		result = prime * result + ((vrHelpTitle == null) ? 0 : vrHelpTitle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		SetGlobalProperties other = (SetGlobalProperties) obj;
		if (helpPrompt == null) {
			if (other.helpPrompt != null) { 
				return false;
			}
		}
		else if (!helpPrompt.equals(other.helpPrompt)) { 
			return false;
		}
		if (keyboardProperties == null) {
			if (other.keyboardProperties != null) { 
				return false;
			}
		}
		else if (!keyboardProperties.equals(other.keyboardProperties)) { 
			return false;
		}
		if (menuIcon == null) {
			if (other.menuIcon != null) { 
				return false;
			}
		} 
		else if (!menuIcon.equals(other.menuIcon)) { 
			return false;
		}
		if (menuTitle == null) {
			if (other.menuTitle != null) { 
				return false;
			}
		} 
		else if (!menuTitle.equals(other.menuTitle)) { 
			return false;
		}
		if (timeoutPrompt == null) {
			if (other.timeoutPrompt != null) { 
				return false;
			}
		} 
		else if (!timeoutPrompt.equals(other.timeoutPrompt)) { 
			return false;
		}
		if (vrHelp == null) {
			if (other.vrHelp != null) { 
				return false;
			}
		} 
		else if (!vrHelp.equals(other.vrHelp)) { 
			return false;
		}
		if (vrHelpTitle == null) {
			if (other.vrHelpTitle != null) { 
				return false;
			}
		} 
		else if (!vrHelpTitle.equals(other.vrHelpTitle)) { 
			return false;
		}
		return true;
	}
    
}
