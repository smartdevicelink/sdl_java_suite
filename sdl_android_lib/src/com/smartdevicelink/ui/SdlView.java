package com.smartdevicelink.ui;

import java.util.HashMap;

import android.util.SparseArray;

import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.ui.SdlButton.SdlButtonListner;

public class SdlView {
	
	SparseArray<SdlButton> buttons;
	SdlMenu menu;
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
	
	/**
	 * This sets a custom menu for this view. When this view is set at the current view, the SDL view manager will recognize
	 * there is a custom menu attached to this view object and take the necessary steps to populate the hardware with this custom menu.
	 * The menu object must be fully formed before getting to this point.
	 * @param menu The custom menu for this view. The menu object must be fully formed before getting to this point.
	 */
	public void setMenu(SdlMenu menu ){
		this.menu = menu;
	}
	
	
	
}
