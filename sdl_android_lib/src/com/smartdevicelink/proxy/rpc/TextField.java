package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;

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
        Object obj = store.get(KEY_NAME);
        if (obj instanceof TextFieldName) {
            return (TextFieldName) obj;
        } else if (obj instanceof String) {
            return TextFieldName.valueForString((String) obj);
        }
        return null;
    }
    /**
     * Set the enumeration identifying the field.	
     * @param name the name of TextField
     */    
    public void setName( TextFieldName name ) {
        if (name != null) {
            store.put(KEY_NAME, name );
        } else {
        	store.remove(KEY_NAME);
        }
    }
    /**
     * Get the character set that is supported in this field.
     * @return the character set
     */    
    public CharacterSet getCharacterSet() {
        Object obj = store.get(KEY_CHARACTER_SET);
        if (obj instanceof CharacterSet) {
            return (CharacterSet) obj;
        } else if (obj instanceof String) {
            return CharacterSet.valueForString((String) obj);
        }
        return null;
    }
    /**
     * Set the character set that is supported in this field.
     * @param characterSet - the character set
     */    
    public void setCharacterSet( CharacterSet characterSet ) {
        if (characterSet != null) {
            store.put(KEY_CHARACTER_SET, characterSet );
        } else {
        	store.remove(KEY_CHARACTER_SET);
        }
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
        return (Integer) store.get( KEY_WIDTH );
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
        if (width != null) {
            store.put(KEY_WIDTH, width );
        } else {
        	store.remove(KEY_WIDTH);
        }
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
        return (Integer) store.get( KEY_ROWS );
    }
    public void setRows( Integer rows ) {
        if (rows != null) {
            store.put(KEY_ROWS, rows );
        } else {
        	store.remove(KEY_ROWS);
        }
    }
}
