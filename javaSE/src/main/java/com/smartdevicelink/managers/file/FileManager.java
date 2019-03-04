package com.smartdevicelink.managers.file;


import android.support.annotation.NonNull;
import com.smartdevicelink.managers.file.filetypes.SdlFile;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.PutFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

	public FileManager(ISdl internalInterface) {

		// setup
		super(internalInterface);
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

		if(file.getFilePath() != null){
			//Attempt to access the file via a path
			byte[] data = contentsOfFilePath(file.getFilePath());
			if(data != null ){
				putFile.setFileData(data);
			}else{
				throw new IllegalArgumentException("File at path was empty");
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
	 *
	 * @param filePath
	 * @return
	 */
	private byte[] contentsOfFilePath(String filePath){
		File file = new File(filePath);
		if(file.isFile() && file.canRead()){
			FileInputStream fileInputStream = null;
			byte[] bytesArray = null;

			try {
				bytesArray = new byte[(int) file.length()];

				//read file into bytes[]
				fileInputStream = new FileInputStream(file);
				fileInputStream.read(bytesArray);

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fileInputStream != null) {
					try {
						fileInputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
			return bytesArray;
		}
		return null;
	}
}
