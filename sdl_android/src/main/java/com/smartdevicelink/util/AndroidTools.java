package com.smartdevicelink.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.pm.PackageManager.NameNotFoundException;

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
}
