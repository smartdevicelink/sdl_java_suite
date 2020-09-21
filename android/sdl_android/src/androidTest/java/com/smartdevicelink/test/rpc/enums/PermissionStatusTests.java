/*
 * Copyright (c) 2020 Livio, Inc.
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

package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.PermissionStatus;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.PermissionStatus}
 */
public class PermissionStatusTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "ALLOWED";
        PermissionStatus enumAllowed = PermissionStatus.valueForString(example);
        example = "DISALLOWED";
        PermissionStatus enumDisallowed = PermissionStatus.valueForString(example);
        example = "USER_DISALLOWED";
        PermissionStatus enumUserDisallowed = PermissionStatus.valueForString(example);
        example = "USER_CONSENT_PENDING";
        PermissionStatus enumUserConsentPending = PermissionStatus.valueForString(example);

        assertNotNull("ALLOWED returned null", enumAllowed);
        assertNotNull("DISALLOWED returned null", enumDisallowed);
        assertNotNull("USER_DISALLOWED returned null", enumUserDisallowed);
        assertNotNull("USER_CONSENT_PENDING returned null", enumUserConsentPending);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "DISALLOwed";
        try {
            PermissionStatus temp = PermissionStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    /**
     * Verifies that a null assignment is invalid.
     */
    public void testNullEnum() {
        String example = null;
        try {
            PermissionStatus temp = PermissionStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of PermissionStatus.
     */
    public void testListEnum() {
        List<PermissionStatus> enumValueList = Arrays.asList(PermissionStatus.values());

        List<PermissionStatus> enumTestList = new ArrayList<>();
        enumTestList.add(PermissionStatus.ALLOWED);
        enumTestList.add(PermissionStatus.DISALLOWED);
        enumTestList.add(PermissionStatus.USER_DISALLOWED);
        enumTestList.add(PermissionStatus.USER_CONSENT_PENDING);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}