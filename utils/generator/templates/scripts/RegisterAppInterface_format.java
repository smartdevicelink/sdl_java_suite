
	public static final String KEY_APP_ID = "appID";
	public static final String KEY_FULL_APP_ID = "fullAppID";
	private static final int APP_ID_MAX_LENGTH = 10;

	/**
	 * Constructs a new RegisterAppInterface object
	 * @param syncMsgVersion a SdlMsgVersion object representing version of the SDL&reg; SmartDeviceLink interface <br>
	 *            <b>Notes: </b>To be compatible, app msg major version number
	 *            must be less than or equal to SDL&reg; major version number.
	 *            If msg versions are incompatible, app has 20 seconds to
	 *            attempt successful RegisterAppInterface (w.r.t. msg version)
	 *            on underlying protocol session, else will be terminated. Major
	 *            version number is a compatibility declaration. Minor version
	 *            number indicates minor functional variations (e.g. features,
	 *            capabilities, bug fixes) when sent from SDL&reg; to app (in
	 *            RegisterAppInterface response). However, the minor version
	 *            number sent from the app to SDL&reg; (in RegisterAppInterface
	 *            request) is ignored by SDL&reg;
	 * @param appName a String value representing the Mobile Application's Name <br>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Must be 1-100 characters in length</li>
	 *            <li>May not be the same (by case insensitive comparison) as
	 *            the name or any synonym of any currently-registered
	 *            application</li>
	 *            </ul>
	 * @param isMediaApplication a Boolean value
	 * @param languageDesired a Language Enumeration
	 * @param hmiDisplayLanguageDesired the requested language to be used on the HMI/Display
	 * @param fullAppID a String value representing a unique ID, which an app will be given when approved <br>
	 *            <b>Notes: </b>Maxlength = 100
	 */
	public RegisterAppInterface(@NonNull SdlMsgVersion sdlMsgVersion, @NonNull String appName, @NonNull Boolean isMediaApplication,
								@NonNull Language languageDesired, @NonNull Language hmiDisplayLanguageDesired, @NonNull String fullAppID) {
		this();
		setSdlMsgVersion(sdlMsgVersion);
		setAppName(appName);
		setIsMediaApplication(isMediaApplication);
		setLanguageDesired(languageDesired);
		setHmiDisplayLanguageDesired(hmiDisplayLanguageDesired);
		setFullAppID(fullAppID);
	}


	/**
	 * Gets the unique ID, which an app will be given when approved
	 *
	 * @return String - a String value representing the unique ID, which an app
	 *         will be given when approved
	 * @since SmartDeviceLink 2.0
	 */
	public String getAppID() {
		return getString(KEY_APP_ID);
	}

	/**
	 * Sets a unique ID, which an app will be given when approved
	 *
	 * @param appID
	 *            a String value representing a unique ID, which an app will be
	 *            given when approved
	 *            <p></p>
	 *            <b>Notes: </b>Maxlength = 100
	 * @since SmartDeviceLink 2.0
	 */
	public void setAppID(@NonNull String appID) {
		if (appID != null) {
			setParameters(KEY_APP_ID, appID.toLowerCase());
		} else {
			setParameters(KEY_APP_ID, appID);
		}
	}

	/**
	 * Gets the unique ID, which an app will be given when approved
	 *
	 * @return String - a String value representing the unique ID, which an app
	 *         will be given when approved
	 * @since SmartDeviceLink 5.0
	 */
	public String getFullAppID() {
		return getString(KEY_FULL_APP_ID);
	}

	/**
	 * Sets a unique ID, which an app will be given when approved <br>
	 * Note: this will automatically parse the fullAppID into the smaller appId and set the appId value as well
	 * @param fullAppID
	 *            a String value representing a unique ID, which an app will be
	 *            given when approved
	 *            <p></p>
	 *            <b>Notes: </b>Maxlength = 100
	 * @since SmartDeviceLink 5.0
	 */
	public void setFullAppID(String fullAppID) {
		if (fullAppID != null) {
			fullAppID = fullAppID.toLowerCase();
			setParameters(KEY_FULL_APP_ID, fullAppID);
			String appID;
			if (fullAppID.length() <= APP_ID_MAX_LENGTH) {
				appID = fullAppID;
			} else {
				appID = fullAppID.replace("-", "").substring(0, APP_ID_MAX_LENGTH);
			}
			setAppID(appID);
		} else {
			setParameters(KEY_FULL_APP_ID, null);
		}
	}

	@Override
	public void format(Version rpcVersion, boolean formatParams) {
		if(rpcVersion == null || rpcVersion.getMajor() >= 5) {
			if (getFullAppID() == null) {
				setFullAppID(getAppID());
			}
		}
		super.format(rpcVersion, formatParams);
	}
