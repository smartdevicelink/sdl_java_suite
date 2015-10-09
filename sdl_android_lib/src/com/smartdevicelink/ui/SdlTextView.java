package com.smartdevicelink.ui;

import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;

public class SdlTextView extends TextField{
	
	String text;
	int id = -1;
	
	public SdlTextView(TextFieldName textField, String text){
		this.setName(textField);
		this.text = text;
		this.id = SdlViewHelper.generateViewId();
	}
	
	public void setText(String text){
		this.text = text;
	}
}
