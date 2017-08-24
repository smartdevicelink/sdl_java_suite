package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Defines Rectangle for each user control object for video streaming application
 */

public class Rectangle extends RPCStruct {
	public static final String KEY_X = "x";
	public static final String KEY_Y = "y";
	public static final String KEY_WIDTH = "width";
	public static final String KEY_HEIGHT = "height";

	public Rectangle() {}
	public Rectangle(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * The X-coordinate of the user control
	 */
	public void setX(Float x) {
		setValue(KEY_X, x);
	}

	public Float getX() {
		return getFloat(KEY_X);
	}

	/**
	 * The Y-coordinate of the user control
	 */
	public void setY(Float y) {
		setValue(KEY_Y, y);
	}

	public Float getY() {
		return getFloat(KEY_Y);
	}

	/**
	 * The width of the user control's bounding rectangle
	 */
	public void setWidth(Float width) {
		setValue(KEY_WIDTH, width);
	}

	public Float getWidth() {
		return getFloat(KEY_WIDTH);
	}

	/**
	 * The height of the user control's bounding rectangle
	 */
	public void setHeight(Float height) {
		setValue(KEY_HEIGHT, height);
	}

	public Float getHeight() {
		return getFloat(KEY_HEIGHT);
	}
}
