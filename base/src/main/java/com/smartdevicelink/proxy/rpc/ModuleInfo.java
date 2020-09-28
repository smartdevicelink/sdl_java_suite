package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Struct that describes a module within different SystemCapabilities
 */
public class ModuleInfo extends RPCStruct {
    public static final String KEY_MODULE_ID = "moduleId";
    public static final String KEY_MODULE_LOCATION = "location";
    public static final String KEY_MODULE_SERVICE_AREA = "serviceArea";
    public static final String KEY_MULTIPLE_ACCESS_ALLOWED = "allowMultipleAccess";

    public ModuleInfo() {
    }

    public ModuleInfo(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Struct that describes a module within different SystemCapabilities
     *
     * @param moduleId Sets the Module ID for this Module
     */
    public ModuleInfo(@NonNull String moduleId) {
        this();
        setModuleId(moduleId);
    }

    /**
     * Sets the Module ID for this Module
     *
     * @param id the id to be set
     */
    public ModuleInfo setModuleId(@NonNull String id) {
        setValue(KEY_MODULE_ID, id);
        return this;
    }

    /**
     * Gets the Module ID for this module
     *
     * @return the Module ID as a String
     */
    public String getModuleId() {
        return getString(KEY_MODULE_ID);
    }

    /**
     * Sets the location of this Module
     *
     * @param location the location to be set
     */
    public ModuleInfo setModuleLocation(Grid location) {
        setValue(KEY_MODULE_LOCATION, location);
        return this;
    }

    /**
     * Gets the location of this Module
     *
     * @return the location of this Module
     */
    public Grid getModuleLocation() {
        return (Grid) getObject(Grid.class, KEY_MODULE_LOCATION);
    }

    /**
     * Sets the service area of this Module
     *
     * @param serviceArea the service area of this Module
     */
    public ModuleInfo setModuleServiceArea(Grid serviceArea) {
        setValue(KEY_MODULE_SERVICE_AREA, serviceArea);
        return this;
    }

    /**
     * Gets the service area of this Module
     *
     * @return the service area of this Module
     */
    public Grid getModuleServiceArea() {
        return (Grid) getObject(Grid.class, KEY_MODULE_SERVICE_AREA);
    }

    /**
     * Sets the multiple access allowance for this Module
     *
     * @param isMultipleAccess the access to be set
     */
    public ModuleInfo setMultipleAccessAllowance(Boolean isMultipleAccess) {
        setValue(KEY_MULTIPLE_ACCESS_ALLOWED, isMultipleAccess);
        return this;
    }

    /**
     * Gets the multiple allowance access of this Module
     *
     * @return the multiple access allowance of this Module
     */
    public Boolean getMultipleAccessAllowance() {
        return getBoolean(KEY_MULTIPLE_ACCESS_ALLOWED);
    }
}
