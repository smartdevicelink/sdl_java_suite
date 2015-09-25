package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;
/** Status of the head lamps.
 * 
 * <p><table border="1" rules="all"></p>
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 * 			<th>Description</th>
 * 		</tr>
 * 		<tr>
 * 			<td>lowBeamsOn</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>Status of the low beam lamps. </td>
 * 		</tr>
 * 		<tr>
 * 			<td>highBeamsOn</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>Status of the high beam lamps. </td>
 * 		</tr>
 *     <tr>
 * 			<td>ambientLightSensorStatus</td>
 * 			<td>AmbientLightStatus</td>
 * 			<td>true</td>
 * 			<td>Status of the ambient light sensor.</td>
 * 		</tr>
 *
 *
 * </table>
 * @see OnVehicleData
 * @see GetVehicleData
 * @since SmartDeviceLink 1.0
 * 
 */

public class HeadLampStatus extends RPCStruct {
	public static final String KEY_AMBIENT_LIGHT_SENSOR_STATUS = "ambientLightSensorStatus";
	public static final String KEY_HIGH_BEAMS_ON = "highBeamsOn";
    public static final String KEY_LOW_BEAMS_ON = "lowBeamsOn";
	/**<p> Constructs a new HeadLampStatus object indicated by the Hashtable
	 * parameter</p>
	 * @param hash
	 * The hash table to use
	 */

    public HeadLampStatus() {}
    public HeadLampStatus(Hashtable<String, Object> hash) {
        super(hash);
    }
    public void setAmbientLightStatus(AmbientLightStatus ambientLightSensorStatus) {
        if (ambientLightSensorStatus != null) {
            store.put(KEY_AMBIENT_LIGHT_SENSOR_STATUS, ambientLightSensorStatus);
        } else {
        	store.remove(KEY_AMBIENT_LIGHT_SENSOR_STATUS);
        }
    }
    public AmbientLightStatus getAmbientLightStatus() {
        Object obj = store.get(KEY_AMBIENT_LIGHT_SENSOR_STATUS);
        if (obj instanceof AmbientLightStatus) {
            return (AmbientLightStatus) obj;
        } else if (obj instanceof String) {
        	return AmbientLightStatus.valueForString((String) obj);
        }
        return null;
    }
    public void setHighBeamsOn(Boolean highBeamsOn) {
        if (highBeamsOn != null) {
            store.put(KEY_HIGH_BEAMS_ON, highBeamsOn);
        } else {
        	store.remove(KEY_HIGH_BEAMS_ON);
        }
    }
    public Boolean getHighBeamsOn() {
    	return (Boolean) store.get(KEY_HIGH_BEAMS_ON);
    }
    public void setLowBeamsOn(Boolean lowBeamsOn) {
        if (lowBeamsOn != null) {
            store.put(KEY_LOW_BEAMS_ON, lowBeamsOn);
        } else {
        	store.remove(KEY_LOW_BEAMS_ON);
        }
    }
    public Boolean getLowBeamsOn() {
    	return (Boolean) store.get(KEY_LOW_BEAMS_ON);
    }
}
