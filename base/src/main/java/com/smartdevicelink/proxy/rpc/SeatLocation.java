package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

public class SeatLocation extends RPCStruct {
    public static final String KEY_GRID = "grid";

    public SeatLocation() {
    }

    public SeatLocation(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets grid data for this seat location
     *
     * @param grid the grid to be set
     */
    public SeatLocation setGrid(Grid grid) {
        setValue(KEY_GRID, grid);
        return this;
    }

    /**
     * Gets the Grid of this seat location
     *
     * @return the grid of this seat location
     */
    public Grid getGrid() {
        return (Grid) getObject(Grid.class, KEY_GRID);
    }

}
