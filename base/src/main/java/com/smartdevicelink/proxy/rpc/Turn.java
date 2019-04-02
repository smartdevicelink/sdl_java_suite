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
 * Describes a navigation turn including an optional icon
 * 
 * <p><b>Parameter List</p>
 * <table border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>SmartDeviceLink Ver. Available</th>
 * </tr>
 * <tr>
 * <td>navigationText</td>
 * <td>String</td>
 * <td>Text to describe the turn (e.g. streetname)
 * <ul>
 * <li>Maxlength = 500</li>
 * </ul>
 * </td>
 * <td>SmartDeviceLink 2.0</td>
 * </tr>
 * <tr>
 * <td>turnIcon</td>
 * <td>Image</td>
 * <td>Image to be shown for a turn</td>
 * <td>SmartDeviceLink 2.0</td>
 * </tr>
 * </table>
 * 
 * @since SmartDeviceLink 2.0
 */
public class Turn extends RPCStruct{
    public static final String KEY_NAVIGATION_TEXT = "navigationText";
    public static final String KEY_TURN_IMAGE = "turnIcon";
    
    public Turn() { }
    public Turn(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * set the text to describe the turn (e.g. streetname)
     * 
     * @param navigationText
     *            the text to describe the turn (e.g. streetname)
     */
    public void setNavigationText(String navigationText){
        setValue(KEY_NAVIGATION_TEXT, navigationText);
    }

    /**
     * get the text to describe the turn (e.g. streetname)
     * 
     * @return the text to describe the turn (e.g. streetname)
     */
    public String getNavigationText(){
        return getString(KEY_NAVIGATION_TEXT);
    }

    /**
     * set Image to be shown for a turn
     * 
     * @param turnIcon
     *            the image to be shown for a turn
     */
    public void setTurnIcon(Image turnIcon){
        setValue(KEY_TURN_IMAGE, turnIcon);
    }

    /**
     * get the image to be shown for a turn
     * 
     * @return the image to be shown for a turn
     */
    @SuppressWarnings("unchecked")
    public Image getTurnIcon(){
        return (Image) getObject(Image.class, KEY_TURN_IMAGE);
    }

}
