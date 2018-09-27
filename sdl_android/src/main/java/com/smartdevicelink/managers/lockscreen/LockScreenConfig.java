package com.smartdevicelink.managers.lockscreen;

/**
 * <strong>LockScreenConfig</strong> <br>
 *
 * This is set during SdlManager instantiation. <br>
 *
 * <li> enable - if false, don't worry about the other parameters. You are responsible for creating and managing a lockscreen.
 * If true, also set the backgroundColor and appIcon if you want. If you don't set the backgroundColor or appIcon, it will use the defaults.</li>
 *
 * <li> backgroundColor - if using the default lockscreen, you can set this to a color of your choosing </li>
 *
 * <li> appIcon - if using the default lockscreen, you can set your own app icon</li>
 *
 * <li> customView - If you would like to provide your own view, you can pass it in here.</li>
 */
public class LockScreenConfig {

	private boolean enable, deviceLogo;
	private int backgroundColor, appIconInt, customViewInt;

	public LockScreenConfig(){
		// set default values
		this.enable = true;
		this.deviceLogo = false;
	}

	/**
	 * If set to true, SDL will manage the showing and dismissing of the lock screen for you. <br>
	 *
	 * If false, you must manage the lock screen
	 * @param enable boolean
	 */
	public void setEnabled(boolean enable){
		this.enable = enable;
	}

	/**
	 * Gets whether the lock screen is being managed for you
	 * @return boolean
	 */
	public boolean isEnabled() {
		return enable;
	}

	/**
	 * Set the resource int of the background color. Colors should define colors in your Colors.xml file
	 * @param resourceColor resource int of the color
	 */
	public void setBackgroundColor(int resourceColor){
		this.backgroundColor = resourceColor;
	}

	/**
	 * Gets the int reference to the custom lock screen background color
	 * @return the color reference
	 */
	public int getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * int of the drawable icon.
	 * @param appIconInt the drawable of the icon to be displayed on the lock screen
	 */
	public void setAppIcon(int appIconInt) {
		this.appIconInt = appIconInt;
	}

	/**
	 * Gets the resource reference of the icon to be displayed on the lock screen
	 * @return the icon reference
	 */
	public int getAppIcon() {
		return appIconInt;
	}

	/**
	 * Sets the reference to the custom layout to be used for the lock screen <br>
	 * <strong>If set, the color and icon setters will be ignored</strong>
	 * @param customViewInt the layout
	 */
	public void setCustomView(int customViewInt) {
		this.customViewInt = customViewInt;
	}

	/**
	 * Gets the reference to the custom lockscreen layout to be used
	 * @return the layout reference
	 */
	public int getCustomView() {
		return customViewInt;
	}

	/**
	 * Whether or not to show the device's logo on the default lock screen <br>
	 * The logo will come from the connected hardware, if set by the manufacturer <br>
	 * If using a Custom View, this will be ignored.
	 * @param deviceLogo - boolean
	 */
	public void showDeviceLogo(boolean deviceLogo) {
		this.deviceLogo = deviceLogo;
	}

	/**
	 * Get whether or not the device's Logo is shown on the default lock screen <br>
	 * The logo will come from the connected hardware, if set by the manufacturer <br>
	 * @return deviceLogo - boolean
	 */
	public boolean isDeviceLogoEnabled() {
		return deviceLogo;
	}

}
