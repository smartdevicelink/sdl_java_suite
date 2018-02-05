package com.smartdevicelink.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;

import com.smartdevicelink.transport.TransportConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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


	public static List<SdlAppInfo> querySdlAppInfo(Context context, Comparator<SdlAppInfo> comparator){
		List<SdlAppInfo> sdlAppInfoList = new ArrayList<>();

		Intent intent = new Intent(TransportConstants.START_ROUTER_SERVICE_ACTION);
		List<ResolveInfo> resolveInfoList = context.getPackageManager().queryBroadcastReceivers(intent, PackageManager.GET_META_DATA);
		if(resolveInfoList != null && resolveInfoList.size() > 0) {
			PackageManager packageManager = context.getPackageManager();

			for (ResolveInfo info : resolveInfoList) {
				PackageInfo packageInfo = null;
				try {
					packageInfo = packageManager.getPackageInfo(info.activityInfo.packageName, 0);
				} catch (NameNotFoundException e) {
				}finally {
					sdlAppInfoList.add(new SdlAppInfo(info, packageInfo));

				}


			}

			if (comparator != null) {
				Collections.sort(sdlAppInfoList, comparator); //TODO ensure this sorts correctly
			}
		}

		return sdlAppInfoList;
	}

}
