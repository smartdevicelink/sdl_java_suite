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
		return getInteger(KEY_ID);
	}

	/**
	 * The X-coordinate of the user control
	 */
	public void setX(Integer x) {
		setValue(KEY_X, x);
	}

	public Integer getX() {
		return getInteger(KEY_X);
	}

	/**
	 * The Y-coordinate of the user control
	 */
	public void setY(Integer y) {
		setValue(KEY_Y, y);
	}

	public Integer getY() {
		return getInteger(KEY_Y);
	}

	/**
	 * The width of the user control's bounding rectangle
	 */
	public void setWidth(Integer width) {
		setValue(KEY_WIDTH, width);
	}

	public Integer getWidth() {
		return getInteger(KEY_WIDTH);
	}

	/**
	 * The height of the user control's bounding rectangle
	 */
	public void setHeight(Integer height) {
		setValue(KEY_HEIGHT, height);
	}

	public Integer getHeight() { return getInteger(KEY_HEIGHT); }
}
