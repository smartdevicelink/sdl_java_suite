package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.ECallConfirmationStatus;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.ECallConfirmationStatus}
 */
public class ECallConfirmationStatusTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.normal_caps);
		ECallConfirmationStatus enumNormal = ECallConfirmationStatus.valueForString(example);
		example = mContext.getString(R.string.call_in_prog_caps);
		ECallConfirmationStatus enumCallInProgress = ECallConfirmationStatus.valueForString(example);
		example = mContext.getString(R.string.call_canc_caps);
		ECallConfirmationStatus enumCancelled = ECallConfirmationStatus.valueForString(example);
		example = mContext.getString(R.string.call_comp_caps);
		ECallConfirmationStatus enumCompleted = ECallConfirmationStatus.valueForString(example);
		example = mContext.getString(R.string.call_unsucc_caps);
		ECallConfirmationStatus enumUnsuccessful = ECallConfirmationStatus.valueForString(example);
		example = mContext.getString(R.string.ecall_config_off_caps);
		ECallConfirmationStatus enumConfiguredOff = ECallConfirmationStatus.valueForString(example);
		example = mContext.getString(R.string.call_comp_dtmf_to_caps);
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
		String example = mContext.getString(R.string.invalid_caps);
		try {
		    ECallConfirmationStatus temp = ECallConfirmationStatus.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (IllegalArgumentException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}

	/**
	 * Verifies that a null assignment is invalid.
	 */
	public void testNullEnum () {
		String example = null;
		try {
		    ECallConfirmationStatus temp = ECallConfirmationStatus.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
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

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}