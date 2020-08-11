/*
 * Copyright (c) 2019, Livio, Inc.
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
package com.smartdevicelink.managers.screen;

import androidx.annotation.NonNull;

import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.util.DebugTool;

/**
 * <strong>SoftButtonState</strong> <br>
 * Defines an individual state for SoftButtonObject.<br>
 * The states of SoftButtonObject allow the developer to not have to manage multiple SoftButtons that have very similar functionality.<br>
 * For example, a repeat button in a music app can be thought of as one SoftButtonObject with three typical states: repeat off, repeat 1, and repeat on.<br>
 * @see SoftButtonObject
 */
public class SoftButtonState {
    private static final String TAG = "SoftButtonState";
    private String name;
    private SdlArtwork artwork;
    private final SoftButton softButton;

    /**
     * Creates a new instance of SoftButtonState
     * Note: state names should be different for each SoftButtonObject
     * @param name a String value represents name of the state
     * @param text a String represents the text for the state
     * @param artwork an SdlArtwork represents the artwork for the state
     */
    public SoftButtonState(@NonNull String name, String text, SdlArtwork artwork) {
        if (text == null && artwork == null) {
            DebugTool.logError(TAG, "Attempted to create an invalid soft button state: text and artwork are both null");
            softButton = null;
            return;
        }
        this.name = name;
        this.artwork = artwork;


        // Create a SoftButton and set its Type
        SoftButtonType type;
        if (artwork != null && text != null) {
            type = SoftButtonType.SBT_BOTH;
        } else if (artwork != null) {
            type = SoftButtonType.SBT_IMAGE;
        } else {
            type = SoftButtonType.SBT_TEXT;
        }
        this.softButton = new SoftButton(type, SoftButtonObject.SOFT_BUTTON_ID_NOT_SET_VALUE);


        // Set the SoftButton's image
        if (artwork != null) {
            this.softButton.setImage(artwork.getImageRPC());
        }

        // Set the SoftButton's text
        if (text != null) {
            this.softButton.setText(text);
        }

        this.softButton.setSystemAction(SystemAction.DEFAULT_ACTION);
    }

    /**
     * Get the state name
     * @return a String value represents the name of the state
     */
    public String getName() {
        return name;
    }

    /**
     * Set the state name
     * @param name a String value represents the name of the state
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Get whether or not the button should be highlighted on the UI
     * @return boolean representing whether or not the button should be highlighted
     */
    public boolean getHighlighted(){
        return this.getSoftButton().getIsHighlighted();
    }

    /**
     * Set whether or not the button should be highlighted on the UI
     * @param highlighted boolean representing whether or not the button should be highlighted
     */
    public void setHighlighted(boolean highlighted){
        this.getSoftButton().setIsHighlighted(highlighted);
    }

    /**
     * Get whether selecting a SoftButton shall call a specific system action
     * See {@link SystemAction}
     * @return SystemAction value representing whether selecting a SoftButton shall call a specific action
     */
    public SystemAction getSystemAction(){
        return this.getSoftButton().getSystemAction();
    }

    /**
     * Set whether selecting a SoftButton shall call a specific system action
     * See {@link SystemAction}
     * @param systemAction SystemAction value representing whether selecting a SoftButton shall call a specific action
     */
    public void setSystemAction(SystemAction systemAction){
        this.getSoftButton().setSystemAction(systemAction);
    }

    /**
     * Get the SoftButton for the state
     * @return a SoftButton object represents the SoftButton for the state
     */
    public SoftButton getSoftButton() {
        return softButton;
    }

    /**
     * Get the Artwork for the state
     * @return an SdlArtwork object represents the artwork for the state
     */
    public SdlArtwork getArtwork() {
        return artwork;
    }

    /**
     * Used to compile hashcode for SoftButtonState for use to compare in equals method
     * @return Custom hashcode of SoftButtonState variables
     */
    @Override
    public int hashCode() {
        int result = 1;
        result += ((getName() == null) ? 0 : Integer.rotateLeft(getName().hashCode(), 1));
        result += ((getArtwork() == null) ? 0 : Integer.rotateLeft(getArtwork().hashCode(),2));
        return result;
    }

    /**
     * Uses our custom hashCode for SoftButtonState objects
     * @param o - The object to compare
     * @return boolean of whether the objects are the same or not
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        // if this is the same memory address, it's the same
        if (this == o) return true;
        // if this is not an instance of SoftButtonState, not the same
        if (!(o instanceof SoftButtonState)) return false;
        // return comparison
        return hashCode() == o.hashCode();
    }
}
