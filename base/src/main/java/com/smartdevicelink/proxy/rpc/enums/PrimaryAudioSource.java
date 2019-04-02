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

/**
 * Reflects the current primary audio source of SDL (if selected).
 * @since SmartDeviceLink 2.0
 */
public enum PrimaryAudioSource {
    /**
     * Currently no source selected
     */
    NO_SOURCE_SELECTED,
    /**
     * CD is current source
     */
    CD,
    /**
     * USB is current source
     */
    USB,
    /**
     * USB2 is current source
     */
    USB2,
    /**
     * Bluetooth Stereo is current source
     */
    BLUETOOTH_STEREO_BTST,
    /**
     * Line in is current source
     */
    LINE_IN,
    /**
     * iPod is current source
     */
    IPOD,
    /**
     * Mobile app is current source
     */
    MOBILE_APP,
    AM,
    FM,
    XM,
    DAB,
    ;

    /**
     * Convert String to PrimaryAudioSource
     * @param value String
     * @return PrimaryAudioSource
     */
    public static PrimaryAudioSource valueForString(String value) {
		try{
			return valueOf(value);
		}catch(Exception e){
			return null;
		}
    }
}
