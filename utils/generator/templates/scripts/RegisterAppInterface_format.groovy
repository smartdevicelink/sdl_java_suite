
	private static final int APP_ID_MAX_LENGTH = 10;

	@Override
	public void format(Version rpcVersion, boolean formatParams) {
		if(rpcVersion == null || rpcVersion.getMajor() >= 5) {
			if (getFullAppID() == null) {
				setFullAppID(getAppID());
			}
		}
		super.format(rpcVersion, formatParams);
	}
