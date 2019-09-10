package com.smartdevicelink.proxy.rpc;


import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
import java.util.List;

public class SeatLocationCapability extends RPCStruct {
    public static final String KEY_ROWS = "rows";
    public static final String KEY_COLS = "columns";
    public static final String KEY_LEVELS = "levels";
    public static final String KEY_SEATS = "seats";

    public SeatLocationCapability(){}

    public SeatLocationCapability(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the seat rows for this capability
     * @param rows rows to be set
     */
    public void setRows(Integer rows) {
        setValue(KEY_ROWS, rows);
    }

    /**
     * Gets the seat rows of this capability
     * @return the seat rows
     */
    public Integer getRows() {
        return getInteger(KEY_ROWS);
    }

    /**
     * Sets the seat columns for this capability
     * @param cols the seat columns to be set
     */
    public void setCols(Integer cols) {
        setValue(KEY_COLS, cols);
    }

    /**
     * Gets the seat columns of this capability
     * @return the seat columns
     */
    public Integer getCols() {
        return getInteger(KEY_COLS);
    }

    /**
     * Sets the levels for this capability
     * @param levels the levels to be set
     */
    public void setLevels(Integer levels) {
        setValue(KEY_LEVELS, levels);
    }

    /**
     * Gets the seat levels of this capability
     * @return the seat levels
     */
    public Integer getLevels() {
        return getInteger(KEY_LEVELS);
    }

    /**
     * Sets the seat locations for this capability
     * @param locations the locations to be set
     */
    public void setSeats(List<SeatLocation> locations) {
        setValue(KEY_SEATS, locations);
    }

    /**
     * Gets the seat locations of this capability
     * @return the seat locations
     */
    @SuppressWarnings("unchecked")
    public List<SeatLocation> getSeatLocations() {
        return (List<SeatLocation>) getObject(SeatLocation.class, KEY_SEATS);
    }
}
