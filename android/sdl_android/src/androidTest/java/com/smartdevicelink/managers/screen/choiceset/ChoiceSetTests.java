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

import com.smartdevicelink.test.TestValues;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class ChoiceSetTests {

    private ChoiceSetSelectionListener listener;
    private ChoiceSetLayout layout;
    private List<ChoiceCell> choices;
    private Integer defaultTimeout;
    private Boolean canceledHandlerCalled;

    @Before
    public void setUp() throws Exception {

        listener = mock(ChoiceSetSelectionListener.class);
        layout = ChoiceSetLayout.CHOICE_SET_LAYOUT_LIST;
        defaultTimeout = 10;
        choices = Arrays.asList(new ChoiceCell(TestValues.GENERAL_STRING), new ChoiceCell(TestValues.GENERAL_STRING));
        canceledHandlerCalled = false;
    }


    @Test
    public void testSettersAndGetters() {

        // test small constructor
        ChoiceSet choiceSet = new ChoiceSet(TestValues.GENERAL_STRING, choices, listener);

        // use getters and assert equality
        assertEquals(choiceSet.getTitle(), TestValues.GENERAL_STRING);
        assertEquals(choiceSet.getLayout(), layout);
        assertEquals(choiceSet.getTimeout(), defaultTimeout);
        assertEquals(choiceSet.getChoices(), choices);
        assertEquals(choiceSet.getChoiceSetSelectionListener(), listener);
    }

    @Test
    public void testConstructors() {

        // first constructor was tested in previous method, use the rest here
        ChoiceSet choiceSet = new ChoiceSet(TestValues.GENERAL_STRING, layout, TestValues.GENERAL_INTEGER, TestValues.GENERAL_STRING, TestValues.GENERAL_STRING, TestValues.GENERAL_STRING, TestValues.GENERAL_VRHELPITEM_LIST, TestValues.GENERAL_KEYBOARDPROPERTIES, choices, listener);
        assertEquals(choiceSet.getTitle(), TestValues.GENERAL_STRING);
        assertEquals(choiceSet.getInitialPrompt().get(0).getText(), TestValues.GENERAL_STRING);
        assertEquals(choiceSet.getHelpPrompt().get(0).getText(), TestValues.GENERAL_STRING);
        assertEquals(choiceSet.getTimeoutPrompt().get(0).getText(), TestValues.GENERAL_STRING);
        assertEquals(choiceSet.getLayout(), layout);
        assertEquals(choiceSet.getTimeout(), TestValues.GENERAL_INTEGER);
        assertEquals(choiceSet.getChoices(), choices);
        assertEquals(choiceSet.getChoiceSetSelectionListener(), listener);

        ChoiceSet choiceSet2 = new ChoiceSet(TestValues.GENERAL_STRING, layout, TestValues.GENERAL_INTEGER, TestValues.GENERAL_TTSCHUNK_LIST, TestValues.GENERAL_TTSCHUNK_LIST, TestValues.GENERAL_TTSCHUNK_LIST, TestValues.GENERAL_VRHELPITEM_LIST, TestValues.GENERAL_KEYBOARDPROPERTIES, choices, listener);
        assertEquals(choiceSet2.getTitle(), TestValues.GENERAL_STRING);
        assertEquals(choiceSet2.getInitialPrompt(), TestValues.GENERAL_TTSCHUNK_LIST);
        assertEquals(choiceSet2.getHelpPrompt(), TestValues.GENERAL_TTSCHUNK_LIST);
        assertEquals(choiceSet2.getTimeoutPrompt(), TestValues.GENERAL_TTSCHUNK_LIST);
        assertEquals(choiceSet2.getLayout(), layout);
        assertEquals(choiceSet2.getTimeout(), TestValues.GENERAL_INTEGER);
        assertEquals(choiceSet2.getChoices(), choices);
        assertEquals(choiceSet2.getChoiceSetSelectionListener(), listener);
    }

    @Test
    public void testCancelingChoiceSet() {
        ChoiceSet choiceSet = new ChoiceSet(TestValues.GENERAL_STRING, choices, listener);

        choiceSet.canceledListener = new ChoiceSetCanceledListener() {
            @Override
            public void onChoiceSetCanceled() {
                canceledHandlerCalled = true;
            }
        };

        choiceSet.cancel();
        assertTrue(canceledHandlerCalled);
    }
}
