package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.SeatMemoryActionType;

import java.util.Hashtable;

public class SeatMemoryAction extends RPCStruct{
    public static final String KEY_ID = "id";
    public static final String KEY_LABEL = "label";
    public static final String KEY_ACTION = "action";

    /**
     * Constructs a new SeatMemoryAction object
     */
    public SeatMemoryAction() { }

    /**
     * <p>Constructs a new SeatMemoryAction object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash
     *            The Hashtable to use
     */
    public SeatMemoryAction(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the id portion of the SeatMemoryAction class
     *
     * @param id
     */
    public void setId(Integer id) {
        setValue(KEY_ID, id);
    }

    /**
     * Gets the id portion of the SeatMemoryAction class
     *
     * @return Integer
     */
    public Integer getId() {
        return getInteger(KEY_ID);
    }

    /**
     * Sets the label portion of the SeatMemoryAction class
     *
     * @param label
     */
    public void setLabel(String label) {
        setValue(KEY_LABEL, label);
    }

    /**
     * Gets the label portion of the SeatMemoryAction class
     *
     * @return String
     */
    public String getLabel() {
        return getString(KEY_LABEL);
    }

    /**
     * Sets the action portion of the SeatMemoryAction class
     *
     * @param action
     */
    public void setAction(SeatMemoryActionType action) {
        setValue(KEY_ACTION, action);
    }

    /**
     * Gets the action portion of the SeatMemoryAction class
     *
     * @return SeatMemoryActionType.
     */
    public SeatMemoryActionType getAction() {
        return (SeatMemoryActionType) getObject(SeatMemoryActionType.class, KEY_ACTION);
    }
}
