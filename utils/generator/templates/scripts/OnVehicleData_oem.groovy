    public void setFuelLevelState(ComponentVolumeStatus fuelLevelState) {
        setParameters(KEY_FUEL_LEVEL_STATE, fuelLevelState);
    }

    public ComponentVolumeStatus getFuelLevelState() {
        return (ComponentVolumeStatus) getObject(ComponentVolumeStatus.class, KEY_FUEL_LEVEL_STATE);
    }

    /**
     * Sets a value for OEM Custom VehicleData.
     * @param vehicleDataName a String value
     * @param vehicleDataState a VehicleDataResult value
     */
    public void setOEMCustomVehicleData(String vehicleDataName, Object vehicleDataState){
        setParameters(vehicleDataName, vehicleDataState);
    }

    /**
     * Gets a VehicleData value for the vehicle data item.
     * @return a Object related to the vehicle data
     */
    public Object getOEMCustomVehicleData(String vehicleDataName){
        return getParameters(vehicleDataName);
    }
