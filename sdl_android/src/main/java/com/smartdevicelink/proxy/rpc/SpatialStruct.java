package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Defines spatial for each user control object for video streaming application
 */

public class SpatialStruct extends RPCStruct {
	public static final String KEY_ID = "id";
	public static final String KEY_X = "x";
	public static final String KEY_Y = "y";
	public static final String KEY_WIDTH = "width";
	public static final String KEY_HEIGHT = "height";

	public SpatialStruct() {}
	public SpatialStruct(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * A user control spatial identifier
	 */
	public void setID(Integer id) {
		setValue(KEY_ID, id);
	}

	public Integer getID() {
		return getInteger(KEY_X);
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
