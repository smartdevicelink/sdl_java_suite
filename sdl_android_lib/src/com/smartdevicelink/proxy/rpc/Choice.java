package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

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
public class Choice extends RPCObject {
	public static final String KEY_SECONDARY_TEXT = "secondaryText";
	public static final String KEY_TERTIARY_TEXT = "tertiaryText";
	public static final String KEY_SECONDARY_IMAGE = "secondaryImage";
	public static final String KEY_MENU_NAME = "menuName";
	public static final String KEY_VR_COMMANDS = "vrCommands";
	public static final String KEY_CHOICE_ID = "choiceID";
	public static final String KEY_IMAGE = "image";
	
	private Integer choiceId;
	private String name, secondaryText, tertiaryText;
	private List<String> vrCommands;
	private Image image, secondaryImage;
	
	/**
	 * Constructs a newly allocated Choice object
	 */
    public Choice() { }
    
    /**
     * Creates a Choice object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     * @param sdlVersion The version of SDL represented in the JSON
     */
    public Choice(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.choiceId = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_CHOICE_ID);
            this.name = JsonUtils.readStringFromJsonObject(jsonObject, KEY_MENU_NAME);
            this.secondaryText = JsonUtils.readStringFromJsonObject(jsonObject, KEY_SECONDARY_TEXT);
            this.tertiaryText = JsonUtils.readStringFromJsonObject(jsonObject, KEY_TERTIARY_TEXT);
            
            this.vrCommands = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_VR_COMMANDS);
            
            JSONObject imageObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_IMAGE);
            if(imageObj != null){
                this.image = new Image(imageObj);
            }
            
            imageObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_SECONDARY_IMAGE);
            if(imageObj != null){
                this.secondaryImage = new Image(imageObj);
            }
            
            break;
        }
    }
    
    /**
     * Get the application-scoped identifier that uniquely identifies this choice.
     * @return choiceID Min: 0  Max: 65535
     */    
    public Integer getChoiceID() {
        return choiceId;
    }
    
    /**
     * Set the application-scoped identifier that uniquely identifies this choice.
     * @param choiceID Min: 0  Max: 65535
     */    
    public void setChoiceID(Integer choiceID) {
        this.choiceId = choiceID;
    }
    
    /**
     * Text which appears in menu, representing this choice.
     *				<br/>Min: 1
     *				<br/>Max: 100
     * @return menuName the menu name
     */    
    public String getMenuName() {
        return this.name;
    }
    
    /**
     * Text which appears in menu, representing this choice.
     *				<br/>Min: 1
     *				<br/>Max: 100
     * @param menuName the menu name
     */    
    public void setMenuName(String menuName) {
        this.name = menuName;
    }
    
    /**
     * Get an array of strings to be used as VR synonyms for this choice. If this array is provided, it must have at least one non-empty element
     * @return vrCommands List
     * @since SmartDeviceLink 2.0
     */
    public List<String> getVrCommands() {
        return this.vrCommands;
    }
    
    /**
     * Set an array of strings to be used as VR synonyms for this choice. If this array is provided, it must have at least one non-empty element
     * @param vrCommands the List of  vrCommands
     * @since SmartDeviceLink 2.0
     */    
    public void setVrCommands(List<String> vrCommands) {
        this.vrCommands = vrCommands;
    }
    
    /**
     * Set the image
     * @param image the image of the choice
     */    
    public void setImage(Image image) {
        this.image = image;
    }
    
    /**
     * Get the image
     * @return the image of the choice
     */
    public Image getImage() {
    	return this.image;
    }
    
    public String getSecondaryText() {
        return this.secondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    public String getTertiaryText() {
        return this.tertiaryText;
    }

    public void setTertiaryText(String tertiaryText) {
        this.tertiaryText = tertiaryText;
    }

    public void setSecondaryImage(Image image) {
        this.secondaryImage = image;
    }

    public Image getSecondaryImage() {
        return this.secondaryImage;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_MENU_NAME, this.name);
            JsonUtils.addToJsonObject(result, KEY_SECONDARY_TEXT, this.secondaryText);
            JsonUtils.addToJsonObject(result, KEY_TERTIARY_TEXT, this.tertiaryText);
            JsonUtils.addToJsonObject(result, KEY_CHOICE_ID, this.choiceId);
            
            JsonUtils.addToJsonObject(result, KEY_IMAGE, (this.image == null) ? null : 
                this.image.getJsonParameters(sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_SECONDARY_IMAGE, (this.secondaryImage == null) ? null :
                this.secondaryImage.getJsonParameters(sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_VR_COMMANDS, (this.vrCommands == null) ? null :
                JsonUtils.createJsonArray(this.vrCommands));
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((choiceId == null) ? 0 : choiceId.hashCode());
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((secondaryImage == null) ? 0 : secondaryImage.hashCode());
		result = prime * result + ((secondaryText == null) ? 0 : secondaryText.hashCode());
		result = prime * result + ((tertiaryText == null) ? 0 : tertiaryText.hashCode());
		result = prime * result + ((vrCommands == null) ? 0 : vrCommands.hashCode());
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
		Choice other = (Choice) obj;
		if (choiceId == null) {
			if (other.choiceId != null) { 
				return false;
			}
		} else if (!choiceId.equals(other.choiceId)) { 
			return false;
		}
		if (image == null) {
			if (other.image != null) { 
				return false;
			}
		} else if (!image.equals(other.image)) { 
			return false;
		}
		if (name == null) {
			if (other.name != null) { 
				return false;
			}
		} else if (!name.equals(other.name)) { 
			return false;
		}
		if (secondaryImage == null) {
			if (other.secondaryImage != null) { 
				return false;
			}
		} else if (!secondaryImage.equals(other.secondaryImage)) { 
			return false;
		}
		if (secondaryText == null) {
			if (other.secondaryText != null) { 
				return false;
			}
		} else if (!secondaryText.equals(other.secondaryText)) { 
			return false;
		}
		if (tertiaryText == null) {
			if (other.tertiaryText != null) { 
				return false;
			}
		} else if (!tertiaryText.equals(other.tertiaryText)) { 
			return false;
		}
		if (vrCommands == null) {
			if (other.vrCommands != null) { 
				return false;
			}
		} else if (!vrCommands.equals(other.vrCommands)) { 
			return false;
		}
		return true;
	}
}
