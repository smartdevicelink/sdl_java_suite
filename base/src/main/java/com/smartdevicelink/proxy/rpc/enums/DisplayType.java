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
/** The type of the display.
 * 
 * 
 * @since SmartDeviceLink 1.0
 *
 */

public enum DisplayType {
	/**
	* Center Information Display.
	*This display type provides a 2-line x 20 character "dot matrix" display.
	*
	*/

    CID("CID"),
    /** TYPE II display.
    1 line older radio head unit. */

    TYPE2("TYPE2"),
    /**
    * TYPE V display
    Old radio head unit.

    */

    TYPE5("TYPE5"),
    /**
     * Next Generation Navigation display.    
     */

    NGN("NGN"),
    /**
     * GEN-2, 8 inch display.
     */

    GEN2_8_DMA("GEN2_8_DMA"),
    /**
     * GEN-2, 6 inch display.
     */

    GEN2_6_DMA("GEN2_6_DMA"),
    /**
     * 3 inch GEN1.1 display.    
     */

    MFD3("MFD3"),
    /**
     * 4 inch GEN1.1 display    
     */

    MFD4("MFD4"),
    /**
     * 5 inch GEN1.1 display.    
     */

    MFD5("MFD5"),
    /**
     * GEN-3, 8 inch display.    
     */

    GEN3_8_INCH("GEN3_8-INCH"),
    
    /**
     * SDL_GENERIC display type. Used for most SDL integrations.
     */
    SDL_GENERIC("SDL_GENERIC"),
    
    ;

    private final String VALUE;

    private DisplayType(String value) {
        this.VALUE = value;
    }

    public static DisplayType valueForString(String value) {
        if(value == null){
            return null;
        }
        
        for (DisplayType type : DisplayType.values()) {
            if (type.toString().equals(value)) {
                return type;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        return VALUE;
    }
}
