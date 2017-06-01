package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;

import static com.smartdevicelink.proxy.rpc.TireStatus.KEY_INNER_RIGHT_REAR;

/**
 * Struct defining the characteristics of a displayed field on the HMI.
 * <p><b> Parameter List</b></p>
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
 * 			<td>Integer</td>
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
 * 			<td>Integer</td>
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
 * 
 * @see TextFieldName
 * @see Alert
 * @see Show
 * @see PerformInteraction
 * @see ScrollableMessage
 * @see PerformAudioPassThru
 * @see ShowConstantTBT
 * 
 */
public class TextField extends RPCStruct {
	public static final String KEY_WIDTH = "width";
	public static final String KEY_CHARACTER_SET = "characterSet";
	public static final String KEY_ROWS = "rows";
	public static final String KEY_NAME = "name";
	/**
	 * Constructs a newly allocated TextField object
	 */
    public TextField() { }
    /**
     * Constructs a newly allocated TextField object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */    
    public TextField(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Get the enumeration identifying the field.	
     * @return the name of TextField
     */    
    public TextFieldName getName() {
        return (TextFieldName) getObject(TextFieldName.class, KEY_NAME);
    }
    /**
     * Set the enumeration identifying the field.	
     * @param name the name of TextField
     */    
    public void setName( TextFieldName name ) {
        setValue(KEY_NAME, name);
    }
    /**
     * Get the character set that is supported in this field.
     * @return the character set
     */    
    public CharacterSet getCharacterSet() {
        return (CharacterSet) getObject(CharacterSet.class, KEY_CHARACTER_SET);
    }
    /**
     * Set the character set that is supported in this field.
     * @param characterSet - the character set
     */    
    public void setCharacterSet( CharacterSet characterSet ) {
        setValue(KEY_CHARACTER_SET, characterSet);
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
        return getInteger( KEY_WIDTH );
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
        setValue(KEY_WIDTH, width);
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
        return getInteger( KEY_ROWS );
    }
    public void setRows( Integer rows ) {
        setValue(KEY_ROWS, rows);
    }
}
