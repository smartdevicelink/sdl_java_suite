package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.TemperatureUnit;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;

public class Temperature extends RPCStruct{
    public static final String KEY_UNIT = "unit";
    public static final String KEY_VALUE = "value";

    public Temperature() { }
    public Temperature(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setUnit(TemperatureUnit unit) {
        setValue(KEY_UNIT, unit);
    }

    public TemperatureUnit getUnit() {
        return (TemperatureUnit) getObject(TemperatureUnit.class, KEY_UNIT);
    }

    public Float getValue() {
        Object value = getValue(KEY_VALUE);
        return SdlDataTypeConverter.objectToFloat(value);
    }

    public void setValue(Float value) {
        setValue(KEY_VALUE, value);
    }
}
