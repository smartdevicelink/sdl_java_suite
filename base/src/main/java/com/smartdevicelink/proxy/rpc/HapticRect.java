package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

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
 * Defines a haptic rectangle that contains a reference ID and the spatial data of a rectangle UI component.
 * @since SmartDeviceLink 4.5.0
 *
 */

public class HapticRect extends RPCStruct {
	public static final String KEY_ID = "id";
	public static final String KEY_RECT = "rect";

	public HapticRect() {}

	public HapticRect(Hashtable<String, Object> hash) {
		super(hash);
	}

	public HapticRect(@NonNull Integer id, @NonNull Rectangle rect) {
		this();
		setId(id);
		setRect(rect);
	}
	/**
	 * Set a user control spatial identifier that references the supplied spatial data
	 */
	public HapticRect setId(@NonNull Integer id) {
        setValue(KEY_ID, id);
        return this;
    }

	/**
	 * @return  a user control spatial identifier that references the supplied spatial data
	 */
	public Integer getId() {
		return getInteger(KEY_ID);
	}

	/**
	 * Set the position of the haptic rectangle to be highlighted. The center of this rectangle will be "touched" when a press occurs.
	 */
	public HapticRect setRect(@NonNull Rectangle rect) {
        setValue(KEY_RECT, rect);
        return this;
    }

	/**
	 * @return the position of the haptic rectangle to be highlighted. The center of this rectangle will be "touched" when a press occurs.
	 */
	public Rectangle getRect() {
		return (Rectangle) getObject(Rectangle.class, KEY_RECT);
	}
}
