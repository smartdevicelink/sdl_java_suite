package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;
/** Information related to the MyKey feature.
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 * 			<th>Description</th>
 * 		</tr>
 * 		<tr>
 * 			<td>e911Override</td>
 * 			<td>VehicleDataStatus</td>
 * 			<td>true</td>
 *			<td>Indicates whether e911 override is on.</td>
 * 		</tr>
 *  </table>
 *				
 *@since SmartDeviceLink 2.0	
 *				
 *@see GetVehicleData				
 *@see OnVehicleData 			
 *@see VehicleDataStatus
 * 
 *
 */

public class MyKey extends RPCStruct {
    public static final String KEY_E_911_OVERRIDE = "e911Override";
	/**
	* <p>
	* Constructs a new MyKey object indicated by the Hashtable
	* parameter
	* </p>
	* 
	* @param hash
	*            The Hashtable to use
	*/

    public MyKey() { }
    public MyKey(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setE911Override(VehicleDataStatus e911Override) {
        if (e911Override != null) {
            store.put(KEY_E_911_OVERRIDE, e911Override);
        } else {
        	store.remove(KEY_E_911_OVERRIDE);
        }
    }
    public VehicleDataStatus getE911Override() {
        Object obj = store.get(KEY_E_911_OVERRIDE);
        if (obj instanceof VehicleDataStatus) {
            return (VehicleDataStatus) obj;
        } else if (obj instanceof String) {
        	return VehicleDataStatus.valueForString((String) obj);
        }
        return null;
    }
}
