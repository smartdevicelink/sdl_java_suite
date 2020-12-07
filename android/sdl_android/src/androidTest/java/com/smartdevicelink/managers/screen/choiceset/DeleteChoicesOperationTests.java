/*
 * Copyright (c)  2019 Livio, Inc.
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
 *
 * Created by brettywhite on 6/12/19 1:52 PM
 *
 */

package com.smartdevicelink.managers.screen.choiceset;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.mock;

@RunWith(AndroidJUnit4.class)
public class DeleteChoicesOperationTests {

    private DeleteChoicesOperation deleteChoicesOperation;

    @Before
    public void setUp() throws Exception {

        ChoiceCell cell1 = new ChoiceCell("cell 1");
        ChoiceCell cell2 = new ChoiceCell("cell 2");
        HashSet<ChoiceCell> cellsToDelete = new HashSet<>();
        cellsToDelete.add(cell1);
        cellsToDelete.add(cell2);

        ISdl internalInterface = mock(ISdl.class);
        deleteChoicesOperation = new DeleteChoicesOperation(internalInterface, cellsToDelete, null);
    }


    @Test
    public void testCreateListDeleteInteractionSets() {
        List<DeleteInteractionChoiceSet> deletes = deleteChoicesOperation.createDeleteSets();
        assertNotNull(deletes);
        assertEquals(deletes.size(), 2);
        for (DeleteInteractionChoiceSet delete : deletes) {
            assertNotNull(delete);
        }
    }

}
