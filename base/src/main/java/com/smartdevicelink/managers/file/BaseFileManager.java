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

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ISdl;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.DeleteFile;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.DebugTool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <strong>FileManager</strong> <br>
 *
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 *
 * The SDLFileManager uploads files and keeps track of all the uploaded files names during a session. <br>
 *
 * We need to add the following struct: SDLFile<br>
 *
 * It is broken down to these areas: <br>
 *
 * 1. Getters <br>
 * 2. Deletion methods <br>
 * 3. Uploading Files / Artwork
 */
abstract class BaseFileManager extends BaseSubManager {

	final static String TAG = "FileManager";
	final static int SPACE_AVAILABLE_MAX_VALUE = 2000000000;
	private List<String> remoteFiles;
	private final List<String> uploadedEphemeralFileNames;
	private int bytesAvailable = SPACE_AVAILABLE_MAX_VALUE;
	private final FileManagerConfig fileManagerConfig;
	private final HashMap<String, Integer> failedFileUploadsIndex;

	/**
	 * Constructor for BaseFileManager
	 * @param internalInterface ISDL
	 * @param fileManagerConfig FileManagerConfig
	 */
	BaseFileManager(ISdl internalInterface, FileManagerConfig fileManagerConfig) {

		// setup
		super(internalInterface);
		uploadedEphemeralFileNames = new ArrayList<>();
		this.fileManagerConfig = fileManagerConfig;
		failedFileUploadsIndex = new HashMap<>();
	}

	@Override
	@RestrictTo(RestrictTo.Scope.LIBRARY)
	public void start(CompletionListener listener) {
		// prepare manager - don't set state to ready until we have list of files
		retrieveRemoteFiles();
		super.start(listener);
	}

	// GETTERS

	/**
	 * Returns a list of file names currently residing on core
	 * @return List<String> of remote file names
	 */
	public List<String> getRemoteFileNames() {
		if (getState() != BaseSubManager.READY){
			// error and don't return list
			throw new IllegalArgumentException("FileManager is not READY");
		}
		// return list (this is synchronous at this point)
		return remoteFiles;
	}

	/**
	 * Get the number of bytes still available for files for this app.
	 * @return int value representing The number of bytes still available
	 */
	public int getBytesAvailable(){
		return bytesAvailable;
	}

	private void retrieveRemoteFiles(){
		remoteFiles = new ArrayList<>();
		// hold list in remoteFiles class var
		ListFiles listFiles = new ListFiles();
		listFiles.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				ListFilesResponse listFilesResponse = (ListFilesResponse) response;
				if (listFilesResponse.getSuccess()) {
					bytesAvailable = listFilesResponse.getSpaceAvailable() != null ? listFilesResponse.getSpaceAvailable() : SPACE_AVAILABLE_MAX_VALUE;

					if (listFilesResponse.getFilenames() != null) {
						remoteFiles.addAll(listFilesResponse.getFilenames());
					}
					// on callback set manager to ready state
					transitionToState(BaseSubManager.READY);
				} else {
					// file list could not be received. assume that setting can work and allow SDLManager to start
					DebugTool.logError(TAG, "File Manager could not list files");
					bytesAvailable = SPACE_AVAILABLE_MAX_VALUE;
					transitionToState(BaseSubManager.READY);
				}
			}
		});
		internalInterface.sendRPC(listFiles);
	}

	// DELETION

	/**
	 * Attempts to delete the desired file from core, calls listener with indication of success/failure
	 * @param fileName name of file to be deleted
	 * @param listener callback that is called on response from core
	 */
	public void deleteRemoteFileWithName(@NonNull final String fileName, final CompletionListener listener){
		DeleteFile deleteFile = new DeleteFile();
		deleteFile.setSdlFileName(fileName);
		deleteFile.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				DeleteFileResponse deleteFileResponse = (DeleteFileResponse) response;
				if(deleteFileResponse.getSuccess()){
					bytesAvailable = deleteFileResponse.getSpaceAvailable() != null ? deleteFileResponse.getSpaceAvailable() : SPACE_AVAILABLE_MAX_VALUE;

					remoteFiles.remove(fileName);
					uploadedEphemeralFileNames.remove(fileName);
				}
				if(listener != null){
					listener.onComplete(deleteFileResponse.getSuccess());
				}
			}
		});
		internalInterface.sendRPC(deleteFile);
	}

	/**
	 * Attempts to delete a list of files from core, calls listener with indication of success/failure
	 * @param fileNames list of file names to be deleted
	 * @param listener callback that is called once core responds to all deletion requests
	 */
	public void deleteRemoteFilesWithNames(@NonNull List<String> fileNames, final MultipleFileCompletionListener listener){
		if(fileNames.isEmpty()){
			return;
		}
		final List<DeleteFile> deleteFileRequests = new ArrayList<>();
		for(String fileName : fileNames){
			DeleteFile deleteFile = new DeleteFile();
			deleteFile.setSdlFileName(fileName);
			deleteFileRequests.add(deleteFile);
		}
		final Map<String, String> errors = new HashMap<>();
		sendMultipleFileOperations(deleteFileRequests, listener, errors);
	}

	// UPLOAD FILES / ARTWORK

	/**
	 * Creates and returns a PutFile request that would upload a given SdlFile
	 * @param file SdlFile with fileName and one of A) fileData, B) Uri, or C) resourceID set
	 * @return a valid PutFile request if SdlFile contained a fileName and sufficient data
	 */
	abstract PutFile createPutFile(@NonNull final SdlFile file);

	/**
	 * Sends list of provided requests (strictly PutFile or DeleteFile) asynchronously through internalInterface,
	 * calls listener on conclusion of sending requests.
	 *
	 * @param requests Non-empty list of PutFile or DeleteFile requests
	 * @param listener MultipleFileCompletionListener that is called upon conclusion of sending requests
	 * @param errors a hashMap that keeps track of RPCRequest that have failed to upload and returns to developer if listener is not null
	 */
	private void sendMultipleFileOperations(final List<? extends RPCRequest> requests, final MultipleFileCompletionListener listener, final Map<String, String> errors) {
		final HashMap<Integer, RPCRequest> requestMap = new HashMap<>();
		final List<RPCRequest> requestsToResend = new ArrayList<>();
		final boolean deletionOperation;
		if (requests.get(0) instanceof PutFile) {
			deletionOperation = false;
		} else if (requests.get(0) instanceof DeleteFile) {
			deletionOperation = true;
		} else {
			return;
		}

		OnMultipleRequestListener onMultipleRequestListener = new OnMultipleRequestListener() {
			int fileNum = 0;

			@Override
			public void addCorrelationId(int correlationId) {
				super.addCorrelationId(correlationId);
				requestMap.put(correlationId, requests.get(fileNum++));
			}

			@Override
			public void onUpdate(int remainingRequests) {
			}

			@Override
			public void onFinished() {
				if (!deletionOperation) {
					if (!requestsToResend.isEmpty()) {
						sendMultipleFileOperations(requestsToResend, listener, errors);
					} else if (listener != null) {
						listener.onComplete(errors.isEmpty() ? null : errors);
					}
				} else {
					if (listener != null) {
						listener.onComplete(errors.isEmpty() ? null : errors);
					}
				}
			}

			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if (response.getSuccess()) {
					if (response instanceof PutFileResponse) {
						PutFileResponse putFileResponse = (PutFileResponse) response;
						bytesAvailable = putFileResponse.getSpaceAvailable() != null ? putFileResponse.getSpaceAvailable() : SPACE_AVAILABLE_MAX_VALUE;

						PutFile putFile = ((PutFile) requestMap.get(correlationId));
						if (putFile != null) {
							remoteFiles.add(putFile.getSdlFileName());
							uploadedEphemeralFileNames.add(putFile.getSdlFileName());
						}

					} else if (response instanceof DeleteFileResponse) {
						DeleteFileResponse deleteFileResponse = (DeleteFileResponse) response;
						bytesAvailable = deleteFileResponse.getSpaceAvailable() != null ? deleteFileResponse.getSpaceAvailable() : SPACE_AVAILABLE_MAX_VALUE;

						DeleteFile deleteFile = (DeleteFile) requestMap.get(correlationId);
						if (deleteFile != null) {
							remoteFiles.remove(deleteFile.getSdlFileName());
							uploadedEphemeralFileNames.remove(deleteFile.getSdlFileName());
						}
					}
				} else {
					final RPCRequest request = requestMap.get(correlationId);
					if (request != null) {
						if (!deletionOperation) {
							if (shouldReUploadFile(((PutFile) request).getSdlFileName(), ((PutFile) request).getFileType())) {
								request.setOnRPCResponseListener(null);
								requestsToResend.add(request);
							} else {
								errors.put(((PutFile) request).getSdlFileName(), buildErrorString(response.getResultCode(), response.getInfo()));
							}
						} else {
							errors.put(((DeleteFile) request).getSdlFileName(), buildErrorString(response.getResultCode(), response.getInfo()));
						}
					}
				}
			}
		};
		internalInterface.sendRPCs(requests, onMultipleRequestListener);
	}

	/**
	 * Attempts to upload a SdlFile to core
	 * @param file SdlFile with file name and one of A) fileData, B) Uri, or C) resourceID set
	 * @param listener called when core responds to the attempt to upload the file
	 */
	public void uploadFile(@NonNull final SdlFile file, final CompletionListener listener) {
		if (file.isStaticIcon()) {
			DebugTool.logWarning(TAG, String.format("%s is a static icon and doesn't need to be uploaded", file.getName()));
			listener.onComplete(true);
			return;
		}
		if (!file.getOverwrite() && hasUploadedFile(file)) {
			DebugTool.logWarning(TAG, String.format("%s has already been uploaded and the overwrite property is set to false. It will not be uploaded again", file.getName()));
			listener.onComplete(true);
			return;
		}
		PutFile putFile = createPutFile(file);
		putFile.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				PutFileResponse putFileResponse = (PutFileResponse) response;
				if (putFileResponse.getSuccess()) {
					bytesAvailable = putFileResponse.getSpaceAvailable() != null ? putFileResponse.getSpaceAvailable() : SPACE_AVAILABLE_MAX_VALUE;
					remoteFiles.add(file.getName());
					uploadedEphemeralFileNames.add(file.getName());
					if (listener != null) {
						listener.onComplete(true);
					}
				} else {
					if (shouldReUploadFile(file.getName(), file.getType())) {
						uploadFile(file, listener);
					} else if (listener != null) {
						listener.onComplete(false);
					}
				}
			}
		});
		internalInterface.sendRPC(putFile);
	}

	/**
	 * Check to see if file can be re-uploaded
	 * @param fileName  a String that represents an SdlFile's name
	 * @param fileType an instances of FileType that represents a type of File
	 * @return true or false depending on if file with given type and name can be re-uploaded
	 */
	private boolean shouldReUploadFile(String fileName, FileType fileType) {
		if (!failedFileUploadsIndex.containsKey(fileName)) {
			if (FileType.GRAPHIC_JPEG.equals(fileType) ||
					FileType.GRAPHIC_BMP.equals(fileType) ||
					FileType.GRAPHIC_PNG.equals(fileType)) {
				failedFileUploadsIndex.put(fileName, fileManagerConfig.getArtworkRetryCount());
			} else {
				failedFileUploadsIndex.put(fileName, fileManagerConfig.getFileRetryCount());
			}
		}
		Integer fileRetryValue = failedFileUploadsIndex.get(fileName);
		if (fileRetryValue != null && fileRetryValue > 0) {
			failedFileUploadsIndex.put(fileName, fileRetryValue - 1);
			return true;
		}
		return false;
	}

	/**
	 * Attempts to upload a list of SdlFiles to core
	 * @param files list of SdlFiles with file name and one of A) fileData, B) Uri, or C) resourceID set
	 * @param listener callback that is called once core responds to all upload requests
	 */
	public void uploadFiles(@NonNull List<? extends SdlFile> files, final MultipleFileCompletionListener listener) {
		if (files.isEmpty()) {
			return;
		}
		final List<PutFile> putFileRequests = new ArrayList<>();
		for (SdlFile file : files) {
			if (file.isStaticIcon()) {
				DebugTool.logWarning(TAG, String.format("%s is a static icon and doesn't need to be uploaded", file.getName()));
				continue;
			}
			if (!file.getOverwrite() && hasUploadedFile(file)) {
				DebugTool.logWarning(TAG, String.format("%s has already been uploaded and the overwrite property is set to false. It will not be uploaded again", file.getName()));
				continue;
			}
			putFileRequests.add(createPutFile(file));
		}
		// if all files are static icons we complete listener with no errors
		if (putFileRequests.isEmpty()) {
			listener.onComplete(null);
		} else {
			final Map<String, String> errors = new HashMap<>();
			sendMultipleFileOperations(putFileRequests, listener, errors);
		}
	}

	/**
	 * Attempts to upload a SdlArtwork to core
	 * @param file SdlArtwork with file name and one of A) fileData, B) Uri, or C) resourceID set
	 * @param listener called when core responds to the attempt to upload the file
	 */
	public void uploadArtwork(final SdlArtwork file, final CompletionListener listener){
		uploadFile(file, listener);
	}

	/**
	 * Attempts to upload a list of SdlArtworks to core
	 * @param files list of SdlArtworks with file name and one of A) fileData, B) Uri, or C) resourceID set
	 * @param listener callback that is called once core responds to all upload requests
	 */
	public void uploadArtworks(List<SdlArtwork> files, final MultipleFileCompletionListener listener){
		uploadFiles(files, listener);
	}

	/**
	 * Check if an SdlFile has been uploaded to core
	 * @param file SdlFile
	 * @return boolean that tells whether file has been uploaded to core (true) or not (false)
	 */
	public boolean hasUploadedFile(@NonNull SdlFile file){
		if(file.isPersistent() && remoteFiles != null && remoteFiles.contains(file.getName())){
			return true;
		}else if(!file.isPersistent() && remoteFiles != null && remoteFiles.contains(file.getName())
				&& uploadedEphemeralFileNames.contains(file.getName())){
			return true;
		}
		return false;
	}

	// HELPERS

	/**
	 * Builds an error string for a given Result and info string
	 * @param resultCode Result
	 * @param info String returned from OnRPCRequestListener.onError()
	 * @return Error string
	 */
	static public String buildErrorString(Result resultCode, String info){
		return resultCode.toString() + " : " + info;
	}

	/**
	 * Helper method to take InputStream and turn it into byte array
	 * @param is valid InputStream
	 * @return Resulting byte array
	 */
	byte[] contentsOfInputStream(InputStream is){
		if(is == null){
			return null;
		}
		try{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			final int bufferSize = 4096;
			final byte[] buffer = new byte[bufferSize];
			int available;
			while ((available = is.read(buffer)) >= 0) {
				os.write(buffer, 0, available);
			}
			return os.toByteArray();
		} catch (IOException e){
			DebugTool.logError(TAG, "Can't read from InputStream", e);
			return null;
		}
	}

}
