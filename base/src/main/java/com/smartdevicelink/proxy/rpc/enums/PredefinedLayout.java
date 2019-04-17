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

import java.util.EnumSet;

/**
 A template layout an app uses to display information. The broad details of the layout are defined, but the details depend on the IVI system. Used in SetDisplayLayout.
 */
public enum PredefinedLayout {

	/**
	 A default layout
	 */
	DEFAULT("DEFAULT"),

	/**
	 The default media layout
	 */
	MEDIA("MEDIA"),

	/**
	 The default non-media layout
	 */
	NON_MEDIA("NON-MEDIA"),

	/**
	 A media layout containing preset buttons
	 */
	ONSCREEN_PRESETS("ONSCREEN_PRESETS"),

	/**
	 The default navigation layout with a fullscreen map
	 */
	NAV_FULLSCREEN_MAP("NAV_FULLSCREEN_MAP"),

	/**
	 A list layout used for navigation apps
	 */
	NAV_LIST("NAV_LIST"),

	/**
	 A keyboard layout used for navigation apps
	 */
	NAV_KEYBOARD("NAV_KEYBOARD"),

	/**
	 A layout with a single graphic on the left and text on the right
	 */
	GRAPHIC_WITH_TEXT("GRAPHIC_WITH_TEXT"),

	/**
	 A layout with text on the left and a single graphic on the right
	 */
	TEXT_WITH_GRAPHIC("TEXT_WITH_GRAPHIC"),

	/**
	 A layout with only softbuttons placed in a tile layout
	 */
	TILES_ONLY("TILES_ONLY"),

	/**
	 A layout with only soft buttons that only accept text
	 */
	TEXTBUTTONS_ONLY("TEXTBUTTONS_ONLY"),

	/**
	 A layout with a single graphic on the left and soft buttons in a tile layout on the right
	 */
	GRAPHIC_WITH_TILES("GRAPHIC_WITH_TILES"),

	/**
	 A layout with soft buttons in a tile layout on the left and a single graphic on the right
	 */
	TILES_WITH_GRAPHIC("TILES_WITH_GRAPHIC"),

	/**
	 A layout with a single graphic on the left and both text and soft buttons on the right
	 */
	GRAPHIC_WITH_TEXT_AND_SOFTBUTTONS("GRAPHIC_WITH_TEXT_AND_SOFTBUTTONS"),

	/**
	 A layout with both text and soft buttons on the left and a single graphic on the right
	 */
	TEXT_AND_SOFTBUTTONS_WITH_GRAPHIC("TEXT_AND_SOFTBUTTONS_WITH_GRAPHIC"),

	/**
	 A layout with a single graphic on the left and text-only soft buttons on the right
	 */
	GRAPHIC_WITH_TEXTBUTTONS("GRAPHIC_WITH_TEXTBUTTONS"),

	/**
	 A layout with text-only soft buttons on the left and a single graphic on the right
	 */
	TEXTBUTTONS_WITH_GRAPHIC("TEXTBUTTONS_WITH_GRAPHIC"),

	/**
	 A layout with a single large graphic and soft buttons
	 */
	LARGE_GRAPHIC_WITH_SOFTBUTTONS("LARGE_GRAPHIC_WITH_SOFTBUTTONS"),

	/**
	 A layout with two graphics and soft buttons
	 */
	DOUBLE_GRAPHIC_WITH_SOFTBUTTONS("DOUBLE_GRAPHIC_WITH_SOFTBUTTONS"),

	/**
	 A layout with only a single large graphic
	 */
	LARGE_GRAPHIC_ONLY("LARGE_GRAPHIC_ONLY"),
	;

	private final String INTERNAL_NAME;

	private PredefinedLayout(String internalName) {
		this.INTERNAL_NAME = internalName;
	}
	/**
	 * Returns a String representing a PredefinedLayout
	 */
	public String toString() {
		return this.INTERNAL_NAME;
	}

	/**
	 * Returns a PredefinedLayout
	 * @param value a String
	 * @return PredefinedLayout
	 */
	public static PredefinedLayout valueForString(String value) {
		if(value == null){
			return null;
		}

		for (PredefinedLayout anEnum : EnumSet.allOf(PredefinedLayout.class)) {
			if (anEnum.toString().equals(value)) {
				return anEnum;
			}
		}
		return null;
	}
}
