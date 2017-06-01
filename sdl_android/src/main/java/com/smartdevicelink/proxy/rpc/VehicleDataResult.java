package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataType;
/**
 * 
 * Individual published data request result.
 * 
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
 * 			<td>dataType</td>
 * 			<td>VehicleDataType</td>
 * 			<td>Defined published data element type.</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>resultCode</td>
 * 			<td>VehicleDataResultCode</td>
 * 			<td>Published data result code.</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 *
 */
public class VehicleDataResult extends RPCStruct {
	public static final String KEY_DATA_TYPE = "dataType";
	public static final String KEY_RESULT_CODE = "resultCode";
	  /**
		* <p>
		* Constructs a new VehicleDataResult object indicated by the Hashtable
		* parameter
		* </p>
		* 
		* @param hash
		* 
		*            <p>The Hashtable to use</p>
		*/

    public VehicleDataResult() { }
    public VehicleDataResult(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setDataType(VehicleDataType dataType) {
    	setValue(KEY_DATA_TYPE, dataType);
    }
    public VehicleDataType getDataType() {
        return (VehicleDataType) getObject(VehicleDataType.class, KEY_DATA_TYPE);
    }
    public void setResultCode(VehicleDataResultCode resultCode) {
    	setValue(KEY_RESULT_CODE, resultCode);
    }
    public VehicleDataResultCode getResultCode() {
        return (VehicleDataResultCode) getObject(VehicleDataResultCode.class, KEY_RESULT_CODE);
    }
}
