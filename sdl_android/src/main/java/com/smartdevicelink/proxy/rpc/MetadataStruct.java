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

	public MetadataStruct(){}
	public MetadataStruct(Hashtable<String, Object> hash){super(hash);}

	public void setMainField1( List<TextFieldType> mainField1 ) {
		setValue(KEY_MAIN_FIELD_1_TYPE, mainField1);
	}

	@SuppressWarnings("unchecked")
	public List<TextFieldType> getMainField1() {
		return (List<TextFieldType>) getObject(TextFieldType.class, KEY_MAIN_FIELD_1_TYPE);
	}

	public void setMainField2( List<TextFieldType> mainField2 ) {
		setValue(KEY_MAIN_FIELD_2_TYPE, mainField2);
	}

	@SuppressWarnings("unchecked")
	public List<TextFieldType> getMainField2() {
		return (List<TextFieldType>) getObject(TextFieldType.class, KEY_MAIN_FIELD_2_TYPE);
	}

	public void setMainField3( List<TextFieldType> mainField3 ) {
		setValue(KEY_MAIN_FIELD_3_TYPE, mainField3);
	}

	@SuppressWarnings("unchecked")
	public List<TextFieldType> getMainField3() {
		return (List<TextFieldType>) getObject(TextFieldType.class, KEY_MAIN_FIELD_3_TYPE);
	}

	public void setMainField4( List<TextFieldType> mainField4 ) {
		setValue(KEY_MAIN_FIELD_4_TYPE, mainField4);
	}

	@SuppressWarnings("unchecked")
	public List<TextFieldType> getMainField4() {
		return (List<TextFieldType>) getObject(TextFieldType.class, KEY_MAIN_FIELD_4_TYPE);
	}

}
