package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
/*
 * Copyright (c) 2017 Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * Defines Rectangle for each user control object for video streaming application
 * @since SmartDeviceLink 4.5.0
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

	public Rectangle(@NonNull Float x, @NonNull Float y, @NonNull Float width, @NonNull Float height) {
		this();
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	/**
	 * Set the X-coordinate pixel in of the user control that starts in the upper left corner
	 */
	public void setX(@NonNull Float x) {
		setValue(KEY_X, x);
	}

	/**
	 * @return  the X-coordinate pixel of the user control that starts in the upper left corner
	 */
	public Float getX() {
		return getFloat(KEY_X);
	}

	/**
	 * Set the Y-coordinate pixel of the user control that starts in the upper left corner
	 */
	public void setY(@NonNull Float y) {
		setValue(KEY_Y, y);
	}

	/**
	 * @return the Y-coordinate pixel of the user control that starts in the upper left corner
	 */
	public Float getY() {
		return getFloat(KEY_Y);
	}

	/**
	 * Set the width in pixels of the user control's bounding rectangle in pixels
	 */
	public void setWidth(@NonNull Float width) {
		setValue(KEY_WIDTH, width);
	}

	/**
	 * @return  the width in pixels of the user control's bounding rectangle in pixels
	 */
	public Float getWidth() {
		return getFloat(KEY_WIDTH);
	}

	/**
	 * The height in pixels of the user control's bounding rectangle
	 */
	public void setHeight(@NonNull Float height) {
		setValue(KEY_HEIGHT, height);
	}

	/**
	 * @return the width in pixels of the user control's bounding rectangle in pixels
	 */
	public Float getHeight() {
		return getFloat(KEY_HEIGHT);
	}
}
