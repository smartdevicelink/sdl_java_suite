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

package com.smartdevicelink.managers.file;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.DeleteFile;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.lang.ref.WeakReference;

/**
 * Created by Bilal Alsharifi on 12/1/20.
 */
class DeleteFileOperation extends Task {
    private static final String TAG = "FileManager.DeleteFileOperation";
    private final WeakReference<ISdl> internalInterface;
    private String fileName;
    private FileManagerCompletionListener completionListener;

    DeleteFileOperation(ISdl internalInterface, String fileName, FileManagerCompletionListener completionListener) {
        super("DeleteFileOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileName = fileName;
        this.completionListener = completionListener;
    }

    @Override
    public void onExecute() {
        start();
    }

    private void start() {
        if (getState() == Task.CANCELED) {
            return;
        }

        deleteFile();
    }

    private void deleteFile() {
        DeleteFile deleteFile = new DeleteFile(fileName);
        deleteFile.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                DeleteFileResponse deleteFileResponse = (DeleteFileResponse) response;
                boolean success = deleteFileResponse.getSuccess();

                // If spaceAvailable is null, set it to the max value
                int bytesAvailable = deleteFileResponse.getSpaceAvailable() != null ? deleteFileResponse.getSpaceAvailable() : BaseFileManager.SPACE_AVAILABLE_MAX_VALUE;

                if (completionListener != null) {
                    String errorMessage = response.getInfo() + ": " + response.getResultCode();
                    completionListener.onComplete(success, bytesAvailable, null, errorMessage);
                }

                onFinished();
            }
        });

        if (internalInterface.get() != null) {
            internalInterface.get().sendRPC(deleteFile);
        }
    }

    @Override
    public String getName() {
        return super.getName() + " - " + fileName;
    }
}

