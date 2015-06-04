package com.smartdevicelink.util;

import java.util.Enumeration;
import java.util.Vector;

public abstract class ByteEnumer {	
	
	protected ByteEnumer(byte value, String name) {
		this.value = value;
		this.name = name;
	}
	
	private byte value;
	private String name;
	
	public byte getValue() { return value; }
	public String getName() { return name; }
	
	public boolean equals(ByteEnumer other) {
		return name == other.getName();
	}
	
	public boolean eq(ByteEnumer other) {
		return equals(other);
	}
		
	public byte value() {
		return value;
	}
	
	/**
	 * Searches the provided list for the ByteEnumer object that contains the
	 * given byte key.
	 * 
	 * @param list The list of classes that extends ByteEnumer.
	 * @param key The key being used to find and return the appropriate 
	 * ByteEnumer object.
	 * 
	 * @return ByteEnumer object with the corresponding byte value or null if
	 * the object could not be found, the list contained incompatible objects,
	 * or the list was null.
	 */
	public static ByteEnumer get(Vector<?> list, byte key) {
		try {
			// Can cause a NullPointerException.
			Enumeration<?> enumer = list.elements();
			
			// Iterates through the given list.
			while (enumer.hasMoreElements()) {
				
				// Can cause a ClassCastException.
				ByteEnumer current = (ByteEnumer) enumer.nextElement();
				
				// Checks if the object contains the key value.
				if (current.getValue() == key) {
					return current;
				}
			}
			return null; // There were no objects with the given key.
		} catch (NullPointerException e) {
			return null; // The list was null.
		} catch (ClassCastException e) {
			return null; // The list was incompatible.
		}
	}
	
	/**
	 * Searches the provided list for the ByteEnumer object that contains the
	 * given string key.
	 * 
	 * @param list The list of classes that extends ByteEnumer.
	 * @param key The key being used to find and return the appropriate 
	 * ByteEnumer object.
	 * 
	 * @return ByteEnumer object with the corresponding string value or null if
	 * the object could not be found, the list contained incompatible objects,
	 * or the list was null.
	 */
	public static ByteEnumer get(Vector<?> list, String key) {
		try {
			// Can cause a NullPointerException.
			Enumeration<?> enumer = list.elements();
			
			// Iterates through the given list.
			while (enumer.hasMoreElements()) {
				
				// Can cause a ClassCastException.
				ByteEnumer current = (ByteEnumer) enumer.nextElement();
				
				// Checks if the object contains the key value.
				if (current.getName().equals(key)) {
					return current;
				}
			}
			return null; // There were no objects with the given key.
		} catch (NullPointerException e) {
			return null; // The list was null.
		} catch (ClassCastException e) {
			return null; // The list was incompatible.
		}
	}
}