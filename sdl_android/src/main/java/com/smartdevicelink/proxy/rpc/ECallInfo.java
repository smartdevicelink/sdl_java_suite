package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.ECallConfirmationStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;

import java.util.Hashtable;

/** Emergency Call notification and confirmation data.
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
 * 			<td>eCallNotificationStatus</td>
 * 			<td>VehicleDataNotificationStatus</td>
 * 			<td>References signal "eCallNotification_4A".</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>auxECallNotificationStatus</td>
 * 			<td>VehicleDataNotificationStatus</td>
 * 			<td>References signal "eCallNotification". This is an alternative signal available on some carlines replacing the eCallNotificationStatus, but showing the same values.</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>eCallConfirmationStatus</td>
 * 			<td>ECallConfirmationStatus</td>
 * 			<td>References signal "eCallConfirmation"</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 * @see SubscribeVehicleData
 * 
 * 
 * @see TextFieldName
 * @see Image
 *
 */

public class ECallInfo extends RPCStruct {
    public static final String KEY_E_CALL_NOTIFICATION_STATUS = "eCallNotificationStatus";
    public static final String KEY_AUX_E_CALL_NOTIFICATION_STATUS = "auxECallNotificationStatus";
    public static final String KEY_E_CALL_CONFIRMATION_STATUS = "eCallConfirmationStatus";
    /** Constructs a new ECallInfo object indicated by the Hashtable
	 * parameter
	 * @param hash
	 * 
	 * 			<p>The hash table to use</p>
	 */
    public ECallInfo() { }
    public ECallInfo(Hashtable<String, Object> hash) {
        super(hash);
    }

    public void setECallNotificationStatus(VehicleDataNotificationStatus eCallNotificationStatus) {
        setValue(KEY_E_CALL_NOTIFICATION_STATUS, eCallNotificationStatus);
    }
    public VehicleDataNotificationStatus getECallNotificationStatus() {
        return (VehicleDataNotificationStatus) getObject(VehicleDataNotificationStatus.class, KEY_E_CALL_NOTIFICATION_STATUS);
    }
    public void setAuxECallNotificationStatus(VehicleDataNotificationStatus auxECallNotificationStatus) {
        setValue(KEY_AUX_E_CALL_NOTIFICATION_STATUS, auxECallNotificationStatus);
    }
    public VehicleDataNotificationStatus getAuxECallNotificationStatus() {
        return (VehicleDataNotificationStatus) getObject(VehicleDataNotificationStatus.class, KEY_AUX_E_CALL_NOTIFICATION_STATUS);
    }
    public void setECallConfirmationStatus(ECallConfirmationStatus eCallConfirmationStatus) {
        setValue(KEY_E_CALL_CONFIRMATION_STATUS, eCallConfirmationStatus);
    }
    public ECallConfirmationStatus getECallConfirmationStatus() {
        return (ECallConfirmationStatus) getObject(ECallConfirmationStatus.class, KEY_E_CALL_CONFIRMATION_STATUS);
    }
}
