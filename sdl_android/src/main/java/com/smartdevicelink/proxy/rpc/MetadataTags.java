package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;

import java.util.Collections;
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
	 * Set the metadata types of data contained in the "mainField1" text field
	 */
	public void setMainField1( List<MetadataType> metadataTypes ) {
		setValue(KEY_MAIN_FIELD_1_TYPE, metadataTypes);
	}

	/**
	 * Set the metadata type of data contained in the "mainField1" text field
	 */
	public void setMainField1(MetadataType metadataType) {
		setValue(KEY_MAIN_FIELD_1_TYPE, Collections.singletonList(metadataType));
	}

	/**
	 * @return   The type of data contained in the "mainField1" text field
	 */
	@SuppressWarnings("unchecked")
	public List<MetadataType> getMainField1() {
		return (List<MetadataType>) getObject(MetadataType.class, KEY_MAIN_FIELD_1_TYPE);
	}

	/**
	 * Set the metadata types of data contained in the "mainField2" text field
	 */
	public void setMainField2( List<MetadataType> metadataTypes ) {
		setValue(KEY_MAIN_FIELD_2_TYPE, metadataTypes);
	}

	/**
	 * Set the metadata type of data contained in the "mainField2" text field
	 */
	public void setMainField2(MetadataType metadataType) {
		setValue(KEY_MAIN_FIELD_2_TYPE, Collections.singletonList(metadataType));
	}

	/**
	 * @return   The type of data contained in the "mainField2" text field
	 */
	@SuppressWarnings("unchecked")
	public List<MetadataType> getMainField2() {
		return (List<MetadataType>) getObject(MetadataType.class, KEY_MAIN_FIELD_2_TYPE);
	}

	/**
	 * Set the metadata types of data contained in the "mainField3" text field
	 */
	public void setMainField3( List<MetadataType> metadataTypes ) {
		setValue(KEY_MAIN_FIELD_3_TYPE, metadataTypes);
	}

	/**
	 * Set the metadata type of data contained in the "mainField3" text field
	 */
	public void setMainField3(MetadataType metadataType) {
		setValue(KEY_MAIN_FIELD_3_TYPE, Collections.singletonList(metadataType));
	}

	/**
	 * @return   The type of data contained in the "mainField3" text field
	 */
	@SuppressWarnings("unchecked")
	public List<MetadataType> getMainField3() {
		return (List<MetadataType>) getObject(MetadataType.class, KEY_MAIN_FIELD_3_TYPE);
	}

	/**
	 * Set the metadata types of data contained in the "mainField4" text field
	 */
	public void setMainField4( List<MetadataType> metadataTypes ) {
		setValue(KEY_MAIN_FIELD_4_TYPE, metadataTypes);
	}

	/**
	 * Set the metadata type of data contained in the "mainField4" text field
	 */
	public void setMainField4(MetadataType metadataType) {
		setValue(KEY_MAIN_FIELD_4_TYPE, Collections.singletonList(metadataType));
	}

	/**
	 * @return   The type of data contained in the "mainField4" text field
	 */
	@SuppressWarnings("unchecked")
	public List<MetadataType> getMainField4() {
		return (List<MetadataType>) getObject(MetadataType.class, KEY_MAIN_FIELD_4_TYPE);
	}

}
