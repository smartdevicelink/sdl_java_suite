package com.smartdevicelink.ui;

import java.util.HashMap;
import java.util.List;

import android.util.Log;
import android.util.SparseArray;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCRequestFactory;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SubscribeButton;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

public class ViewManager implements IViewManager{

	private static final String TAG = "SDL View Manager";
	
	
	/**
	 * Private static instance of this class.
	 */
	private static ViewManager instance = null;
	
	private SdlProxyALM proxy;
	OnRPCNotificationListener onButtonPressListener,onButtonEventListener;
	private SparseArray<SdlView> views;
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
	}
	
	public static ViewManager getInstance(){
		if(instance==null){
			instance = new ViewManager();
		}
		return instance;
	}
	
	/* 
	 * In the future this method should be changed to just am interface that allows for writing out bytes
	 * @param proxy
	 */
	public void setProxy( SdlProxyALM proxy){ 
		this.proxy = proxy;
		//Add the Rpc Notifications to the proxy
		proxy.addOnRPCNotificationListener(FunctionID.ON_BUTTON_PRESS, onButtonPressListener);
		proxy.addOnRPCNotificationListener(FunctionID.ON_BUTTON_EVENT, onButtonEventListener);
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
		views.put(view.id, view);
		return view.id;
	}
	
	/**
	 * This will set the current view that is being displayed on the connected hardware. 
	 * @param viewId
	 * @return true if the view was found and being processed to be set. False if not found
	 */
	@SuppressWarnings("unchecked")
	public boolean setView(int viewId){
		if(views.indexOfKey(viewId)>=0){
			SdlView view = views.get(viewId);
			if(view!=null){
				//Subscribe buttons
				HashMap<ButtonName,SdlButton> subscribedButtons = view.getSubscribedButtons();
				if(subscribedButtons!=null){
					SubscribeButton msg;
					for(ButtonName buttonName:subscribedButtons.keySet()){
						msg = RPCRequestFactory.buildSubscribeButton(buttonName, correlationID);
						sendRpc(msg);
					}
				}
				
				
				Show show = new Show();
				
				//TODO add all the text views, images, etc
				
				//Grab the buttons
				List<SdlButton> subButtons = SdlViewHelper.asList(view.buttons); //Kind of hack-y, but it should work
				List<? extends SoftButton> castedButtons = subButtons;
				show.setSoftButtons((List<SoftButton>) castedButtons);

				//Finally send the show
				this.sendRpc(show);
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
