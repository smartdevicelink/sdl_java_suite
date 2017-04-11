package com.smartdevicelink.ui;

import com.smartdevicelink.ui.SdlMenuItem.SdlMenuItemListener;

import android.util.SparseArray;

public class SdlMenu {

	int id = -1;
	boolean isRoot = true;
	String menuName = null;
	SparseArray<SdlMenuItem> items;
	SparseArray<SdlMenu> subMenus;
	
	public SdlMenu(){
		
	}
	
	public SdlMenu(SparseArray<SdlMenuItem> items){
		this.items = items;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setMenuName(String name){
		this.menuName = name;
	}
	
	/**
	 * Adds a menu item to this menu. 
	 * @param item the item that is to be added
	 * @return if the menu item was added or not
	 */
	public boolean addMenuItem(SdlMenuItem item){
		if(this.items == null){
			this.items = new SparseArray<SdlMenuItem>();
		}
		
		if(item.id>=0){
			items.put(item.id, item);
			return true;
		}
		return false;
	}
	
	/**
	 * Adds a sub menu to this menu object. This will only work if the menu is the root menu. This is to prevent nesting of lots of menus.
	 * @param id The id for the submenu being added
	 * @param menu The completed menu object to be used as the submenu
	 * @return if the submenu was added or not
	 */
	public boolean addSubMenu(int id, SdlMenu menu){//All items should already have their parent id set
		if(this.isRoot){
			menu.id = id;
			menu.isRoot = false;
			subMenus.put(id, menu);
			
			//We now create a menu item at the root level to correspond to this sub menu
			SdlMenuItem item = new SdlMenuItem(null); //TODO check if this is ok. Should be fine as the hardware would should just switch to the new submenu
			item.menuId = id;// SdlViewHelper.generateMenuId();
			item.setMenuName(menu.menuName);
			item.setParentId(0);//TODO check on this. 
			//Add the actual menu to our sparse array of sub menus
			return addMenuItem(item);
		}
		return false;
	}
	
	public SdlMenuItemListener parseMenuItems(int id){
		SdlMenuItem item = this.items.get(id);
		if(item == null){
			//Might be a submenu command
			SdlMenu tempMenu;
			for(int j=0; j<subMenus.size();j++){
				tempMenu = subMenus.valueAt(j);
				if(tempMenu!=null){
					item = tempMenu.items.get(id);
					if(item != null){
						return item.listener;
					}
				}
			}
		}else{
			return item.listener;
		}
		
		return null;
	}
	
}
