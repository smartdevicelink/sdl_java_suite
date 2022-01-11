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
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;

import java.util.Hashtable;

/**
 * The status of the seat belts.
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 * 			<th>Description</th>
 * 		</tr>
 * 		<tr>
 * 			<td>driverBeltDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The driver seat belt is deployed.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerBeltDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The passenger seat belt is deployed.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerBuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The passenger seat belt is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>driverBuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The driver seat belt is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>leftRow2BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The left seat belt of the 2nd row is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerChildDetected</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The child passenger is detected.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rightRow2BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The right seat belt of the 2nd row is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>middleRow2BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The middle seat belt of the 2nd row is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>middleRow3BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The middle seat belt of the 3rd row is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>leftRow3BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The left seat belt of the 3rd row is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rightRow3BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The right seat belt of the 3rd row is buckled.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>leftRearInflatableBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The left rear inflatable is belted.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rightRearInflatableBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The right rear inflatable is belted.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>middleRow1BeltDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The seat belt of the middle row is deployed.</td>
 * 		</tr>
 * 		<tr>
 * 			<td>middleRow1BuckleBelted</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>false</td>
 * 			<td>The seat belt of the middle row is buckled.</td>
 * 		</tr>
 *  </table>
 *
 * @see VehicleDataEventStatus
 * @see GetVehicleData
 * @see OnVehicleData
 * @see SubscribeVehicleData
 * @since SmartDeviceLink 2.0
 */

public class BeltStatus extends RPCStruct {
    public static final String KEY_DRIVER_BELT_DEPLOYED = "driverBeltDeployed";
    public static final String KEY_PASSENGER_BELT_DEPLOYED = "passengerBeltDeployed";
    public static final String KEY_PASSENGER_BUCKLE_BELTED = "passengerBuckleBelted";
    public static final String KEY_DRIVER_BUCKLE_BELTED = "driverBuckleBelted";
    public static final String KEY_LEFT_ROW_2_BUCKLE_BELTED = "leftRow2BuckleBelted";
    public static final String KEY_PASSENGER_CHILD_DETECTED = "passengerChildDetected";
    public static final String KEY_RIGHT_ROW_2_BUCKLE_BELTED = "rightRow2BuckleBelted";
    public static final String KEY_MIDDLE_ROW_2_BUCKLE_BELTED = "middleRow2BuckleBelted";
    public static final String KEY_MIDDLE_ROW_3_BUCKLE_BELTED = "middleRow3BuckleBelted";
    public static final String KEY_LEFT_ROW_3_BUCKLE_BELTED = "leftRow3BuckleBelted";

    public static final String KEY_RIGHT_ROW_3_BUCKLE_BELTED = "rightRow3BuckleBelted";
    public static final String KEY_LEFT_REAR_INFLATABLE_BELTED = "leftRearInflatableBelted";
    public static final String KEY_RIGHT_REAR_INFLATABLE_BELTED = "rightRearInflatableBelted";
    public static final String KEY_MIDDLE_ROW_1_BELT_DEPLOYED = "middleRow1BeltDeployed";
    public static final String KEY_MIDDLE_ROW_1_BUCKLE_BELTED = "middleRow1BuckleBelted";

    public BeltStatus() {
    }

    /**
     * Constructs a new BeltStatus object indicated by the Hashtable
     * parameter
     *
     * @param hash hashtable filled with params to create an instance of this RPC
     *             The hash table to use
     */
    public BeltStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    public BeltStatus(@NonNull VehicleDataEventStatus driverBeltDeployed, @NonNull VehicleDataEventStatus passengerBeltDeployed, @NonNull VehicleDataEventStatus passengerBuckleBelted,
                      @NonNull VehicleDataEventStatus driverBuckleBelted, @NonNull VehicleDataEventStatus leftRow2BuckleBelted, @NonNull VehicleDataEventStatus passengerChildDetected,
                      @NonNull VehicleDataEventStatus rightRow2BuckleBelted, @NonNull VehicleDataEventStatus middleRow2BuckleBelted, @NonNull VehicleDataEventStatus middleRow3BuckleBelted,
                      @NonNull VehicleDataEventStatus leftRow3BuckleBelted, @NonNull VehicleDataEventStatus rightRow3BuckleBelted, @NonNull VehicleDataEventStatus leftRearInflatableBelted,
                      @NonNull VehicleDataEventStatus rightRearInflatableBelted, @NonNull VehicleDataEventStatus middleRow1BeltDeployed, @NonNull VehicleDataEventStatus middleRow1BuckleBelted
    ) {
        this();
        setDriverBeltDeployed(driverBeltDeployed);
        setPassengerBeltDeployed(passengerBeltDeployed);
        setPassengerBuckleBelted(passengerBuckleBelted);
        setDriverBuckleBelted(driverBuckleBelted);
        setLeftRow2BuckleBelted(leftRow2BuckleBelted);
        setPassengerChildDetected(passengerChildDetected);
        setRightRow2BuckleBelted(rightRow2BuckleBelted);
        setMiddleRow2BuckleBelted(middleRow2BuckleBelted);
        setMiddleRow3BuckleBelted(middleRow3BuckleBelted);
        setLeftRow3BuckleBelted(leftRow3BuckleBelted);
        setRightRow3BuckleBelted(rightRow3BuckleBelted);
        setLeftRearInflatableBelted(leftRearInflatableBelted);
        setRightRearInflatableBelted(rightRearInflatableBelted);
        setMiddleRow1BeltDeployed(middleRow1BeltDeployed);
        setMiddleRow1BuckleBelted(middleRow1BuckleBelted);
    }

    public BeltStatus setDriverBeltDeployed(@NonNull VehicleDataEventStatus driverBeltDeployed) {
        setValue(KEY_DRIVER_BELT_DEPLOYED, driverBeltDeployed);
        return this;
    }

    public VehicleDataEventStatus getDriverBeltDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_BELT_DEPLOYED);
    }

    public BeltStatus setPassengerBeltDeployed(@NonNull VehicleDataEventStatus passengerBeltDeployed) {
        setValue(KEY_PASSENGER_BELT_DEPLOYED, passengerBeltDeployed);
        return this;
    }

    public VehicleDataEventStatus getPassengerBeltDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_PASSENGER_BELT_DEPLOYED);
    }

    public BeltStatus setPassengerBuckleBelted(@NonNull VehicleDataEventStatus passengerBuckleBelted) {
        setValue(KEY_PASSENGER_BUCKLE_BELTED, passengerBuckleBelted);
        return this;
    }

    public VehicleDataEventStatus getPassengerBuckleBelted() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_PASSENGER_BUCKLE_BELTED);
    }

    public BeltStatus setDriverBuckleBelted(VehicleDataEventStatus driverBuckleBelted) {
        setValue(KEY_DRIVER_BUCKLE_BELTED, driverBuckleBelted);
        return this;
    }

    public VehicleDataEventStatus getDriverBuckleBelted() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_BUCKLE_BELTED);
    }

    public BeltStatus setLeftRow2BuckleBelted(VehicleDataEventStatus leftRow2BuckleBelted) {
        setValue(KEY_LEFT_ROW_2_BUCKLE_BELTED, leftRow2BuckleBelted);
        return this;
    }

    public VehicleDataEventStatus getLeftRow2BuckleBelted() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_LEFT_ROW_2_BUCKLE_BELTED);
    }

    public BeltStatus setPassengerChildDetected(@NonNull VehicleDataEventStatus passengerChildDetected) {
        setValue(KEY_PASSENGER_CHILD_DETECTED, passengerChildDetected);
        return this;
    }

    public VehicleDataEventStatus getPassengerChildDetected() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_PASSENGER_CHILD_DETECTED);
    }

    public BeltStatus setRightRow2BuckleBelted(@NonNull VehicleDataEventStatus rightRow2BuckleBelted) {
        setValue(KEY_RIGHT_ROW_2_BUCKLE_BELTED, rightRow2BuckleBelted);
        return this;
    }

    public VehicleDataEventStatus getRightRow2BuckleBelted() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_RIGHT_ROW_2_BUCKLE_BELTED);
    }

    public BeltStatus setMiddleRow2BuckleBelted(@NonNull VehicleDataEventStatus middleRow2BuckleBelted) {
        setValue(KEY_MIDDLE_ROW_2_BUCKLE_BELTED, middleRow2BuckleBelted);
        return this;
    }

    public VehicleDataEventStatus getMiddleRow2BuckleBelted() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_MIDDLE_ROW_2_BUCKLE_BELTED);
    }

    public BeltStatus setMiddleRow3BuckleBelted(@NonNull VehicleDataEventStatus middleRow3BuckleBelted) {
        setValue(KEY_MIDDLE_ROW_3_BUCKLE_BELTED, middleRow3BuckleBelted);
        return this;
    }

    public VehicleDataEventStatus getMiddleRow3BuckleBelted() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_MIDDLE_ROW_3_BUCKLE_BELTED);
    }

    public BeltStatus setLeftRow3BuckleBelted(@NonNull VehicleDataEventStatus leftRow3BuckleBelted) {
        setValue(KEY_LEFT_ROW_3_BUCKLE_BELTED, leftRow3BuckleBelted);
        return this;
    }

    public VehicleDataEventStatus getLeftRow3BuckleBelted() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_LEFT_ROW_3_BUCKLE_BELTED);
    }

    public BeltStatus setRightRow3BuckleBelted(@NonNull VehicleDataEventStatus rightRow3BuckleBelted) {
        setValue(KEY_RIGHT_ROW_3_BUCKLE_BELTED, rightRow3BuckleBelted);
        return this;
    }

    public VehicleDataEventStatus getRightRow3BuckleBelted() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_RIGHT_ROW_3_BUCKLE_BELTED);
    }

    public BeltStatus setLeftRearInflatableBelted(@NonNull VehicleDataEventStatus rearInflatableBelted) {
        setValue(KEY_LEFT_REAR_INFLATABLE_BELTED, rearInflatableBelted);
        return this;
    }

    public VehicleDataEventStatus getLeftRearInflatableBelted() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_LEFT_REAR_INFLATABLE_BELTED);
    }

    public BeltStatus setRightRearInflatableBelted(@NonNull VehicleDataEventStatus rightRearInflatableBelted) {
        setValue(KEY_RIGHT_REAR_INFLATABLE_BELTED, rightRearInflatableBelted);
        return this;
    }

    public VehicleDataEventStatus getRightRearInflatableBelted() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_RIGHT_REAR_INFLATABLE_BELTED);
    }

    public BeltStatus setMiddleRow1BeltDeployed(@NonNull VehicleDataEventStatus middleRow1BeltDeployed) {
        setValue(KEY_MIDDLE_ROW_1_BELT_DEPLOYED, middleRow1BeltDeployed);
        return this;
    }

    public VehicleDataEventStatus getMiddleRow1BeltDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_MIDDLE_ROW_1_BELT_DEPLOYED);
    }

    public BeltStatus setMiddleRow1BuckleBelted(@NonNull VehicleDataEventStatus middleRow1BuckleBelted) {
        setValue(KEY_MIDDLE_ROW_1_BUCKLE_BELTED, middleRow1BuckleBelted);
        return this;
    }

    public VehicleDataEventStatus getMiddleRow1BuckleBelted() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_MIDDLE_ROW_1_BUCKLE_BELTED);
    }
}
