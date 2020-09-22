package com.smartdevicelink.test.rpc.enums;

import com.smartdevicelink.proxy.rpc.enums.LightName;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.enums.LightName}
 */
public class LightNameTests extends TestCase {

    /**
     * Verifies that the enum values are not null upon valid assignment.
     */
    public void testValidEnums() {
        String example = "FRONT_LEFT_HIGH_BEAM";
        LightName enumFrontLeftHighBeam = LightName.valueForString(example);
        example = "FRONT_RIGHT_HIGH_BEAM";
        LightName enumFrontRightHighBeam = LightName.valueForString(example);
        example = "FRONT_LEFT_LOW_BEAM";
        LightName enumFrontLeftLowBeam = LightName.valueForString(example);
        example = "FRONT_RIGHT_LOW_BEAM";
        LightName enumFrontRightLowBeam = LightName.valueForString(example);
        example = "FRONT_LEFT_PARKING_LIGHT";
        LightName enumFrontLeftParkingLight = LightName.valueForString(example);
        example = "FRONT_RIGHT_PARKING_LIGHT";
        LightName enumFrontRightParkingLight = LightName.valueForString(example);
        example = "FRONT_LEFT_FOG_LIGHT";
        LightName enumFrontLeftFogLight = LightName.valueForString(example);
        example = "FRONT_RIGHT_FOG_LIGHT";
        LightName enumFrontRightFogLight = LightName.valueForString(example);
        example = "FRONT_LEFT_DAYTIME_RUNNING_LIGHT";
        LightName enumFrontLeftDaytimeRunningLight = LightName.valueForString(example);
        example = "FRONT_RIGHT_DAYTIME_RUNNING_LIGHT";
        LightName enumFrontRightDaytimeRunningLight = LightName.valueForString(example);
        example = "FRONT_LEFT_TURN_LIGHT";
        LightName enumFrontLeftTurnLight = LightName.valueForString(example);
        example = "FRONT_RIGHT_TURN_LIGHT";
        LightName enumFrontRightTurnLight = LightName.valueForString(example);
        example = "REAR_LEFT_FOG_LIGHT";
        LightName enumRearLeftFogLight = LightName.valueForString(example);
        example = "REAR_RIGHT_FOG_LIGHT";
        LightName enumRearRightFogLight = LightName.valueForString(example);
        example = "REAR_LEFT_TAIL_LIGHT";
        LightName enumRearLeftTailLight = LightName.valueForString(example);
        example = "REAR_RIGHT_TAIL_LIGHT";
        LightName enumRearRightTailLight = LightName.valueForString(example);
        example = "REAR_LEFT_BRAKE_LIGHT";
        LightName enumRearLeftBrakeLight = LightName.valueForString(example);
        example = "REAR_RIGHT_BRAKE_LIGHT";
        LightName enumRearRightBrakeLight = LightName.valueForString(example);
        example = "REAR_LEFT_TURN_LIGHT";
        LightName enumRearLeftTurnLight = LightName.valueForString(example);
        example = "REAR_RIGHT_TURN_LIGHT";
        LightName enumRearRightTurnLight = LightName.valueForString(example);
        example = "REAR_REGISTRATION_PLATE_LIGHT";
        LightName enumRearRegistrationPlateLight = LightName.valueForString(example);
        example = "HIGH_BEAMS";
        LightName enumHighBeams = LightName.valueForString(example);
        example = "LOW_BEAMS";
        LightName enumLowBeams = LightName.valueForString(example);
        example = "FOG_LIGHTS";
        LightName enumFogLights = LightName.valueForString(example);
        example = "RUNNING_LIGHTS";
        LightName enumRunningLights = LightName.valueForString(example);
        example = "PARKING_LIGHTS";
        LightName enumParkingLights = LightName.valueForString(example);
        example = "BRAKE_LIGHTS";
        LightName enumBrakeLights = LightName.valueForString(example);
        example = "REAR_REVERSING_LIGHTS";
        LightName enumRearReversingLights = LightName.valueForString(example);
        example = "SIDE_MARKER_LIGHTS";
        LightName enumSideMarkerLights = LightName.valueForString(example);
        example = "LEFT_TURN_LIGHTS";
        LightName enumLeftTurnLights = LightName.valueForString(example);
        example = "RIGHT_TURN_LIGHTS";
        LightName enumRightTurnLights = LightName.valueForString(example);
        example = "HAZARD_LIGHTS";
        LightName enumHazardLights = LightName.valueForString(example);

        example = "REAR_CARGO_LIGHTS";
        LightName enumRearCargoLights = LightName.valueForString(example);
        example = "REAR_TRUCK_BED_LIGHTS";
        LightName enumRearTruckBedLights = LightName.valueForString(example);
        example = "REAR_TRAILER_LIGHTS";
        LightName enumRearTrailerLights = LightName.valueForString(example);
        example = "LEFT_SPOT_LIGHTS";
        LightName enumLeftSpotLights = LightName.valueForString(example);
        example = "RIGHT_SPOT_LIGHTS";
        LightName enumRightSpotLights = LightName.valueForString(example);
        example = "LEFT_PUDDLE_LIGHTS";
        LightName enumLeftPuddleLights = LightName.valueForString(example);
        example = "RIGHT_PUDDLE_LIGHTS";
        LightName enumRightPuddleLights = LightName.valueForString(example);
        example = "AMBIENT_LIGHTS";
        LightName enumAmbientLights = LightName.valueForString(example);
        example = "OVERHEAD_LIGHTS";
        LightName enumOverheadLights = LightName.valueForString(example);
        example = "READING_LIGHTS";
        LightName enumReadingLights = LightName.valueForString(example);
        example = "TRUNK_LIGHTS";
        LightName enumTrunkLights = LightName.valueForString(example);
        example = "EXTERIOR_FRONT_LIGHTS";
        LightName enumExteriorFrontLights = LightName.valueForString(example);
        example = "EXTERIOR_REAR_LIGHTS";
        LightName enumExteriorRearLights = LightName.valueForString(example);
        example = "EXTERIOR_LEFT_LIGHTS";
        LightName enumExteriorLeftLights = LightName.valueForString(example);
        example = "EXTERIOR_RIGHT_LIGHTS";
        LightName enumExteriorRightLights = LightName.valueForString(example);
        example = "EXTERIOR_ALL_LIGHTS";
        LightName enumExteriorAllLights = LightName.valueForString(example);

        assertNotNull("FRONT_LEFT_HIGH_BEAM returned null", enumFrontLeftHighBeam);
        assertNotNull("FRONT_RIGHT_HIGH_BEAM returned null", enumFrontRightHighBeam);
        assertNotNull("FRONT_LEFT_LOW_BEAM returned null", enumFrontLeftLowBeam);
        assertNotNull("FRONT_RIGHT_LOW_BEAM returned null", enumFrontRightLowBeam);
        assertNotNull("FRONT_LEFT_PARKING_LIGHT returned null", enumFrontLeftParkingLight);
        assertNotNull("FRONT_RIGHT_PARKING_LIGHT returned null", enumFrontRightParkingLight);
        assertNotNull("FRONT_LEFT_FOG_LIGHT returned null", enumFrontLeftFogLight);
        assertNotNull("FRONT_RIGHT_FOG_LIGHT returned null", enumFrontRightFogLight);
        assertNotNull("FRONT_LEFT_DAYTIME_RUNNING_LIGHT returned null", enumFrontLeftDaytimeRunningLight);
        assertNotNull("FRONT_RIGHT_DAYTIME_RUNNING_LIGHT returned null", enumFrontRightDaytimeRunningLight);

        assertNotNull("FRONT_LEFT_TURN_LIGHT returned null", enumFrontLeftTurnLight);
        assertNotNull("FRONT_RIGHT_TURN_LIGHT returned null", enumFrontRightTurnLight);
        assertNotNull("REAR_LEFT_FOG_LIGHT returned null", enumRearLeftFogLight);
        assertNotNull("REAR_RIGHT_FOG_LIGHT returned null", enumRearRightFogLight);
        assertNotNull("REAR_LEFT_TAIL_LIGHT returned null", enumRearLeftTailLight);
        assertNotNull("REAR_RIGHT_TAIL_LIGHT returned null", enumRearRightTailLight);
        assertNotNull("REAR_LEFT_BRAKE_LIGHT returned null", enumRearLeftBrakeLight);
        assertNotNull("REAR_RIGHT_BRAKE_LIGHT returned null", enumRearRightBrakeLight);
        assertNotNull("REAR_LEFT_TURN_LIGHT returned null", enumRearLeftTurnLight);
        assertNotNull("REAR_RIGHT_TURN_LIGHT returned null", enumRearRightTurnLight);
        assertNotNull("REAR_REGISTRATION_PLATE_LIGHT returned null", enumRearRegistrationPlateLight);

        assertNotNull("HIGH_BEAMS returned null", enumHighBeams);
        assertNotNull("LOW_BEAMS returned null", enumLowBeams);
        assertNotNull("FOG_LIGHTS returned null", enumFogLights);
        assertNotNull("RUNNING_LIGHTS returned null", enumRunningLights);
        assertNotNull("PARKING_LIGHTS returned null", enumParkingLights);
        assertNotNull("BRAKE_LIGHTS returned null", enumBrakeLights);

        assertNotNull("REAR_REVERSING_LIGHTS returned null", enumRearReversingLights);
        assertNotNull("SIDE_MARKER_LIGHTS returned null", enumSideMarkerLights);
        assertNotNull("LEFT_TURN_LIGHTS returned null", enumLeftTurnLights);
        assertNotNull("RIGHT_TURN_LIGHTS returned null", enumRightTurnLights);
        assertNotNull("HAZARD_LIGHTS returned null", enumHazardLights);
        assertNotNull("REAR_CARGO_LIGHTS returned null", enumRearCargoLights);
        assertNotNull("REAR_TRUCK_BED_LIGHTS returned null", enumRearTruckBedLights);
        assertNotNull("REAR_TRAILER_LIGHTS returned null", enumRearTrailerLights);
        assertNotNull("LEFT_SPOT_LIGHTS returned null", enumLeftSpotLights);
        assertNotNull("RIGHT_SPOT_LIGHTS returned null", enumRightSpotLights);
        assertNotNull("LEFT_PUDDLE_LIGHTS returned null", enumLeftPuddleLights);
        assertNotNull("RIGHT_PUDDLE_LIGHTS returned null", enumRightPuddleLights);

        assertNotNull("AMBIENT_LIGHTS returned null", enumAmbientLights);
        assertNotNull("OVERHEAD_LIGHTS returned null", enumOverheadLights);
        assertNotNull("READING_LIGHTS returned null", enumReadingLights);
        assertNotNull("TRUNK_LIGHTS returned null", enumTrunkLights);

        assertNotNull("EXTERIOR_FRONT_LIGHTS returned null", enumExteriorFrontLights);
        assertNotNull("EXTERIOR_REAR_LIGHTS returned null", enumExteriorRearLights);
        assertNotNull("EXTERIOR_LEFT_LIGHTS returned null", enumExteriorLeftLights);
        assertNotNull("EXTERIOR_RIGHT_LIGHTS returned null", enumExteriorRightLights);

        assertNotNull("EXTERIOR_ALL_LIGHTS returned null", enumExteriorAllLights);
    }

    /**
     * Verifies that an invalid assignment is null.
     */
    public void testInvalidEnum() {
        String example = "fRONT_LEFT_HIGH_BEAM";
        try {
            LightName temp = LightName.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (IllegalArgumentException exception) {
            fail("Invalid enum throws IllegalArgumentException.");
        }
    }

    /**
     * Verifies that a null assignment is invalid.
     */
    public void testNullEnum() {
        String example = null;
        try {
            LightName temp = LightName.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
        } catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
        }
    }

    /**
     * Verifies the possible enum values of LightName.
     */
    public void testListEnum() {
        List<LightName> enumValueList = Arrays.asList(LightName.values());

        List<LightName> enumTestList = new ArrayList<LightName>();
        enumTestList.add(LightName.FRONT_LEFT_HIGH_BEAM);
        enumTestList.add(LightName.FRONT_RIGHT_HIGH_BEAM);
        enumTestList.add(LightName.FRONT_LEFT_LOW_BEAM);
        enumTestList.add(LightName.FRONT_RIGHT_LOW_BEAM);
        enumTestList.add(LightName.FRONT_LEFT_PARKING_LIGHT);
        enumTestList.add(LightName.FRONT_RIGHT_PARKING_LIGHT);
        enumTestList.add(LightName.FRONT_LEFT_FOG_LIGHT);
        enumTestList.add(LightName.FRONT_RIGHT_FOG_LIGHT);
        enumTestList.add(LightName.FRONT_LEFT_DAYTIME_RUNNING_LIGHT);
        enumTestList.add(LightName.FRONT_RIGHT_DAYTIME_RUNNING_LIGHT);
        enumTestList.add(LightName.FRONT_LEFT_TURN_LIGHT);
        enumTestList.add(LightName.FRONT_RIGHT_TURN_LIGHT);
        enumTestList.add(LightName.REAR_LEFT_FOG_LIGHT);
        enumTestList.add(LightName.REAR_RIGHT_FOG_LIGHT);
        enumTestList.add(LightName.REAR_LEFT_TAIL_LIGHT);
        enumTestList.add(LightName.REAR_RIGHT_TAIL_LIGHT);
        enumTestList.add(LightName.REAR_LEFT_BRAKE_LIGHT);
        enumTestList.add(LightName.REAR_RIGHT_BRAKE_LIGHT);
        enumTestList.add(LightName.REAR_LEFT_TURN_LIGHT);
        enumTestList.add(LightName.REAR_RIGHT_TURN_LIGHT);
        enumTestList.add(LightName.REAR_REGISTRATION_PLATE_LIGHT);
        enumTestList.add(LightName.HIGH_BEAMS);
        enumTestList.add(LightName.LOW_BEAMS);
        enumTestList.add(LightName.FOG_LIGHTS);
        enumTestList.add(LightName.RUNNING_LIGHTS);
        enumTestList.add(LightName.PARKING_LIGHTS);
        enumTestList.add(LightName.BRAKE_LIGHTS);
        enumTestList.add(LightName.REAR_REVERSING_LIGHTS);
        enumTestList.add(LightName.SIDE_MARKER_LIGHTS);
        enumTestList.add(LightName.LEFT_TURN_LIGHTS);
        enumTestList.add(LightName.RIGHT_TURN_LIGHTS);
        enumTestList.add(LightName.HAZARD_LIGHTS);
        enumTestList.add(LightName.REAR_CARGO_LIGHTS);
        enumTestList.add(LightName.REAR_TRUCK_BED_LIGHTS);
        enumTestList.add(LightName.REAR_TRAILER_LIGHTS);
        enumTestList.add(LightName.LEFT_SPOT_LIGHTS);
        enumTestList.add(LightName.RIGHT_SPOT_LIGHTS);
        enumTestList.add(LightName.LEFT_PUDDLE_LIGHTS);
        enumTestList.add(LightName.RIGHT_PUDDLE_LIGHTS);
        enumTestList.add(LightName.AMBIENT_LIGHTS);
        enumTestList.add(LightName.OVERHEAD_LIGHTS);
        enumTestList.add(LightName.READING_LIGHTS);
        enumTestList.add(LightName.TRUNK_LIGHTS);
        enumTestList.add(LightName.EXTERIOR_FRONT_LIGHTS);
        enumTestList.add(LightName.EXTERIOR_REAR_LIGHTS);
        enumTestList.add(LightName.EXTERIOR_LEFT_LIGHTS);
        enumTestList.add(LightName.EXTERIOR_RIGHT_LIGHTS);
        enumTestList.add(LightName.EXTERIOR_ALL_LIGHTS);

        assertTrue("Enum value list does not match enum class list",
                enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
    }
}