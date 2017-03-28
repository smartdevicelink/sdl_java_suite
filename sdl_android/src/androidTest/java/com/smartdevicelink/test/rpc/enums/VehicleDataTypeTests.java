package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.VehicleDataType;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.VehicleData}
 */
public class VehicleDataTypeTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "VEHICLEDATA_GPS";
		VehicleDataType enumVehicleDataGps = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_SPEED";
		VehicleDataType enumVehicleDataSpeed = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_RPM";
		VehicleDataType enumVehicleDataRpm = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_FUELLEVEL";
		VehicleDataType enumVehicleDataFuelLevel = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_FUELLEVEL_STATE";
		VehicleDataType enumVehicleDataFuelLevelState = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_FUELCONSUMPTION";
		VehicleDataType enumVehicleDataFuelConsumption = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_EXTERNTEMP";
		VehicleDataType enumVehicleDataExternTemp = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_VIN";
		VehicleDataType enumVehicleDataVin = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_PRNDL";
		VehicleDataType enumVehicleDataPrndl = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_TIREPRESSURE";
		VehicleDataType enumVehicleDataTirePressure = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_ODOMETER";
		VehicleDataType enumVehicleDataOdometer = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_BELTSTATUS";
		VehicleDataType enumVehicleDataBeltStatus = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_BODYINFO";
		VehicleDataType enumVehicleDataBodyInfo = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_DEVICESTATUS";
		VehicleDataType enumVehicleDataDeviceStatus = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_BRAKING";
		VehicleDataType enumVehicleDataBraking = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_WIPERSTATUS";
		VehicleDataType enumVehicleDataWiperStatus = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_HEADLAMPSTATUS";
		VehicleDataType enumVehicleDataHeadlampStatus = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_BATTVOLTAGE";
		VehicleDataType enumVehicleDataBattVoltage = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_ENGINETORQUE";
		VehicleDataType enumVehicleDataEngineTorque = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_ACCPEDAL";
		VehicleDataType enumVehicleDataAccPedal = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_STEERINGWHEEL";
		VehicleDataType enumVehicleDataSteeringWheel = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_ECALLINFO";
		VehicleDataType enumVehicleDataECallInfo = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_AIRBAGSTATUS";
		VehicleDataType enumVehicleDataAirbagStatus = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_EMERGENCYEVENT";
		VehicleDataType enumVehicleDataEmergencyEvent = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_CLUSTERMODESTATUS";
		VehicleDataType enumVehicleDataClusterModeStatus = VehicleDataType.valueForString(example);
		example = "VEHICLEDATA_MYKEY";
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
		String example = "VeHIcLEDatA_GPs";
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