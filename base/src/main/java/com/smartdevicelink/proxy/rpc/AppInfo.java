/*
 * Copyright (c) 2019 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class AppInfo extends RPCStruct {

	public static final String KEY_APP_DISPLAY_NAME = "appDisplayName";
	public static final String KEY_APP_BUNDLE_ID = "appBundleID";
	public static final String KEY_APP_VERSION = "appVersion";
	public static final String KEY_APP_ICON = "appIcon";

	/**
	 * Constructs a new AppInfo object
	 */
	public AppInfo() { }

	/**
	 * Constructs a new AppInfo object indicated by the Hashtable parameter
	 * @param hash The Hashtable to use
	 */
	public AppInfo(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a new AppInfo object
	 * @param appDisplayName - name displayed for the mobile application on the mobile device
	 * @param appBundleID - package name of the application.
	 * @param appVersion - build version number of this particular mobile app.
	 */
	public AppInfo(@NonNull String appDisplayName, String appBundleID, String appVersion){
		this();
		setAppDisplayName(appDisplayName);
		setAppBundleID(appBundleID);
		setAppVersion(appVersion);
	}

	/** Sets the name displayed for the mobile application on the mobile device (can differ from the app name set in the initial RAI request).
	 * @param appDisplayName - name displayed for the mobile application on the mobile device.
	 */
	public void setAppDisplayName(@NonNull String appDisplayName) {
		setValue(KEY_APP_DISPLAY_NAME, appDisplayName);
	}

	/** Gets the name displayed for the mobile application on the mobile device (can differ from the app name set in the initial RAI request).
	 * @return appDisplayName - name displayed for the mobile application on the mobile device.
	 */
	public String getAppDisplayName() {
		return getString(KEY_APP_DISPLAY_NAME);
	}

	/** Sets package name of the Android application. This supports App Launch strategies for each platform.
	 * @param appBundleID - package name of the application
	 */
	public void setAppBundleID(@NonNull String appBundleID) {
		setValue(KEY_APP_BUNDLE_ID, appBundleID);
	}

	/** Gets package name of the Android application. This supports App Launch strategies for each platform.
	 * @return appBundleID - package name of the application.
	 */
	public String getAppBundleID() {
		return getString(KEY_APP_BUNDLE_ID);
	}

	/** Sets build version number of this particular mobile app.
	 * @param appVersion - build version number of this particular mobile app.
	 */
	public void setAppVersion(@NonNull String appVersion) {
		setValue(KEY_APP_VERSION, appVersion);
	}

	/** Gets build version number of this particular mobile app.
	 * @return appVersion - build version number of this particular mobile app.
	 */
	public String getAppVersion() {
		return getString(KEY_APP_VERSION);
	}

	/** Sets file reference to the icon utilized by this app (simplifies the process of setting an app icon during app registration).
	 * @param appIcon - file reference to the icon utilized by this app
	 */
	public void setAppIcon(String appIcon) {
		setValue(KEY_APP_ICON, appIcon);
	}

	/** Gets build version number of this particular mobile app.
	 * @return appIcon - build version number of this particular mobile app.
	 */
	public String getAppIcon() {
		return getString(KEY_APP_ICON);
	}
}
