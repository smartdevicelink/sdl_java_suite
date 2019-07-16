package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Struct that describes a location (origin coordinates and span) of a vehicle component (Module)
 */

public class Grid extends RPCStruct {
    public static final String KEY_COLUMN = "col";
    public static final String KEY_ROW = "row";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_COL_SPAN = "colspan";
    public static final String KEY_ROW_SPAN = "rowspan";
    public static final String KEY_LEVEL_SPAN = "levelspan";

    public Grid() {}

    public Grid(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the column of this Grid
     * @param col the column to be set
     */
    public void setColumn(Integer col) {
        setValue(KEY_COLUMN, col);
    }

    /**
     * Get the column value of this Grid
     * @return the column value
     */
    public Integer getColumn() {
        return getInteger(KEY_COLUMN);
    }

    /**
     * Sets the row's value of this Grid
     * @param row the row to be set
     */
    public void setRow(Integer row) {
        setValue(KEY_ROW, row);
    }

    /**
     * Gets the row value of this Grid
     * @return the row value
     */
    public Integer getRow() {
        return getInteger(KEY_ROW);
    }

    /**
     * Sets the level value of this Grid
     * @param level the level to be set
     */
    public void setLevel(Integer level) {
        setValue(KEY_LEVEL, level);
    }

    /**
     * Gets the level value of this Grid
     * @return the level
     */
    public Integer getLevel() {
        return getInteger(KEY_LEVEL);
    }

    /**
     * Sets the column span of this Grid
     * @param span the span to be set
     */
    public void setColumnSpan(Integer span) {
        setValue(KEY_COL_SPAN, span);
    }

    /**
     * Gets the column span of this Grid
     * @return the column span
     */
    public Integer getColumnSpan() {
        return getInteger(KEY_COL_SPAN);
    }

    /**
     * Sets the row span of this Grid
     * @param span the span to be set
     */
    public void setRowSpan(Integer span) {
        setValue(KEY_ROW_SPAN, span);
    }

    /**
     * Gets the row span of this Grid
     * @return the row span
     */
    public Integer getRowSpan() {
        return getInteger(KEY_ROW_SPAN);
    }

    /**
     * Sets the level span of this Grid
     * @param span the span to be set
     */
    public void setLevelSpan(Integer span) {
        setValue(KEY_LEVEL_SPAN, span);
    }

    /**
     * Gets the level span of this Grid
     * @return the level span
     */
    public Integer getLevelSpan() {
        return getInteger(KEY_LEVEL_SPAN);
    }
}
