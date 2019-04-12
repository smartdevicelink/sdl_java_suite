/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
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
package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.listeners.OnPutFileUpdateListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.util.Hashtable;
import java.util.zip.CRC32;

/**
 * Used to push a binary data onto the SDL module from a mobile device, such as
 * icons and album art.
 * 
 * <p><b> Parameter List</b></p>
 * 
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>FileName</td>
 * 			<td>String</td>
 * 			<td>File reference name.</td>
 *                 <td>Y</td>
 * 			<td>Maxlength=500</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>fileType</td>
 * 			<td>FileType</td>
 * 			<td>Selected file type.</td>
 *                 <td>Y</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>persistentFile</td>
 * 			<td>Boolean</td>
 * 			<td>Indicates if the file is meant to persist between sessions / ignition cycles. If set to TRUE,then the system will aim to persist this file through session / cycles. While files with this designation will have priority over others,they are subject to deletion by the system at any time.In the event of automatic deletion by the system, the app will receive a rejection and have to resend the file. If omitted, the value will be set to false.</td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>systemFile</td>
 * 			<td>Boolean</td>
 * 			<td>Indicates if the file is meant to be passed thru core to elsewhere on the system. If set to TRUE, then the system will instead pass the data thru as it arrives to a predetermined area outside of core. If omitted, the value will be set to false.</td>
 *                 <td>N</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.3.2</td>
 * 		</tr>
 * 		<tr>
 * 			<td>offset</td>
 * 			<td>Float</td>
 * 			<td>Optional offset in bytes for resuming partial data chunks</td>
 *                 <td>N</td>
 * 			<td>Minvalue=0; Maxvalue=100000000000</td>
 * 			<td>SmartDeviceLink 2.3.2</td>
 * 		</tr>
 * 		<tr>
 * 			<td>length</td>
 * 			<td>Float</td>
 * 			<td>Optional length in bytes for resuming partial data chunks. If offset is set to 0, then length is the total length of the file to be downloaded</td>
 *                 <td>N</td>
 * 			<td>Minvalue=0; Maxvalue=100000000000</td>
 * 			<td>SmartDeviceLink 2.3.2</td>
 * 		</tr>
 * 		<tr>
 * 			<td>crc</td>
 * 			<td>Long</td>
 * 			<td>Additional CRC32 checksum to protect data integrity up to 512 Mbits .</td>
 *                 <td>N</td>
 * 			<td>minvalue="0" maxvalue="4294967295"</td>
 * 			<td>SmartDeviceLink 2.3.2</td>
 * 		</tr>
 *  </table>
 * <p> <b>Note: </b></p>
 *  When using PutFiles you may want to check for memory
 *  
 * <p><b>Response</b> </p>
 * Response is sent, when the file data was copied (success case). Or when an error occurred. Not supported on First generation SDL modules.
 * 
 * <p><b>	Non-default Result Codes:</b></p>
 * <p>	SUCCESS</p>
 * <p>	INVALID_DATA</p>
 * <p>	OUT_OF_MEMORY</p>
 * <p>	TOO_MANY_PENDING_REQUESTS</p>
 * <p>	APPLICATION_NOT_REGISTERED</p>
 * <p>	GENERIC_ERROR</p>
 * 	<p>REJECTED</p>
 *
 * <p><table border="1" rules="all"></p>
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>spaceAvailable</td>
 * 			<td>Integer</td>
 * 			<td>Provides the total local space available on SDL for the registered app.</td>
 *                 <td></td>
 * 			<td>Minvalue=0; Maxvalue=2000000000</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 *  </table>
 * @since SmartDeviceLink 2.0
 * @see DeleteFile
 * @see ListFiles
 */
public class PutFile extends RPCRequest {
	public static final String KEY_PERSISTENT_FILE = "persistentFile";
    public static final String KEY_SYSTEM_FILE = "systemFile";
    public static final String KEY_FILE_TYPE = "fileType";
    public static final String KEY_SDL_FILE_NAME = "syncFileName";
    public static final String KEY_OFFSET = "offset";
    public static final String KEY_LENGTH = "length";
    public static final String KEY_CRC = "crc";

	/**
	 * Constructs a new PutFile object
	 */
    public PutFile() {
        super(FunctionID.PUT_FILE.toString());
    }

	/**
	 * Constructs a new PutFile object indicated by the Hashtable parameter
	 * 
	 * @param hash The Hashtable to use
	 */
    public PutFile(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new PutFile object
	 * @param syncFileName a String value representing a file reference name
	 * <b>Notes: </b>Maxlength=500, however the max file name length may vary based on remote filesystem limitations
	 * @param fileType a FileType value representing a selected file type
	 */
	public PutFile(@NonNull String syncFileName, @NonNull FileType fileType) {
		this();
		setSdlFileName(syncFileName);
		setFileType(fileType);
	}

	/**
	 * Sets a file reference name
	 * 
	 * @param sdlFileName
	 *            a String value representing a file reference name
	 *            <p></p>
	 *            <b>Notes: </b>Maxlength=500, however the max file name length may vary based on remote filesystem limitations
	 */
    public void setSdlFileName(@NonNull String sdlFileName) {
        setParameters(KEY_SDL_FILE_NAME, sdlFileName);
    }

	/**
	 * Gets a file reference name
	 * 
	 * @return String - a String value representing a file reference name
	 */
    public String getSdlFileName() {
        return getString(KEY_SDL_FILE_NAME);
    }

	/**
	 * Sets file type
	 * 
	 * @param fileType
	 *            a FileType value representing a selected file type
	 */
    public void setFileType(@NonNull FileType fileType) {
        setParameters(KEY_FILE_TYPE, fileType);
    }

	/**
	 * Gets a file type
	 * 
	 * @return FileType -a FileType value representing a selected file type
	 */
    public FileType getFileType() {
        return (FileType) getObject(FileType.class, KEY_FILE_TYPE);
    }

	/**
	 * Sets a value to indicates if the file is meant to persist between
	 * sessions / ignition cycles. If set to TRUE, then the system will aim to
	 * persist this file through session / cycles. While files with this
	 * designation will have priority over others, they are subject to deletion
	 * by the system at any time. In the event of automatic deletion by the
	 * system, the app will receive a rejection and have to resend the file. If
	 * omitted, the value will be set to false
	 * <p></p>
	 * 
	 * @param persistentFile
	 *            a Boolean value
	 */
    public void setPersistentFile(Boolean persistentFile) {
        setParameters(KEY_PERSISTENT_FILE, persistentFile);
    }

	/**
	 * Gets a value to Indicates if the file is meant to persist between
	 * sessions / ignition cycles
	 * 
	 * @return Boolean -a Boolean value to indicates if the file is meant to
	 *         persist between sessions / ignition cycles
	 */
    public Boolean getPersistentFile() {
        return getBoolean(KEY_PERSISTENT_FILE);
    }
    public void setFileData(byte[] fileData) {
        setBulkData(fileData);
    }
    public byte[] getFileData() {
        return getBulkData();
    }
    
    /**
     * @deprecated as of SmartDeviceLink 4.0
     * @param offset Optional offset in bytes for resuming partial data chunks
     */
    public void setOffset(Integer offset) {
    	if(offset == null){
    		setOffset((Long)null);
    	}else{
    		setOffset(offset.longValue());
    	}
    }

	/**
	 * @param offset Optional offset in bytes for resuming partial data chunks
	 */
    public void setOffset(Long offset) {
        setParameters(KEY_OFFSET, offset);
    }

    public Long getOffset() {
        final Object o = getParameters(KEY_OFFSET);
        if (o == null){
        	return null;
        }
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        }else if(o instanceof Long){
        	return (Long) o;
        }


        return null;
    }

    /**
     * @deprecated as of SmartDeviceLink 4.0
     * @param length Optional length in bytes for resuming partial data chunks. If offset is set to 0, then length is
	 *               the total length of the file to be downloaded
     */
    public void setLength(Integer length) {
    	if(length == null){
    		setLength((Long)null);
    	}else{
    		setLength(length.longValue());
    	}
    }

	/**
	 * @param length Optional length in bytes for resuming partial data chunks. If offset is set to 0, then length is
	 *               the total length of the file to be downloaded
	 */
    public void setLength(Long length) {
        setParameters(KEY_LENGTH, length);
    }

    public Long getLength() {
        final Object o = getParameters(KEY_LENGTH);
        if (o == null){
        	return null;
        }
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        }else if(o instanceof Long){
        	return (Long) o;
        }

        return null;
    }

    public void setSystemFile(Boolean systemFile) {
        setParameters(KEY_SYSTEM_FILE, systemFile);
    }

    public Boolean getSystemFile() {
        final Object o = getParameters(KEY_SYSTEM_FILE);
        if (o instanceof Boolean) {
            return (Boolean) o;
        }
        else
        	return null;
    }

	/**
	 * This takes the file data as an array of bytes and calculates the
	 * CRC32 for it.
	 * @param fileData - the file as a byte array
	 */
	public void setCRC(byte[] fileData) {
		if (fileData != null) {
			CRC32 crc = new CRC32();
			crc.update(fileData);
			parameters.put(KEY_CRC, crc.getValue());
		} else {
			parameters.remove(KEY_CRC);
		}
	}

	/**
	 * This assumes you have created your own CRC32 and are setting it with the file
	 * <STRONG>Please avoid using your own calculations for this, and use the method
	 * included in java.util</STRONG>
	 * @param crc - the CRC32 of the file being set
	 */
	public void setCRC(Long crc) {
		if (crc != null) {
			parameters.put(KEY_CRC, crc);
		} else {
			parameters.remove(KEY_CRC);
		}
	}

	/**
	 * This returns the CRC, if it has been set, for the file object
	 * @return - a CRC32 Long
	 */
	public Long getCRC() {
		final Object o = parameters.get(KEY_CRC);
		if (o == null){
			return null;
		}
		if (o instanceof Integer) {
			return ((Integer) o).longValue();
		}else if(o instanceof Long){
			return (Long) o;
		}
		return null;
	}

	@Override
	public final void setOnRPCResponseListener(OnRPCResponseListener listener) {
		super.setOnRPCResponseListener(listener);
	}

	public void setOnPutFileUpdateListener(OnPutFileUpdateListener listener) {
		super.setOnRPCResponseListener(listener); //We can use the same method because it get stored as a parent class
	}

	public OnPutFileUpdateListener getOnPutFileUpdateListener() {
		return (OnPutFileUpdateListener)getOnRPCResponseListener();
	}
}
