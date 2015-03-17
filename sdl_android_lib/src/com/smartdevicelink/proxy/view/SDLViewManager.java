package com.smartdevicelink.proxy.view;

import java.util.List;
import java.util.Vector;

import com.smartdevicelink.abstraction.listeners.HMINotificationListener;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;

public class SDLViewManager {
	
	private List<SDLView> mViewStack;
	//	TODO better object tracking.  Right now it is done by index.  
	//  We have to figure out which methods the objects will be the same in the list
	//  and compare on the object.
	public SDLViewManager() {
		mViewStack = new Vector<SDLView>();
	}
	
	
	//TODO This is before the show response is sent.
	//This allows the app to send other RPCs before the view is displayed.
	public void viewInitialized(SDLView view){
		view.onInitialize();
	}
	
	//TODO This is after the show response is successful
	public void viewShown(SDLView view){
		if(view == null)
			return;
		mViewStack.add(0, view);
	
		//TODO do we want this before or after notifying the old view?
		view.onShown();
		
		if (mViewStack.size() < 2) return;
		
		SDLView oldView = mViewStack.get(1);
		if(oldView == null)
			return;
		oldView.onBackground();
		oldView.onDestroyed();
				
	}	
	
	public HMINotificationListener hmiListener = new HMINotificationListener() {
		
		@Override
		public void onHMIStatus(OnHMIStatus status) {
			if (mViewStack.isEmpty()) return;
			
			SDLView view = mViewStack.get(0);
			if(view == null)
				return;
			if(status.getHmiLevel() == HMILevel.HMI_BACKGROUND)
				view.onBackground();
		}
	};

}
