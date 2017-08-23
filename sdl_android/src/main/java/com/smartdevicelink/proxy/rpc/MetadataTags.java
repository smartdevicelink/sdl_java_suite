package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;

import java.util.Hashtable;
import java.util.List;

public class MetadataTags extends RPCStruct {

	public static final String KEY_MAIN_FIELD_1_TYPE = "mainField1";
	public static final String KEY_MAIN_FIELD_2_TYPE = "mainField2";
	public static final String KEY_MAIN_FIELD_3_TYPE = "mainField3";
	public static final String KEY_MAIN_FIELD_4_TYPE = "mainField4";

	/**
	 * Constructs a newly allocated MetadataTags object
	 */
	public MetadataTags(){}

	/**
	 * Constructs a newly allocated MetadataTags object indicated by the Hashtable parameter
	 * @param hash The Hashtable to use
	 */
	public MetadataTags(Hashtable<String, Object> hash){super(hash);}

	/**
	 * set       The type of data contained in the "mainField1" text field
	 */
	public void setMainField1( List<MetadataType> mainField1 ) {
		setValue(KEY_MAIN_FIELD_1_TYPE, mainField1);
	}

	/**
	 * @return   The type of data contained in the "mainField1" text field
	 */
	@SuppressWarnings("unchecked")
	public List<MetadataType> getMainField1() {
		return (List<MetadataType>) getObject(MetadataType.class, KEY_MAIN_FIELD_1_TYPE);
	}

	/**
	 * set       The type of data contained in the "mainField2" text field
	 */
	public void setMainField2( List<MetadataType> mainField2 ) {
		setValue(KEY_MAIN_FIELD_2_TYPE, mainField2);
	}

	/**
	 * @return   The type of data contained in the "mainField2" text field
	 */
	@SuppressWarnings("unchecked")
	public List<MetadataType> getMainField2() {
		return (List<MetadataType>) getObject(MetadataType.class, KEY_MAIN_FIELD_2_TYPE);
	}

	/**
	 * set       The type of data contained in the "mainField3" text field
	 */
	public void setMainField3( List<MetadataType> mainField3 ) {
		setValue(KEY_MAIN_FIELD_3_TYPE, mainField3);
	}

	/**
	 * @return   The type of data contained in the "mainField3" text field
	 */
	@SuppressWarnings("unchecked")
	public List<MetadataType> getMainField3() {
		return (List<MetadataType>) getObject(MetadataType.class, KEY_MAIN_FIELD_3_TYPE);
	}

	/**
	 * set       The type of data contained in the "mainField4" text field
	 */
	public void setMainField4( List<MetadataType> mainField4 ) {
		setValue(KEY_MAIN_FIELD_4_TYPE, mainField4);
	}

	/**
	 * @return   The type of data contained in the "mainField4" text field
	 */
	@SuppressWarnings("unchecked")
	public List<MetadataType> getMainField4() {
		return (List<MetadataType>) getObject(MetadataType.class, KEY_MAIN_FIELD_4_TYPE);
	}

}
