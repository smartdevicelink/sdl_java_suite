package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.DebugTool;

/**
 * A choice is an option which a user can select either via the menu or via voice recognition (VR) during an application initiated interaction.
 *  For example, the application may request for the user`s choice among several suggested ones: Yes, No, Skip.
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>choiceID</td>
 * 			<td>Integer</td>
 * 			<td>Application-scoped identifier that uniquely identifies this choice.
 *             Min: 0;
 *				Max: 65535
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>menuName</td>
 * 			<td>String</td>
 * 			<td>Text which appears in menu, representing this choice.
 *				Min: 1;
 *				Max: 100
 * 			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *     <tr>
 * 			<td>vrCommands</td>
 * 			<td>String[]</td>
 * 			<td>An array of strings to be used as VR synonyms for this choice. If this array is provided, it must have at least one non-empty element</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *     <tr>
 * 			<td>image</td>
 * 			<td>Image</td>
 * 			<td>Either a static hex icon value or a binary image file  name identifier (sent by PutFile).</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * </table>
 * 
  * @since SmartDeviceLink 1.0
  * 
  * @see AddCommand
  * @see PerformInteraction
  * @see Image
 */
public class Choice extends RPCStruct {
	public static final String KEY_SECONDARY_TEXT = "secondaryText";
	public static final String KEY_TERTIARY_TEXT = "tertiaryText";
	public static final String KEY_SECONDARY_IMAGE = "secondaryImage";
	public static final String KEY_MENU_NAME = "menuName";
	public static final String KEY_VR_COMMANDS = "vrCommands";
	public static final String KEY_CHOICE_ID = "choiceID";
	public static final String KEY_IMAGE = "image";
	/**
	 * Constructs a newly allocated Choice object
	 */
    public Choice() { }
    /**
     * Constructs a newly allocated Choice object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */    
    public Choice(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Get the application-scoped identifier that uniquely identifies this choice.
     * @return choiceID Min: 0;  Max: 65535
     */    
    public Integer getChoiceID() {
        return (Integer) store.get(KEY_CHOICE_ID);
    }
    /**
     * Set the application-scoped identifier that uniquely identifies this choice.
     * @param choiceID Min: 0  Max: 65535
     */    
    public void setChoiceID(Integer choiceID) {
        if (choiceID != null) {
            store.put(KEY_CHOICE_ID, choiceID);
        } else {
        	store.remove(KEY_CHOICE_ID);
        }
    }
    /**
     * Text which appears in menu, representing this choice.
     *				Min: 1;
     *				Max: 100
     * @return menuName the menu name
     */    
    public String getMenuName() {
        return (String) store.get(KEY_MENU_NAME);
    }
    /**
     * Text which appears in menu, representing this choice.
     *				Min: 1;
     *				Max: 100
     * @param menuName the menu name
     */    
    public void setMenuName(String menuName) {
        if (menuName != null) {
            store.put(KEY_MENU_NAME, menuName);
        } else {
        	store.remove(KEY_MENU_NAME);
        }
    }
    /**
     * Get an array of strings to be used as VR synonyms for this choice. If this array is provided, it must have at least one non-empty element
     * @return vrCommands List
     * @since SmartDeviceLink 2.0
     */    
    @SuppressWarnings("unchecked")
    public List<String> getVrCommands() {
        if (store.get(KEY_VR_COMMANDS) instanceof List<?>) {
        	List<?> list = (List<?>)store.get( KEY_VR_COMMANDS);
        	if (list != null && list.size() > 0) {
        		Object obj = list.get(0);
        		if (obj instanceof String) {
                	return (List<String>) list;
        		}
        	}
        }
        return null;
    }
    /**
     * Set an array of strings to be used as VR synonyms for this choice. If this array is provided, it must have at least one non-empty element
     * @param vrCommands the List of  vrCommands
     * @since SmartDeviceLink 2.0
     */    
    public void setVrCommands(List<String> vrCommands) {
        if (vrCommands != null) {
            store.put(KEY_VR_COMMANDS, vrCommands);
        } else {
        	store.remove(KEY_VR_COMMANDS);
        }
    }
    /**
     * Set the image
     * @param image the image of the choice
     */    
    public void setImage(Image image) {
        if (image != null) {
            store.put(KEY_IMAGE, image);
        } else {
        	store.remove(KEY_IMAGE);
        }
    }
    /**
     * Get the image
     * @return the image of the choice
     */    
    @SuppressWarnings("unchecked")
    public Image getImage() {
    	Object obj = store.get(KEY_IMAGE);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new Image((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_IMAGE, e);
            }
        }
        return null;
    }
    
    public String getSecondaryText() {
        return (String) store.get(KEY_SECONDARY_TEXT);
    }

    public void setSecondaryText(String secondaryText) {
        if (secondaryText != null) {
            store.put(KEY_SECONDARY_TEXT, secondaryText);
        }
        else {
            store.remove(KEY_SECONDARY_TEXT);
        }
    }

    public String getTertiaryText() {
        return (String) store.get(KEY_TERTIARY_TEXT);
    }

    public void setTertiaryText(String tertiaryText) {
        if (tertiaryText != null) {
            store.put(KEY_TERTIARY_TEXT, tertiaryText);
        }
        else {
            store.remove(KEY_TERTIARY_TEXT);
        }        
    }

    public void setSecondaryImage(Image image) {
        if (image != null) {
            store.put(KEY_SECONDARY_IMAGE, image);
        } else {
            store.remove(KEY_SECONDARY_IMAGE);
        }
    }

    @SuppressWarnings("unchecked")
    public Image getSecondaryImage() {
        Object obj = store.get(KEY_SECONDARY_IMAGE);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
            try {
                return new Image((Hashtable<String, Object>) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_SECONDARY_IMAGE, e);
            }
        }
        return null;
    }      
}
