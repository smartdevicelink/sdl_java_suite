package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.util.JsonUtils;

/**
 * Struct defining the characteristics of a displayed field on the HMI.
 * <p><b> Parameter List
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>name</td>
 * 			<td>TextFieldName</td>
 * 			<td>Enumeration identifying the field.	</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>characterSet</td>
 * 			<td>CharacterSet</td>
 * 			<td>The character set that is supported in this field.	</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>width</td>
 * 			<td>Int16</td>
 * 			<td>The number of characters in one row of this field.
 * 					<ul>
 *					<li>Minvalue="1"</li>
 *					<li>maxvalue="500"</li>
 *					</ul>
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rows</td>
 * 			<td>Int16</td>
 * 			<td>The number of rows for this text field.
 * 					<ul>
 *					<li>Minvalue="1"</li>
 *					<li>maxvalue="3"</li>
 *					</ul>
 *			</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 1.0
 */
public class TextField extends RPCObject {
	public static final String KEY_WIDTH = "width";
	public static final String KEY_CHARACTER_SET = "characterSet";
	public static final String KEY_ROWS = "rows";
	public static final String KEY_NAME = "name";
	
	private String name; // represents TextFieldName enum
	private String charSet; // represents CharacterSet enum
	private Integer width, rows;
	
	/**
	 * Constructs a newly allocated TextField object
	 */
    public TextField() { }
    
    /**
     * Creates a TextField object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public TextField(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.name = JsonUtils.readStringFromJsonObject(jsonObject, KEY_NAME);
            this.charSet = JsonUtils.readStringFromJsonObject(jsonObject, KEY_CHARACTER_SET);
            this.width = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_WIDTH);
            this.rows = JsonUtils.readIntegerFromJsonObject(jsonObject, KEY_ROWS);
            break;
        }
    }
    
    /**
     * Get the enumeration identifying the field.	
     * @return the name of TextField
     */    
    public TextFieldName getName() {
        return TextFieldName.valueForJsonName(this.name, sdlVersion);
    }
    
    /**
     * Set the enumeration identifying the field.	
     * @param name the name of TextField
     */    
    public void setName( TextFieldName name ) {
        this.name = name.getJsonName(sdlVersion);
    }
    
    /**
     * Get the character set that is supported in this field.
     * @return the character set
     */    
    public CharacterSet getCharacterSet() {
        return CharacterSet.valueForJsonName(this.charSet, sdlVersion);
    }
    
    /**
     * Set the character set that is supported in this field.
     * @param characterSet - the character set
     */    
    public void setCharacterSet( CharacterSet characterSet ) {
        this.charSet = characterSet.getJsonName(sdlVersion);
    }
    
    /**
     * Get the number of characters in one row of this field.
     * 					<ul>
     *					<li>Minvalue="1"</li>
     *					<li>maxvalue="500"</li>
     *					</ul>
     * @return the number of characters in one row of this field
     */    
    public Integer getWidth() {
        return this.width;
    }
    
    /**
     * Set the number of characters in one row of this field.
     * 					<ul>
     *					<li>Minvalue="1"</li>
     *					<li>maxvalue="500"</li>
     *					</ul>
     * @param width  the number of characters in one row of this field
     */    
    public void setWidth( Integer width ) {
        this.width = width;
    }
    
    /**
     *Get the number of rows for this text field.
     * 					<ul>
     *					<li>Minvalue="1"</li>
     *					<li>maxvalue="3"</li>
     *					</ul>
     * @return  the number of rows for this text field
     */    
    public Integer getRows() {
        return this.rows;
    }
    
    public void setRows( Integer rows ) {
        this.rows = rows;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_CHARACTER_SET, this.charSet);
            JsonUtils.addToJsonObject(result, KEY_NAME, this.name);
            JsonUtils.addToJsonObject(result, KEY_ROWS, this.rows);
            JsonUtils.addToJsonObject(result, KEY_WIDTH, this.width);
            break;
        }
        
        return result;
    }
}
