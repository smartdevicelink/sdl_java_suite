package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.util.DebugTool;
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
	public static final String initialText = "initialText";
	public static final String interactionMode = "interactionMode";
	public static final String interactionChoiceSetIDList = "interactionChoiceSetIDList";
	public static final String interactionLayout = "interactionLayout";
	public static final String initialPrompt = "initialPrompt";
	public static final String helpPrompt = "helpPrompt";
	public static final String timeoutPrompt = "timeoutPrompt";
	public static final String timeout = "timeout";
	public static final String vrHelp = "vrHelp";
	/**
	 * Constructs a new PerformInteraction object
	 */
    public PerformInteraction() {
        super("PerformInteraction");
    }
	/**
	 * Constructs a new PerformInteraction object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */    
    public PerformInteraction(Hashtable hash) {
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
        return (String) parameters.get(PerformInteraction.initialText);
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
            parameters.put(PerformInteraction.initialText, initialText);
        } else {
        	parameters.remove(PerformInteraction.initialText);
        }
    }
	/**
	 * Gets an An array of one or more TTSChunks that, taken together, specify
	 * what is to be spoken to the user at the start of an interaction
	 * 
	 * @return List<TTSChunk> -a List<TTSChunk> value, specify what is to be
	 *         spoken to the user at the start of an interaction
	 */
    public List<TTSChunk> getInitialPrompt() {
        if (parameters.get(PerformInteraction.initialPrompt) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(PerformInteraction.initialPrompt);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (List<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<TTSChunk> newList = new ArrayList<TTSChunk>();
	                for (Object hashObj : list) {
	                    newList.add(new TTSChunk((Hashtable)hashObj));
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
            parameters.put(PerformInteraction.initialPrompt, initialPrompt);
        } else {
        	parameters.remove(PerformInteraction.initialPrompt);
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
        Object obj = parameters.get(PerformInteraction.interactionMode);
        if (obj instanceof InteractionMode) {
            return (InteractionMode) obj;
        } else if (obj instanceof String) {
            InteractionMode theCode = null;
            try {
                theCode = InteractionMode.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + PerformInteraction.interactionMode, e);
            }
            return theCode;
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
            parameters.put(PerformInteraction.interactionMode, interactionMode);
        } else {
        	parameters.remove(PerformInteraction.interactionMode);
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
    public List<Integer> getInteractionChoiceSetIDList() {
    	if(parameters.get(PerformInteraction.interactionChoiceSetIDList) instanceof List<?>){
    		List<?> list = (List<?>)parameters.get(PerformInteraction.interactionChoiceSetIDList);
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
	 *            <p>
	 *            <b>Notes: </b>Min Value: 0; Max Vlaue: 2000000000
	 */    
    public void setInteractionChoiceSetIDList(List<Integer> interactionChoiceSetIDList) {
        if (interactionChoiceSetIDList != null) {
            parameters.put(PerformInteraction.interactionChoiceSetIDList, interactionChoiceSetIDList);
        } else {
        	parameters.remove(PerformInteraction.interactionChoiceSetIDList);
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
    public List<TTSChunk> getHelpPrompt() {
        if(parameters.get(PerformInteraction.helpPrompt) instanceof List<?>){
        	List<?> list = (List<?>)parameters.get(PerformInteraction.helpPrompt);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (List<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<TTSChunk> newList = new ArrayList<TTSChunk>();
	                for (Object hashObj : list) {
	                    newList.add(new TTSChunk((Hashtable)hashObj));
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
        if (helpPrompt != null) {
            parameters.put(PerformInteraction.helpPrompt, helpPrompt);
        } else {
        	parameters.remove(PerformInteraction.helpPrompt);
        }
    }
	/**
	 * Gets An array of TTSChunks which, taken together, specify the phrase to
	 * be spoken when the listen times out during the VR session
	 * 
	 * @return List<TTSChunk> -a List<TTSChunk> specify the phrase to be
	 *         spoken when the listen times out during the VR session
	 */    
    public List<TTSChunk> getTimeoutPrompt() {
        if (parameters.get(PerformInteraction.timeoutPrompt) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(PerformInteraction.timeoutPrompt);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (List<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<TTSChunk> newList = new ArrayList<TTSChunk>();
	                for (Object hashObj : list) {
	                    newList.add(new TTSChunk((Hashtable)hashObj));
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
	 * <p>
	 * <b>Notes: </b>The timeoutPrompt specified in
	 * {@linkplain SetGlobalProperties} is not used by PerformInteraction
	 * 
	 * @param timeoutPrompt
	 *            a List<TTSChunk> specify the phrase to be spoken when the
	 *            listen times out during the VR session
	 */    
    public void setTimeoutPrompt(List<TTSChunk> timeoutPrompt) {
        if (timeoutPrompt != null) {
            parameters.put(PerformInteraction.timeoutPrompt, timeoutPrompt);
        } else {
        	parameters.remove(PerformInteraction.timeoutPrompt);
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
        return (Integer) parameters.get(PerformInteraction.timeout);
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
        if (timeout != null) {
            parameters.put(PerformInteraction.timeout, timeout);
        } else {
        	parameters.remove(PerformInteraction.timeout);
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
    public List<VrHelpItem> getVrHelp() {
        if (parameters.get(PerformInteraction.vrHelp) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(PerformInteraction.vrHelp);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof VrHelpItem) {
	                return (List<VrHelpItem>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<VrHelpItem> newList = new ArrayList<VrHelpItem>();
	                for (Object hashObj : list) {
	                    newList.add(new VrHelpItem((Hashtable)hashObj));
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
	 *            on-screen during Perform Interaction<br/>
	 *            If omitted on supported displays, the default SDL generated
	 *            list of suggested choices will be displayed
	 *            <p>
	 *            <b>Notes: </b>Min=1; Max=100
	 * @since SmartDeviceLink 2.0
	 */
    public void setVrHelp(List<VrHelpItem> vrHelp) {
        if (vrHelp != null) {
            parameters.put(PerformInteraction.vrHelp, vrHelp);
        } else {
        	parameters.remove(PerformInteraction.vrHelp);
        }
    }
    
    public LayoutMode getInteractionLayout() {
        Object obj = parameters.get(PerformInteraction.interactionLayout);
        if (obj instanceof DisplayType) {
            return (LayoutMode) obj;
        } else if (obj instanceof String) {
        	LayoutMode theCode = null;
            try {
                theCode = LayoutMode.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + PerformInteraction.interactionLayout, e);
            }
            return theCode;
        }
        return null;
    }
  
    public void setInteractionLayout( LayoutMode interactionLayout ) {
        if (interactionLayout != null) {
        	parameters.put(PerformInteraction.interactionLayout, interactionLayout );
        }
        else {
        	parameters.remove(PerformInteraction.interactionLayout);
        }
    }    
}
