package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Used to set existing local file on SDL as the app's icon. Not supported on
 * first generation SDL vehicles
 * 
 * <p><b>Parameter List</b></p>
 * 
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>SDLFileName</td>
 * 			<td>String</td>
 * 			<td>File reference name.</td>
 *                 <td>Y</td>
 * 			<td>Maxlength=500</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 *  </table>
 *  
 *<p><b>Response </b></p>
 *<p><b> Non-default Result Codes: </b></p>
 *<p>SUCCESS</p>
 *	<p> INVALID_DATA</p>
 *	<p> OUT_OF_MEMORY</p>
 *   <p>  TOO_MANY_PENDING_REQUESTS</p>
 *   <p>  APPLICATION_NOT_REGISTERED</p>
 *   <p>  GENERIC_ERROR</p>
 *   <p>  REJECTED</p>
 *
 * @since SmartDeviceLink 2.0
 * @see Image
 */
public class SetAppIcon extends RPCRequest {
	public static final String KEY_SDL_FILE_NAME = "syncFileName";

	/**
	 * Constructs a new SetAppIcon object
	 */
    public SetAppIcon() {
        super(FunctionID.SET_APP_ICON.toString());
    }

	/**
	 * Constructs a new SetAppIcon object indicated by the Hashtable parameter
	 * <p></p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SetAppIcon(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Sets a file reference name
	 * 
	 * @param sdlFileName
	 *            a String value representing a file reference name
	 *            <p></p>
	 *            <b>Notes: </b>Maxlength=500
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
	 * @return String -a String value
	 */
    public String getSdlFileName() {
        return (String) parameters.get(KEY_SDL_FILE_NAME);
    }
}
