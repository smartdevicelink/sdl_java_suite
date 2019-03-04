package com.smartdevicelink.managers.file;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.PutFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

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
public class FileManager extends BaseFileManager {

	private final WeakReference<Context> context;

	public FileManager(ISdl internalInterface, Context context) {

		// setup
		super(internalInterface);
		this.context = new WeakReference<>(context);
	}

	/**
	 * Creates and returns a PutFile request that would upload a given SdlFile
	 * @param file SdlFile with fileName and one of A) fileData, B) Uri, or C) resourceID set
	 * @return a valid PutFile request if SdlFile contained a fileName and sufficient data
	 */
	@Override
	PutFile createPutFile(@NonNull final SdlFile file){
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

	/**
	 * Helper method to take resource files and turn them into byte arrays
	 * @param resource Resource file id
	 * @return Resulting byte array
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
	 * @return Resulting byte array
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
}
