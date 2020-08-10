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

import androidx.test.runner.AndroidJUnit4;

import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.test.TestValues;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ChoiceCellTests {

    private static final int MAX_ID = 2000000000;
    private SdlArtwork artwork = new SdlArtwork("image", FileType.GRAPHIC_PNG, 1, true);

    @Test
    public void testSettersAndGetters(){

        // set everything
        ChoiceCell choiceCell = new ChoiceCell(TestValues.GENERAL_STRING);
        choiceCell.setSecondaryText(TestValues.GENERAL_STRING);
        choiceCell.setTertiaryText(TestValues.GENERAL_STRING);
        choiceCell.setVoiceCommands(TestValues.GENERAL_STRING_LIST);
        choiceCell.setArtwork(artwork);
        choiceCell.setSecondaryArtwork(artwork);

        // use getters and assert equality
        assertEquals(choiceCell.getText(), TestValues.GENERAL_STRING);
        assertEquals(choiceCell.getSecondaryText(), TestValues.GENERAL_STRING);
        assertEquals(choiceCell.getTertiaryText(), TestValues.GENERAL_STRING);
        assertEquals(choiceCell.getVoiceCommands(), TestValues.GENERAL_STRING_LIST);
        assertEquals(choiceCell.getArtwork(), artwork);
        assertEquals(choiceCell.getSecondaryArtwork(), artwork);
        assertEquals(choiceCell.getChoiceId(), MAX_ID);
    }

    @Test
    public void testConstructors() {
        // first constructor was tested in previous method, use the rest here

        ChoiceCell choiceCell = new ChoiceCell(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING_LIST, artwork);
        choiceCell.setSecondaryText(TestValues.GENERAL_STRING);
        choiceCell.setTertiaryText(TestValues.GENERAL_STRING);
        choiceCell.setSecondaryArtwork(artwork);
        assertEquals(choiceCell.getText(), TestValues.GENERAL_STRING);
        assertEquals(choiceCell.getSecondaryText(), TestValues.GENERAL_STRING);
        assertEquals(choiceCell.getTertiaryText(), TestValues.GENERAL_STRING);
        assertEquals(choiceCell.getVoiceCommands(), TestValues.GENERAL_STRING_LIST);
        assertEquals(choiceCell.getArtwork(), artwork);
        assertEquals(choiceCell.getSecondaryArtwork(), artwork);
        assertEquals(choiceCell.getChoiceId(), MAX_ID);


        choiceCell = new ChoiceCell(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING, TestValues.GENERAL_STRING, TestValues.GENERAL_STRING_LIST, artwork, artwork);
        assertEquals(choiceCell.getText(), TestValues.GENERAL_STRING);
        assertEquals(choiceCell.getSecondaryText(), TestValues.GENERAL_STRING);
        assertEquals(choiceCell.getTertiaryText(), TestValues.GENERAL_STRING);
        assertEquals(choiceCell.getVoiceCommands(), TestValues.GENERAL_STRING_LIST);
        assertEquals(choiceCell.getArtwork(), artwork);
        assertEquals(choiceCell.getSecondaryArtwork(), artwork);
        assertEquals(choiceCell.getChoiceId(), MAX_ID);
    }

    @Test
    public void testCellEquality(){

        ChoiceCell choiceCell = new ChoiceCell(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING_LIST, artwork);
        choiceCell.setSecondaryText(TestValues.GENERAL_STRING);
        choiceCell.setTertiaryText(TestValues.GENERAL_STRING);
        choiceCell.setSecondaryArtwork(artwork);

        ChoiceCell choiceCell2 = new ChoiceCell(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING_LIST, artwork);
        choiceCell2.setSecondaryText(TestValues.GENERAL_STRING);
        choiceCell2.setTertiaryText(TestValues.GENERAL_STRING);
        choiceCell2.setSecondaryArtwork(artwork);

        ChoiceCell choiceCell3 = new ChoiceCell(TestValues.GENERAL_STRING, TestValues.GENERAL_STRING_LIST, artwork);
        choiceCell3.setSecondaryText(TestValues.GENERAL_STRING);
        choiceCell3.setTertiaryText(TestValues.GENERAL_STRING);

        // Make sure our overridden method works, even though these are different objects in memory
        assertTrue(choiceCell.equals(choiceCell2));
        assertFalse(choiceCell.equals(choiceCell3));

    }
}
