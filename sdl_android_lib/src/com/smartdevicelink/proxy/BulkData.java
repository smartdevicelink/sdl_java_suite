package com.smartdevicelink.proxy;


/**
 * Contains methods that are common to any data type that needs to send bulk data,
 * such as raw bytes for an image or media file, through SDL.
 *
 * @author Mike Burke
 *
 */
public interface BulkData {
    public static final String BULK_DATA = "bulkData";
    
    /**
     * Called when the system needs the raw data associated with the object.
     * 
     * @return The raw bytes of bulk data
     */
    public byte[] getBulkData();
    public void setBulkData(byte[] rawData);
}
