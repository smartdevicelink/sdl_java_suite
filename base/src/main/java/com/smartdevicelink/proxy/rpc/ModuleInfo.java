package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Struct that describes a module within different SystemCapabilities
 */
public class ModuleInfo extends RPCStruct {
    public static final String KEY_MODULE_ID = "KEY_MODULE_ID";
    public static final String KEY_MODULE_LOCATION = "KEY_MODULE_LOCATION";
    public static final String KEY_MODULE_SERVICE_AREA = "KEY_MODULE_SERVICE_AREA";
    public static final String KEY_MULTIPLE_ACCESS_ALLOWED = "KEY_MULTIPLE_ACCESS_ALLOWED";

    public ModuleInfo(){}

    public ModuleInfo(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the Module ID for this Module
     * @param id the id to be set
     */
    public void setModuleId(String id) {
        setValue(KEY_MODULE_ID, id);
    }

    /**
     * Gets the Module ID for this module
     * @return the Module ID as a String
     */
    public String getModuleId() {
        return getString(KEY_MODULE_ID);
    }

    /**
     * Sets the location of this Module
     * @param location the location to be set
     */
    public void setModuleGridLocation(Grid location) {
        setValue(KEY_MODULE_LOCATION, location);
    }

    /**
     * Gets the location of this Module
     * @return the location of this Module
     */
    public Grid getModuleGridLocation() {
        return (Grid) getObject(Grid.class, KEY_MODULE_LOCATION);
    }

    /**
     * Sets the service area of this Module
     * @param serviceArea the service area of this Module
     */
    public void setModuleServiceArea(Grid serviceArea) {
        setValue(KEY_MODULE_SERVICE_AREA, serviceArea);
    }

    /**
     * Gets the service area of this Module
     * @return the service area of this Module
     */
    public Grid getModuleServiceArea() {
        return (Grid) getObject(Grid.class, KEY_MODULE_SERVICE_AREA);
    }

    /**
     * Sets the multiple access allowance for this Module
     * @param isMultipleAccess the access to be set
     */
    public void setMultipleAccessAllowance(Boolean isMultipleAccess) {
        setValue(KEY_MULTIPLE_ACCESS_ALLOWED, isMultipleAccess);
    }

    /**
     * Gets the multiple allowance access of this Module
     * @return the multiple access allowance of this Module
     */
    public Boolean getMultipleAccessAllowance() {
        return getBoolean(KEY_MULTIPLE_ACCESS_ALLOWED);
    }
}
