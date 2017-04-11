package com.smartdevicelink.ui;

import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.SoftButton;

public class SdlButton extends SoftButton{
	
	SdlButtonListner listener;
	int id;
	
	public SdlButton(){
		super();
		//TODO create constructor to take in basic things
		id = SdlViewHelper.generateViewId();
		this.setSoftButtonID(Integer.valueOf(id));
	}
	
	public SdlButton(String text, Image image){
		super();
		id = SdlViewHelper.generateViewId();
		this.setSoftButtonID(Integer.valueOf(id));
		this.setText(text);
		this.setImage(image);
	}
	
	public void setSdlButtonListerner(SdlButtonListner listener){
		this.listener = listener;
	}
	
	public SdlButtonListner getSdlButtonListner(){
		return this.listener;
	}
	
	public interface SdlButtonListner{
		public void onButtonPress();
		public void onButtonEvent();
	}
}
