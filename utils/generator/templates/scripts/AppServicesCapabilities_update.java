
	/**
	 * This method will update the current List<AppServiceCapability> with the updated items. If the
	 * items don't exist in the original ist they will be added. If the original list is null or
	 * empty, the new list will simply be set as the list.
	 * @param updatedAppServiceCapabilities the List<AppServiceCapability> that have been updated
	 * @return if the list was updated
	 */
	public boolean updateAppServices(@NonNull List<AppServiceCapability> updatedAppServiceCapabilities){
		if(updatedAppServiceCapabilities == null){
			return false;
		}

		List<AppServiceCapability> appServiceCapabilities = getAppServices();

		if(appServiceCapabilities == null){
			//If there are currently no app services, create one to iterate over with no entries
			appServiceCapabilities = new ArrayList<>(0);
		}

		//Create a shallow copy for us to alter while iterating through the original list
		List<AppServiceCapability> tempList = new ArrayList<>(appServiceCapabilities);

		for(AppServiceCapability updatedAppServiceCapability: updatedAppServiceCapabilities){
			if(updatedAppServiceCapability != null) {
				//First search if the record exists in the current list and remove it if so
				for (AppServiceCapability appServiceCapability : appServiceCapabilities) {
					if (updatedAppServiceCapability.matchesAppService(appServiceCapability)) {
						tempList.remove(appServiceCapability); //Remove the old entry
						break;
					}
				}

				if(!ServiceUpdateReason.REMOVED.equals(updatedAppServiceCapability.getUpdateReason())){
					//If the app service was anything but removed, we can add the updated
					//record back into the temp list. If it was REMOVED as the update reason
					//it will not be added back.
					tempList.add(updatedAppServiceCapability);
				}
			}
		}

		setAppServices(tempList);
		return !tempList.equals(appServiceCapabilities); //Return if the list is not equal to the original
	}
