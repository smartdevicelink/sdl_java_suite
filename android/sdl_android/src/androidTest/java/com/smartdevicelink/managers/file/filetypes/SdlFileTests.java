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

package com.smartdevicelink.managers.file.filetypes;

import com.smartdevicelink.AndroidTestCase2;
import com.smartdevicelink.test.Test;

public class SdlFileTests extends AndroidTestCase2 {

    public void testConstructorWithNoParams() {
        SdlFile sdlFile;

        // Case 1 (Setting data)
        sdlFile = new SdlFile();
        sdlFile.setFileData(Test.GENERAL_BYTE_ARRAY);
        assertEquals(sdlFile.getFileData(), Test.GENERAL_BYTE_ARRAY);
        sdlFile.setName(null);
        assertEquals(sdlFile.getName(), "e9800998ecf8427e");
        sdlFile.setName(Test.GENERAL_STRING);
        assertEquals(sdlFile.getName(), Test.GENERAL_STRING);
        sdlFile.setType(Test.GENERAL_FILETYPE);
        assertEquals(sdlFile.getType(), Test.GENERAL_FILETYPE);
        sdlFile.setPersistent(Test.GENERAL_BOOLEAN);
        assertEquals(sdlFile.isPersistent(), Test.GENERAL_BOOLEAN);

        // Case 2 (Setting resourceId)
        sdlFile = new SdlFile();
        sdlFile.setResourceId(Test.GENERAL_INTEGER);
        assertEquals((Integer) sdlFile.getResourceId(), Test.GENERAL_INTEGER);
        sdlFile.setName(null);
        assertEquals(sdlFile.getName(), "ec9ebc78777cf40d");
        sdlFile.setName(Test.GENERAL_STRING);
        assertEquals(sdlFile.getName(), Test.GENERAL_STRING);
        sdlFile.setType(Test.GENERAL_FILETYPE);
        assertEquals(sdlFile.getType(), Test.GENERAL_FILETYPE);
        sdlFile.setPersistent(Test.GENERAL_BOOLEAN);
        assertEquals(sdlFile.isPersistent(), Test.GENERAL_BOOLEAN);

        // Case 3 (Setting URI)
        sdlFile = new SdlFile();
        sdlFile.setUri(Test.GENERAL_URI);
        assertEquals(sdlFile.getUri(), Test.GENERAL_URI);
        sdlFile.setName(null);
        assertEquals(sdlFile.getName(), "d3467db131372140");
        sdlFile.setName(Test.GENERAL_STRING);
        assertEquals(sdlFile.getName(), Test.GENERAL_STRING);
        sdlFile.setType(Test.GENERAL_FILETYPE);
        assertEquals(sdlFile.getType(), Test.GENERAL_FILETYPE);
        sdlFile.setPersistent(Test.GENERAL_BOOLEAN);
        assertEquals(sdlFile.isPersistent(), Test.GENERAL_BOOLEAN);
    }

    public void testConstructorWithResourceId() {
        // Case1 (Set the name manually)
        SdlFile sdlFile1 = new SdlFile(Test.GENERAL_STRING, Test.GENERAL_FILETYPE, Test.GENERAL_INTEGER, Test.GENERAL_BOOLEAN);
        assertEquals(sdlFile1.getName(), Test.GENERAL_STRING);
        assertEquals(sdlFile1.getType(), Test.GENERAL_FILETYPE);
        assertEquals((Integer) sdlFile1.getResourceId(), Test.GENERAL_INTEGER);
        assertEquals(sdlFile1.isPersistent(), Test.GENERAL_BOOLEAN);

        // Case2 (Let the library generate a name)
        SdlFile sdlFile2 = new SdlFile(null, Test.GENERAL_FILETYPE, Test.GENERAL_INTEGER, Test.GENERAL_BOOLEAN);
        SdlFile sdlFile3 = new SdlFile(null, Test.GENERAL_FILETYPE, Test.GENERAL_INTEGER, Test.GENERAL_BOOLEAN);
        assertEquals(sdlFile2.getName(), sdlFile3.getName());
        assertEquals(sdlFile2.getName(), "ec9ebc78777cf40d");
        assertEquals(sdlFile2.getType(), Test.GENERAL_FILETYPE);
        assertEquals((Integer) sdlFile2.getResourceId(), Test.GENERAL_INTEGER);
        assertEquals(sdlFile2.isPersistent(), Test.GENERAL_BOOLEAN);
    }

    public void testConstructorWithData() {
        // Case1 (Set the name manually)
        SdlFile sdlFile1 = new SdlFile(Test.GENERAL_STRING, Test.GENERAL_FILETYPE, Test.GENERAL_BYTE_ARRAY, Test.GENERAL_BOOLEAN);
        assertEquals(sdlFile1.getName(), Test.GENERAL_STRING);
        assertEquals(sdlFile1.getType(), Test.GENERAL_FILETYPE);
        assertEquals(sdlFile1.getFileData(), Test.GENERAL_BYTE_ARRAY);
        assertEquals(sdlFile1.isPersistent(), Test.GENERAL_BOOLEAN);

        // Case2 (Let the library generate a name)
        SdlFile sdlFile2 = new SdlFile(null, Test.GENERAL_FILETYPE, Test.GENERAL_BYTE_ARRAY, Test.GENERAL_BOOLEAN);
        SdlFile sdlFile3 = new SdlFile(null, Test.GENERAL_FILETYPE, Test.GENERAL_BYTE_ARRAY, Test.GENERAL_BOOLEAN);
        assertEquals(sdlFile2.getName(), sdlFile3.getName());
        assertEquals(sdlFile2.getName(), "e9800998ecf8427e");
        assertEquals(sdlFile2.getType(), Test.GENERAL_FILETYPE);
        assertEquals(sdlFile2.getFileData(), Test.GENERAL_BYTE_ARRAY);
        assertEquals(sdlFile2.isPersistent(), Test.GENERAL_BOOLEAN);
    }

    public void testConstructorWithUri() {
        // Case1 (Set the name manually)
        SdlFile sdlFile1 = new SdlFile(Test.GENERAL_STRING, Test.GENERAL_FILETYPE, Test.GENERAL_URI, Test.GENERAL_BOOLEAN);
        assertEquals(sdlFile1.getName(), Test.GENERAL_STRING);
        assertEquals(sdlFile1.getType(), Test.GENERAL_FILETYPE);
        assertEquals(sdlFile1.getUri(), Test.GENERAL_URI);
        assertEquals(sdlFile1.isPersistent(), Test.GENERAL_BOOLEAN);

        // Case2 (Let the library generate a name)
        SdlFile sdlFile2 = new SdlFile(null, Test.GENERAL_FILETYPE, Test.GENERAL_URI, Test.GENERAL_BOOLEAN);
        SdlFile sdlFile3 = new SdlFile(null, Test.GENERAL_FILETYPE, Test.GENERAL_URI, Test.GENERAL_BOOLEAN);
        assertEquals(sdlFile2.getName(), sdlFile3.getName());
        assertEquals(sdlFile2.getName(), "d3467db131372140");
        assertEquals(sdlFile2.getType(), Test.GENERAL_FILETYPE);
        assertEquals(sdlFile2.getUri(), Test.GENERAL_URI);
        assertEquals(sdlFile2.isPersistent(), Test.GENERAL_BOOLEAN);
    }
}
