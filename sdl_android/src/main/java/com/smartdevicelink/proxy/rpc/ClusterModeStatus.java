package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.CarModeStatus;
import com.smartdevicelink.proxy.rpc.enums.PowerModeQualificationStatus;
import com.smartdevicelink.proxy.rpc.enums.PowerModeStatus;

import static android.provider.Contacts.SettingsColumns.KEY;

/** <p>The status modes of the instrument panel cluster.</p>
 * 
 * 
  * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 * 			<th>Description</th>
 * 		</tr>
 * 		<tr>
 * 			<td>powerModeActive</td>
 * 			<td>Boolean</td>
 * 			<td></td>
 * 			<td>References signal "PowerMode_UB".</td>
 * 		</tr>
 * 		<tr>
 * 			<td>powerModeQualificationStatus</td>
 * 			<td>PowerModeQualificationStatus</td>
 * 			<td></td>
 * 			<td>References signal "PowerModeQF".</td>
 * 		</tr>
 * 		<tr>
 * 			<td>carModeStatus</td>
 * 			<td>CarModeStatus</td>
 * 			<td></td>
 * 			<td>Describes the carmode the vehicle is in.</td>
 *           </tr>
 * 		<tr>
 * 			<td>powerModeStatus</td>
 * 			<td>PowerModeStatus</td>
 * 			<td>true</td>
 * 			<td>Describes the different powermodes</td>
 * 		</tr>
 * 
 *  </table>
 * @since SmartDeviceLink 1.0
 * 
 * @see SubscribeVehicleData
 * @see SubscribeVehicleData 
 * @see Image
 *
 */

public class ClusterModeStatus extends RPCStruct {
    public static final String KEY_POWER_MODE_ACTIVE = "powerModeActive";
    public static final String KEY_POWER_MODE_QUALIFICATION_STATUS = "powerModeQualificationStatus";
    public static final String KEY_CAR_MODE_STATUS = "carModeStatus";
    public static final String KEY_POWER_MODE_STATUS = "powerModeStatus";
	/** <p>Constructs a new ClusterModeStatus object indicated by the Hashtable
	 * parameter</p>
	 * @param hash
	 * The hash table to use
	 * 
	 */

	    public ClusterModeStatus() { }
	    public ClusterModeStatus(Hashtable<String, Object> hash) {
	        super(hash);
	    }

	    public void setPowerModeActive(Boolean powerModeActive) {
	        setValue(KEY_POWER_MODE_ACTIVE, powerModeActive);
	    }
	    public Boolean getPowerModeActive() {
	        return getBoolean(KEY_POWER_MODE_ACTIVE);
	    }
	    public void setPowerModeQualificationStatus(PowerModeQualificationStatus powerModeQualificationStatus) {
	        setValue(KEY_POWER_MODE_QUALIFICATION_STATUS, powerModeQualificationStatus);
	    }
	    public PowerModeQualificationStatus getPowerModeQualificationStatus() {
			return (PowerModeQualificationStatus) getObject(PowerModeQualificationStatus.class, KEY_POWER_MODE_QUALIFICATION_STATUS);
	    }
	    public void setCarModeStatus(CarModeStatus carModeStatus) {
	        setValue(KEY_CAR_MODE_STATUS, carModeStatus);
	    }
	    public CarModeStatus getCarModeStatus() {
			return (CarModeStatus) getObject(CarModeStatus.class, KEY_CAR_MODE_STATUS);
	    }
	    public void setPowerModeStatus(PowerModeStatus powerModeStatus) {
	        setValue(KEY_POWER_MODE_STATUS, powerModeStatus);
	    }
	    public PowerModeStatus getPowerModeStatus() {
			return (PowerModeStatus) getObject(PowerModeStatus.class, KEY_POWER_MODE_STATUS);
	    }
}
