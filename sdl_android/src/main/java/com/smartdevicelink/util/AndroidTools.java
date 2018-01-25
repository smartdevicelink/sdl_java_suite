package com.smartdevicelink.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;

import com.smartdevicelink.transport.TransportConstants;

import java.util.HashMap;
import java.util.List;

public class AndroidTools {
	/**
	 * Check to see if a component is exported
	 * @param Context object used to retrieve the package manager
	 * @param componentName of the component in question
	 * @return true if this component is tagged as exported
	 */
	public static boolean isServiceExported(Context context, ComponentName name) {
	    try {
	        ServiceInfo serviceInfo = context.getPackageManager().getServiceInfo(name, PackageManager.GET_META_DATA);
	        return serviceInfo.exported;
	    } catch (NameNotFoundException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	/**
	 * Get all SDL enabled apps. If the package name is null, it will return all apps. However, if the package name is included, the
	 * resulting hash map will not include the app with that package name.
	 * @param context
	 * @param myPackageName
	 * @return
	 */
	public static HashMap<String,ResolveInfo> getSdlEnabledApps(Context context, String myPackageName){
		Intent intent = new Intent(TransportConstants.START_ROUTER_SERVICE_ACTION);
		List<ResolveInfo> infos = context.getPackageManager().queryBroadcastReceivers(intent, 0);
		HashMap<String,ResolveInfo> sdlMultiList = new HashMap<String,ResolveInfo>();
		for(ResolveInfo info: infos){
			if(info.activityInfo.applicationInfo.packageName.equals(myPackageName)){
				continue; //Ignoring my own package
			}
			sdlMultiList.put(info.activityInfo.packageName, info);
		}
		return sdlMultiList;
	}

	/**
	 * Checks if the given Intent can be resolved.
	 * @param pm the PackageManager to use
	 * @param i the intent to be checked
	 * @return true if the intent can be resolve; false otherwise
	 */
	public static boolean isIntentAvailable(PackageManager pm, Intent i) {
		List<ResolveInfo> list = pm.queryIntentServices(i, PackageManager.MATCH_DEFAULT_ONLY);

		return list.size() > 1;
	}

}
