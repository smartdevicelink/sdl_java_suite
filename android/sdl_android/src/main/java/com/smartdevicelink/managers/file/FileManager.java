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

package com.smartdevicelink.managers.file;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.util.DebugTool;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * <strong>FileManager</strong> <br>
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 * The FileManager uploads files and keeps track of all the uploaded files names during a session. <br>
 */
public class FileManager extends BaseFileManager {

    private final WeakReference<Context> context;

    /**
     * Constructor for FileManager
     *
     * @param internalInterface an instance of the ISdl interface that can be used for common SDL operations (sendRpc, addRpcListener, etc)
     * @param context           an instances of Context interface to global information for application
     * @param fileManagerConfig an instance of the FileManagerConfig gives access to artworkRetryCount and fileRetryCount to let us if those file types can be re-upload if they fail
     */
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public FileManager(ISdl internalInterface, Context context, FileManagerConfig fileManagerConfig) {
        // setup
        super(internalInterface, fileManagerConfig);
        this.context = new WeakReference<>(context);
    }

    @Override
    InputStream openInputStreamWithFile(@NonNull SdlFile file) {
        InputStream inputStream = null;

        if (context.get() == null) {
            DebugTool.logError(TAG, "Context is null. Cannot open file input stream!");
            return null;
        }

        if (file.getResourceId() > 0) {
            inputStream = context.get().getResources().openRawResource(file.getResourceId());
        } else if (file.getUri() != null) {
            try {
                inputStream = context.get().getContentResolver().openInputStream(file.getUri());
            } catch (FileNotFoundException e) {
                DebugTool.logError(TAG, String.format("File at %s cannot be found.", file.getUri()));
            }
        } else if (file.getFileData() != null) {
            inputStream = new ByteArrayInputStream(file.getFileData());
        } else {
            throw new IllegalArgumentException("The SdlFile to upload does not specify its resourceId, Uri, or file data");
        }

        return inputStream;
    }
}
