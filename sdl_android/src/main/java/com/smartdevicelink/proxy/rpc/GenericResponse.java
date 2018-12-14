package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * Generic Response is sent, when the name of a received msg cannot be
 * retrieved. Only used in case of an error. Currently, only resultCode
 * INVALID_DATA is used.
 * <p><b>Parameter List</b></p>
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
 * 			<td>success</td>
 * 			<td>Boolean</td>
 * 			<td>true, if successful; false, if failed</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>resultCode</td>
 * 			<td>Result</td>
 * 			<td>Defines the possible result codes returned by SDL to the application in a Response to a requested operation</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>info</td>
 * 			<td>String</td>
 * 			<td>Provides additional human readable info regarding the result.</td>
 *                 <td>N</td>
 * 			<td>maxlength=1000</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 *  @since SmartDeviceLink 1.0
 */
public class GenericResponse extends RPCResponse {

    public GenericResponse() {
        super(FunctionID.GENERIC_RESPONSE.toString());
    }
    public GenericResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a new GenericResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public GenericResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}