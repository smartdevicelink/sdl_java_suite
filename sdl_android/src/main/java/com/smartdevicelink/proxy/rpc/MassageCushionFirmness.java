package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.MassageCushion;

import java.util.Hashtable;

/**
 * The intensity or firmness of a cushion.
 */
public class MassageCushionFirmness extends RPCStruct {
	public static final String KEY_CUSHION = "cushion";
	public static final String KEY_FIRMNESS = "firmness";

	/**
	 * Constructs a new MassageCushionFirmness object
	 */
	public MassageCushionFirmness() {
	}

	/**
	 * <p>Constructs a new MassageCushionFirmness object indicated by the Hashtable parameter
	 * </p>
	 *
	 * @param hash The Hashtable to use
	 */
	public MassageCushionFirmness(Hashtable<String, Object> hash) {
		super(hash);
	}

	public MassageCushionFirmness(@NonNull MassageCushion cushion, @NonNull Integer firmness) {
		this();
		setCushion(cushion);
		setFirmness(firmness);
	}

	/**
	 * Sets the cushion portion of the MassageCushionFirmness class
	 *
	 * @param cushion
	 */
	public void setCushion(@NonNull MassageCushion cushion) {
		setValue(KEY_CUSHION, cushion);
	}

	/**
	 * Gets the cushion portion of the MassageCushionFirmness class
	 *
	 * @return MassageCushion.
	 */
	public MassageCushion getCushion() {
		return (MassageCushion) getObject(MassageCushion.class, KEY_CUSHION);
	}

	/**
	 * Sets the firmness portion of the MassageCushionFirmness class
	 *
	 * @param firmness
	 */
	public void setFirmness(@NonNull Integer firmness) {
		setValue(KEY_FIRMNESS, firmness);
	}

	/**
	 * Gets the firmness portion of the MassageCushionFirmness class
	 *
	 * @return Integer
	 */
	public Integer getFirmness() {
		return getInteger(KEY_FIRMNESS);
	}
}
