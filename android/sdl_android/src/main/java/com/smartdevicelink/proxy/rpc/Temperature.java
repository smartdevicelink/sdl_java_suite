package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

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

	/**
	 * Create the temperature object
	 * @param unit Temperature Unit.
	 * @param value Temperature Value in TemperatureUnit specified unit
	 */
	public Temperature(@NonNull TemperatureUnit unit, @NonNull Float value){
		this();
		setUnit(unit);
		setValue(value);
	}

    /**
     * Sets the unit portion of the Temperature class
     *
     * @param unit
     * Temperature Unit.
     */
    public void setUnit(@NonNull TemperatureUnit unit) {
        setValue(KEY_UNIT, unit);
    }

    /**
     * Gets the unit portion of the Temperature class
     *
     * @return TemperatureUnit - Temperature Unit.
     */
    public TemperatureUnit getUnit() {
        return (TemperatureUnit) getObject(TemperatureUnit.class, KEY_UNIT);
    }

    /**
     * Gets the value portion of the Temperature class
     *
     * @return Float - Temperature Value in TemperatureUnit specified unit. Range depends on OEM and is not checked by SDL.
     */
    public Float getValue() {
        Object value = getValue(KEY_VALUE);
        return SdlDataTypeConverter.objectToFloat(value);
    }

    /**
     * Sets the value portion of the Temperature class
     *
     * @param value
     * Temperature Value in TemperatureUnit specified unit. Range depends on OEM and is not checked by SDL.
     */
    public void setValue(@NonNull Float value) {
        setValue(KEY_VALUE, value);
    }
}
