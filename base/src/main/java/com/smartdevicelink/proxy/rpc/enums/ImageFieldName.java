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
 * The name that identifies the field.
 * 
 * @since SmartDeviceLink 2.3.2
 */
public enum ImageFieldName {
	/** The image field for SoftButton
	 * 
	 */

	softButtonImage,
	/** The first image field for Choice.
	 * 
	 */

	choiceImage,
	/** The secondary image field for Choice.
	 * 
	 */

	choiceSecondaryImage,
	/** The image field for vrHelpItem.
	 * 
	 */

	vrHelpItem,
	/** The image field for Turn.
	 * 
	 */

	turnIcon,
	/** The image field for the menu icon in SetGlobalProperties.
	 * 
	 */

	menuIcon,
	/** The image filed for AddCommand.
	 * 
	 */

	cmdIcon,
	/** The image field for the app icon ( set by setAppIcon).
	 * 
	 */

	appIcon,
	/** The image filed for Show.
	 * 
	 */

	graphic,
	/** The primary image field for ShowConstant TBT.
	 * 
	 */

	showConstantTBTIcon,

	/** The secondary image field for ShowConstant TBT.
	 * 
	 */
	showConstantTBTNextTurnIcon,
	/**
     * The optional image of a destination / location
     * 
     * @since SmartDeviceLink 4.0
     */
	locationImage,
	/**
	 * The secondary graphic image field
	 *
	 * @since SmartDeviceLink 5.0
	 */
	secondaryGraphic,
	/**
	 * The image field for Alert
	 *
	 * @since SmartDeviceLink 6.0.0
	 */
	alertIcon,
	/**
	 * The image of the subtle alert; applies to `SubtleAlert` `alertIcon`
	 *
	 * @since SmartDeviceLink 7.0.0
	 */
	subtleAlertIcon
	;
    
	/**
     * Convert String to ImageFieldName
     * @param value String
     * @return ImageFieldName
     */  
	public static ImageFieldName valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
