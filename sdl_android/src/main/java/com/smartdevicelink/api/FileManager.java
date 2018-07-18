package com.smartdevicelink.api;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.DeleteFile;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnPutFileUpdateListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
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
public class FileManager extends BaseSubManager {

	private static String TAG = "FileManager";
	private List<String> remoteFiles;
	private WeakReference<Context> context;

	FileManager(ISdl internalInterface, Context context) {

		// setup
		super(internalInterface);
		this.context = new WeakReference<>(context);

		// prepare manager - don't set state to ready until we have list of files
		retrieveRemoteFiles();
	}

	// GETTERS

	public List<String> getRemoteFileNames() {
		if (getState() != BaseSubManager.READY){
			// error and dont return list
			throw new IllegalArgumentException("FileManager is not READY");
		}
		// return list (this is synchronous at this point)
		return remoteFiles;
	}

	private void retrieveRemoteFiles(){
		remoteFiles = new ArrayList<>();
		// hold list in remoteFiles class var
		ListFiles listFiles = new ListFiles();
		listFiles.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if(response.getSuccess()){
					remoteFiles.addAll(((ListFilesResponse) response).getFilenames());
					// on callback set manager to ready state
					transitionToState(BaseSubManager.READY);
				}else{
					// file list could not be received
					transitionToState(BaseSubManager.SHUTDOWN);
				}
			}
		});
		internalInterface.sendRPCRequest(listFiles);
	}

	// DELETION

	public void deleteRemoteFileWithName(final String fileName, final CompletionListener listener){
		if(fileName == null){
			return;
		}
		DeleteFile deleteFile = new DeleteFile();
		deleteFile.setSdlFileName(fileName);
		deleteFile.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if(response.getSuccess()){
					remoteFiles.remove(fileName);
				}
				listener.onComplete(response.getSuccess());
			}
		});
		internalInterface.sendRPCRequest(deleteFile);
	}

	public void deleteRemoteFilesWithNames(List<String> fileNames, final MultipleFileCompletionListener listener){
		if(fileNames == null || fileNames.isEmpty()){
			return;
		}
		final Map<String, String> errors = new HashMap<>();
		final List<DeleteFile> deleteFileRequests = new ArrayList<>();
		final SparseArray<String> fileNameMap = new SparseArray<>();
		for(String fileName : fileNames){
			DeleteFile deleteFile = new DeleteFile();
			deleteFile.setSdlFileName(fileName);
			deleteFileRequests.add(deleteFile);
		}
		internalInterface.sendRequests(deleteFileRequests, new OnMultipleRequestListener() {
			int fileNum = 0;

			@Override
			public void addCorrelationId(int correlationid) {
				super.addCorrelationId(correlationid);
				fileNameMap.put(correlationid, deleteFileRequests.get(fileNum++).getSdlFileName());
			}

			@Override
			public void onUpdate(int remainingRequests) {}

			@Override
			public void onFinished() {
				if(errors.isEmpty()){
					listener.onComplete(null);
				}else{
					listener.onComplete(errors);
				}
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info) {
				if(fileNameMap.get(correlationId) != null){
					errors.put(fileNameMap.get(correlationId), resultCode.toString() + " : " + info);
				}// else no fileName for given correlation ID
			}

			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if(response.getSuccess()){
					if(fileNameMap.get(correlationId) != null){
						remoteFiles.remove(fileNameMap.get(correlationId));
					}
				}
			}
		});
	}

	// UPLOAD FILES / ARTWORK

	private PutFile createPutFile(final SdlFile file){
		if(file == null){
			return null;
		}
		PutFile putFile = new PutFile();
		if(file.getName() == null){
			throw new IllegalArgumentException("You must specify an file name in the SdlFile");
		}else{
			putFile.setSdlFileName(file.getName());
		}

		if(file.getResourceId() > 0){
			// Use resource id to upload file
			byte[] contents = contentsOfResource(file.getResourceId());
			if(contents != null){
				putFile.setFileData(contents);
			}else{
				throw new IllegalArgumentException("Resource file id was empty");
			}
		}else if(file.getUri() != null){
			// Use URI to upload file
			byte[] contents = contentsOfUri(file.getUri());
			if(contents != null){
				putFile.setFileData(contents);
			}else{
				throw new IllegalArgumentException("Uri was empty");
			}
		}else if(file.getFileData() != null){
			// Use file data (raw bytes) to upload file
			putFile.setFileData(file.getFileData());
		}else{
			throw new IllegalArgumentException("The SdlFile to upload does " +
					"not specify its resourceId, Uri, or file data");
		}

		if(file.getType() != null){
			putFile.setFileType(file.getType());
		}
		putFile.setPersistentFile(file.isPersistent());

		return putFile;
	}

	public void uploadFile(final SdlFile file, final CompletionListener listener){
		PutFile putFile = createPutFile(file);

		putFile.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if(response.getSuccess()){
					remoteFiles.add(file.getName());
				}
				listener.onComplete(response.getSuccess());
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info) {
				super.onError(correlationId, resultCode, info);
				listener.onComplete(false);
			}
		});

		internalInterface.sendRPCRequest(putFile);
	}

	public void uploadFiles(List<? extends SdlFile> files, final MultipleFileCompletionListener listener){
		if(files ==null || files.isEmpty()){
			return;
		}
		final Map<String, String> errors = new HashMap<>();
		final List<PutFile> putFileRequests = new ArrayList<>();
		final SparseArray<String> fileNameMap = new SparseArray<>();
		for(SdlFile file : files){
			putFileRequests.add(createPutFile(file));
		}
		internalInterface.sendRequests(putFileRequests, new OnMultipleRequestListener() {
			int fileNum = 0;

			@Override
			public void addCorrelationId(int correlationid) {
				super.addCorrelationId(correlationid);
				fileNameMap.put(correlationid, putFileRequests.get(fileNum++).getSdlFileName());
			}

			@Override
			public void onUpdate(int remainingRequests) {}

			@Override
			public void onFinished() {
				if(errors.isEmpty()){
					listener.onComplete(null);
				}else{
					listener.onComplete(errors);
				}
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info) {
				if(fileNameMap.get(correlationId) != null){
					errors.put(fileNameMap.get(correlationId), buildErrorString(resultCode, info));
				}// else no fileName for given correlation ID
			}

			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if(response.getSuccess()){
					if(fileNameMap.get(correlationId) != null){
						remoteFiles.add(fileNameMap.get(correlationId));
					}
				}
			}
		});
	}

	public void uploadArtwork(final SdlArtwork file, final CompletionListener listener){
		uploadFile(file, listener);
	}

	public void uploadArtworks(List<SdlArtwork> files, final MultipleFileCompletionListener listener){
		uploadFiles(files, listener);
	}

	// HELPERS

	static public String buildErrorString(Result resultCode, String info){
		return resultCode.toString() + " : " + info;
	}

	/**
	 * Helper method to take resource files and turn them into byte arrays
	 * @param resource Resource file id.
	 * @return Resulting byte array.
	 */
	private byte[] contentsOfResource(int resource) {
		InputStream is = null;
		try {
			is = context.get().getResources().openRawResource(resource);
			return contentsOfInputStream(is);
		} catch (Resources.NotFoundException e) {
			Log.w(TAG, "Can't read from resource", e);
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Helper method to take Uri and turn it into byte array
	 * @param uri Uri for desired file
	 * @return Resulting byte array.
	 */
	private byte[] contentsOfUri(Uri uri){
		InputStream is = null;
		try{
			is = context.get().getContentResolver().openInputStream(uri);
			return contentsOfInputStream(is);
		} catch (IOException e){
			Log.w(TAG, "Can't read from Uri", e);
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private byte[] contentsOfInputStream(InputStream is){
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
			Log.w(TAG, "Can't read from InputStream", e);
			return null;
		}
	}

}
