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
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.Set;

/**
 * Created by Bilal Alsharifi on 12/1/20.
 */
class DeleteFileOperation extends Task {
    private static final String TAG = "DeleteFileOperation";
    private final WeakReference<ISdl> internalInterface;
    private String fileName;
    private FileManagerCompletionListener completionListener;
    private Set<String> mutableRemoteFileNames;

    DeleteFileOperation(ISdl internalInterface, String fileName, Set<String> mutableRemoteFileNames, FileManagerCompletionListener completionListener) {
        super("DeleteFileOperation");
        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileName = fileName;
        this.completionListener = completionListener;
        this.mutableRemoteFileNames = mutableRemoteFileNames;
    }

    @Override
    public void onExecute() {
        start();
    }

    private void start() {
        if (getState() == Task.CANCELED) {
            return;
        }
        if (!mutableRemoteFileNames.contains(fileName) && completionListener != null) {
            String errorMessage = "File to delete is no longer on the head unit, aborting operation";
            // Returning BaseFileManager.SPACE_AVAILABLE_MAX_VALUE for bytesAvaialble as a placeHolder, it will not get updated in BaseFileManager as long as success returned is false.
            completionListener.onComplete(false, BaseFileManager.SPACE_AVAILABLE_MAX_VALUE, mutableRemoteFileNames, errorMessage);
            onFinished();
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
                String errorMessage = success ? null : response.getInfo() + ": " + response.getResultCode();
                if (errorMessage != null) {
                    DebugTool.logInfo(TAG, "Error deleting file: " + errorMessage);
                }

                // If spaceAvailable is null, set it to the max value
                int bytesAvailable = deleteFileResponse.getSpaceAvailable() != null ? deleteFileResponse.getSpaceAvailable() : BaseFileManager.SPACE_AVAILABLE_MAX_VALUE;

                if (completionListener != null) {
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

