package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

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

    /**
	 * Constructs a new HeadLampStatus object
     */
    public HeadLampStatus() {}
    /**
	 * <p>Constructs a new HeadLampStatus object indicated by the Hashtable
     * parameter</p>
     * @param hash The hash table to use
     */
    public HeadLampStatus(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
	 * Constructs a new HeadLampStatus object
     */
    public HeadLampStatus(@NonNull Boolean lowBeamsOn, @NonNull Boolean highBeamsOn) {
        this();
        setLowBeamsOn(lowBeamsOn);
        setHighBeamsOn(highBeamsOn);
    }
    public void setAmbientLightStatus(AmbientLightStatus ambientLightSensorStatus) {
        setValue(KEY_AMBIENT_LIGHT_SENSOR_STATUS, ambientLightSensorStatus);
    }
    public AmbientLightStatus getAmbientLightStatus() {
        return (AmbientLightStatus) getObject(AmbientLightStatus.class, KEY_AMBIENT_LIGHT_SENSOR_STATUS);
    }
    public void setHighBeamsOn(@NonNull Boolean highBeamsOn) {
        setValue(KEY_HIGH_BEAMS_ON, highBeamsOn);
    }
    public Boolean getHighBeamsOn() {
    	return getBoolean(KEY_HIGH_BEAMS_ON);
    }
    public void setLowBeamsOn(@NonNull Boolean lowBeamsOn) {
        setValue(KEY_LOW_BEAMS_ON, lowBeamsOn);
    }
    public Boolean getLowBeamsOn() {
    	return getBoolean(KEY_LOW_BEAMS_ON);
    }
}
