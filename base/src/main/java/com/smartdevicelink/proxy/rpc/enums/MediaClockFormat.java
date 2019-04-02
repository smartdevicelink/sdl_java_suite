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
 * Indicates the format of the time displayed on the connected SDL unit.Format
 * description follows the following nomenclature:<p> Sp = Space</p> <p>| = or </p><p>c =
 * character</p>
 * 
 * @since SmartDeviceLink 1.0
 */
public enum MediaClockFormat {
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>maxHours = 19</li>
	 * <li>maxMinutes = 59</li>
	 * <li>maxSeconds = 59</li>
	 * </ul>
	 * 
	 * @since SmartDeviceLink 1.0
	 * 
	 */
	CLOCK1,
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>maxHours = 59</li>
	 * <li>maxMinutes = 59</li>
	 * <li>maxSeconds = 59</li>
	 * </ul>
	 * 
	 * @since SmartDeviceLink 1.0
	 * 
	 */    
	CLOCK2,
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>maxHours = 9</li>
	 * <li>maxMinutes = 59</li>
	 * <li>maxSeconds = 59</li>
	 * </ul>
	 * 
	 * @since SmartDeviceLink 2.0
	 * 
	 */
    CLOCK3,
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>5 characters possible</li>
	 * <li>Format: 1|sp c :|sp c c</li>
	 * <li>1|sp : digit "1" or space</li>
	 * <li>c : character out of following character set: sp|0-9|[letters, see
	 * TypeII column in XLS.</li>
	 * <li>:|sp : colon or space</li>
	 * <li>used for Type II headunit</li>
	 * </ul>
	 * 
	 * @since SmartDeviceLink 1.0
	 */    
    CLOCKTEXT1,
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>5 characters possible</li>
	 * <li>Format: 1|sp c :|sp c c</li>
	 * <li>1|sp : digit "1" or space</li>
	 * <li>c : character out of following character set: sp|0-9|[letters, see
	 * CID column in XLS.</li>
	 * <li>:|sp : colon or space</li>
	 * <li>used for CID headunit</li>
	 * </ul>
	 * difference between CLOCKTEXT1 and CLOCKTEXT2 is the supported character
	 * set
	 * 
	 * @since SmartDeviceLink 1.0
	 */    
    CLOCKTEXT2,
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>6 chars possible</li>
	 * <li>Format: 1|sp c c :|sp c c</li>
	 * <li>1|sp : digit "1" or space</li>
	 * <li>c : character out of following character set: sp|0-9|[letters, see
	 * Type 5 column in XLS].</li>
	 * <li>:|sp : colon or space</li>
	 * <li>used for Type V headunit</li>
	 * </ul>
	 * difference between CLOCKTEXT1 and CLOCKTEXT2 is the supported character
	 * set
	 * 
	 * 
	 * @since SmartDeviceLink 1.0
	 */    
    CLOCKTEXT3,
	/**
	 * <p>
	 * </p>
	 * <ul>
	 * <li>6 chars possible</li>
	 * <li>Format:      c   :|sp   c   c   :   c   c</li>
	 * <li>:|sp : colon or space</li>
	 * <li>c    : character out of following character set: sp|0-9|[letters]</li>
	 * <li>used for MFD3/4/5 headunits</li>
	 * </ul>
	 * 
	 * 
	 * @since SmartDeviceLink 2.0
	 */
    CLOCKTEXT4;

    public static MediaClockFormat valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
