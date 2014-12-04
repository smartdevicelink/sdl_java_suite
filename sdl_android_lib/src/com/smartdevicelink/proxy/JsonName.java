package com.smartdevicelink.proxy;


/**
 * Represents an object that has a JSON name associated with it.
 * 
 * When implementing this interface, it is highly recommended to provide a static method that will look
 * up the enum value based on the JSON name.
 *
 * @author Mike Burke
 *
 */
public interface JsonName{
    /**
     * Returns the JSON name associated with the object.
     * 
     * @param version The SDL version to get the JSON name for
     * @return The JSON name
     */
    public String getJsonName(int version);
}
