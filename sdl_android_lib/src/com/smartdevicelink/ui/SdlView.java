package com.smartdevicelink.ui;

import java.util.HashMap;

import android.util.SparseArray;

import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.ui.SdlButton.SdlButtonListner;

public class SdlView {
	SparseArray<SdlButton> buttons;
	HashMap<ButtonName,SdlButton> subscribedButtons;
	int id = -1;
	IViewManager iViewManager = null;
	
	
	public SdlView(){
		id = SdlViewHelper.generateViewId();
	}
	
	/**
	 * 
	 * @param id This id MUST be generated from the SldViewHelper.generateId() method.
	 */
	public SdlView(int id){
		this.id =id;
	}
	
	public int getId(){
		return this.id;
	}
	
	protected void setIViewManager(IViewManager iFace){
		iViewManager = iFace;
	}
	
	public void addButton(SdlButton button){//For now we are assuming these are custom buttons
		//TODO Check for ButtonName
		buttons.put(button.id, button);
	}
	
	public SparseArray<SdlButton> getButtons(){
		return this.buttons;
	}
	
	public SdlButton getButtonForId(int id){
		return this.buttons.get(id);
	}
	
	public SdlButton getSubscribedButton(ButtonName name){
		return this.subscribedButtons.get(name);
	}
	
	public HashMap<ButtonName,SdlButton> getSubscribedButtons(){
		return this.subscribedButtons;
	}
	
	public void subscribeButton(ButtonName name, SdlButtonListner listener){
		
		if(subscribedButtons == null){
			subscribedButtons = new HashMap<ButtonName,SdlButton>();
		}
		
		SdlButton button = new SdlButton();
		button.setSdlButtonListerner(listener);
		subscribedButtons.put(name, button);
	}
	
	
	
}
