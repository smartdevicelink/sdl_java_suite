
    /**
     * Sets a boolean value. If true, subscribes fuelLevelState data
     *
     * @param fuelLevelState
     *            a boolean value
     */
    public void setFuelLevelState(Boolean fuelLevelState) {
		setParameters(KEY_FUEL_LEVEL_STATE, fuelLevelState);
    }

    /**
     * Gets a boolean value. If true, means the fuelLevelState data has been
     * subscribed.
     *
     * @return Boolean -a Boolean value. If true, means the fuelLevelState data
     *         has been subscribed.
     */
    public Boolean getFuelLevelState() {
        return getBoolean(KEY_FUEL_LEVEL_STATE);
    }

	/**
	 * Sets a boolean value for OEM Custom VehicleData.
	 * @param vehicleDataName a String value
	 * @param vehicleDataState a boolean value
	 */
	public void setOEMCustomVehicleData(String vehicleDataName, Boolean vehicleDataState){
		setParameters(vehicleDataName, vehicleDataState);
	}

	/**
	 * Gets a boolean value for OEM Custom VehicleData.
	 * @return a Boolean value.
	 */
	public Boolean getOEMCustomVehicleData(String vehicleDataName){
		return getBoolean(vehicleDataName);
	}
