package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.TextFieldType;

import java.util.Hashtable;
import java.util.List;

public class MetadataStruct extends RPCStruct {

	public static final String KEY_MAIN_FIELD_1_TYPE = "mainField1Type";
	public static final String KEY_MAIN_FIELD_2_TYPE = "mainField2Type";
	public static final String KEY_MAIN_FIELD_3_TYPE = "mainField3Type";
	public static final String KEY_MAIN_FIELD_4_TYPE = "mainField4Type";

	/**
	 * Constructs a newly allocated MetadataStruct object
	 */
	public MetadataStruct(){}

	/**
	 * Constructs a newly allocated MetadataStruct object indicated by the Hashtable parameter
	 * @param hash The Hashtable to use
	 */
	public MetadataStruct(Hashtable<String, Object> hash){super(hash);}

	/**
	 * set       The type of data contained in the "mainField1" text field
	 */
	public void setMainField1( List<TextFieldType> mainField1 ) {
		setValue(KEY_MAIN_FIELD_1_TYPE, mainField1);
	}

	/**
	 * @return   The type of data contained in the "mainField1" text field
	 */
	@SuppressWarnings("unchecked")
	public List<TextFieldType> getMainField1() {
		return (List<TextFieldType>) getObject(TextFieldType.class, KEY_MAIN_FIELD_1_TYPE);
	}

	/**
	 * set       The type of data contained in the "mainField2" text field
	 */
	public void setMainField2( List<TextFieldType> mainField2 ) {
		setValue(KEY_MAIN_FIELD_2_TYPE, mainField2);
	}

	/**
	 * @return   The type of data contained in the "mainField2" text field
	 */
	@SuppressWarnings("unchecked")
	public List<TextFieldType> getMainField2() {
		return (List<TextFieldType>) getObject(TextFieldType.class, KEY_MAIN_FIELD_2_TYPE);
	}

	/**
	 * set       The type of data contained in the "mainField3" text field
	 */
	public void setMainField3( List<TextFieldType> mainField3 ) {
		setValue(KEY_MAIN_FIELD_3_TYPE, mainField3);
	}

	/**
	 * @return   The type of data contained in the "mainField3" text field
	 */
	@SuppressWarnings("unchecked")
	public List<TextFieldType> getMainField3() {
		return (List<TextFieldType>) getObject(TextFieldType.class, KEY_MAIN_FIELD_3_TYPE);
	}

	/**
	 * set       The type of data contained in the "mainField4" text field
	 */
	public void setMainField4( List<TextFieldType> mainField4 ) {
		setValue(KEY_MAIN_FIELD_4_TYPE, mainField4);
	}

	/**
	 * @return   The type of data contained in the "mainField4" text field
	 */
	@SuppressWarnings("unchecked")
	public List<TextFieldType> getMainField4() {
		return (List<TextFieldType>) getObject(TextFieldType.class, KEY_MAIN_FIELD_4_TYPE);
	}

}
