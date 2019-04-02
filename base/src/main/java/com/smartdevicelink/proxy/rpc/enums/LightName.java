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
package com.smartdevicelink.proxy.rpc.enums;

public enum LightName {
	/*Common Single Light*/
	FRONT_LEFT_HIGH_BEAM,
	FRONT_RIGHT_HIGH_BEAM,
	FRONT_LEFT_LOW_BEAM,
	FRONT_RIGHT_LOW_BEAM,
	FRONT_LEFT_PARKING_LIGHT,
	FRONT_RIGHT_PARKING_LIGHT,
	FRONT_LEFT_FOG_LIGHT,
	FRONT_RIGHT_FOG_LIGHT,
	FRONT_LEFT_DAYTIME_RUNNING_LIGHT,
	FRONT_RIGHT_DAYTIME_RUNNING_LIGHT,
	FRONT_LEFT_TURN_LIGHT,
	FRONT_RIGHT_TURN_LIGHT,
	REAR_LEFT_FOG_LIGHT,
	REAR_RIGHT_FOG_LIGHT,
	REAR_LEFT_TAIL_LIGHT,
	REAR_RIGHT_TAIL_LIGHT,
	REAR_LEFT_BRAKE_LIGHT,
	REAR_RIGHT_BRAKE_LIGHT,
	REAR_LEFT_TURN_LIGHT,
	REAR_RIGHT_TURN_LIGHT,
	REAR_REGISTRATION_PLATE_LIGHT,

	/**
	 * Include all high beam lights: front_left and front_right.
	 */
	HIGH_BEAMS,
	/**
	 * Include all low beam lights: front_left and front_right.
	 */
	LOW_BEAMS,
	/**
	 * Include all fog lights: front_left, front_right, rear_left and rear_right.
	 */
	FOG_LIGHTS,
	/**
	 * Include all daytime running lights: front_left and front_right.
	 */
	RUNNING_LIGHTS,
	/**
	 * Include all parking lights: front_left and front_right.
	 */
	PARKING_LIGHTS,
	/**
	 * Include all brake lights: rear_left and rear_right.
	 */
	BRAKE_LIGHTS,
	REAR_REVERSING_LIGHTS,
	SIDE_MARKER_LIGHTS,

	/**
	 * Include all left turn signal lights: front_left, rear_left, left_side and mirror_mounted.
	 */
	LEFT_TURN_LIGHTS,
	/**
	 * Include all right turn signal lights: front_right, rear_right, right_side and mirror_mounted.
	 */
	RIGHT_TURN_LIGHTS,
	/**
	 * Include all hazard lights: front_left, front_right, rear_left and rear_right.
	 */
	HAZARD_LIGHTS,
	/**
	 * Cargo lamps illuminate the cargo area.
	 */
	REAR_CARGO_LIGHTS,
	/**
	 * Truck bed lamps light up the bed of the truck.
	 */
	REAR_TRUCK_BED_LIGHTS,
	/**
	 * Trailer lights are lamps mounted on a trailer hitch.
	 */
	REAR_TRAILER_LIGHTS,
	/**
	 * It is the spotlights mounted on the left side of a vehicle.
	 */
	LEFT_SPOT_LIGHTS,
	/**
	 * It is the spotlights mounted on the right side of a vehicle.
	 */
	RIGHT_SPOT_LIGHTS,
	/**
	 * Puddle lamps illuminate the ground beside the door as the customer is opening or approaching the door.
	 */
	LEFT_PUDDLE_LIGHTS,
	/**
	 * Puddle lamps illuminate the ground beside the door as the customer is opening or approaching the door.
	 */
	RIGHT_PUDDLE_LIGHTS,

	/*Interior Lights by common function groups*/

	AMBIENT_LIGHTS,
	OVERHEAD_LIGHTS,
	READING_LIGHTS,
	TRUNK_LIGHTS,


	/*Lights by location*/

	/**
	 * Include exterior lights located in front of the vehicle. For example, fog lights and low beams.
	 */
	EXTERIOR_FRONT_LIGHTS,
	/**
	 * Include exterior lights located at the back of the vehicle. For example, license plate lights, reverse lights, cargo lights, bed lights an trailer assist lights.
	 */
	EXTERIOR_REAR_LIGHTS,
	/**
	 * Include exterior lights located at the left side of the vehicle. For example, left puddle lights and spot lights.
	 */
	EXTERIOR_LEFT_LIGHTS,
	/**
	 * Include exterior lights located at the right side of the vehicle. For example, right puddle lights and spot lights.
	 */
	EXTERIOR_RIGHT_LIGHTS,
	/**
	 * Include all exterior lights around the vehicle.
	 */
	EXTERIOR_ALL_LIGHTS,
	;

	public static LightName valueForString(String value) {
		try {
			return valueOf(value);
		} catch (Exception e) {
			return null;
		}
	}
}
