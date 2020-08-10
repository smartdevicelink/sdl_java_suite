/*
 * Copyright (c) 2019 Livio, Inc.
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
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.Direction;
import com.smartdevicelink.proxy.rpc.enums.NavigationAction;
import com.smartdevicelink.proxy.rpc.enums.NavigationJunction;

import java.util.Hashtable;

public class NavigationInstruction extends RPCStruct {

	public static final String KEY_LOCATION_DETAILS = "locationDetails";
	public static final String KEY_ACTION = "action";
	public static final String KEY_ETA = "eta";
	public static final String KEY_BEARING = "bearing";
	public static final String KEY_JUNCTION_TYPE = "junctionType";
	public static final String KEY_DRIVING_SIDE = "drivingSide";
	public static final String KEY_DETAILS = "details";
	public static final String KEY_IMAGE = "image";

	// Constructors

	public NavigationInstruction() { }

	public NavigationInstruction(Hashtable<String, Object> hash) {
		super(hash);
	}

	public NavigationInstruction(@NonNull LocationDetails locationDetails, @NonNull NavigationAction action){
		this();
		setLocationDetails(locationDetails);
		setAction(action);
	}

	// Setters and Getters

	/**
	 * @param locationDetails -
	 */
	public void setLocationDetails(@NonNull LocationDetails locationDetails){
		setValue(KEY_LOCATION_DETAILS, locationDetails);
	}

	/**
	 * @return locationDetails
	 */
	public LocationDetails getLocationDetails(){
		return (LocationDetails) getObject(LocationDetails.class, KEY_LOCATION_DETAILS);
	}

	/**
	 * @param action -
	 */
	public void setAction(@NonNull NavigationAction action){
		setValue(KEY_ACTION, action);
	}

	/**
	 * @return action
	 */
	public NavigationAction getAction(){
		return (NavigationAction) getObject(NavigationAction.class, KEY_ACTION);
	}

	/**
	 * @param eta -
	 */
	public void setEta(DateTime eta){
		setValue(KEY_ETA, eta);
	}

	/**
	 * @return eta
	 */
	public DateTime getEta(){
		return (DateTime) getObject(DateTime.class, KEY_ETA);
	}

	/**
	 * The angle at which this instruction takes place. For example, 0 would mean straight, <=45
	 * is bearing right, >= 135 is sharp right, between 45 and 135 is a regular right, and 180 is
	 * a U-Turn, etc.
	 * @param bearing - minValue="0" maxValue="359"
	 */
	public void setBearing(Integer bearing){
		setValue(KEY_BEARING, bearing);
	}

	/**
	 * The angle at which this instruction takes place. For example, 0 would mean straight, <=45
	 * is bearing right, >= 135 is sharp right, between 45 and 135 is a regular right, and 180 is
	 * a U-Turn, etc.
	 * @return bearing - minValue="0" maxValue="359"
	 */
	public Integer getBearing(){
		return getInteger(KEY_BEARING);
	}

	/**
	 * @param junctionType -
	 */
	public void setJunctionType(NavigationJunction junctionType){
		setValue(KEY_JUNCTION_TYPE, junctionType);
	}

	/**
	 * @return junctionType
	 */
	public NavigationJunction getJunctionType(){
		return (NavigationJunction) getObject(NavigationJunction.class, KEY_JUNCTION_TYPE);
	}

	/**
	 * Used to infer which side of the road this instruction takes place. For a U-Turn (action=TURN, bearing=180) this
	 * will determine which direction the turn should take place.
	 * @param drivingSide - Direction enum value that represents the driving side
	 */
	public void setDrivingSide(Direction drivingSide){
		setValue(KEY_DRIVING_SIDE, drivingSide);
	}

	/**
	 * Used to infer which side of the road this instruction takes place. For a U-Turn (action=TURN, bearing=180) this
	 * will determine which direction the turn should take place.
	 * @return drivingSide
	 */
	public Direction getDrivingSide(){
		return (Direction) getObject(Direction.class, KEY_DRIVING_SIDE);
	}

	/**
	 * This is a string representation of this instruction, used to display instructions to the
	 * users. This is not intended to be read aloud to the users, see the param prompt in
	 * NavigationServiceData for that.
	 * @param details -
	 */
	public void setDetails(String details){
		setValue(KEY_DETAILS, details);
	}

	/**
	 * This is a string representation of this instruction, used to display instructions to the
	 * users. This is not intended to be read aloud to the users, see the param prompt in
	 * NavigationServiceData for that.
	 * @return details
	 */
	public String getDetails(){
		return getString(KEY_DETAILS);
	}

	/**
	 * An image representation of this instruction.
	 * @param image -
	 */
	public void setImage(Image image){
		setValue(KEY_IMAGE, image);
	}

	/**
	 * An image representation of this instruction.
	 * @return image
	 */
	public Image getImage(){
		return (Image) getObject(Image.class, KEY_IMAGE);
	}

}
