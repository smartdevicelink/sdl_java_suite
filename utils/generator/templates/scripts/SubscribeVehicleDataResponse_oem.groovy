
    /**
     * Sets Fuel Level State
     * @param fuelLevelState a VehicleDataResult related to FuelLevel State
     */
    public void setFuelLevelState(VehicleDataResult fuelLevelState) {
        setParameters(KEY_FUEL_LEVEL_STATE, fuelLevelState);
    }
    /**
     * Gets Fuel Level State
     * @return a VehicleDataResult related to FuelLevel State
     */
    @SuppressWarnings("unchecked")
    public VehicleDataResult getFuelLevelState() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_FUEL_LEVEL_STATE);
    }

    public void setClusterModeStatus(VehicleDataResult clusterModeStatus) {
        setParameters(KEY_CLUSTER_MODE_STATUS, clusterModeStatus);
    }
    @SuppressWarnings("unchecked")
    public VehicleDataResult getClusterModeStatus() {
        return (VehicleDataResult) getObject(VehicleDataResult.class, KEY_CLUSTER_MODE_STATUS);
    }

    /**
     * Sets a value for OEM Custom VehicleData.
     * @param vehicleDataName a String value
     * @param vehicleDataState a VehicleDataResult value
     */
    public void setOEMCustomVehicleData(String vehicleDataName, VehicleDataResult vehicleDataState){
        setParameters(vehicleDataName, vehicleDataState);
    }

    /**
     * Gets a VehicleDataResult for the vehicle data item.
     * @return a VehicleDataResult related to the vehicle data
     */
    public VehicleDataResult getOEMCustomVehicleData(String vehicleDataName){
        return (VehicleDataResult) getObject(VehicleDataResult.class, vehicleDataName);
    }

