package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
/**
 * Performs an application-initiated interaction in which the user can select a
 * {@linkplain Choice} from among the specified Choice Sets. For instance, an
 * application may use a PerformInteraction to ask a user to say the name of a
 * song to play. The user's response is only valid if it appears in the
 * specified Choice Sets and is recognized by SDL
 * <p></p>
 * <p>Function Group: Base</p>
 * 
 * <p><b>HMILevel needs to be FULL</b></p>
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
 * 			<td>initialText</td>
 * 			<td>String</td>
 * 			<td>Displayed when the interaction begins. This text may be overlaid by the "Listening" prompt during the interaction. Text is displayed on first line of multiline display, and is centered. If text does not fit on line, it will be truncated</td>
 *                 <td>Y</td>
 * 			<td>maxlength:500</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>initialPrompt</td>
 * 			<td>TTSChunk</td>
 * 			<td>An array of one or more TTSChunks that, taken together, specify what is to be spoken to the user at the start of an interaction.</td>
 *                 <td>Y</td>
 * 			<td>minsize:1; maxsize:100</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>interactionMode</td>
 * 			<td>InteractionMode</td>
 * 			<td>Indicates how user selects interaction choice. User can choose either by voice (VR_ONLY), by visual selection from the menu (MANUAL_ONLY), or by either mode (BOTH). </td>
 *                 <td>Y</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>interactionChoiceSetIDList</td>
 * 			<td>Integer</td>
 * 			<td>Array of one or more Choice Set IDs. User can select any choice from any of the specified Choice Sets.</td>
 *                 <td>Y</td>
 * 			<td>minsize:0; maxsize:100; minvalue:0; maxvalue:2000000000</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>helpPrompt</td>
 * 			<td>TTSChunk</td>
 * 			<td>An array of TTSChunks which, taken together, specify the help phrase to be spoken when the user says "help" during the VR session. If this parameter is omitted, the help prompt will be constructed by SDL from the first vrCommand of each choice of all the Choice Sets specified in the interactionChoiceSetIDList parameter. </td>
 *                 <td>N</td>
 * 			<td>minsize:1; maxsize:100; The helpPrompt specified in SetGlobalProperties is not used by PerformInteraction.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>timeoutPrompt</td>
 * 			<td>TTSChunk</td>
 * 			<td>An array of TTSChunks which, taken together, specify the phrase to be spoken when the listen times out during the VR session. If this parameter is omitted, the timeout prompt will be the same as the help prompt (see helpPrompt parameter). </td>
 *                 <td>N</td>
 * 			<td>The timeoutPrompt specified in SetGlobalProperties is not used by PerformInteraction. minsize:1;maxsize:100</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>timeout</td>
 * 			<td>Integer</td>
 * 			<td>The amount of time, in milliseconds, SDL will wait for the user to make a choice (VR or Menu). If this time elapses without the user making a choice, the timeoutPrompt will be spoken. After this timeout value has been reached, the interaction will stop and a subsequent interaction will take place after SDL speaks the timeout prompt. If that times out as well, the interaction will end completely. If omitted, the default is 10000ms.</td>
 *                 <td>N</td>
 * 			<td>minvalue:5000; maxvalue:100000; defvalue:10000</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>vrHelp</td>
 * 			<td>VrHelpItem</td>
 * 			<td>Ability to send suggested VR Help Items to display on-screen during Perform Interaction If omitted on supported displays, the default SDL generated list of suggested choices will be displayed.</td>
 *                 <td>N</td>
 * 			<td>Min = 1;Max = 100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>interactionLayout</td>
 * 			<td>LayoutMode</td>
 * 			<td>See {@linkplain LayoutMode}</td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 3.0</td>
 * 		</tr>
 *  </table>
 *  
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
	/**
	 * Constructs a new PerformInteraction object
	 */
    public PerformInteraction() {
        super(FunctionID.PERFORM_INTERACTION.toString());
    }
	/**
	 * Constructs a new PerformInteraction object indicated by the Hashtable
	 * parameter
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */    
    public PerformInteraction(Hashtable<String, Object> hash) {
        super(hash);
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
        return (String) parameters.get(KEY_INITIAL_TEXT);
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
        if (initialText != null) {
            parameters.put(KEY_INITIAL_TEXT, initialText);
        } else {
        	parameters.remove(KEY_INITIAL_TEXT);
        }
    }
	/**
	 * Gets an An array of one or more TTSChunks that, taken together, specify
	 * what is to be spoken to the user at the start of an interaction
	 * 
	 * @return List<TTSChunk> -a List<TTSChunk> value, specify what is to be
	 *         spoken to the user at the start of an interaction
	 */
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getInitialPrompt() {
        if (parameters.get(KEY_INITIAL_PROMPT) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_INITIAL_PROMPT);
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
	 * Sets An array of one or more TTSChunks that, taken together, specify what
	 * is to be spoken to the user at the start of an interaction
	 * 
	 * @param initialPrompt
	 *            a List<TTSChunk> value, specify what is to be spoken to the
	 *            user at the start of an interaction
	 */    
    public void setInitialPrompt(List<TTSChunk> initialPrompt) {
        if (initialPrompt != null) {
            parameters.put(KEY_INITIAL_PROMPT, initialPrompt);
        } else {
        	parameters.remove(KEY_INITIAL_PROMPT);
        }
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
        Object obj = parameters.get(KEY_INTERACTION_MODE);
        if (obj instanceof InteractionMode) {
            return (InteractionMode) obj;
        } else if (obj instanceof String) {
            return InteractionMode.valueForString((String) obj);
        }
        return null;
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
        if (interactionMode != null) {
            parameters.put(KEY_INTERACTION_MODE, interactionMode);
        } else {
        	parameters.remove(KEY_INTERACTION_MODE);
        }
    }
	/**
	 * Gets a List<Integer> value representing an Array of one or more Choice
	 * Set IDs
	 * 
	 * @return List<Integer> -a List<Integer> value representing an Array of
	 *         one or more Choice Set IDs. User can select any choice from any
	 *         of the specified Choice Sets
	 */    
    @SuppressWarnings("unchecked")
    public List<Integer> getInteractionChoiceSetIDList() {
    	if(parameters.get(KEY_INTERACTION_CHOICE_SET_ID_LIST) instanceof List<?>){
    		List<?> list = (List<?>)parameters.get(KEY_INTERACTION_CHOICE_SET_ID_LIST);
    		if(list != null && list.size()>0){
        		Object obj = list.get(0);
        		if(obj instanceof Integer){
        			return (List<Integer>) list;
        		}
    		}
    	}
        return null;
    }
	/**
	 * Sets a List<Integer> representing an Array of one or more Choice Set
	 * IDs. User can select any choice from any of the specified Choice Sets
	 * 
	 * @param interactionChoiceSetIDList
	 *            -a List<Integer> representing an Array of one or more Choice
	 *            Set IDs. User can select any choice from any of the specified
	 *            Choice Sets
	 *            <p></p>
	 *            <b>Notes: </b>Min Value: 0; Max Vlaue: 2000000000
	 */    
    public void setInteractionChoiceSetIDList(List<Integer> interactionChoiceSetIDList) {
        if (interactionChoiceSetIDList != null) {
            parameters.put(KEY_INTERACTION_CHOICE_SET_ID_LIST, interactionChoiceSetIDList);
        } else {
        	parameters.remove(KEY_INTERACTION_CHOICE_SET_ID_LIST);
        }
    }
	/**
	 * Gets a List<TTSChunk> which taken together, specify the help phrase to
	 * be spoken when the user says "help" during the VR session
	 * 
	 * @return List<TTSChunk> -a List<TTSChunk> which taken together,
	 *         specify the help phrase to be spoken when the user says "help"
	 *         during the VR session
	 */    
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getHelpPrompt() {
        if(parameters.get(KEY_HELP_PROMPT) instanceof List<?>){
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
	 * Sets An array of TTSChunks which, taken together, specify the help phrase
	 * to be spoken when the user says "help" during the VR session
	 * <p></p>
	 * If this parameter is omitted, the help prompt will be constructed by SDL
	 * from the first vrCommand of each choice of all the Choice Sets specified
	 * in the interactionChoiceSetIDList parameter
	 * <P></p>
	 * <b>Notes: </b>The helpPrompt specified in
	 * {@linkplain SetGlobalProperties} is not used by PerformInteraction
	 * 
	 * @param helpPrompt
	 *            a List<TTSChunk> which taken together, specify the help
	 *            phrase to be spoken when the user says "help" during the VR
	 *            session
	 */    
    public void setHelpPrompt(List<TTSChunk> helpPrompt) {
        if (helpPrompt != null) {
            parameters.put(KEY_HELP_PROMPT, helpPrompt);
        } else {
        	parameters.remove(KEY_HELP_PROMPT);
        }
    }
	/**
	 * Gets An array of TTSChunks which, taken together, specify the phrase to
	 * be spoken when the listen times out during the VR session
	 * 
	 * @return List<TTSChunk> -a List<TTSChunk> specify the phrase to be
	 *         spoken when the listen times out during the VR session
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
	 * Sets An array of TTSChunks which, taken together, specify the phrase to
	 * be spoken when the listen times out during the VR session
	 * <p></p>
	 * <b>Notes: </b>The timeoutPrompt specified in
	 * {@linkplain SetGlobalProperties} is not used by PerformInteraction
	 * 
	 * @param timeoutPrompt
	 *            a List<TTSChunk> specify the phrase to be spoken when the
	 *            listen times out during the VR session
	 */    
    public void setTimeoutPrompt(List<TTSChunk> timeoutPrompt) {
        if (timeoutPrompt != null) {
            parameters.put(KEY_TIMEOUT_PROMPT, timeoutPrompt);
        } else {
        	parameters.remove(KEY_TIMEOUT_PROMPT);
        }
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
        return (Integer) parameters.get(KEY_TIMEOUT);
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
	 *            <p></p>
	 *            <b>Notes: </b>Min Value: 5000; Max Value: 100000
	 */    
    public void setTimeout(Integer timeout) {
        if (timeout != null) {
            parameters.put(KEY_TIMEOUT, timeout);
        } else {
        	parameters.remove(KEY_TIMEOUT);
        }
    }

	/**
	 * Gets a Voice recognition Help, which is a suggested VR Help Items to
	 * display on-screen during Perform Interaction
	 * 
	 * @return List<VrHelpItem> -a List value representing a suggested VR
	 *         Help Items to display on-screen during Perform Interaction
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
	 * 
	 * @param vrHelp
	 *            a List representing a suggested VR Help Items to display
	 *            on-screen during Perform Interaction
	 *            If omitted on supported displays, the default SDL generated
	 *            list of suggested choices will be displayed
	 *            <p></p>
	 *            <b>Notes: </b>Min=1; Max=100
	 * @since SmartDeviceLink 2.0
	 */
    public void setVrHelp(List<VrHelpItem> vrHelp) {
        if (vrHelp != null) {
            parameters.put(KEY_VR_HELP, vrHelp);
        } else {
        	parameters.remove(KEY_VR_HELP);
        }
    }
    
    public LayoutMode getInteractionLayout() {
        Object obj = parameters.get(KEY_INTERACTION_LAYOUT);
        if (obj instanceof LayoutMode) {
            return (LayoutMode) obj;
        } else if (obj instanceof String) {
        	return LayoutMode.valueForString((String) obj);
        }
        return null;
    }
  
    public void setInteractionLayout( LayoutMode interactionLayout ) {
        if (interactionLayout != null) {
        	parameters.put(KEY_INTERACTION_LAYOUT, interactionLayout );
        }
        else {
        	parameters.remove(KEY_INTERACTION_LAYOUT);
        }
    }    
}
