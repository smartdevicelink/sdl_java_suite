package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.ECallConfirmationStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.ECallConfirmationStatus}
 */
public class ECallConfirmationStatusTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "NORMAL";
		ECallConfirmationStatus enumNormal = ECallConfirmationStatus.valueForString(example);
		example = "CALL_IN_PROGRESS";
		ECallConfirmationStatus enumCallInProgress = ECallConfirmationStatus.valueForString(example);
		example = "CALL_CANCELLED";
		ECallConfirmationStatus enumCancelled = ECallConfirmationStatus.valueForString(example);
		example = "CALL_COMPLETED";
		ECallConfirmationStatus enumCompleted = ECallConfirmationStatus.valueForString(example);
		example = "CALL_UNSUCCESSFUL";
		ECallConfirmationStatus enumUnsuccessful = ECallConfirmationStatus.valueForString(example);
		example = "ECALL_CONFIGURED_OFF";
		ECallConfirmationStatus enumConfiguredOff = ECallConfirmationStatus.valueForString(example);
		example = "CALL_COMPLETE_DTMF_TIMEOUT";
		ECallConfirmationStatus enumCompleteDtmfTimeout = ECallConfirmationStatus.valueForString(example);
		
		assertNotNull("NORMAL returned null", enumNormal);
		assertNotNull("CALL_IN_PROGRESS returned null", enumCallInProgress);
		assertNotNull("CALL_CANCELLED returned null", enumCancelled);
		assertNotNull("CALL_COMPLETED returned null", enumCompleted);
		assertNotNull("CALL_UNSUCCESSFUL returned null", enumUnsuccessful);
		assertNotNull("ECALL_CONFIGURED_OFF returned null", enumConfiguredOff);
		assertNotNull("CALL_COMPLETE_DTMF_TIMEOUT returned null", enumCompleteDtmfTimeout);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "noRMal";
		try {
		    ECallConfirmationStatus temp = ECallConfirmationStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (IllegalArgumentException exception) {
            fail("Null string throws NullPointerException.");
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    ECallConfirmationStatus temp = ECallConfirmationStatus.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of ECallConfirmationStatus.
	 */
	public void testListEnum() {
 		List<ECallConfirmationStatus> enumValueList = Arrays.asList(ECallConfirmationStatus.values());

		List<ECallConfirmationStatus> enumTestList = new ArrayList<ECallConfirmationStatus>();
		enumTestList.add(ECallConfirmationStatus.NORMAL);
		enumTestList.add(ECallConfirmationStatus.CALL_IN_PROGRESS);
		enumTestList.add(ECallConfirmationStatus.CALL_CANCELLED);
		enumTestList.add(ECallConfirmationStatus.CALL_COMPLETED);
		enumTestList.add(ECallConfirmationStatus.CALL_UNSUCCESSFUL);
		enumTestList.add(ECallConfirmationStatus.ECALL_CONFIGURED_OFF);		
		enumTestList.add(ECallConfirmationStatus.CALL_COMPLETE_DTMF_TIMEOUT);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}