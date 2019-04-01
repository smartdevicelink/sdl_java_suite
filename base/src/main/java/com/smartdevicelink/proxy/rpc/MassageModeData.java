package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.MassageMode;
import com.smartdevicelink.proxy.rpc.enums.MassageZone;

import java.util.Hashtable;

/**
 * Specify the mode of a massage zone.
 */
public class MassageModeData extends RPCStruct {
	public static final String KEY_MASSAGE_ZONE = "massageZone";
	public static final String KEY_MASSAGE_MODE = "massageMode";

	/**
	 * Constructs a new MassageModeData object
	 */
	public MassageModeData() {
	}

	/**
	 * <p>Constructs a new MassageModeData object indicated by the Hashtable parameter
	 * </p>
	 *
	 * @param hash The Hashtable to use
	 */
	public MassageModeData(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a newly allocated MassageModeData object
	 * @param massageZone type of MassageZone for multi-contour massage seat
	 * @param massageMode mode of massage zone.
	 */
	public MassageModeData(@NonNull MassageZone massageZone, @NonNull MassageMode massageMode) {
		this();
		setMassageZone(massageZone);
		setMassageMode(massageMode);
	}

	/**
	 * Sets the massageZone portion of the MassageModeData class
	 *
	 * @param massageZone
	 */
	public void setMassageZone(@NonNull MassageZone massageZone) {
		setValue(KEY_MASSAGE_ZONE, massageZone);
	}

	/**
	 * Gets the massageZone portion of the MassageModeData class
	 *
	 * @return MassageZone.
	 */
	public MassageZone getMassageZone() {
		return (MassageZone) getObject(MassageZone.class, KEY_MASSAGE_ZONE);
	}

	/**
	 * Gets the massageMode portion of the MassageModeData class
	 *
	 * @return MassageMode
	 */
	public MassageMode getMassageMode() {
		return (MassageMode) getObject(MassageMode.class, KEY_MASSAGE_MODE);
	}

	/**
	 * Sets the massageMode portion of the MassageModeData class
	 *
	 * @param massageMode
	 */
	public void setMassageMode(@NonNull MassageMode massageMode) {
		setValue(KEY_MASSAGE_MODE, massageMode);
	}
}
