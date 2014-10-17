package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.DebugTool;

/**
 * A choice is an option which a user can select either via the menu or via voice recognition (VR) during an application initiated interaction.
 * <p><b> Parameter List
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>choiceID</td>
 * 			<td>Int16</td>
 * 			<td>Application-scoped identifier that uniquely identifies this choice.
 *             <br/>Min: 0
 *				<br/>Max: 65535
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>menuName</td>
 * 			<td>String</td>
 * 			<td>Text which appears in menu, representing this choice.
 *				<br/>Min: 1
 *				<br/>Max: 100
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
 */
public class Choice extends RPCStruct {
	public static final String secondaryText = "secondaryText";
	public static final String tertiaryText = "tertiaryText";
	public static final String secondaryImage = "secondaryImage";
	public static final String menuName = "menuName";
	public static final String vrCommands = "vrCommands";
	public static final String choiceID = "choiceID";
	public static final String image = "image";
	/**
	 * Constructs a newly allocated Choice object
	 */
    public Choice() { }
    /**
     * Constructs a newly allocated Choice object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */    
    public Choice(Hashtable hash) {
        super(hash);
    }
    /**
     * Get the application-scoped identifier that uniquely identifies this choice.
     * @return choiceID Min: 0  Max: 65535
     */    
    public Integer getChoiceID() {
        return (Integer) store.get(Choice.choiceID);
    }
    /**
     * Set the application-scoped identifier that uniquely identifies this choice.
     * @param choiceID Min: 0  Max: 65535
     */    
    public void setChoiceID(Integer choiceID) {
        if (choiceID != null) {
            store.put(Choice.choiceID, choiceID);
        }
    }
    /**
     * Text which appears in menu, representing this choice.
     *				<br/>Min: 1
     *				<br/>Max: 100
     * @return menuName the menu name
     */    
    public String getMenuName() {
        return (String) store.get(Choice.menuName);
    }
    /**
     * Text which appears in menu, representing this choice.
     *				<br/>Min: 1
     *				<br/>Max: 100
     * @param menuName the menu name
     */    
    public void setMenuName(String menuName) {
        if (menuName != null) {
            store.put(Choice.menuName, menuName);
        }
    }
    /**
     * Get an array of strings to be used as VR synonyms for this choice. If this array is provided, it must have at least one non-empty element
     * @return vrCommands List
     * @since SmartDeviceLink 2.0
     */    
    public List<String> getVrCommands() {
        if (store.get(Choice.vrCommands) instanceof List<?>) {
        	List<?> list = (List<?>)store.get( Choice.vrCommands);
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
            store.put(Choice.vrCommands, vrCommands);
        }
    }
    /**
     * Set the image
     * @param image the image of the choice
     */    
    public void setImage(Image image) {
        if (image != null) {
            store.put(Choice.image, image);
        } else {
        	store.remove(Choice.image);
        }
    }
    /**
     * Get the image
     * @return the image of the choice
     */    
    public Image getImage() {
    	Object obj = store.get(Choice.image);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new Image((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Choice.image, e);
            }
        }
        return null;
    }
    
    public String getSecondaryText() {
        return (String) store.get(Choice.secondaryText);
    }

    public void setSecondaryText(String secondaryText) {
        if (secondaryText != null) {
            store.put(Choice.secondaryText, secondaryText);
        }
        else {
            store.remove(Choice.secondaryText);
        }
    }

    public String getTertiaryText() {
        return (String) store.get(Choice.tertiaryText);
    }

    public void setTertiaryText(String tertiaryText) {
        if (tertiaryText != null) {
            store.put(Choice.tertiaryText, tertiaryText);
        }
        else {
            store.remove(Choice.tertiaryText);
        }        
    }

    public void setSecondaryImage(Image image) {
        if (image != null) {
            store.put(Choice.secondaryImage, image);
        } else {
            store.remove(Choice.secondaryImage);
        }
    }

    public Image getSecondaryImage() {
        Object obj = store.get(Choice.secondaryImage);
        if (obj instanceof Image) {
            return (Image) obj;
        } else if (obj instanceof Hashtable) {
            try {
                return new Image((Hashtable) obj);
            } catch (Exception e) {
                DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Choice.secondaryImage, e);
            }
        }
        return null;
    }      
}
