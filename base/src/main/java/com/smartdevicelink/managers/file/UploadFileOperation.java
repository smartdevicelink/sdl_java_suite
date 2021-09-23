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

import androidx.annotation.NonNull;

import com.livio.taskmaster.Task;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.protocol.SdlProtocolBase;
import com.smartdevicelink.protocol.enums.SessionType;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;
import com.smartdevicelink.util.Version;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by Bilal Alsharifi on 12/1/20.
 */
class UploadFileOperation extends Task {
    private static final String TAG = "UploadFileOperation";
    private final WeakReference<ISdl> internalInterface;
    private final WeakReference<BaseFileManager> fileManager;
    private SdlFileWrapper fileWrapper;
    private InputStream inputStream;
    private int fileSize;
    private String streamError;
    private int bytesAvailable;
    private int highestCorrelationIDReceived;

    UploadFileOperation(ISdl internalInterface, BaseFileManager fileManager, SdlFileWrapper fileWrapper) {
        super("UploadFileOperation");

        this.internalInterface = new WeakReference<>(internalInterface);
        this.fileManager = new WeakReference<>(fileManager);
        this.fileWrapper = fileWrapper;
    }

    @Override
    public void onExecute() {
        start();
    }

    private void start() {
        if (getState() == Task.CANCELED) {
            return;
        }

        SdlFile file = fileWrapper.getFile();
        // HAX: [#827](https://github.com/smartdevicelink/sdl_ios/issues/827) Older versions of Core
        // had a bug where list files would cache incorrectly. This led to attempted uploads failing
        // due to the system thinking they were already there when they were not. This is only needed
        // if connecting to Core v4.3.1 or less which corresponds to RPC v4.3.1 or less
        if (internalInterface.get() != null && fileManager.get() != null) {
            Version rpcVersion = new Version(internalInterface.get().getSdlMsgVersion());
            if (!file.isPersistent() && !fileManager.get().hasUploadedFile(file) && new Version(4, 4, 0).isNewerThan(rpcVersion) == 1) {
                file.setOverwrite(true);
            }
            // Check our overwrite settings and error out if it would overwrite
            if (!file.getOverwrite() && fileManager.get().mutableRemoteFileNames.contains(file.getName())) {
                DebugTool.logWarning(TAG, fileManager.get().fileManagerCannotOverwriteError);
                if (this.fileWrapper.getCompletionListener() != null) {
                    this.fileWrapper.getCompletionListener().onComplete(false, bytesAvailable, null, fileManager.get().fileManagerCannotOverwriteError);
                }
                onFinished();
                return;
            }
        }

        int mtuSize = 0;
        if (internalInterface.get() != null) {
            mtuSize = (int) internalInterface.get().getMtu(SessionType.RPC);
        }
        sendFile(this.fileWrapper.getFile(), mtuSize, this.fileWrapper.getCompletionListener());
    }

    /**
     * Sends data asynchronously to the SDL Core by breaking the data into smaller packets, each of which is
     * sent via a PutFile. If the SDL Core receives all the PutFile successfully, a success response with
     * the amount of free storage space left on the SDL Core is returned. Otherwise the error returned by
     * the SDL Core is passed along.
     *
     * @param file               The file containing the data to be sent to the SDL Core
     * @param mtuSize            The maximum packet size allowed
     * @param completionListener listener returning whether or not the upload was a success
     */
    private void sendFile(SdlFile file, int mtuSize, final FileManagerCompletionListener completionListener) {
        streamError = null;
        bytesAvailable = BaseFileManager.SPACE_AVAILABLE_MAX_VALUE;
        highestCorrelationIDReceived = -1;

        if (getState() == Task.CANCELED) {
            String errorMessage = "The file upload transaction was canceled before it could be completed.";
            completionListener.onComplete(false, bytesAvailable, null, errorMessage);
            onFinished();
            return;
        }

        if (file == null) {
            String errorMessage = "The file manager was unable to send the file. This could be because the file does not exist at the specified file path or that passed data is invalid.";
            completionListener.onComplete(false, bytesAvailable, null, errorMessage);
            onFinished();
            return;
        }

        if (fileManager.get() != null) {
            this.inputStream = fileManager.get().openInputStreamWithFile(file);
            this.fileSize = getFileSizeFromInputStream(inputStream);
        }

        int maxBulkDataSize = getMaxBulkDataSize(mtuSize, file, fileSize);

        // If the file does not exist or the passed data is null, return an error
        if (inputStream == null || fileSize == 0) {
            closeInputStream();

            String errorMessage = "The file manager was unable to send the file. This could be because the file does not exist at the specified file path or that passed data is invalid.";
            completionListener.onComplete(false, bytesAvailable, null, errorMessage);
            onFinished();
            return;
        }

        final DispatchGroup putFileGroup = new DispatchGroup();
        putFileGroup.enter();

        // Wait for all packets be sent before returning whether or not the upload was a success
        putFileGroup.notify(new Runnable() {
            @Override
            public void run() {
                closeInputStream();

                if (streamError != null || getState() == Task.CANCELED) {
                    completionListener.onComplete(false, bytesAvailable, null, streamError);
                } else {
                    completionListener.onComplete(true, bytesAvailable, null, null);
                }

                onFinished();
            }
        });

        // Break the data into small pieces, each of which will be sent in a separate PutFile
        int currentOffset = 0;
        int numberOfPieces = ((fileSize - 1) / maxBulkDataSize) + 1;
        for (int i = 0; i < numberOfPieces; i++) {
            putFileGroup.enter();

            // Get a chunk of data from the input stream
            int putFileLength = getPutFileLengthForOffset(currentOffset, fileSize, maxBulkDataSize);
            int putFileBulkDataSize = getDataSizeForOffset(currentOffset, fileSize, maxBulkDataSize);
            byte[] putFileBulkData = getDataChunkWithSize(putFileBulkDataSize, this.inputStream);

            final PutFile putFile = new PutFile(file.getName(), file.getType())
                    .setPersistentFile(file.isPersistent())
                    .setSystemFile(false)
                    .setOffset(currentOffset)
                    .setLength(putFileLength)
                    .setCRC(file.getFileData());
            putFile.setBulkData(putFileBulkData);
            putFile.setOnRPCResponseListener(new OnRPCResponseListener() {
                @Override
                public void onResponse(int correlationId, RPCResponse response) {
                    PutFileResponse putFileResponse = (PutFileResponse) response;

                    // Check if the upload process has been cancelled by another packet. If so, stop the upload process.
                    if (getState() == Task.CANCELED) {
                        putFileGroup.leave();
                        return;
                    }

                    // If the SDL Core returned an error, cancel the upload the process in the future
                    if (!response.getSuccess() || getState() == Task.CANCELED) {
                        streamError = response.getInfo() + ": " + response.getResultCode();
                        putFileGroup.leave();
                        cancelTask();
                        return;
                    }

                    // If no errors, watch for a response containing the amount of storage left on the SDL Core
                    if (newHighestCorrelationID(correlationId, highestCorrelationIDReceived)) {
                        highestCorrelationIDReceived = correlationId;

                        // If spaceAvailable is null, set it to the max value
                        bytesAvailable = putFileResponse.getSpaceAvailable() != null ? putFileResponse.getSpaceAvailable() : BaseFileManager.SPACE_AVAILABLE_MAX_VALUE;
                    }

                    putFileGroup.leave();
                }
            });

            currentOffset += putFileBulkDataSize;

            if (internalInterface.get() != null) {
                internalInterface.get().sendRPC(putFile);
            }
        }

        putFileGroup.leave();
    }

    /**
     * Close the input stream once all the data has been read
     */
    private void closeInputStream() {
        if (this.inputStream == null) {
            return;
        }
        try {
            this.inputStream.close();
        } catch (IOException e) {
            DebugTool.logError(TAG,"Error attempting to close input stream", e);
        }
    }

    /**
     * Returns the max possible size for the JSON data in each of the PutFile pieces.
     *
     * @param file     The file containing the data to be sent to the SDL Core
     * @param fileSize The size of the file
     * @return max possible size for the JSON data
     */
    private int getMaxJSONSize(@NonNull SdlFile file, int fileSize) {
        int maxJSONSize = 0;

        final PutFile putFile = new PutFile(file.getName(), file.getType())
                .setPersistentFile(file.isPersistent())
                .setSystemFile(false)
                .setOffset(fileSize)
                .setLength(fileSize)
                .setCRC(file.getFileData());

        if (putFile != null && putFile.getStore() != null) {
            maxJSONSize = putFile.getStore().toString().getBytes().length;
        }
        return maxJSONSize;
    }

    /**
     * Returns the max size of bulk data that we can load into each PutFile to guarantee that the
     * packet size do not exceed the max MTU size allowed by the SDL Core.
     *
     * @param mtuSize  The maximum packet size allowed
     * @param file     The file containing the data to be sent to the SDL Core
     * @param fileSize The size of the file
     * @return max size of bulk data that we can load into each PutFile
     */
    private int getMaxBulkDataSize(int mtuSize, @NonNull SdlFile file, int fileSize) {
        // Each RPC packet contains : frame header + payload (binary header + JSON data + bulk data)
        // To make sure that packets do not exceed MTU size, the bulk data size for each packet should not exceed:
        // mtuSize - (frameHeaderSize + binaryHeaderSize + maxJSONSize)

        int frameHeaderSize = SdlProtocolBase.V2_HEADER_SIZE;
        int binaryHeaderSize = 12;
        int maxJSONSize = getMaxJSONSize(file, fileSize);
        return mtuSize - (frameHeaderSize + binaryHeaderSize + maxJSONSize);
    }

    /**
     * Returns the length of the data being sent in the PutFile. The first PutFile's length is unique in
     * that it sends the full size of the data. For the rest of the PutFiles, the length parameter is equal
     * to the size of the chunk of data being sent in the PutFile.
     *
     * @param currentOffset   The current position in the file
     * @param fileSize        The size of the file
     * @param maxBulkDataSize The max size of bulk data that we can load into each PutFile
     * @return The length of the data being sent in the PutFile
     */
    private int getPutFileLengthForOffset(int currentOffset, int fileSize, int maxBulkDataSize) {
        int putFileLength;
        if (currentOffset == 0) {
            // The first PutFile sends the full file size
            putFileLength = fileSize;
        } else if ((fileSize - currentOffset) < maxBulkDataSize) {
            // The last PutFile sends the size of the remaining data
            putFileLength = fileSize - currentOffset;
        } else {
            // All other PutFiles send the maximum bulk data size
            putFileLength = maxBulkDataSize;
        }
        return putFileLength;
    }

    /**
     * Gets the size of the data to be sent in a packet.
     * Packet size can not be greater than the max MTU size allowed by the SDL Core.
     *
     * @param currentOffset   The position in the file where to start reading data
     * @param fileSize        he size of the file
     * @param maxBulkDataSize The max size of bulk data that we can load into each PutFile
     * @return The size of the data to be sent in the packet.
     */
    private int getDataSizeForOffset(int currentOffset, int fileSize, int maxBulkDataSize) {
        int dataSize;
        int fileSizeRemaining = fileSize - currentOffset;
        if (fileSizeRemaining < maxBulkDataSize) {
            dataSize = fileSizeRemaining;
        } else {
            dataSize = maxBulkDataSize;
        }
        return dataSize;
    }

    /**
     * Reads a chunk of data from input stream.
     *
     * @param size        The amount of data to read from the input stream
     * @param inputStream The stream from which to read the data
     * @return The data read from the socket
     */
    private byte[] getDataChunkWithSize(int size, InputStream inputStream) {
        if (size < 0) {
            return null;
        }

        int bytesRead = 0;
        byte[] buffer = new byte[size];
        try {
            bytesRead = inputStream.read(buffer, 0, size);
        } catch (IOException e) {
            DebugTool.logError(TAG,"Error attempting to read from input stream", e);
        }

        if (bytesRead > 0) {
            return buffer;
        } else {
            return null;
        }
    }

    /**
     * One of the responses returned by the SDL Core will contain the correct remaining free storage
     * size on the SDL Core. Since communication with the SDL Core is asynchronous, there is no way
     * to predict which response contains the correct bytes available other than to watch for the
     * largest correlation id, since that will be the last response sent by the SDL Core.
     *
     * @param correlationID                The correlationID for the newest response returned by the SDL Core for a PutFile
     * @param highestCorrelationIDReceived The largest currently received correlation id
     * @return Whether or not the newest request contains the highest correlationId
     */
    private boolean newHighestCorrelationID(int correlationID, int highestCorrelationIDReceived) {
        return correlationID > highestCorrelationIDReceived;
    }

    /**
     * Gets the size of the data.
     *
     * @return The size of the data.
     */
    private int getFileSizeFromInputStream(InputStream inputStream) {
        int size = 0;
        if (inputStream != null) {
            try {
                size = inputStream.available();
            } catch (IOException e) {
                DebugTool.logError(TAG,"Error trying to get input stream size", e);
            }
        }
        return size;
    }

    @Override
    public String getName() {
        return super.getName() + " - " + fileWrapper.getFile().getName();
    }
}

