package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

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

    public Coordinate(@NonNull Float latitudeDegrees, @NonNull Float longitudeDegrees) {
        this();
        setLatitudeDegrees(latitudeDegrees);
        setLongitudeDegrees(longitudeDegrees);
    }

    public Float getLatitudeDegrees() {
        Object value = getValue(KEY_LATITUDE_DEGREES);
        return SdlDataTypeConverter.objectToFloat(value);
    }

    public void setLatitudeDegrees(@NonNull Float latitudeDegrees) {
        setValue(KEY_LATITUDE_DEGREES, latitudeDegrees);
    }

    public Float getLongitudeDegrees() {
        Object value = getValue(KEY_LONGITUDE_DEGREES);
        return SdlDataTypeConverter.objectToFloat(value);
    }

    public void setLongitudeDegrees(@NonNull Float longitudeDegrees) {
        setValue(KEY_LONGITUDE_DEGREES, longitudeDegrees);
    }
}
