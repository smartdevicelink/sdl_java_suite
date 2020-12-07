/*
 * Copyright (c) 2019 Livio, Inc.
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

package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.AppInfo;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.AppInfo}
 */
public class AppInfoTests extends TestCase {

    private AppInfo msg;

    @Override
    public void setUp() {

        msg = new AppInfo();
        msg.setAppDisplayName(TestValues.GENERAL_STRING);
        msg.setAppBundleID(TestValues.GENERAL_STRING);
        msg.setAppVersion(TestValues.GENERAL_STRING);
        msg.setAppIcon(TestValues.GENERAL_STRING);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        String appDisplayName = msg.getAppDisplayName();
        String appBundleID = msg.getAppBundleID();
        String appVersion = msg.getAppVersion();
        String appIcon = msg.getAppIcon();

        // Valid Tests
        assertEquals(TestValues.GENERAL_STRING, appDisplayName);
        assertEquals(TestValues.GENERAL_STRING, appBundleID);
        assertEquals(TestValues.GENERAL_STRING, appVersion);
        assertEquals(TestValues.GENERAL_STRING, appIcon);

        // Invalid/Null Tests
        AppInfo msg = new AppInfo();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getAppDisplayName());
        assertNull(TestValues.NULL, msg.getAppBundleID());
        assertNull(TestValues.NULL, msg.getAppVersion());
        assertNull(TestValues.NULL, msg.getAppIcon());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(AppInfo.KEY_APP_DISPLAY_NAME, TestValues.GENERAL_STRING);
            reference.put(AppInfo.KEY_APP_BUNDLE_ID, TestValues.GENERAL_STRING);
            reference.put(AppInfo.KEY_APP_VERSION, TestValues.GENERAL_STRING);
            reference.put(AppInfo.KEY_APP_ICON, TestValues.GENERAL_STRING);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }

}
