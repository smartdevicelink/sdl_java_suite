package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.util.DebugTool;

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
public class TextField extends RPCStruct {
	/**
	 * Constructs a newly allocated TextField object
	 */
    public TextField() { }
    /**
     * Constructs a newly allocated TextField object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */    
    public TextField(Hashtable hash) {
        super(hash);
    }
    /**
     * Get the enumeration identifying the field.	
     * @return the name of TextField
     */    
    public TextFieldName getName() {
        Object obj = store.get(Names.name);
        if (obj instanceof TextFieldName) {
            return (TextFieldName) obj;
        } else if (obj instanceof String) {
            TextFieldName theCode = null;
            try {
                theCode = TextFieldName.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.name, e);
            }
            return theCode;
        }
        return null;
    }
    /**
     * Set the enumeration identifying the field.	
     * @param name the name of TextField
     */    
    public void setName( TextFieldName name ) {
        if (name != null) {
            store.put(Names.name, name );
        }
    }
    /**
     * Get the character set that is supported in this field.
     * @return the character set
     */    
    public CharacterSet getCharacterSet() {
        Object obj = store.get(Names.characterSet);
        if (obj instanceof CharacterSet) {
            return (CharacterSet) obj;
        } else if (obj instanceof String) {
            CharacterSet theCode = null;
            try {
                theCode = CharacterSet.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.characterSet, e);
            }
            return theCode;
        }
        return null;
    }
    /**
     * Set the character set that is supported in this field.
     * @param characterSet - the character set
     */    
    public void setCharacterSet( CharacterSet characterSet ) {
        if (characterSet != null) {
            store.put(Names.characterSet, characterSet );
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
        return (Integer) store.get( Names.width );
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
            store.put(Names.width, width );
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
        return (Integer) store.get( Names.rows );
    }
    public void setRows( Integer rows ) {
        if (rows != null) {
            store.put(Names.rows, rows );
        }
    }
}
