package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.LightName;
import com.smartdevicelink.proxy.rpc.enums.LightStatus;
import com.smartdevicelink.util.SdlDataTypeConverter;

import java.util.Hashtable;

public class LightState extends RPCStruct {
	public static final String KEY_ID = "id";
	public static final String KEY_STATUS = "status";
	public static final String KEY_DENSITY = "density";
	public static final String KEY_COLOR = "color";

	/**
	 * Constructs a new LightState object
	 */
	public LightState() {
	}

	/**
	 * <p>Constructs a new LightState object indicated by the Hashtable parameter
	 * </p>
	 *
	 * @param hash The Hashtable to use
	 */
	public LightState(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a newly allocated LightState object
	 *
	 * @param id     The name of a light or a group of lights.
	 * @param status Status of Light.
	 */
	public LightState(@NonNull LightName id, @NonNull LightStatus status) {
		this();
		setId(id);
		setStatus(status);
	}

	/**
	 * Sets the id portion of the LightState class
	 *
	 * @param id The name of a light or a group of lights.
	 */
	public void setId(@NonNull LightName id) {
		setValue(KEY_ID, id);
	}

	/**
	 * Gets the id portion of the LightState class
	 *
	 * @return LightName  - The name of a light or a group of lights.
	 */
	public LightName getId() {
		return (LightName) getObject(LightName.class, KEY_ID);
	}

	/**
	 * Sets the status portion of the LightState class
	 *
	 * @param status
	 */
	public void setStatus(@NonNull LightStatus status) {
		setValue(KEY_STATUS, status);
	}

	/**
	 * Gets the status portion of the LightState class
	 *
	 * @return LightStatus
	 */
	public LightStatus getStatus() {
		return (LightStatus) getObject(LightStatus.class, KEY_STATUS);
	}

	/**
	 * Gets the density portion of the LightState class
	 *
	 * @return Float
	 */
	public Float getDensity() {
		Object value = getValue(KEY_DENSITY);
		return SdlDataTypeConverter.objectToFloat(value);
	}

	/**
	 * Sets the density portion of the LightState class
	 *
	 * @param density
	 */
	public void setDensity(Float density) {
		setValue(KEY_DENSITY, density);
	}

	/**
	 * Gets the color portion of the LightState class
	 *
	 * @return RGBColor
	 */
	@SuppressWarnings("unchecked")
	public RGBColor getColor() {
		return (RGBColor) getObject(RGBColor.class, KEY_COLOR);
	}

	/**
	 * Sets the color portion of the LightState class
	 *
	 * @param color
	 */
	public void setColor(RGBColor color) {
		setValue(KEY_COLOR, color);
	}
}
