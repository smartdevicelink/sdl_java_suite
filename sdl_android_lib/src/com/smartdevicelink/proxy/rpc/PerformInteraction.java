package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;
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
        return (String) parameters.get(Names.initialText);
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
            parameters.put(Names.initialText, initialText);
        } else {
        	parameters.remove(Names.initialText);
        }
    }
	/**
	 * Gets an An array of one or more TTSChunks that, taken together, specify
	 * what is to be spoken to the user at the start of an interaction
	 * 
	 * @return Vector<TTSChunk> -a Vector<TTSChunk> value, specify what is to be
	 *         spoken to the user at the start of an interaction
	 */
    public Vector<TTSChunk> getInitialPrompt() {
        if (parameters.get(Names.initialPrompt) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)parameters.get(Names.initialPrompt);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (Vector<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<TTSChunk> newList = new Vector<TTSChunk>();
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
	 *            a Vector<TTSChunk> value, specify what is to be spoken to the
	 *            user at the start of an interaction
	 */    
    public void setInitialPrompt(Vector<TTSChunk> initialPrompt) {
        if (initialPrompt != null) {
            parameters.put(Names.initialPrompt, initialPrompt);
        } else {
        	parameters.remove(Names.initialPrompt);
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
        Object obj = parameters.get(Names.interactionMode);
        if (obj instanceof InteractionMode) {
            return (InteractionMode) obj;
        } else if (obj instanceof String) {
            InteractionMode theCode = null;
            try {
                theCode = InteractionMode.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.interactionMode, e);
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
            parameters.put(Names.interactionMode, interactionMode);
        } else {
        	parameters.remove(Names.interactionMode);
        }
    }
	/**
	 * Gets a Vector<Integer> value representing an Array of one or more Choice
	 * Set IDs
	 * 
	 * @return Vector<Integer> -a Vector<Integer> value representing an Array of
	 *         one or more Choice Set IDs. User can select any choice from any
	 *         of the specified Choice Sets
	 */    
    public Vector<Integer> getInteractionChoiceSetIDList() {
    	if(parameters.get(Names.interactionChoiceSetIDList) instanceof Vector<?>){
    		Vector<?> list = (Vector<?>)parameters.get(Names.interactionChoiceSetIDList);
    		if(list != null && list.size()>0){
        		Object obj = list.get(0);
        		if(obj instanceof Integer){
        			return (Vector<Integer>) list;
        		}
    		}
    	}
        return null;
    }
	/**
	 * Sets a Vector<Integer> representing an Array of one or more Choice Set
	 * IDs. User can select any choice from any of the specified Choice Sets
	 * 
	 * @param interactionChoiceSetIDList
	 *            -a Vector<Integer> representing an Array of one or more Choice
	 *            Set IDs. User can select any choice from any of the specified
	 *            Choice Sets
	 *            <p>
	 *            <b>Notes: </b>Min Value: 0; Max Vlaue: 2000000000
	 */    
    public void setInteractionChoiceSetIDList(Vector<Integer> interactionChoiceSetIDList) {
        if (interactionChoiceSetIDList != null) {
            parameters.put(Names.interactionChoiceSetIDList, interactionChoiceSetIDList);
        } else {
        	parameters.remove(Names.interactionChoiceSetIDList);
        }
    }
	/**
	 * Gets a Vector<TTSChunk> which taken together, specify the help phrase to
	 * be spoken when the user says "help" during the VR session
	 * 
	 * @return Vector<TTSChunk> -a Vector<TTSChunk> which taken together,
	 *         specify the help phrase to be spoken when the user says "help"
	 *         during the VR session
	 */    
    public Vector<TTSChunk> getHelpPrompt() {
        if(parameters.get(Names.helpPrompt) instanceof Vector<?>){
	    	Vector<?> list = (Vector<?>)parameters.get(Names.helpPrompt);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (Vector<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<TTSChunk> newList = new Vector<TTSChunk>();
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
	 *            a Vector<TTSChunk> which taken together, specify the help
	 *            phrase to be spoken when the user says "help" during the VR
	 *            session
	 */    
    public void setHelpPrompt(Vector<TTSChunk> helpPrompt) {
        if (helpPrompt != null) {
            parameters.put(Names.helpPrompt, helpPrompt);
        } else {
        	parameters.remove(Names.helpPrompt);
        }
    }
	/**
	 * Gets An array of TTSChunks which, taken together, specify the phrase to
	 * be spoken when the listen times out during the VR session
	 * 
	 * @return Vector<TTSChunk> -a Vector<TTSChunk> specify the phrase to be
	 *         spoken when the listen times out during the VR session
	 */    
    public Vector<TTSChunk> getTimeoutPrompt() {
        if (parameters.get(Names.timeoutPrompt) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)parameters.get(Names.timeoutPrompt);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (Vector<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<TTSChunk> newList = new Vector<TTSChunk>();
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
	 *            a Vector<TTSChunk> specify the phrase to be spoken when the
	 *            listen times out during the VR session
	 */    
    public void setTimeoutPrompt(Vector<TTSChunk> timeoutPrompt) {
        if (timeoutPrompt != null) {
            parameters.put(Names.timeoutPrompt, timeoutPrompt);
        } else {
        	parameters.remove(Names.timeoutPrompt);
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
        return (Integer) parameters.get(Names.timeout);
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
            parameters.put(Names.timeout, timeout);
        } else {
        	parameters.remove(Names.timeout);
        }
    }

	/**
	 * Gets a Voice recognition Help, which is a suggested VR Help Items to
	 * display on-screen during Perform Interaction
	 * 
	 * @return Vector<VrHelpItem> -a Vector value representing a suggested VR
	 *         Help Items to display on-screen during Perform Interaction
	 * @since SmartDeviceLink 2.0
	 */
    public Vector<VrHelpItem> getVrHelp() {
        if (parameters.get(Names.vrHelp) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)parameters.get(Names.vrHelp);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof VrHelpItem) {
	                return (Vector<VrHelpItem>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<VrHelpItem> newList = new Vector<VrHelpItem>();
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
	 *            a Vector representing a suggested VR Help Items to display
	 *            on-screen during Perform Interaction<br/>
	 *            If omitted on supported displays, the default SDL generated
	 *            list of suggested choices will be displayed
	 *            <p>
	 *            <b>Notes: </b>Min=1; Max=100
	 * @since SmartDeviceLink 2.0
	 */
    public void setVrHelp(Vector<VrHelpItem> vrHelp) {
        if (vrHelp != null) {
            parameters.put(Names.vrHelp, vrHelp);
        } else {
        	parameters.remove(Names.vrHelp);
        }
    }
    
    public LayoutMode getInteractionLayout() {
        Object obj = parameters.get(Names.interactionLayout);
        if (obj instanceof DisplayType) {
            return (LayoutMode) obj;
        } else if (obj instanceof String) {
        	LayoutMode theCode = null;
            try {
                theCode = LayoutMode.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.interactionLayout, e);
            }
            return theCode;
        }
        return null;
    }
  
    public void setInteractionLayout( LayoutMode interactionLayout ) {
        if (interactionLayout != null) {
        	parameters.put(Names.interactionLayout, interactionLayout );
        }
        else {
        	parameters.remove(Names.interactionLayout);
        }
    }    
}
