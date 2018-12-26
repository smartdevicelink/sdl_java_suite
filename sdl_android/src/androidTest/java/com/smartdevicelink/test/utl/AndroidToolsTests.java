package com.smartdevicelink.test.utl;

import junit.framework.Assert;
import android.content.ComponentName;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.util.AndroidTools;

public class AndroidToolsTests extends AndroidTestCase2 {
	
	
	public void testIsServiceExportedNormal(){
		
		try{
			AndroidTools.isServiceExported(mContext, new ComponentName(mContext, "test"));
		}catch(Exception e){
			Assert.fail("Exception during normal test: " + e.getMessage());
		}
		
	}
	public void testIsServiceExportedNull(){
		
		try{
			AndroidTools.isServiceExported(mContext, null);
			Assert.fail("Proccessed null data");
		}catch(Exception e){
			
		}
		
	}
	public void testSendExplicitBroadcast() {
		Intent pingIntent = new Intent();
		pingIntent.setAction(TransportConstants.START_ROUTER_SERVICE_ACTION);
		pingIntent.putExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_EXTRA, true);
		pingIntent.putExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_APP_PACKAGE, getContext().getPackageName());
		pingIntent.putExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_CMP_NAME, new ComponentName(getContext().getPackageName(), getClass().toString()));
		pingIntent.putExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_PING, true);
		Intent param = new Intent(pingIntent);
		AndroidTools.sendExplicitBroadcast(getContext(), pingIntent, null);
		if (pingIntent.getAction() == param.getAction() &&
				pingIntent.getClass() == param.getClass() &&
				pingIntent.getBooleanExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_EXTRA, false) == param.getBooleanExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_EXTRA, true) &&
				pingIntent.getStringExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_APP_PACKAGE) == param.getStringExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_APP_PACKAGE) &&
				pingIntent.getBooleanExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_PING, false) == param.getBooleanExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_PING, true) &&
				pingIntent.getComponent() == null && param.getComponent() == null){
			ComponentName comp1 = (ComponentName)pingIntent.getParcelableExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_CMP_NAME);
			ComponentName comp2 = (ComponentName)param.getParcelableExtra(TransportConstants.START_ROUTER_SERVICE_SDL_ENABLED_CMP_NAME);
			if (comp1.compareTo(comp2) != 0) {
				Assert.fail("ComponentName has been changed");
			}
		} else {
			Assert.fail("Given intent has been changed");
		}
	}

}
