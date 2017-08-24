package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Created by brettywhite on 8/24/17.
 */

public class HapticRect extends RPCStruct {
	public static final String KEY_ID = "id";
	public static final String KEY_RECT = "rect";

	public HapticRect() {}
	public HapticRect(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * A user control spatial identifier
	 */
	public void setId(Integer id) {
		setValue(KEY_ID, id);
	}

	public Integer getId() {
		return getInteger(KEY_ID);
	}

	/**
	 * The position of the haptic rectangle to be highlighted. The center of this rectangle will be "touched" when a press occurs.
	 */
	public void setRect(Rectangle rect) {
		setValue(KEY_RECT, rect);
	}

	public Rectangle getRect() {
		return (Rectangle) getObject(Rectangle.class, KEY_RECT);
	}
}
