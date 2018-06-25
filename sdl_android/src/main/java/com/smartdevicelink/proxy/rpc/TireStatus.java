package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;
import com.smartdevicelink.proxy.rpc.enums.WarningLightStatus;
import com.smartdevicelink.util.DebugTool;

import java.util.Hashtable;

import static com.smartdevicelink.proxy.rpc.ECallInfo.KEY_E_CALL_NOTIFICATION_STATUS;

/** <p>The status and pressure of the tires.</p>
 *   <p><b> Parameter List:</b></p>
 *   
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *          <th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>PressureTellTale</td>
 * 			<td>WarningLightStatus</td>
 * 			<td>Status of the Tire Pressure TellTale</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * <tr>
 * 			<td>LeftFront</td>
 * 			<td>SingleTireStatus</td>
 * 			<td>The status of the left front tire.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * <tr>
 * 			<td>RightFront</td>
 * 			<td>SingleTireStatus</td>
 * 			<td>The status of the right front tire.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * <tr>
 * 			<td>LeftRear</td>
 * 			<td>SingleTireStatus</td>
 * 			<td>The status of the left rear tire.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>RightRear</td>
 * 			<td>SingleTireStatus</td>
 * 			<td>The status of the right rear tire</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>InnerLeftRear</td>
 * 			<td>SingleTireStatus</td>
 * 			<td>The status of the inner left rear tire.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>InnerRightRear</td>
 * 			<td>SingleTireStatus</td>
 * 			<td>The status of the inner right rear tire.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 *  
 *  @since SmartDeviceLink 2.0
 *  
 * @see WarningLightStatus
 * @see SingleTireStatus
 * @see GetVehicleData 
 * @see OnVehicleData
 */

public class TireStatus extends RPCStruct {
	public static final String KEY_PRESSURE_TELL_TALE = "pressureTellTale";
	public static final String KEY_LEFT_FRONT = "leftFront";
	public static final String KEY_RIGHT_FRONT = "rightFront";
	public static final String KEY_LEFT_REAR = "leftRear";
	public static final String KEY_INNER_LEFT_REAR = "innerLeftRear";
	public static final String KEY_INNER_RIGHT_REAR = "innerRightRear";
	public static final String KEY_RIGHT_REAR = "rightRear";

	public TireStatus() { }

	 /**
		 * <p>Constructs a new TireStatus object indicated by the Hashtable parameter</p>
		 *
		 * @param hash The Hashtable to use
		 */
	public TireStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new TireStatus object
	 * @param pressureTellTale Status of the Tire Pressure TellTale
	 * @param leftFront The status of the left front tire.
	 * @param rightFront The status of the right front tire.
	 * @param leftRear The status of the left rear tire.
	 * @param rightRear The status of the right rear tire
	 * @param innerLeftRear The status of the inner left rear tire.
	 * @param innerRightRear The status of the inner right rear tire.
	 */
	public TireStatus(@NonNull WarningLightStatus pressureTellTale, @NonNull SingleTireStatus leftFront, @NonNull SingleTireStatus rightFront, @NonNull SingleTireStatus leftRear, @NonNull SingleTireStatus rightRear, @NonNull SingleTireStatus innerLeftRear, @NonNull SingleTireStatus innerRightRear){
		this();
		setPressureTellTale(pressureTellTale);
		setLeftFront(leftFront);
		setRightFront(rightFront);
		setLeftRear(leftRear);
		setRightRear(rightRear);
		setInnerLeftRear(innerLeftRear);
		setInnerRightRear(innerRightRear);
	}

    public void setPressureTellTale(@NonNull WarningLightStatus pressureTellTale) {
    	setValue(KEY_PRESSURE_TELL_TALE, pressureTellTale);
    }
    public WarningLightStatus getPressureTellTale() {
        return (WarningLightStatus) getObject(WarningLightStatus.class, KEY_PRESSURE_TELL_TALE);
    }
    public void setLeftFront(@NonNull SingleTireStatus leftFront) {
    	setValue(KEY_LEFT_FRONT, leftFront);
    }
    @SuppressWarnings("unchecked")
    public SingleTireStatus getLeftFront() {
        return (SingleTireStatus) getObject(SingleTireStatus.class, KEY_LEFT_FRONT);
    }
    public void setRightFront(@NonNull SingleTireStatus rightFront) {
    	setValue(KEY_RIGHT_FRONT, rightFront);
    }
    @SuppressWarnings("unchecked")
    public SingleTireStatus getRightFront() {
        return (SingleTireStatus) getObject(SingleTireStatus.class, KEY_RIGHT_FRONT);
    }
    public void setLeftRear(@NonNull SingleTireStatus leftRear) {
    	setValue(KEY_LEFT_REAR, leftRear);
    }
    @SuppressWarnings("unchecked")
    public SingleTireStatus getLeftRear() {
        return (SingleTireStatus) getObject(SingleTireStatus.class, KEY_LEFT_REAR);
    }
    public void setRightRear(@NonNull SingleTireStatus rightRear) {
    	setValue(KEY_RIGHT_REAR, rightRear);
    }
    @SuppressWarnings("unchecked")
    public SingleTireStatus getRightRear() {
        return (SingleTireStatus) getObject(SingleTireStatus.class, KEY_RIGHT_REAR);
    }
    public void setInnerLeftRear(@NonNull SingleTireStatus innerLeftRear) {
    	setValue(KEY_INNER_LEFT_REAR, innerLeftRear);
    }
    @SuppressWarnings("unchecked")
    public SingleTireStatus getInnerLeftRear() {
        return (SingleTireStatus) getObject(SingleTireStatus.class, KEY_INNER_LEFT_REAR);
    }
    public void setInnerRightRear(@NonNull SingleTireStatus innerRightRear) {
    	setValue(KEY_INNER_RIGHT_REAR, innerRightRear);
    }
    @SuppressWarnings("unchecked")
    public SingleTireStatus getInnerRightRear() {
        return (SingleTireStatus) getObject(SingleTireStatus.class, KEY_INNER_RIGHT_REAR);
    }
}
