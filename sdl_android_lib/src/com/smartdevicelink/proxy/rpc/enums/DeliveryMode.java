package com.smartdevicelink.proxy.rpc.enums;

public enum DeliveryMode {
	PROMPT,
    DESTINATION,
    QUEUE,
    ;

    public static DeliveryMode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
