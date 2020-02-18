	@Deprecated
    public String getProxyVersionInfo() {
		return null;
    }

	@Override
	public void format(com.smartdevicelink.util.Version rpcVersion, boolean formatParams){
		//Add in 5.0.0 of the rpc spec
		if(getIconResumed() == null){
			setIconResumed(Boolean.FALSE);
		}

		List<ButtonCapabilities> capabilities = getButtonCapabilities();
		if(capabilities != null){
			List<ButtonCapabilities> additions = new ArrayList<>();
			for(ButtonCapabilities capability : capabilities){
				if(ButtonName.OK.equals(capability.getName())){
					if(rpcVersion == null || rpcVersion.getMajor() < 5){
						//If version is < 5, the play pause button must also be added
						additions.add(new ButtonCapabilities(ButtonName.PLAY_PAUSE, capability.getShortPressAvailable(), capability.getLongPressAvailable(), capability.getUpDownAvailable()));
					}
				}
			}
			capabilities.addAll(additions);
			setButtonCapabilities(capabilities);
		}


		super.format(rpcVersion,formatParams);
	}