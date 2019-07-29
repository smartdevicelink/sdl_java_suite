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

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.test.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;

public class ChoiceSetTests extends AndroidTestCase2 {

    private ChoiceSetSelectionListener listener;
    private ChoiceSetLayout layout;
    private List<ChoiceCell> choices;
    private Integer defaultTimeout;

    @Override
    public void setUp() throws Exception{
        super.setUp();

        listener = mock(ChoiceSetSelectionListener.class);
        layout = ChoiceSetLayout.CHOICE_SET_LAYOUT_LIST;
        defaultTimeout = 10;
        choices = Arrays.asList(new ChoiceCell(Test.GENERAL_STRING), new ChoiceCell(Test.GENERAL_STRING));
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testSettersAndGetters(){

        // test small constructor
        ChoiceSet choiceSet = new ChoiceSet(Test.GENERAL_STRING, choices, listener);

        // use getters and assert equality
        assertEquals(choiceSet.getTitle(), Test.GENERAL_STRING);
        assertEquals(choiceSet.getLayout(), layout);
        assertEquals(choiceSet.getTimeout(), defaultTimeout);
        assertEquals(choiceSet.getChoices(), choices);
        assertEquals(choiceSet.getChoiceSetSelectionListener(), listener);
    }

    public void testConstructors() {

        // first constructor was tested in previous method, use the rest here
        ChoiceSet choiceSet = new ChoiceSet(Test.GENERAL_STRING, layout, Test.GENERAL_INTEGER, Test.GENERAL_STRING, Test.GENERAL_STRING, Test.GENERAL_STRING, Test.GENERAL_VRHELPITEM_LIST, Test.GENERAL_KEYBOARDPROPERTIES, choices, listener);
        assertEquals(choiceSet.getTitle(), Test.GENERAL_STRING);
        assertEquals(choiceSet.getInitialPrompt().get(0).getText(),Test.GENERAL_STRING);
        assertEquals(choiceSet.getHelpPrompt().get(0).getText(), Test.GENERAL_STRING);
        assertEquals(choiceSet.getTimeoutPrompt().get(0).getText(), Test.GENERAL_STRING);
        assertEquals(choiceSet.getLayout(), layout);
        assertEquals(choiceSet.getTimeout(), Test.GENERAL_INTEGER);
        assertEquals(choiceSet.getChoices(), choices);
        assertEquals(choiceSet.getChoiceSetSelectionListener(), listener);

        ChoiceSet choiceSet2 = new ChoiceSet(Test.GENERAL_STRING, layout, Test.GENERAL_INTEGER, Test.GENERAL_TTSCHUNK_LIST, Test.GENERAL_TTSCHUNK_LIST, Test.GENERAL_TTSCHUNK_LIST, Test.GENERAL_VRHELPITEM_LIST, Test.GENERAL_KEYBOARDPROPERTIES, choices, listener);
        assertEquals(choiceSet2.getTitle(), Test.GENERAL_STRING);
        assertEquals(choiceSet2.getInitialPrompt(),Test.GENERAL_TTSCHUNK_LIST);
        assertEquals(choiceSet2.getHelpPrompt(), Test.GENERAL_TTSCHUNK_LIST);
        assertEquals(choiceSet2.getTimeoutPrompt(), Test.GENERAL_TTSCHUNK_LIST);
        assertEquals(choiceSet2.getLayout(), layout);
        assertEquals(choiceSet2.getTimeout(), Test.GENERAL_INTEGER);
        assertEquals(choiceSet2.getChoices(), choices);
        assertEquals(choiceSet2.getChoiceSetSelectionListener(), listener);
    }
}
