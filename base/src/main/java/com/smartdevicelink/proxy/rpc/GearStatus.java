package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.TransmissionType;

import java.util.Hashtable;

public class GearStatus extends RPCStruct {
    public static final String KEY_USER_SELECTED_GEAR = "userSelectedGear";
    public static final String KEY_ACTUAL_GEAR = "actualGear";
    public static final String KEY_TRANSMISSION_TYPE = "transmissionType";

    /**
     * Constructs a newly allocated GearStatus object
     */
    public GearStatus() {}

    /**
     * Constructs a newly allocated GearStatus object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public GearStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a newly allocated GearStatus object
     *
     * @param userSelectedGear PRNDL
     * @param actualGear PRNDL
     * @param transmissionType TransmissionType
     */
    public GearStatus(
            @NonNull PRNDL userSelectedGear,
            @NonNull PRNDL actualGear,
            @NonNull TransmissionType transmissionType
    ) {
        this();
        setUserSelectedGear(userSelectedGear);
        setActualGear(actualGear);
        setTransmissionType(transmissionType);
    }

    /**
     * Set gear position selected by the user
     * @param selectedGear PRNDL
     */
    public void setUserSelectedGear(PRNDL selectedGear){
        setValue(KEY_USER_SELECTED_GEAR, selectedGear);
    }

    /**
     * Get gear position selected by the user
     * @return PRNDL
     */
    @SuppressWarnings("unchecked")
    public PRNDL getUserSelectedGear(){
        return (PRNDL)getObject(PRNDL.class, KEY_USER_SELECTED_GEAR);
    }

    /**
     * Set actual gear used by transmission
     * @param actualGear PRNDL
     */
    public void setActualGear(PRNDL actualGear){
        setValue(KEY_ACTUAL_GEAR, actualGear);
    }

    /**
     * Get actual gear used by transmission
     * @return PRNDL
     */
    @SuppressWarnings("unchecked")
    public PRNDL getActualGear(){
        return (PRNDL)getObject(PRNDL.class, KEY_ACTUAL_GEAR);
    }

    /**
     * Sets transmission type
     * Tells the transmission type
     * @param transmissionType TransmissionType
     */
    public void setTransmissionType(TransmissionType transmissionType){
        setValue(KEY_TRANSMISSION_TYPE, transmissionType);
    }

    /**
     * Get transmission type
     * Tells the transmission type
     * @return TransmissionType
     */
    @SuppressWarnings("unchecked")
    public TransmissionType getTransmissionType(){
        return (TransmissionType)getObject(TransmissionType.class, KEY_TRANSMISSION_TYPE);
    }
}
