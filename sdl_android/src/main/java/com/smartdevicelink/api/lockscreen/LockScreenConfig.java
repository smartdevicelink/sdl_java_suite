package com.smartdevicelink.api.lockscreen;

import android.graphics.Color;

/**
 * <strong>LockScreenConfig</strong> <br>
 *
 * This is set during SdlManager instantiation. <br>
 *
 * <li> enableAutomaticLockScreen - if false, don't worry about the other parameters. You are responsible for creating and managing a lockscreen.
 * If true, also set the backgroundColor and appIcon if you want. If you don't set the backgroundColor or appIcon, it will use the defaults.</li>
 *
 * <li> backgroundColor - if using the default lockscreen, you can set this to a color of your choosing </li>
 *
 * <li> appIcon - if using the default lockscreen, you can set your own app icon</li>
 *
 * <li> customView - If you would like to provide your own view, you can pass it in here.</li>
 */
public class LockScreenConfig {

	public Boolean enableAutomaticLockScreen;
	public Color backgroundColor;
	public int appIconInt;
	public int customViewInt;

	public LockScreenConfig(){}

	public void setEnabled(Boolean enableAutomaticLockScreen){
		this.enableAutomaticLockScreen = enableAutomaticLockScreen;
	}

	public Boolean getEnabled() {
		return enableAutomaticLockScreen;
	}

	public void setBackgroundColor(Color backgroundColor){
		this.backgroundColor = backgroundColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setAppIcon(int appIconInt) {
		this.appIconInt = appIconInt;
	}

	public int getAppIcon() {
		return appIconInt;
	}

	public void setCustomView(int customViewInt) {
		this.customViewInt = customViewInt;
	}

	public int getCustomView() {
		return customViewInt;
	}

}
