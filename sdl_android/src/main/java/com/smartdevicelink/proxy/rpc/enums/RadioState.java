package com.smartdevicelink.proxy.rpc.enums;

/**
 *  List possible states of a remote control radio module.
 */
public enum RadioState {
    ACQUIRING,
    ACQUIRED,
    MULTICAST,
    NOT_FOUND,
    ;

    public static RadioState valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
