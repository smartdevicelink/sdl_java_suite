package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;

public class Coordinate extends RPCStruct{
    public static final String KEY_LATITUDE_DEGREES = "latitudeDegrees";
    public static final String KEY_LONGITUDE_DEGREES = "longitudeDegrees";

    public Coordinate() {
    }

    public Coordinate(Hashtable<String, Object> hash) {
        super(hash);
    }

    public Float getLatitudeDegrees() {
        Object value = store.get(KEY_LATITUDE_DEGREES);
        return SdlDataTypeConverter.objectToFloat(value);
    }

    public void setLatitudeDegrees(Float latitudeDegrees) {
        if (latitudeDegrees != null) {
            store.put(KEY_LATITUDE_DEGREES, latitudeDegrees);
        } else {
            store.remove(KEY_LATITUDE_DEGREES);
        }
    }

    public Float getLongitudeDegrees() {
        Object value = store.get(KEY_LONGITUDE_DEGREES);
        return SdlDataTypeConverter.objectToFloat(value);
    }

    public void setLongitudeDegrees(Float longitudeDegrees) {
        if (longitudeDegrees != null) {
            store.put(KEY_LONGITUDE_DEGREES, longitudeDegrees);
        } else {
            store.remove(KEY_LONGITUDE_DEGREES);
        }
    }
}
