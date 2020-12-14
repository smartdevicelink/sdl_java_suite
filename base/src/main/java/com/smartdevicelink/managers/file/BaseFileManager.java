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

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.livio.taskmaster.Queue;
import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <strong>FileManager</strong> <br>
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 * The FileManager uploads files and keeps track of all the uploaded files names during a session. <br>
 */
abstract class BaseFileManager extends BaseSubManager {

    final static String TAG = "FileManager";
    final static int SPACE_AVAILABLE_MAX_VALUE = 2000000000;

    private final Set<String> mutableRemoteFileNames;
    private final Set<String> uploadedEphemeralFileNames;
    private int bytesAvailable;
    private Queue transactionQueue;
    private HashMap<String, Integer> failedFileUploadsCount;
    private final int maxFileUploadAttempts;
    private final int maxArtworkUploadAttempts;

    /**
     * Constructor for BaseFileManager
     *
     * @param internalInterface ISDL
     * @param fileManagerConfig FileManagerConfig
     */
    BaseFileManager(ISdl internalInterface, FileManagerConfig fileManagerConfig) {
        super(internalInterface);
        this.bytesAvailable = 0;

        this.mutableRemoteFileNames = new HashSet<>();
        this.transactionQueue = internalInterface.getTaskmaster().createQueue("FileManager", 5, false);
        this.uploadedEphemeralFileNames = new HashSet<>();

        this.failedFileUploadsCount = new HashMap<>();
        this.maxFileUploadAttempts = fileManagerConfig.getFileRetryCount() + 1;
        this.maxArtworkUploadAttempts = fileManagerConfig.getArtworkRetryCount() + 1;
    }

    @Override
    @RestrictTo(RestrictTo.Scope.LIBRARY)
    public void start(CompletionListener listener) {
        // Prepare manager - don't set state to ready until we have list of files
        retrieveRemoteFiles();
        super.start(listener);
    }

    @Override
    public void dispose() {
        super.dispose();

        // Cancel the operations
        if (transactionQueue != null) {
            transactionQueue.close();
            transactionQueue = null;
        }

        if (mutableRemoteFileNames != null) {
            mutableRemoteFileNames.clear();
        }

        bytesAvailable = 0;

        // Clear the failed uploads tracking so failed files can be uploaded again when a new connection has been established with Core
        if (failedFileUploadsCount != null) {
            failedFileUploadsCount.clear();
        }
    }

    /**
     * Returns a list of file names currently residing on core
     *
     * @return List<String> of remote file names
     */
    public List<String> getRemoteFileNames() {
        return new ArrayList<>(mutableRemoteFileNames);
    }

    /**
     * Get the number of bytes still available for files for this app.
     *
     * @return int value representing The number of bytes still available
     */
    public int getBytesAvailable() {
        return this.bytesAvailable;
    }

    private void retrieveRemoteFiles() {
        listRemoteFilesWithCompletionListener(new FileManagerCompletionListener() {
            @Override
            public void onComplete(boolean success, int bytesAvailable, Collection<String> fileNames, String errorMessage) {
                if (errorMessage != null) {
                    // HAX: In the case we are DISALLOWED we still want to transition to a ready state.
                    // Some head units return DISALLOWED for this RPC but otherwise work.
                    DebugTool.logWarning(TAG, "ListFiles is disallowed. Certain file manager APIs may not work properly.");
                    transitionToState(READY);
                    return;
                }

                // If no error, make sure we're in the ready state
                transitionToState(READY);
            }
        });
    }

    private void listRemoteFilesWithCompletionListener(final FileManagerCompletionListener completionListener) {
        ListFilesOperation operation = new ListFilesOperation(internalInterface, new FileManagerCompletionListener() {
            @Override
            public void onComplete(boolean success, int bytesAvailable, Collection<String> fileNames, String errorMessage) {
                if (errorMessage != null || !success) {
                    completionListener.onComplete(success, bytesAvailable, fileNames, errorMessage);
                    return;
                }

                // If there was no error, set our properties and call back to the completion listener
                BaseFileManager.this.mutableRemoteFileNames.addAll(fileNames);
                BaseFileManager.this.bytesAvailable = bytesAvailable;

                completionListener.onComplete(success, bytesAvailable, fileNames, errorMessage);
            }
        });

        transactionQueue.add(operation, false);
    }

    /**
     * Attempts to delete the desired file from core, calls listener with indication of success/failure
     *
     * @param fileName name of file to be deleted
     * @param listener callback that is called on response from core
     */
    public void deleteRemoteFileWithName(@NonNull final String fileName, final CompletionListener listener) {
        deleteRemoteFileWithNamePrivate(fileName, new FileManagerCompletionListener() {
            @Override
            public void onComplete(boolean success, int bytesAvailable, Collection<String> fileNames, String errorMessage) {
                if (listener != null) {
                    listener.onComplete(success);
                }
            }
        });
    }

    private void deleteRemoteFileWithNamePrivate(@NonNull final String fileName, final FileManagerCompletionListener listener) {
        if (!mutableRemoteFileNames.contains(fileName) && listener != null) {
            String errorMessage = "No such remote file is currently known";
            listener.onComplete(false, bytesAvailable, mutableRemoteFileNames, errorMessage);
            return;
        }

        DeleteFileOperation operation = new DeleteFileOperation(internalInterface, fileName, new FileManagerCompletionListener() {
            @Override
            public void onComplete(boolean success, int bytesAvailable, Collection<String> fileNames, String errorMessage) {
                if (success) {
                    BaseFileManager.this.bytesAvailable = bytesAvailable;
                    BaseFileManager.this.mutableRemoteFileNames.remove(fileName);
                }

                if (listener != null) {
                    listener.onComplete(success, bytesAvailable, mutableRemoteFileNames, errorMessage);
                }
            }
        });
        transactionQueue.add(operation, false);
    }

    /**
     * Attempts to delete a list of files from core, calls listener with indication of success/failure
     *
     * @param fileNames list of file names to be deleted
     * @param listener  callback that is called once core responds to all deletion requests
     */
    public void deleteRemoteFilesWithNames(@NonNull List<String> fileNames, final MultipleFileCompletionListener listener) {
        if (fileNames.isEmpty()) {
            throw new IllegalArgumentException("This request requires that the array of files not be empty");
        }

        final Map<String, String> failedDeletes = new HashMap<>();

        final DispatchGroup deleteFilesTask = new DispatchGroup();
        deleteFilesTask.enter();

        for (final String name : fileNames) {
            deleteFilesTask.enter();
            deleteRemoteFileWithNamePrivate(name, new FileManagerCompletionListener() {
                @Override
                public void onComplete(boolean success, int bytesAvailable, Collection<String> fileNames, String errorMessage) {
                    if (!success) {
                        failedDeletes.put(name, errorMessage);
                    }
                    deleteFilesTask.leave();
                }
            });
        }

        deleteFilesTask.leave();

        // Wait for all files to be deleted
        deleteFilesTask.notify(new Runnable() {
            @Override
            public void run() {
                if (listener == null) {
                    return;
                }
                if (failedDeletes.size() > 0) {
                    listener.onComplete(failedDeletes);
                    return;
                }
                listener.onComplete(null);
            }
        });
    }

    /**
     * Check if an SdlFile has been uploaded to core
     *
     * @param file SdlFile
     * @return boolean that tells whether file has been uploaded to core (true) or not (false)
     */
    public boolean hasUploadedFile(@NonNull SdlFile file) {
        // HAX: [#827](https://github.com/smartdevicelink/sdl_ios/issues/827) Older versions of Core had a bug where list files would cache incorrectly.
        if (file.isPersistent() && mutableRemoteFileNames != null && mutableRemoteFileNames.contains(file.getName())) {
            // If it's a persistent file, the bug won't present itself; just check if it's on the remote system
            return true;
        } else if (!file.isPersistent() && mutableRemoteFileNames != null && mutableRemoteFileNames.contains(file.getName()) && uploadedEphemeralFileNames.contains(file.getName())) {
            // If it's an ephemeral file, the bug will present itself; check that it's a remote file AND that we've uploaded it this session
            return true;
        }
        return false;
    }

    /**
     * Check if an SdlFile needs to be uploaded to Core or not.
     * It is different from hasUploadedFile() because it takes isStaticIcon and overwrite properties into consideration.
     * ie, if the file is static icon, the method always returns false.
     * If the file is dynamic, it returns true in one of these situations:
     * 1) the file has the overwrite property set to true
     * 2) the file hasn't been uploaded to Core before.
     *
     * @param file the SdlFile that needs to be checked
     * @return boolean that tells whether file needs to be uploaded to Core or not
     */
    public boolean fileNeedsUpload(@NonNull SdlFile file) {
        if (file != null && !file.isStaticIcon()) {
            return file.getOverwrite() || !hasUploadedFile(file);
        }
        return false;
    }

    /**
     * Attempts to upload a list of SdlFiles to core
     *
     * @param files    list of SdlFiles with file name and one of A) fileData, B) Uri, or C) resourceID set
     * @param listener callback that is called once core responds to all upload requests
     */
    public void uploadFiles(@NonNull List<? extends SdlFile> files, final MultipleFileCompletionListener listener) {
        if (files.isEmpty()) {
            throw new IllegalArgumentException("This request requires that the array of files not be empty.");
        }

        final Map<String, String> failedUploads = new HashMap<>();
        final DispatchGroup uploadFilesTask = new DispatchGroup();
        uploadFilesTask.enter();

        // Wait for all files to be uploaded
        uploadFilesTask.notify(new Runnable() {
            @Override
            public void run() {
                if (listener == null) {
                    return;
                }

                if (failedUploads.size() > 0) {
                    listener.onComplete(failedUploads);
                    return;
                }

                listener.onComplete(null);
            }
        });

        for (int i = 0; i < files.size(); i++) {
            final SdlFile file = files.get(i);
            uploadFilesTask.enter();

            uploadFilePrivate(file, new FileManagerCompletionListener() {
                @Override
                public void onComplete(boolean success, int bytesAvailable, Collection<String> fileNames, String errorMessage) {
                    if (!success) {
                        failedUploads.put(file.getName(), errorMessage);
                    }

                    uploadFilesTask.leave();
                }
            });
        }
        uploadFilesTask.leave();
    }

    /**
     * Attempts to upload a SdlFile to core
     *
     * @param file     SdlFile with file name and one of A) fileData, B) Uri, or C) resourceID set
     * @param listener called when core responds to the attempt to upload the file
     */
    public void uploadFile(@NonNull final SdlFile file, final CompletionListener listener) {
        uploadFilePrivate(file, new FileManagerCompletionListener() {
            @Override
            public void onComplete(boolean success, int bytesAvailable, Collection<String> fileNames, String errorMessage) {
                if (listener != null) {
                    listener.onComplete(success);
                }
            }
        });
    }

    private void uploadFilePrivate(@NonNull final SdlFile file, final FileManagerCompletionListener listener) {
        if (file == null) {
            if (listener != null) {
                listener.onComplete(false, bytesAvailable, null, "The file upload was canceled. The data for the file is missing.");
            }
            return;
        }

        if (file.isStaticIcon()) {
            if (listener != null) {
                listener.onComplete(false, bytesAvailable, null, "The file upload was canceled. The file is a static icon, which cannot be uploaded.");
            }
            return;
        }

        if (getState() != READY) {
            if (listener != null) {
                listener.onComplete(false, bytesAvailable, null, "The file manager was unable to send this file. This could be because the file manager has not started, or the head unit does not support files.");
            }
            return;
        }

        // HAX: [#827](https://github.com/smartdevicelink/sdl_ios/issues/827) Older versions of Core
        // had a bug where list files would cache incorrectly. This led to attempted uploads failing
        // due to the system thinking they were already there when they were not. This is only needed
        // if connecting to Core v4.3.1 or less which corresponds to RPC v4.3.1 or less
        Version rpcVersion = new Version(internalInterface.getSdlMsgVersion());
        if (!file.isPersistent() && !hasUploadedFile(file) && new Version(4, 4, 0).isNewerThan(rpcVersion) == 1) {
            file.setOverwrite(true);
        }

        // Check our overwrite settings and error out if it would overwrite
        if (!file.getOverwrite() && mutableRemoteFileNames.contains(file.getName())) {
            String errorMessage = "Cannot overwrite remote file. The remote file system already has a file of this name, and the file manager is set to not automatically overwrite files.";
            DebugTool.logWarning(TAG, errorMessage);
            if (listener != null) {
                listener.onComplete(true, bytesAvailable, null, errorMessage);
            }
            return;
        }

        // If we didn't error out over the overwrite, then continue on
        sdl_uploadFilePrivate(file, listener);
    }

    private void sdl_uploadFilePrivate(@NonNull final SdlFile file, final FileManagerCompletionListener listener) {
        final String fileName = file.getName();

        SdlFileWrapper fileWrapper = new SdlFileWrapper(file, new FileManagerCompletionListener() {
            @Override
            public void onComplete(boolean success, int bytesAvailable, Collection<String> fileNames, String errorMessage) {
                if (success) {
                    BaseFileManager.this.bytesAvailable = bytesAvailable;
                    BaseFileManager.this.mutableRemoteFileNames.add(fileName);
                    BaseFileManager.this.uploadedEphemeralFileNames.add(fileName);
                } else {
                    incrementFailedUploadCountForFileName(file.getName(), BaseFileManager.this.failedFileUploadsCount);

                    int maxUploadCount = file instanceof SdlArtwork ? maxArtworkUploadAttempts : maxFileUploadAttempts;
                    if (canFileBeUploadedAgain(file, maxUploadCount, failedFileUploadsCount)) {
                        DebugTool.logInfo(TAG, String.format("Attempting to resend file with name %s after a failed upload attempt", file.getName()));
                        sdl_uploadFilePrivate(file, listener);
                        return;
                    }
                }

                if (listener != null) {
                    listener.onComplete(success, bytesAvailable, null, errorMessage);
                }
            }
        });

        UploadFileOperation operation = new UploadFileOperation(internalInterface, this, fileWrapper);
        transactionQueue.add(operation, false);
    }

    /**
     * Attempts to upload a SdlArtwork to core
     *
     * @param file     SdlArtwork with file name and one of A) fileData, B) Uri, or C) resourceID set
     * @param listener called when core responds to the attempt to upload the file
     */
    public void uploadArtwork(final SdlArtwork file, final CompletionListener listener) {
        uploadFile(file, listener);
    }

    /**
     * Attempts to upload a list of SdlArtworks to core
     *
     * @param files    list of SdlArtworks with file name and one of A) fileData, B) Uri, or C) resourceID set
     * @param listener callback that is called once core responds to all upload requests
     */
    public void uploadArtworks(List<SdlArtwork> files, final MultipleFileCompletionListener listener) {
        uploadFiles(files, listener);
    }

    /**
     * Checks if an artwork needs to be uploaded to Core. The artwork should not be sent to Core if
     * the artwork is already on Core or if the artwork is not on Core after the maximum number of
     * repeated upload attempts has been reached.
     *
     * @param file                   The file to be uploaded to Core
     * @param maxUploadCount         The max number of times the file is allowed to be uploaded to Core
     * @param failedFileUploadsCount
     * @return True if the file still needs to be (re)sent to Core; false if not.
     */
    private boolean canFileBeUploadedAgain(SdlFile file, int maxUploadCount, HashMap<String, Integer> failedFileUploadsCount) {
        if (getState() != READY) {
            DebugTool.logWarning(TAG, String.format("File named %s failed to upload. The file manager has shutdown so the file upload will not retry.", file.getName()));
            return false;
        }

        if (file == null) {
            DebugTool.logError(TAG, "File can not be uploaded because it is not a valid file.");
            return false;
        }

        if (hasUploadedFile(file)) {
            DebugTool.logInfo(TAG, String.format("File named %s has already been uploaded.", file.getName()));
            return false;
        }

        Integer failedUploadCount = failedFileUploadsCount.get(file.getName());
        boolean canFileBeUploadedAgain = (failedUploadCount == null) || (failedUploadCount < maxUploadCount);
        if (!canFileBeUploadedAgain) {
            DebugTool.logError(TAG, String.format("File named %s failed to upload. Max number of upload attempts reached.", file.getName()));
        }

        return canFileBeUploadedAgain;
    }

    /**
     * Increments the number of upload attempts for a file name by 1.
     *
     * @param name The name used to upload the file to Core
     * @param failedFileUploadsCount
     * @return
     */
    private void incrementFailedUploadCountForFileName(String name, HashMap<String, Integer> failedFileUploadsCount) {
        Integer currentFailedUploadCount = failedFileUploadsCount.get(name);
        Integer newFailedUploadCount = (currentFailedUploadCount != null) ? (currentFailedUploadCount + 1) : 1;
        failedFileUploadsCount.put(name, newFailedUploadCount);
        DebugTool.logWarning(TAG, String.format("File with name %s failed to upload %s times", name, newFailedUploadCount));
    }

    /**
     * Opens a socket for reading data.
     *
     * @param file The file containing the data or the file url of the data
     */
    abstract InputStream openInputStreamWithFile(@NonNull SdlFile file);

    /**
     * Builds an error string for a given Result and info string
     *
     * @param resultCode Result
     * @param info       String returned from OnRPCRequestListener.onError()
     * @return Error string
     */
    @Deprecated
    static public String buildErrorString(Result resultCode, String info) {
        return resultCode.toString() + " : " + info;
    }
}
