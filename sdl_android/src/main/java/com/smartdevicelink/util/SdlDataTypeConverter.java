package com.smartdevicelink.util;

/**
 * This is a utility class to aid in handling values stored in the RPC classes.
 */
public class SdlDataTypeConverter {
	
	/**
	 * Converts values that are retrieved from an RPC parameters Hashtable as an
	 * Object into the standard number value of the mobile API, Double.
	 * 
	 * @param originalValue The value retrieved from an RPC parameters Hashtable.
	 * @return The Double representation of an integer or double value stored in
	 * the Object, or null if the value could not be converted. 
	 */
	public static Double objectToDouble(Object originalValue) {

		if (originalValue == null) {
			return null;
		}

		Double result = null;

		// Uses reflection to determine if the object is a valid type.
		if (originalValue instanceof Integer) {
			result = ((Integer) originalValue).doubleValue();
		} else if (originalValue instanceof Float){
			result = ((Float) originalValue).doubleValue();
		} else if (originalValue instanceof Double){
			result = (Double) originalValue;
		}

		return result;
	}

	/**
	 * Converts values that are retrieved from an RPC parameters Hashtable as an
	 * Object into the standard number value of the mobile API, Float.
	 *
	 * @param originalValue The value retrieved from an RPC parameters Hashtable.
	 * @return The Float representation of an integer or float value stored in
	 * the Object, or null if the value could not be converted.
	 */
	public static Float objectToFloat(Object originalValue) {

		if (originalValue == null) {
			return null;
		}

		Float result = null;

		// Uses reflection to determine if the object is a valid type.
		if (originalValue instanceof Integer) {
			result = ((Integer) originalValue).floatValue();
		} else if (originalValue instanceof Double){
			result = ((Double) originalValue).floatValue();
		} else if (originalValue instanceof Float){
			result = (Float) originalValue;
		}

		return result;
	}
}
