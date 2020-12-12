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
import com.smartdevicelink.proxy.rpc.enums.WarningLightStatus;

import java.util.Hashtable;

/**
 * The status and pressure of the tires.
 *
 * <p><b>Parameter List</b></p>
 *
 * <table border="1" rules="all">
 *  <tr>
 *      <th>Param Name</th>
 *      <th>Type</th>
 *      <th>Description</th>
 *      <th>Required</th>
 *      <th>Notes</th>
 *      <th>Version Available</th>
 *  </tr>
 *  <tr>
 *      <td>pressureTelltale</td>
 *      <td>WarningLightStatus</td>
 *      <td>Status of the Tire Pressure Telltale. See WarningLightStatus.</td>
 *      <td>N</td>
 *      <td></td>
 *      <td>
 *         @since SmartDeviceLink 2.0.0
 *      </td>
 *  </tr>
 *  <tr>
 *      <td>leftFront</td>
 *      <td>SingleTireStatus</td>
 *      <td>The status of the left front tire.</td>
 *      <td>N</td>
 *      <td></td>
 *      <td>
 *         @since SmartDeviceLink 2.0.0
 *      </td>
 *
 *  </tr>
 *  <tr>
 *      <td>rightFront</td>
 *      <td>SingleTireStatus</td>
 *      <td>The status of the right front tire.</td>
 *      <td>N</td>
 *      <td></td>
 *      <td>
 *         @since SmartDeviceLink 2.0.0
 *      </td>
 *  </tr>
 *  <tr>
 *      <td>leftRear</td>
 *      <td>SingleTireStatus</td>
 *      <td>The status of the left rear tire.</td>
 *      <td>N</td>
 *      <td></td>
 *      <td>
 *         @since SmartDeviceLink 2.0.0
 *      </td>
 *  </tr>
 *  <tr>
 *      <td>rightRear</td>
 *      <td>SingleTireStatus</td>
 *      <td>The status of the right rear tire.</td>
 *      <td>N</td>
 *      <td></td>
 *      <td>
 *         @since SmartDeviceLink 2.0.0
 *      </td>
 *  </tr>
 *  <tr>
 *      <td>innerLeftRear</td>
 *      <td>SingleTireStatus</td>
 *      <td>The status of the inner left rear.</td>
 *      <td>N</td>
 *      <td></td>
 *      <td>
 *         @since SmartDeviceLink 2.0.0
 *      </td>
 *  </tr>
 *  <tr>
 *      <td>innerRightRear</td>
 *      <td>SingleTireStatus</td>
 *      <td>The status of the inner right rear.</td>
 *      <td>N</td>
 *      <td></td>
 *      <td>
 *         @since SmartDeviceLink 2.0.0
 *      </td>
 *  </tr>
 * </table>
 *
 * @since SmartDeviceLink 2.0.0
 */
public class TireStatus extends RPCStruct {
    /**
     * @since SmartDeviceLink 2.0.0
     */
    public static final String KEY_PRESSURE_TELLTALE = "pressureTelltale";
    /**
     * @since SmartDeviceLink 2.0.0
     */
    public static final String KEY_LEFT_FRONT = "leftFront";
    /**
     * @since SmartDeviceLink 2.0.0
     */
    public static final String KEY_RIGHT_FRONT = "rightFront";
    /**
     * @since SmartDeviceLink 2.0.0
     */
    public static final String KEY_LEFT_REAR = "leftRear";
    /**
     * @since SmartDeviceLink 2.0.0
     */
    public static final String KEY_RIGHT_REAR = "rightRear";
    /**
     * @since SmartDeviceLink 2.0.0
     */
    public static final String KEY_INNER_LEFT_REAR = "innerLeftRear";
    /**
     * @since SmartDeviceLink 2.0.0
     */
    public static final String KEY_INNER_RIGHT_REAR = "innerRightRear";

    public TireStatus() {
    }

    /**
     * <p>Constructs a new TireStatus object indicated by the Hashtable parameter</p>
     *
     * @param hash The Hashtable to use
     */
    public TireStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new TireStatus object
     *
     * @param pressureTellTale Status of the Tire Pressure TellTale
     * @param leftFront        The status of the left front tire.
     * @param rightFront       The status of the right front tire.
     * @param leftRear         The status of the left rear tire.
     * @param rightRear        The status of the right rear tire
     * @param innerLeftRear    The status of the inner left rear tire.
     * @param innerRightRear   The status of the inner right rear tire.
     */
    public TireStatus(@NonNull WarningLightStatus pressureTellTale, @NonNull SingleTireStatus leftFront, @NonNull SingleTireStatus rightFront, @NonNull SingleTireStatus leftRear, @NonNull SingleTireStatus rightRear, @NonNull SingleTireStatus innerLeftRear, @NonNull SingleTireStatus innerRightRear) {
        this();
        setPressureTelltale(pressureTellTale);
        setLeftFront(leftFront);
        setRightFront(rightFront);
        setLeftRear(leftRear);
        setRightRear(rightRear);
        setInnerLeftRear(innerLeftRear);
        setInnerRightRear(innerRightRear);
    }

    /**
     * Sets the pressureTelltale.
     *
     * @param pressureTelltale Status of the Tire Pressure Telltale. See WarningLightStatus.
     * @since SmartDeviceLink 2.0.0
     */
    public TireStatus setPressureTelltale(WarningLightStatus pressureTelltale) {
        setValue(KEY_PRESSURE_TELLTALE, pressureTelltale);
        return this;
    }

    /**
     * Gets the pressureTelltale.
     *
     * @return WarningLightStatus Status of the Tire Pressure Telltale. See WarningLightStatus.
     * @since SmartDeviceLink 2.0.0
     */
    public WarningLightStatus getPressureTelltale() {
        return (WarningLightStatus) getObject(WarningLightStatus.class, KEY_PRESSURE_TELLTALE);
    }

    /**
     * Sets the leftFront.
     *
     * @param leftFront The status of the left front tire.
     * @since SmartDeviceLink 2.0.0
     */
    public TireStatus setLeftFront(SingleTireStatus leftFront) {
        setValue(KEY_LEFT_FRONT, leftFront);
        return this;
    }

    /**
     * Gets the leftFront.
     *
     * @return SingleTireStatus The status of the left front tire.
     * @since SmartDeviceLink 2.0.0
     */
    public SingleTireStatus getLeftFront() {
        return (SingleTireStatus) getObject(SingleTireStatus.class, KEY_LEFT_FRONT);
    }

    /**
     * Sets the rightFront.
     *
     * @param rightFront The status of the right front tire.
     * @since SmartDeviceLink 2.0.0
     */
    public TireStatus setRightFront(SingleTireStatus rightFront) {
        setValue(KEY_RIGHT_FRONT, rightFront);
        return this;
    }

    /**
     * Gets the rightFront.
     *
     * @return SingleTireStatus The status of the right front tire.
     * @since SmartDeviceLink 2.0.0
     */
    public SingleTireStatus getRightFront() {
        return (SingleTireStatus) getObject(SingleTireStatus.class, KEY_RIGHT_FRONT);
    }

    /**
     * Sets the leftRear.
     *
     * @param leftRear The status of the left rear tire.
     * @since SmartDeviceLink 2.0.0
     */
    public TireStatus setLeftRear(SingleTireStatus leftRear) {
        setValue(KEY_LEFT_REAR, leftRear);
        return this;
    }

    /**
     * Gets the leftRear.
     *
     * @return SingleTireStatus The status of the left rear tire.
     * @since SmartDeviceLink 2.0.0
     */
    public SingleTireStatus getLeftRear() {
        return (SingleTireStatus) getObject(SingleTireStatus.class, KEY_LEFT_REAR);
    }

    /**
     * Sets the rightRear.
     *
     * @param rightRear The status of the right rear tire.
     * @since SmartDeviceLink 2.0.0
     */
    public TireStatus setRightRear(SingleTireStatus rightRear) {
        setValue(KEY_RIGHT_REAR, rightRear);
        return this;
    }

    /**
     * Gets the rightRear.
     *
     * @return SingleTireStatus The status of the right rear tire.
     * @since SmartDeviceLink 2.0.0
     */
    public SingleTireStatus getRightRear() {
        return (SingleTireStatus) getObject(SingleTireStatus.class, KEY_RIGHT_REAR);
    }

    /**
     * Sets the innerLeftRear.
     *
     * @param innerLeftRear The status of the inner left rear.
     * @since SmartDeviceLink 2.0.0
     */
    public TireStatus setInnerLeftRear(SingleTireStatus innerLeftRear) {
        setValue(KEY_INNER_LEFT_REAR, innerLeftRear);
        return this;
    }

    /**
     * Gets the innerLeftRear.
     *
     * @return SingleTireStatus The status of the inner left rear.
     * @since SmartDeviceLink 2.0.0
     */
    public SingleTireStatus getInnerLeftRear() {
        return (SingleTireStatus) getObject(SingleTireStatus.class, KEY_INNER_LEFT_REAR);
    }

    /**
     * Sets the innerRightRear.
     *
     * @param innerRightRear The status of the inner right rear.
     * @since SmartDeviceLink 2.0.0
     */
    public TireStatus setInnerRightRear(SingleTireStatus innerRightRear) {
        setValue(KEY_INNER_RIGHT_REAR, innerRightRear);
        return this;
    }

    /**
     * Gets the innerRightRear.
     *
     * @return SingleTireStatus The status of the inner right rear.
     * @since SmartDeviceLink 2.0.0
     */
    public SingleTireStatus getInnerRightRear() {
        return (SingleTireStatus) getObject(SingleTireStatus.class, KEY_INNER_RIGHT_REAR);
    }
}
