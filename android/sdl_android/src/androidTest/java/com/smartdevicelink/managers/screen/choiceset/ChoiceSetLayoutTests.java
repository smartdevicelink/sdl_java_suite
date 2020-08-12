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

package com.smartdevicelink.managers.screen.choiceset;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

@RunWith(AndroidJUnit4.class)
public class ChoiceSetLayoutTests {
    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    @Test
    public void testValidEnums() {
        ChoiceSetLayout choiceSetLayoutList = ChoiceSetLayout.valueForString("CHOICE_SET_LAYOUT_LIST");
        ChoiceSetLayout choiceSetLayoutTiles = ChoiceSetLayout.valueForString("CHOICE_SET_LAYOUT_TILES");
        assertNotNull("CHOICE_SET_LAYOUT_LIST returned null", choiceSetLayoutList);
        assertNotNull("choiceSetLayoutTiles returned null", choiceSetLayoutTiles);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    @Test
    public void testInvalidEnum() {
        String example = "deFaUlt";
        try {
            ChoiceSetLayout temp = ChoiceSetLayout.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    /**
     * Verifies that a null assignment is invalid.
     */
    @Test
    public void testNullEnum() {
        String example = null;
        try {
            ChoiceSetLayout temp = ChoiceSetLayout.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of DynamicMenuUpdatesMode.
     */
    @Test
    public void testListEnum() {
        List<ChoiceSetLayout> enumValueList = Arrays.asList(ChoiceSetLayout.values());

        List<ChoiceSetLayout> enumTestList = new ArrayList<>();
        enumTestList.add(ChoiceSetLayout.CHOICE_SET_LAYOUT_LIST);
        enumTestList.add(ChoiceSetLayout.CHOICE_SET_LAYOUT_TILES);


        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}
