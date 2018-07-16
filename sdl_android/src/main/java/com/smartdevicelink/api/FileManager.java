package com.smartdevicelink.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.DeleteFile;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.listeners.OnPutFileUpdateListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

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
	private ArrayList<String> remoteFiles;
	private WeakReference<Context> context;

	FileManager(ISdl internalInterface, Context context) {

		// setup
		super(internalInterface);
		this.context = new WeakReference<>(context);

		// prepare manager - don't set state to ready until we have list of files
		retrieveRemoteFiles();
	}

	// GETTERS

	public ArrayList<String> getRemoteFileNames() {
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
	}

	// DELETION

	public void deleteRemoteFileWithName(final String fileName, final CompletionListener listener){
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
	}

	public void deleteRemoteFilesWithNames(ArrayList<String> fileNames, CompletionListener listener){
		for(String fileName : fileNames){
			deleteRemoteFileWithName(fileName, listener);
		}
	}

	// UPLOAD FILES / ARTWORK

	public void uploadFile(final SdlFile file, final CompletionListener listener){
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

		putFile.setOnPutFileUpdateListener(new OnPutFileUpdateListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response, long totalSize) {
				if(response.getSuccess()){
					remoteFiles.add(file.getName());
				}
				listener.onComplete(response.getSuccess());
			}
		});

		internalInterface.sendRPCRequest(putFile);
	}

	public void uploadFiles(ArrayList<SdlFile> files, CompletionListener listener){
		for(SdlFile file : files){
			uploadFile(file, listener);
		}
	}

	public void uploadArtwork(final SdlArtwork file, final CompletionListener listener){
		uploadFile(file, listener);
	}

	public void uploadArtworks(ArrayList<SdlArtwork> files, CompletionListener listener){
		for(SdlArtwork artwork : files){
			uploadArtwork(artwork, listener);
		}
	}

	// HELPERS

	/**
	 * Helper method to take resource files and turn them into byte arrays
	 * @param resource Resource file id.
	 * @return Resulting byte array.
	 */
	private byte[] contentsOfResource(int resource) {
		InputStream is = null;
		try {
			is = context.get().getResources().openRawResource(resource);
			ByteArrayOutputStream os = new ByteArrayOutputStream(is.available());
			final int bufferSize = 4096;
			final byte[] buffer = new byte[bufferSize];
			int available;
			while ((available = is.read(buffer)) >= 0) {
				os.write(buffer, 0, available);
			}
			return os.toByteArray();
		} catch (IOException e) {
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
			ByteArrayOutputStream os = new ByteArrayOutputStream();
		    final int bufferSize = 4096;
		    final byte[] buffer = new byte[bufferSize];
		    int available;
			while ((available = is.read(buffer)) >= 0) {
				os.write(buffer, 0, available);
			}
		    return os.toByteArray();
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

}
