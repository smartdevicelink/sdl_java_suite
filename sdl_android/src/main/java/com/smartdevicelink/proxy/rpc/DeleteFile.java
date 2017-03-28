package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Used to delete a file resident on the SDL module in the app's local cache.
 * Not supported on first generation SDL vehicles
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>SmartDeviceLink 2.0</th>
 * 		</tr>
 * 		<tr>
 * 			<td>SDLFileName</td>
 * 			<td>String</td>
 * 			<td> File reference name.</td>
 *                 <td>Y</td>
 *                 <td>maxlength:500</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 *  
 * <p><b>Response </b></p>
 * 
 * <p><b>Non-default Result Codes:</b></p>
 * <p>	SUCCESS</p>
 * <p>	INVALID_DATA</p>
 * <p>	OUT_OF_MEMORY</p>
 * <p>	TOO_MANY_PENDING_REQUESTS</p>
 * <p>	APPLICATION_NOT_REGISTERED</p>
 * 	<p>GENERIC_ERROR</p>
 * 	<p>REJECTED</p> 
 * @since SmartDeviceLink 2.0
 * @see PutFile
 * @see ListFiles
 */
public class DeleteFile extends RPCRequest {
	public static final String KEY_SDL_FILE_NAME = "syncFileName";

	/**
	 * Constructs a new DeleteFile object
	 */
    public DeleteFile() {
        super(FunctionID.DELETE_FILE.toString());
    }

	/**
	 * <p>Constructs a new DeleteFile object indicated by the Hashtable parameter</p>
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public DeleteFile(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Sets a file reference name
	 * 
	 * @param sdlFileName
	 *            a String value representing a file reference name
	 */
    public void setSdlFileName(String sdlFileName) {
        if (sdlFileName != null) {
            parameters.put(KEY_SDL_FILE_NAME, sdlFileName);
        } else {
        	parameters.remove(KEY_SDL_FILE_NAME);
        }
    }

	/**
	 * Gets a file reference name
	 * 
	 * @return String -a String value representing a file reference name
	 */
    public String getSdlFileName() {
        return (String) parameters.get(KEY_SDL_FILE_NAME);
    }
}
