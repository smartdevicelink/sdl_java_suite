package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
import java.util.List;

public class RemoteControlCapabilities extends RPCStruct {
	public static final String KEY_CLIMATE_CONTROL_CAPABILITIES = "climateControlCapabilities";
	public static final String KEY_RADIO_CONTROL_CAPABILITIES = "radioControlCapabilities";
	public static final String KEY_BUTTON_CAPABILITIES = "buttonCapabilities";
	public static final String KEY_SEAT_CONTROL_CAPABILITIES = "seatControlCapabilities";

	public RemoteControlCapabilities() {
	}

	public RemoteControlCapabilities(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Sets the climateControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @param climateControlCapabilities If included, the platform supports RC climate controls.
	 *                                   For this baseline version, maxsize=1. i.e. only one climate control module is supported.
	 */
	public void setClimateControlCapabilities(List<ClimateControlCapabilities> climateControlCapabilities) {
		setValue(KEY_CLIMATE_CONTROL_CAPABILITIES, climateControlCapabilities);
	}

	/**
	 * Gets the climateControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @return List<ClimateControlCapabilities>
	 * If included, the platform supports RC climate controls.
	 * For this baseline version, maxsize=1. i.e. only one climate control module is supported.
	 */
	public List<ClimateControlCapabilities> getClimateControlCapabilities() {
		return (List<ClimateControlCapabilities>) getObject(ClimateControlCapabilities.class, KEY_CLIMATE_CONTROL_CAPABILITIES);
	}

	/**
	 * Sets the radioControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @param radioControlCapabilities If included, the platform supports RC climate controls.
	 *                                 For this baseline version, maxsize=1. i.e. only one radio control module is supported.
	 */
	public void setRadioControlCapabilities(List<RadioControlCapabilities> radioControlCapabilities) {
		setValue(KEY_RADIO_CONTROL_CAPABILITIES, radioControlCapabilities);
	}

	/**
	 * Gets the radioControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @return List<RadioControlCapabilities>
	 * If included, the platform supports RC climate controls.
	 * For this baseline version, maxsize=1. i.e. only one radio control module is supported.
	 */
	public List<RadioControlCapabilities> getRadioControlCapabilities() {
		return (List<RadioControlCapabilities>) getObject(RadioControlCapabilities.class, KEY_RADIO_CONTROL_CAPABILITIES);
	}

	/**
	 * Sets the buttonCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @param buttonCapabilities If included, the platform supports RC button controls with the included button names.
	 */
	public void setButtonCapabilities(List<ButtonCapabilities> buttonCapabilities) {
		setValue(KEY_BUTTON_CAPABILITIES, buttonCapabilities);
	}

	/**
	 * Gets the buttonCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @return List<ButtonCapabilities>
	 * If included, the platform supports RC button controls with the included button names.
	 */
	public List<ButtonCapabilities> getButtonCapabilities() {
		return (List<ButtonCapabilities>) getObject(ButtonCapabilities.class, KEY_BUTTON_CAPABILITIES);
	}

	/**
	 * Sets the seatControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @param seatControlCapabilities If included, the platform supports seat controls.
	 */
	public void setSeatControlCapabilities(List<SeatControlCapabilities> seatControlCapabilities) {
		setValue(KEY_SEAT_CONTROL_CAPABILITIES, seatControlCapabilities);
	}

	/**
	 * Gets the seatControlCapabilities portion of the RemoteControlCapabilities class
	 *
	 * @return List<SeatControlCapabilities>
	 * If included, the platform supports seat controls.
	 */
	public List<SeatControlCapabilities> getSeatControlCapabilities() {
		return (List<SeatControlCapabilities>) getObject(SeatControlCapabilities.class, KEY_SEAT_CONTROL_CAPABILITIES);
	}
}
