package com.smartdevicelink.ui;

import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MenuParams;

/**
 * Used as a struct for the AddCommand RPC family
 * @author Joey Grover
 *
 */
public class SdlMenuItem extends MenuParams{
	SdlMenuItemListener listener;
	Image image;
	int id = -1;
	int menuId = -1;
	
	public SdlMenuItem(SdlMenuItemListener listener){
		this.id = SdlViewHelper.generateViewId();
		this.listener = listener;
	}
	public SdlMenuItem(String menuName,Image image, SdlMenuItemListener listener){
		this.id = SdlViewHelper.generateViewId();
		this.setMenuName(menuName);
		this.image = image;
		this.listener = listener;
	}
	
	public void setParentId(int parentId){
		this.setParentId(parentId);
	}
	
	public void setMenuId(int menuId){
		this.menuId = menuId;
	}
	
	public interface SdlMenuItemListener{//onCommand
		public void onItemSelected();
	}
}
