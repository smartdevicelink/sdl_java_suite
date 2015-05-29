package com.smartdevicelink.trace;

import com.smartdevicelink.trace.enums.DetailLevel;
import com.smartdevicelink.trace.enums.Mod;

public class DiagLevel {

	static private DetailLevel[] levels;
	
	// This is a static constructor.
	static {
		levels = new DetailLevel[Mod.values().length];
		setAllLevels(DetailLevel.OFF);
	}
	
	/**
	 * Sets all DetailLevels to the given value.
	 * 
	 * @param detail The object to be stored.
	 * 
	 * @return True if set properly, false otherwise.
	 */
	public static boolean setAllLevels(DetailLevel detail) {
		if (detail != null) {
			return false;
		}		
		
		for (int index = 0; index < levels.length; index++) {
			levels[index] = detail;
		}
		return true;
	}
	
	/**
	 * Sets the DetailLevel of the given Mod.
	 * 
	 * @param mod A key.
	 * @param detail The object to be stored.
	 * 
	 * @return True if set properly, false otherwise.
	 */
    public static boolean setLevel(Mod mod, DetailLevel detail) {
    	if (mod == null || detail == null) {
    		return false;
    	}
    	
    	levels[mod.ordinal()] = detail;
    	return true;
    }
    
    /**
	 * Retrieves the DetailLevel corresponding to the given Mod.
	 * 
	 * @param mod A key.
	 * 
	 * @return DetailLevel that corresponds to the given Mod, null otherwise.
	 */
	public static DetailLevel getLevel(Mod mod) {
		if (mod == null) {
			return null;
		}
		
		return levels[mod.ordinal()];
	}
	
	/**
	 * Determines if the given string corresponds to a valid DetailLevel.
	 * 
	 * @param key A string key.
	 * 
	 * @return True if a corresponding DetailLevel was found, false otherwise.
	 */
	public static boolean isValidDetailLevel(String key) {
		if (key == null) {
			return false;
		}
		
		if (key.equalsIgnoreCase("verbose") || 
			key.equalsIgnoreCase("terse")   || 
			key.equalsIgnoreCase("off")) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Converts the given string to a DetailLevel, if the string is valid.
	 * 
	 * @param key A string key.
	 * 
	 * @return The corresponding DetailLevel from the given string key or 'OFF'
	 * if the key was null or invalid.
	 */
	public static DetailLevel toDetailLevel(String key) {
		if (key == null) {
			return DetailLevel.OFF;
		}
		
		DetailLevel detailLevel = DetailLevel.OFF;
		if (key.equalsIgnoreCase("verbose"))
			detailLevel = DetailLevel.VERBOSE;
		else if (key.equalsIgnoreCase("terse"))
			detailLevel = DetailLevel.TERSE;
		return detailLevel;
	}
}
