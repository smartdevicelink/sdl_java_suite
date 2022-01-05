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

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * A color class that stores RGB values
 * <p><b> Parameter List</b></p>
 * <table border="1" rules="all">
 *         <tr>
 *             <th>Name</th>
 *             <th>Type</th>
 *             <th>Reg.</th>
 *             <th>Notes</th>
 *             <th>Version Available</th>
 *         </tr>
 *         <tr>
 *             <td>red</td>
 *             <td>Integer</td>
 *             <td>Y</td>
 *             <td><ul><li>minvalue="0"</li><li>maxvalue="255"</li></ul></td>
 *             <td>SmartDeviceLink 5.0</td>
 *         </tr>
 *         <tr>
 *             <td>green</td>
 *             <td>Integer</td>
 *             <td>Y</td>
 *             <td><ul><li>minvalue="0"</li><li>maxvalue="255"</li></ul></td>
 *             <td>SmartDeviceLink 5.0</td>
 *         </tr>
 *         <tr>
 *             <td>blue</td>
 *             <td>Integer</td>
 *             <td>Y</td>
 *             <td><ul><li>minvalue="0"</li><li>maxvalue="255"</li></ul></td>
 *             <td>SmartDeviceLink 5.0</td>
 *         </tr>
 * </table>
 *
 * @since SmartDeviceLink 5.0
 */
public class RGBColor extends RPCStruct {
    public static final String KEY_RED = "red";
    public static final String KEY_GREEN = "green";
    public static final String KEY_BLUE = "blue";
    private static final Integer MIN_VALUE = 0, MAX_VALUE = 255;

    /**
     * Constructs a new RGBColor object
     */
    public RGBColor() {
        this(MIN_VALUE, MIN_VALUE, MIN_VALUE);
    }

    /**
     * Constructs a new RGBColor object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public RGBColor(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * Constructs a new RGB object
     *
     * @param red   red value - min: 0; max: 255
     * @param green green value - min: 0; max: 255
     * @param blue  blue value - min: 0; max: 255
     */
    public RGBColor(Integer red, Integer green, Integer blue) {
        Hashtable<String, Object> hash = new Hashtable<>();
        if (red != null && red >= MIN_VALUE && red <= MAX_VALUE) {
            hash.put(KEY_RED, red);
        } else {
            hash.put(KEY_RED, MIN_VALUE);
        }
        if (green != null && green >= MIN_VALUE && green <= MAX_VALUE) {
            hash.put(KEY_GREEN, green);
        } else {
            hash.put(KEY_GREEN, MIN_VALUE);
        }
        if (blue != null && blue >= MIN_VALUE && blue <= MAX_VALUE) {
            hash.put(KEY_BLUE, blue);
        } else {
            hash.put(KEY_BLUE, MIN_VALUE);
        }
        this.store = hash;
    }

    /**
     * Sets the red value of the color object
     *
     * @param color red value - min: 0; max: 255
     */
    public RGBColor setRed(Integer color) {
        if (color != null && color >= MIN_VALUE && color <= MAX_VALUE) {
            setValue(KEY_RED, color);
        }
        return this;
    }

    /**
     * Gets the red value of the color
     *
     * @return red value
     */
    public Integer getRed() {
        return getInteger(KEY_RED);
    }

    /**
     * Sets the green value of the color object
     *
     * @param color green value - min: 0; max: 255
     */
    public RGBColor setGreen(Integer color) {
        if (color != null && color >= MIN_VALUE && color <= MAX_VALUE) {
            setValue(KEY_GREEN, color);
        }
        return this;
    }

    /**
     * Gets the green value of the color
     *
     * @return green value
     */
    public Integer getGreen() {
        return getInteger(KEY_GREEN);
    }

    /**
     * Sets the blue value of the color object
     *
     * @param color blue value - min: 0; max: 255
     */
    public RGBColor setBlue(Integer color) {
        if (color != null && color >= MIN_VALUE && color <= MAX_VALUE) {
            setValue(KEY_BLUE, color);
        }
        return this;
    }

    /**
     * Gets the green value of the color
     *
     * @return green value
     */
    public Integer getBlue() {
        return getInteger(KEY_BLUE);
    }
}
