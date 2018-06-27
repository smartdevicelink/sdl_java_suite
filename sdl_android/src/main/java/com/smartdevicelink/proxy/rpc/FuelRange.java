package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.FuelType;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;

public class FuelRange extends RPCStruct{
    public static final String KEY_TYPE = "type";
    public static final String KEY_RANGE = "range";

    /**
     * Constructs a new FuelRange object
     */
    public FuelRange() { }

    /**
     * <p>Constructs a new FuelRange object indicated by the Hashtable parameter
     * </p>
     *
     * @param hash
     *            The Hashtable to use
     */
    public FuelRange(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Sets the type portion of the FuelRange class
     *
     * @param fuelType
     */
    public void setType(FuelType fuelType) {
        setValue(KEY_TYPE, fuelType);
    }

    /**
     * Gets the type portion of the FuelRange class
     *
     * @return FuelType.
     */
    public FuelType getType() {
        return (FuelType) getObject(FuelType.class, KEY_TYPE);
    }

    /**
     * Gets the range portion of the FuelRange class
     *
     * @return Float - The estimate range in KM the vehicle can travel based on fuel level and consumption.
     */
    public Float getRange() {
        Object object = getValue(KEY_RANGE);
        return SdlDataTypeConverter.objectToFloat(object);
    }

    /**
     * Sets the range portion of the FuelRange class
     *
     * @param range
     * The estimate range in KM the vehicle can travel based on fuel level and consumption.
     */
    public void setRange(Float range) {
        setValue(KEY_RANGE, range);
    }
}
