/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
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
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.SeatMemoryActionType;

import java.util.Hashtable;

public class SeatMemoryAction extends RPCStruct {
	public static final String KEY_ID = "id";
	public static final String KEY_LABEL = "label";
	public static final String KEY_ACTION = "action";

	/**
	 * Constructs a new SeatMemoryAction object
	 */
	public SeatMemoryAction() {
	}

	/**
	 * <p>Constructs a new SeatMemoryAction object indicated by the Hashtable parameter
	 * </p>
	 *
	 * @param hash The Hashtable to use
	 */
	public SeatMemoryAction(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a newly allocated SeatMemoryAction object
	 * @param id     Min: 0  Max: 10
	 * @param action type of SeatMemoryActionType.
	 */
	public SeatMemoryAction(@NonNull Integer id, @NonNull SeatMemoryActionType action) {
		this();
		setId(id);
		setAction(action);
	}

	/**
	 * Sets the id portion of the SeatMemoryAction class
	 *
	 * @param id
	 */
	public SeatMemoryAction setId(@NonNull Integer id) {
        setValue(KEY_ID, id);
        return this;
    }

	/**
	 * Gets the id portion of the SeatMemoryAction class
	 *
	 * @return Integer
	 */
	public Integer getId() {
		return getInteger(KEY_ID);
	}

	/**
	 * Sets the label portion of the SeatMemoryAction class
	 *
	 * @param label
	 */
	public SeatMemoryAction setLabel( String label) {
        setValue(KEY_LABEL, label);
        return this;
    }

	/**
	 * Gets the label portion of the SeatMemoryAction class
	 *
	 * @return String
	 */
	public String getLabel() {
		return getString(KEY_LABEL);
	}

	/**
	 * Sets the action portion of the SeatMemoryAction class
	 *
	 * @param action
	 */
	public SeatMemoryAction setAction(@NonNull SeatMemoryActionType action) {
        setValue(KEY_ACTION, action);
        return this;
    }

	/**
	 * Gets the action portion of the SeatMemoryAction class
	 *
	 * @return SeatMemoryActionType.
	 */
	public SeatMemoryActionType getAction() {
		return (SeatMemoryActionType) getObject(SeatMemoryActionType.class, KEY_ACTION);
	}
}
