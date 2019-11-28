
	/**
	 * Helper method to compare an AppServiceCapability to this instance.
	 * @param capability the AppServiceCapability to compare to this one
	 * @return if both AppServiceCapability objects refer to the same service
	 */
	public boolean matchesAppService(AppServiceCapability capability){
		if(capability != null){
			AppServiceRecord appServiceRecord = getUpdatedAppServiceRecord();
			AppServiceRecord otherASR = capability.getUpdatedAppServiceRecord();

			if(appServiceRecord != null && otherASR != null) {
				// If both service IDs exists we can compare them. If either is null we can't use
				// only this check.
				if(appServiceRecord.getServiceID() != null && otherASR.getServiceID() != null){
					//return whether the app service IDs are equal or not
					return appServiceRecord.getServiceID().equalsIgnoreCase(otherASR.getServiceID());
				}else{
					AppServiceManifest manifest = appServiceRecord.getServiceManifest();
					AppServiceManifest otherManifest = otherASR.getServiceManifest();
					if(manifest != null && otherManifest != null){
						//Check the service names, if they are the same it can be assumed they are the same service
						return (manifest.getServiceName() != null && manifest.getServiceName().equalsIgnoreCase(otherManifest.getServiceName()));
					}
				}
			}
		}
		// If it got to this point it was not the same
		return false;
	}
