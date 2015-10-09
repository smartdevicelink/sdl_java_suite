package com.smartdevicelink.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import android.util.Log;
import android.util.SparseArray;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCRequestFactory;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SubscribeButton;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.ui.SdlMenuItem.SdlMenuItemListener;
import com.smartdevicelink.util.CorrelationIdGenerator;

public class ViewManager implements IViewManager{

	private static final String TAG = "SDL View Manager";
	
	
	/**
	 * Private static instance of this class.
	 */
	private static ViewManager instance = null;
	
	private SdlProxyALM proxy;
	OnRPCNotificationListener onButtonPressListener,onButtonEventListener, onCommandListener;
	private SparseArray<SdlView> views;
	private SdlMenu defaultMenu = null;
	private int currentView;
	
	private ViewManager(){
		views = new SparseArray<SdlView>();
		currentView = -1;
		
		onButtonPressListener = new OnRPCNotificationListener(){

			@Override
			public void onNotified(RPCNotification notification) {
				OnButtonPress press = (OnButtonPress)notification;
				SdlButton button = parseButton(press.getButtonName(), press.getCustomButtonName());
				if(button!=null && button.listener!=null){
					button.listener.onButtonPress();
				}
			}
			
		};
		
		onButtonEventListener = new OnRPCNotificationListener(){

			@Override
			public void onNotified(RPCNotification notification) {
				OnButtonEvent press = (OnButtonEvent)notification;
				SdlButton button = parseButton(press.getButtonName(), press.getCustomButtonID());
				if(button!=null && button.listener!=null){
					button.listener.onButtonEvent();
				}
				
			}
			
		};
		onCommandListener = new OnRPCNotificationListener(){

			@Override
			public void onNotified(RPCNotification notification) {
				OnCommand onCommand = (OnCommand)notification;
				int id = onCommand.getCmdID().intValue();
				SdlMenuItemListener listener = null;
				SdlView view =views.get(currentView);
				
				if(view!=null && view.menu!=null){
					 listener = view.menu.parseMenuItems(id);
				}else if(defaultMenu!=null){
					listener = defaultMenu.parseMenuItems(id);
				}
				
				if(listener!=null){
					listener.onItemSelected();
				}
				
				
			}
			
		};
	}
	
	public static ViewManager getInstance(){
		if(instance==null){
			instance = new ViewManager();
		}
		return instance;
	}
	
	/* 
	 * In the future this method should be changed to just an interface that allows for writing out bytes
	 * @param proxy
	 */
	public void setProxy( SdlProxyALM proxy){ 
		this.proxy = proxy;
		//Add the Rpc Notifications to the proxy
		proxy.addOnRPCNotificationListener(FunctionID.ON_BUTTON_PRESS, onButtonPressListener);
		proxy.addOnRPCNotificationListener(FunctionID.ON_BUTTON_EVENT, onButtonEventListener);
		proxy.addOnRPCNotificationListener(FunctionID.ON_COMMAND, onCommandListener);

	}
	
	/**
	 * Add a view to the manager to use at a latter time. All views are referenced from their view id.
	 * @param view the view that is to be added to the manager, it should already have a view id assigned to it. If not, one will be created and returned.
	 * @return the id of the view added. If one was not set in the view, this will be the id created during the add process.
	 */
	public int addView(SdlView view){
		if(view.id<=0){
			view.id = SdlViewHelper.generateViewId();
		}
		view.setIViewManager(this);
		views.put(view.id, view);
		return view.id;
	}
	
	/**
	 * This will set the current view that is being displayed on the connected hardware. 
	 * @param viewId
	 * @return true if the view was found and being processed to be set. False if not found
	 */
	@SuppressWarnings("unchecked")
	public boolean setView(int viewId){ //Lots of magic
		if(views.indexOfKey(viewId)>=0){
			SdlView view = views.get(viewId);
			if(view!=null){
				
				/* ******* Subscribe buttons *****/
				HashMap<ButtonName,SdlButton> subscribedButtons = view.getSubscribedButtons();
				if(subscribedButtons!=null){
					SubscribeButton msg;
					for(ButtonName buttonName:subscribedButtons.keySet()){
						msg = RPCRequestFactory.buildSubscribeButton(buttonName, CorrelationIdGenerator.generateId());
						sendRpc(msg);
					}
				}
				/* ******* Show *****/	
				view.invalidate();
				
				SdlView oldView = views.get(currentView);
				/* ******* Menus *****/
				//If the view has it's own set of menus we need to delete what we've already sent, then send the new items.
				if(view.menu !=null){
					//Clear whatever menu is currently being displayed
					
					if(oldView!=null && oldView.menu!=null){
						//Delete the menu items that are stored in the previous view
						deleteMenu(oldView.menu);
					}else{
						//We should be using the default menu items. So remove those
						deleteMenu(this.defaultMenu);
					}
					//Now the menus are cleared so it's time to send the new ones
					sendMenu(view.menu);
					
				}else if(oldView !=null && oldView.menu!=null){
					//The old view had it's own menu so we need to delete those
					deleteMenu(oldView.menu);
					//Now the menus are cleared so it's time to send the default ones
					sendMenu(this.defaultMenu);
				}else{ //should be firstRun
					sendMenu(this.defaultMenu);
				}
				
				//Now that everything is set up we set the currentView equal to the new view id
				currentView = viewId;
				return true;
			}
		}
		return false;
	}
	
	private SdlButton parseButton(ButtonName name, int id){
		SdlView view = views.get(currentView);
		if(view!=null){
			if(name!=null){
				if(name.equals(ButtonName.CUSTOM_BUTTON)){
					return view.getButtonForId(id);
				}else{ //We have a predefined button, let's notify the listeners
					return view.getSubscribedButton(name);
				}
			}
		}
		return null;
	}

	/**
	 * Sets the supplied menu as the default menu for all views. This menu will be used as long as the current view doesn't have a custom menu.
	 * @param menu the menu object that will be set as default
	 */
	public void setDefaultMenu(SdlMenu menu){
		defaultMenu = menu;
	}
	
	/**
	 * Sends all the SDL Menu Items contained in this menu. It will generate the AddCommand RPCs and send them to the connected hardware. If it is the root menu and finds submenu
	 * SdlMenuItems it will construct and send AddSubMenu RPCs as well as all the AddCommand RPCs needed to send the corresponding submenu items.
	 * @param menu the menu which should be traversed to send menu items.
	 */
	public void sendMenu(SdlMenu menu){
		SparseArray<SdlMenuItem> items = menu.items;
		AddCommand add;
		AddSubMenu subMenu;
		SdlMenuItem item;
		for(int i=0; i<items.size();i++){
			item = items.valueAt(i);
			if(item.menuId>0){
				//AddSubMenu
				subMenu = new AddSubMenu();
				//subMenu.setMenuParams(item);
				subMenu.setMenuID(item.menuId);
				subMenu.setMenuName(item.getMenuName());
				subMenu.setCorrelationID(CorrelationIdGenerator.generateId());
				sendRpc(subMenu);
			}else{
				add = new AddCommand();
				add.setMenuParams(item);
				add.setCmdID(item.id);
				add.setCmdIcon(item.image);
				add.setCorrelationID(CorrelationIdGenerator.generateId());
				sendRpc(add);
			}
		}
		if(menu.isRoot && menu.subMenus!=null){
			//Add the sub add commands
			SdlMenu tempMenu;
			for(int j=0; j<menu.subMenus.size();j++){
				tempMenu = menu.subMenus.valueAt(j);
				if(tempMenu!=null){
					sendMenu(tempMenu); //Recursively calling this function should just churn through the add commands
				}
				
			}
			
		}
	}
	
	public void deleteMenu(SdlMenu menu){
		SparseArray<SdlMenuItem> items = menu.items;
		DeleteCommand delete;
		DeleteSubMenu deleteSub;
		SdlMenuItem item;
		for(int i=0; i<items.size();i++){
			item = items.valueAt(i);
			if(item.menuId>0){
				deleteSub = new DeleteSubMenu();
				deleteSub.setMenuID(item.menuId);
				deleteSub.setCorrelationID(CorrelationIdGenerator.generateId());
				sendRpc(deleteSub);
			}else{
				delete = new DeleteCommand();
				delete.setCmdID(item.id);
				delete.setCorrelationID(CorrelationIdGenerator.generateId());
				sendRpc(delete);
			}
		}
		//We shouldn't need this since when we delete a sub menu all the commands under it should be removed as well. Keeping just in case.
		/*if(menu.isRoot && menu.subMenus!=null){
			//Add the sub add commands
			SdlMenu tempMenu;
			for(int j=0; j<menu.subMenus.size();j++){
				tempMenu = menu.subMenus.valueAt(j);
				if(tempMenu!=null){
					deleteMenu(tempMenu); //Recursively calling this function should just churn through the delete commands
				}
				
			}
			
		}*/
	}
	
	@Override
	public void sendRpc(RPCRequest message) {
		if(proxy!=null){
			try {
				proxy.sendRPCRequest(message);
			} catch (SdlException e) {
				e.printStackTrace();
			}
		}else{
			//Throw exception?
			Log.e(TAG, "Unable to send RPC, proxy reference is null");
		}
		
	}
	
	
}
