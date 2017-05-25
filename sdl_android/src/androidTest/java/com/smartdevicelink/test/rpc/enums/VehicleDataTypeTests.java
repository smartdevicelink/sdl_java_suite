package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataType;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.VehicleData}
 */
public class VehicleDataTypeTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.vehicle_data_gps_caps);
		VehicleDataType enumVehicleDataGps = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_speed_caps);
		VehicleDataType enumVehicleDataSpeed = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_rpm_caps);
		VehicleDataType enumVehicleDataRpm = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_fuel_level_caps);
		VehicleDataType enumVehicleDataFuelLevel = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_fuel_level_state_caps);
		VehicleDataType enumVehicleDataFuelLevelState = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_fuel_consumption_caps);
		VehicleDataType enumVehicleDataFuelConsumption = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_external_temp_caps);
		VehicleDataType enumVehicleDataExternTemp = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_vin_caps);
		VehicleDataType enumVehicleDataVin = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_prndl_caps);
		VehicleDataType enumVehicleDataPrndl = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_tire_pressure_caps);
		VehicleDataType enumVehicleDataTirePressure = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_odometer_caps);
		VehicleDataType enumVehicleDataOdometer = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_belt_status_caps);
		VehicleDataType enumVehicleDataBeltStatus = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_body_info_caps);
		VehicleDataType enumVehicleDataBodyInfo = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_device_status_caps);
		VehicleDataType enumVehicleDataDeviceStatus = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_braking_caps);
		VehicleDataType enumVehicleDataBraking = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_wiper_status_caps);
		VehicleDataType enumVehicleDataWiperStatus = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_headlamp_status_caps);
		VehicleDataType enumVehicleDataHeadlampStatus = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_batt_voltage_caps);
		VehicleDataType enumVehicleDataBattVoltage = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_engine_torque_caps);
		VehicleDataType enumVehicleDataEngineTorque = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_acc_pedal_caps);
		VehicleDataType enumVehicleDataAccPedal = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_steering_wheel_caps);
		VehicleDataType enumVehicleDataSteeringWheel = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_ecall_info_caps);
		VehicleDataType enumVehicleDataECallInfo = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_airbag_status_caps);
		VehicleDataType enumVehicleDataAirbagStatus = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_ermergerd_emergency_event_caps);
		VehicleDataType enumVehicleDataEmergencyEvent = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_cluster_mode_status_caps);
		VehicleDataType enumVehicleDataClusterModeStatus = VehicleDataType.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_mykey_caps);
		VehicleDataType enumVehicleDataMyKey = VehicleDataType.valueForString(example);
		
		assertNotNull("VEHICLEDATA_GPS returned null", enumVehicleDataGps);
		assertNotNull("VEHICLEDATA_SPEED returned null", enumVehicleDataSpeed);
		assertNotNull("VEHICLEDATA_RPM returned null", enumVehicleDataRpm);
		assertNotNull("VEHICLEDATA_FUELLEVEL returned null", enumVehicleDataFuelLevel);
		assertNotNull("VEHICLEDATA_FUELLEVEL_STATE returned null", enumVehicleDataFuelLevelState);
		assertNotNull("VEHICLEDATA_FUELCONSUMPTION returned null", enumVehicleDataFuelConsumption);
		assertNotNull("VEHICLEDATA_EXTERNTEMP returned null", enumVehicleDataExternTemp);
		assertNotNull("VEHICLEDATA_VIN returned null", enumVehicleDataVin);
		assertNotNull("VEHICLEDATA_PRNDL returned null", enumVehicleDataPrndl);
		assertNotNull("VEHICLEDATA_TIREPRESSURE returned null", enumVehicleDataTirePressure);
		assertNotNull("VEHICLEDATA_ODOMETER returned null", enumVehicleDataOdometer);
		assertNotNull("VEHICLEDATA_BELTSTATUS returned null", enumVehicleDataBeltStatus);
		assertNotNull("VEHICLEDATA_BODYINFO returned null", enumVehicleDataBodyInfo);
		assertNotNull("VEHICLEDATA_DEVICESTATUS returned null", enumVehicleDataDeviceStatus);
		assertNotNull("VEHICLEDATA_BRAKING returned null", enumVehicleDataBraking);
		assertNotNull("VEHICLEDATA_WIPERSTATUS returned null", enumVehicleDataWiperStatus);
		assertNotNull("VEHICLEDATA_HEADLAMPSTATUS returned null", enumVehicleDataHeadlampStatus);
		assertNotNull("VEHICLEDATA_BATTVOLTAGE returned null", enumVehicleDataBattVoltage);
		assertNotNull("VEHICLEDATA_ENGINETORQUE returned null", enumVehicleDataEngineTorque);
		assertNotNull("VEHICLEDATA_ACCPEDAL returned null", enumVehicleDataAccPedal);
		assertNotNull("VEHICLEDATA_STEERINGWHEEL returned null", enumVehicleDataSteeringWheel);
		assertNotNull("VEHICLEDATA_ECALLINFO returned null", enumVehicleDataECallInfo);
		assertNotNull("VEHICLEDATA_AIRBAGSTATUS returned null", enumVehicleDataAirbagStatus);
		assertNotNull("VEHICLEDATA_EMERGENCYEVENT returned null", enumVehicleDataEmergencyEvent);
		assertNotNull("VEHICLEDATA_CLUSTERMODESTATUS returned null", enumVehicleDataClusterModeStatus);
		assertNotNull("VEHICLEDATA_MYKEY returned null", enumVehicleDataMyKey);
	}
	
	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    VehicleDataType temp = VehicleDataType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
		}
	}
	
	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    VehicleDataType temp = VehicleDataType.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	
	
	/**
	 * Verifies the possible enum values of VehicleDataType.
	 */
	public void testListEnum() {
 		List<VehicleDataType> enumValueList = Arrays.asList(VehicleDataType.values());

		List<VehicleDataType> enumTestList = new ArrayList<VehicleDataType>();
		enumTestList.add(VehicleDataType.VEHICLEDATA_GPS);
		enumTestList.add(VehicleDataType.VEHICLEDATA_SPEED);
		enumTestList.add(VehicleDataType.VEHICLEDATA_RPM);
		enumTestList.add(VehicleDataType.VEHICLEDATA_FUELLEVEL);
		enumTestList.add(VehicleDataType.VEHICLEDATA_FUELLEVEL_STATE);
		enumTestList.add(VehicleDataType.VEHICLEDATA_FUELCONSUMPTION);		
		enumTestList.add(VehicleDataType.VEHICLEDATA_EXTERNTEMP);
		enumTestList.add(VehicleDataType.VEHICLEDATA_VIN);	
		enumTestList.add(VehicleDataType.VEHICLEDATA_PRNDL);
		enumTestList.add(VehicleDataType.VEHICLEDATA_TIREPRESSURE);	
		enumTestList.add(VehicleDataType.VEHICLEDATA_ODOMETER);	
		enumTestList.add(VehicleDataType.VEHICLEDATA_BELTSTATUS);
		enumTestList.add(VehicleDataType.VEHICLEDATA_BODYINFO);	
		enumTestList.add(VehicleDataType.VEHICLEDATA_DEVICESTATUS);
		enumTestList.add(VehicleDataType.VEHICLEDATA_BRAKING);	
		enumTestList.add(VehicleDataType.VEHICLEDATA_WIPERSTATUS);	
		enumTestList.add(VehicleDataType.VEHICLEDATA_HEADLAMPSTATUS);
		enumTestList.add(VehicleDataType.VEHICLEDATA_BATTVOLTAGE);
		enumTestList.add(VehicleDataType.VEHICLEDATA_ENGINETORQUE);
		enumTestList.add(VehicleDataType.VEHICLEDATA_ACCPEDAL);
		enumTestList.add(VehicleDataType.VEHICLEDATA_STEERINGWHEEL);
		enumTestList.add(VehicleDataType.VEHICLEDATA_ECALLINFO);		
		enumTestList.add(VehicleDataType.VEHICLEDATA_AIRBAGSTATUS);
		enumTestList.add(VehicleDataType.VEHICLEDATA_EMERGENCYEVENT);	
		enumTestList.add(VehicleDataType.VEHICLEDATA_CLUSTERMODESTATUS);
		enumTestList.add(VehicleDataType.VEHICLEDATA_MYKEY);	

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}