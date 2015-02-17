package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;
/**
 * Performs an application-initiated interaction in which the user can select a
 * {@linkplain Choice} from among the specified Choice Sets. For instance, an
 * application may use a PerformInteraction to ask a user to say the name of a
 * song to play. The user's response is only valid if it appears in the
 * specified Choice Sets and is recognized by SDL
 * <p>
 * Function Group: Base
 * <p>
 * <b>HMILevel needs to be FULL</b>
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 * @see CreateInteractionChoiceSet
 * @see DeleteInteractionChoiceSet
 */
public class PerformInteraction extends RPCRequest {
	public static final String KEY_INITIAL_TEXT = "initialText";
	public static final String KEY_INTERACTION_MODE = "interactionMode";
	public static final String KEY_INTERACTION_CHOICE_SET_ID_LIST = "interactionChoiceSetIDList";
	public static final String KEY_INTERACTION_LAYOUT = "interactionLayout";
	public static final String KEY_INITIAL_PROMPT = "initialPrompt";
	public static final String KEY_HELP_PROMPT = "helpPrompt";
	public static final String KEY_TIMEOUT_PROMPT = "timeoutPrompt";
	public static final String KEY_TIMEOUT = "timeout";
	public static final String KEY_VR_HELP = "vrHelp";
	
	private String initialText;
	private String interactionMode; // represents InteractionMode enum
	private String interactionLayout; // represents LayoutMode enum
	private Integer timeout;
	private List<Integer> choiceSetIds;
	private List<VrHelpItem> vrHelp;
	private List<TTSChunk> initialPrompt, helpPrompt, timeoutPrompt;
	
	/**
	 * Constructs a new PerformInteraction object
	 */
    public PerformInteraction() {
        super(FunctionID.PERFORM_INTERACTION);
    }
	
    /**
     * Creates a PerformInteraction object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public PerformInteraction(JSONObject jsonObject){
        super(SdlCommand.PERFORM_INTERACTION, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.initialText = JsonUtils.readStringFromJsonObject(jsonObject, KEY_INITIAL_TEXT);
            this.interactionMode = JsonUtils.readStringFromJsonObject(jsonObject, KEY_INTERACTION_MODE);
            this.interactionLayout = JsonUtils.readStringFromJsonObject(jsonObject, KEY_INTERACTION_LAYOUT);
            this.timeout = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_TIMEOUT);
            this.choiceSetIds = JsonUtils.readIntegerListFromJsonObject(jsonObject, KEY_INTERACTION_CHOICE_SET_ID_LIST);
            
            List<JSONObject> vrHelpObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_VR_HELP);
            if(vrHelpObjs != null){
                this.vrHelp = new ArrayList<VrHelpItem>(vrHelpObjs.size());
                for(JSONObject vrHelpObj : vrHelpObjs){
                    this.vrHelp.add(new VrHelpItem(vrHelpObj));
                }
            }
            
            List<JSONObject> initialPromptObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_INITIAL_PROMPT);
            if(initialPromptObjs != null){
                this.initialPrompt = new ArrayList<TTSChunk>(initialPromptObjs.size());
                for(JSONObject initialPromptObj : initialPromptObjs){
                    this.initialPrompt.add(new TTSChunk(initialPromptObj));
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
	 * Gets the Text that Displayed when the interaction begins. This text may
	 * be overlaid by the "Listening" prompt during the interaction. Text is
	 * displayed on first line of multiline display, and is centered. If text
	 * does not fit on line, it will be truncated
	 * 
	 * @return String -the text displayed when the interaction begins
	 */
    public String getInitialText() {
        return this.initialText;
    }
    
	/**
	 * Sets the Text that Displayed when the interaction begins. This text may
	 * be overlaid by the "Listening" prompt during the interaction. Text is
	 * displayed on first line of multiline display, and is centered. If text
	 * does not fit on line, it will be truncated
	 * 
	 * @param initialText
	 *            a String value that Displayed when the interaction begins
	 */    
    public void setInitialText(String initialText) {
        this.initialText = initialText;
    }
    
	/**
	 * Gets an An array of one or more TTSChunks that, taken together, specify
	 * what is to be spoken to the user at the start of an interaction
	 * 
	 * @return List<TTSChunk> -a List<TTSChunk> value, specify what is to be
	 *         spoken to the user at the start of an interaction
	 */
    public List<TTSChunk> getInitialPrompt() {
        return this.initialPrompt;
    }
    
	/**
	 * Sets An array of one or more TTSChunks that, taken together, specify what
	 * is to be spoken to the user at the start of an interaction
	 * 
	 * @param initialPrompt
	 *            a List<TTSChunk> value, specify what is to be spoken to the
	 *            user at the start of an interaction
	 */    
    public void setInitialPrompt(List<TTSChunk> initialPrompt) {
        this.initialPrompt = initialPrompt;
    }
    
	/**
	 * Gets the Indicates mode that indicate how user selects interaction
	 * choice. User can choose either by voice (VR_ONLY), by visual selection
	 * from the menu (MANUAL_ONLY), or by either mode (BOTH)
	 * 
	 * @return InteractionMode -indicate how user selects interaction choice
	 *         (VR_ONLY, MANUAL_ONLY or BOTH)
	 */    
    public InteractionMode getInteractionMode() {
        return InteractionMode.valueForJsonName(this.interactionMode, sdlVersion);
    }
    
	/**
	 * Sets the Indicates mode that indicate how user selects interaction
	 * choice. User can choose either by voice (VR_ONLY), by visual selection
	 * from the menu (MANUAL_ONLY), or by either mode (BOTH)
	 * 
	 * @param interactionMode
	 *            indicate how user selects interaction choice (VR_ONLY,
	 *            MANUAL_ONLY or BOTH)
	 */    
    public void setInteractionMode(InteractionMode interactionMode) {
        this.interactionMode = (interactionMode == null) ? null : interactionMode.getJsonName(sdlVersion);
    }
    
	/**
	 * Gets a List<Integer> value representing an Array of one or more Choice
	 * Set IDs
	 * 
	 * @return List<Integer> -a List<Integer> value representing an Array of
	 *         one or more Choice Set IDs. User can select any choice from any
	 *         of the specified Choice Sets
	 */    
    public List<Integer> getInteractionChoiceSetIDList() {
    	return this.choiceSetIds;
    }
    
	/**
	 * Sets a List<Integer> representing an Array of one or more Choice Set
	 * IDs. User can select any choice from any of the specified Choice Sets
	 * 
	 * @param interactionChoiceSetIDList
	 *            -a List<Integer> representing an Array of one or more Choice
	 *            Set IDs. User can select any choice from any of the specified
	 *            Choice Sets
	 *            <p>
	 *            <b>Notes: </b>Min Value: 0; Max Vlaue: 2000000000
	 */    
    public void setInteractionChoiceSetIDList(List<Integer> interactionChoiceSetIDList) {
        this.choiceSetIds = interactionChoiceSetIDList;
    }
    
	/**
	 * Gets a List<TTSChunk> which taken together, specify the help phrase to
	 * be spoken when the user says "help" during the VR session
	 * 
	 * @return List<TTSChunk> -a List<TTSChunk> which taken together,
	 *         specify the help phrase to be spoken when the user says "help"
	 *         during the VR session
	 */
    public List<TTSChunk> getHelpPrompt() {
        return this.helpPrompt;
    }
    
	/**
	 * Sets An array of TTSChunks which, taken together, specify the help phrase
	 * to be spoken when the user says "help" during the VR session
	 * <p>
	 * If this parameter is omitted, the help prompt will be constructed by SDL
	 * from the first vrCommand of each choice of all the Choice Sets specified
	 * in the interactionChoiceSetIDList parameter
	 * <P>
	 * <b>Notes: </b>The helpPrompt specified in
	 * {@linkplain SetGlobalProperties} is not used by PerformInteraction
	 * 
	 * @param helpPrompt
	 *            a List<TTSChunk> which taken together, specify the help
	 *            phrase to be spoken when the user says "help" during the VR
	 *            session
	 */    
    public void setHelpPrompt(List<TTSChunk> helpPrompt) {
        this.helpPrompt = helpPrompt;
    }
    
	/**
	 * Gets An array of TTSChunks which, taken together, specify the phrase to
	 * be spoken when the listen times out during the VR session
	 * 
	 * @return List<TTSChunk> -a List<TTSChunk> specify the phrase to be
	 *         spoken when the listen times out during the VR session
	 */
    public List<TTSChunk> getTimeoutPrompt() {
        return this.timeoutPrompt;
    }
    
	/**
	 * Sets An array of TTSChunks which, taken together, specify the phrase to
	 * be spoken when the listen times out during the VR session
	 * <p>
	 * <b>Notes: </b>The timeoutPrompt specified in
	 * {@linkplain SetGlobalProperties} is not used by PerformInteraction
	 * 
	 * @param timeoutPrompt
	 *            a List<TTSChunk> specify the phrase to be spoken when the
	 *            listen times out during the VR session
	 */    
    public void setTimeoutPrompt(List<TTSChunk> timeoutPrompt) {
        this.timeoutPrompt = timeoutPrompt;
    }
    
	/**
	 * Gets a Integer value representing the amount of time, in milliseconds,
	 * SDL will wait for the user to make a choice (VR or Menu)
	 * 
	 * @return Integer -a Integer representing the amount of time, in
	 *         milliseconds, SDL will wait for the user to make a choice (VR or
	 *         Menu)
	 */    
    public Integer getTimeout() {
        return this.timeout;
    }
    
	/**
	 * Sets the amount of time, in milliseconds, SDL will wait for the user to
	 * make a choice (VR or Menu). If this time elapses without the user making
	 * a choice, the timeoutPrompt will be spoken. After this timeout value has
	 * been reached, the interaction will stop and a subsequent interaction will
	 * take place after SDL speaks the timeout prompt. If that times out as
	 * well, the interaction will end completely. If omitted, the default is
	 * 10000ms
	 * 
	 * @param timeout
	 *            an Integer value representing the amount of time, in
	 *            milliseconds, SDL will wait for the user to make a choice (VR
	 *            or Menu)
	 *            <p>
	 *            <b>Notes: </b>Min Value: 5000; Max Value: 100000
	 */    
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

	/**
	 * Gets a Voice recognition Help, which is a suggested VR Help Items to
	 * display on-screen during Perform Interaction
	 * 
	 * @return List<VrHelpItem> -a List value representing a suggested VR
	 *         Help Items to display on-screen during Perform Interaction
	 * @since SmartDeviceLink 2.0
	 */
    public List<VrHelpItem> getVrHelp() {
        return this.vrHelp;
    }

	/**
	 * 
	 * @param vrHelp
	 *            a List representing a suggested VR Help Items to display
	 *            on-screen during Perform Interaction<br/>
	 *            If omitted on supported displays, the default SDL generated
	 *            list of suggested choices will be displayed
	 *            <p>
	 *            <b>Notes: </b>Min=1; Max=100
	 * @since SmartDeviceLink 2.0
	 */
    public void setVrHelp(List<VrHelpItem> vrHelp) {
        this.vrHelp = vrHelp;
    }
    
    public LayoutMode getInteractionLayout() {
        return LayoutMode.valueForJsonName(this.interactionLayout, sdlVersion);
    }
  
    public void setInteractionLayout( LayoutMode interactionLayout ) {
        this.interactionLayout = (interactionLayout == null) ? null : interactionLayout.getJsonName(sdlVersion);
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_INITIAL_TEXT, this.initialText);
            JsonUtils.addToJsonObject(result, KEY_INTERACTION_LAYOUT, this.interactionLayout);
            JsonUtils.addToJsonObject(result, KEY_INTERACTION_MODE, this.interactionMode);
            JsonUtils.addToJsonObject(result, KEY_TIMEOUT, this.timeout);
            
            JsonUtils.addToJsonObject(result, KEY_HELP_PROMPT, (this.helpPrompt == null) ? null :
                    JsonUtils.createJsonArrayOfJsonObjects(this.helpPrompt, sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_INITIAL_PROMPT,  (this.initialPrompt == null) ? null :
                    JsonUtils.createJsonArrayOfJsonObjects(this.initialPrompt, sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_TIMEOUT_PROMPT,  (this.timeoutPrompt == null) ? null :
                    JsonUtils.createJsonArrayOfJsonObjects(this.timeoutPrompt, sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_VR_HELP,  (this.vrHelp == null) ? null :
                    JsonUtils.createJsonArrayOfJsonObjects(this.vrHelp, sdlVersion));
            JsonUtils.addToJsonObject(result, KEY_INTERACTION_CHOICE_SET_ID_LIST,  
                    (this.choiceSetIds == null) ? null : JsonUtils.createJsonArray(this.choiceSetIds));
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((choiceSetIds == null) ? 0 : choiceSetIds.hashCode());
		result = prime * result + ((helpPrompt == null) ? 0 : helpPrompt.hashCode());
		result = prime * result + ((initialPrompt == null) ? 0 : initialPrompt.hashCode());
		result = prime * result + ((initialText == null) ? 0 : initialText.hashCode());
		result = prime * result + ((interactionLayout == null) ? 0 : interactionLayout.hashCode());
		result = prime * result + ((interactionMode == null) ? 0 : interactionMode.hashCode());
		result = prime * result + ((timeout == null) ? 0 : timeout.hashCode());
		result = prime * result + ((timeoutPrompt == null) ? 0 : timeoutPrompt.hashCode());
		result = prime * result + ((vrHelp == null) ? 0 : vrHelp.hashCode());
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
		PerformInteraction other = (PerformInteraction) obj;
		if (choiceSetIds == null) {
			if (other.choiceSetIds != null) { 
				return false;
			}
		}
		else if (!choiceSetIds.equals(other.choiceSetIds)) { 
			return false;
		}
		if (helpPrompt == null) {
			if (other.helpPrompt != null){ 
				return false;
			}
		} 
		else if (!helpPrompt.equals(other.helpPrompt)) { 
			return false;
		}
		if (initialPrompt == null) {
			if (other.initialPrompt != null) { 
				return false;
			}
		} 
		else if (!initialPrompt.equals(other.initialPrompt)) { 
			return false;
		}
		if (initialText == null) {
			if (other.initialText != null) { 
				return false;
			}
		} 
		else if (!initialText.equals(other.initialText)) { 
			return false;
		}
		if (interactionLayout == null) {
			if (other.interactionLayout != null) { 
				return false;
			}
		} 
		else if (!interactionLayout.equals(other.interactionLayout)) { 
			return false;
		}
		if (interactionMode == null) {
			if (other.interactionMode != null) { 
				return false;
			}
		} 
		else if (!interactionMode.equals(other.interactionMode)) { 
			return false;
		}
		if (timeout == null) {
			if (other.timeout != null) { 
				return false;
			}
		} 
		else if (!timeout.equals(other.timeout)) { 
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
		return true;
	}
}
