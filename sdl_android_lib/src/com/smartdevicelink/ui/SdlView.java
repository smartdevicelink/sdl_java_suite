package com.smartdevicelink.ui;

import android.util.SparseArray;

public class SdlView {
	SparseArray<SdlButton> buttons;
	int id = -1;
	IViewManager iViewManager = null;
	
	
	public SdlView(){
		id = SdlViewHelper.generateViewId();
	}
	
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
	
	
	
	
}
