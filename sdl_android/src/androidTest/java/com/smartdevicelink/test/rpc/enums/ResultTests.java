package com.smartdevicelink.test.rpc.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.Result}
 */
public class ResultTests extends AndroidTestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = mContext.getString(R.string.success_caps);
		Result enumSuccess = Result.valueForString(example);
		example = mContext.getString(R.string.invalid_data_caps);
		Result enumInvalidData = Result.valueForString(example);
		example = mContext.getString(R.string.unsupported_request_caps);
		Result enumUnsupportedRequest = Result.valueForString(example);
		example = mContext.getString(R.string.out_of_memory_caps);
		Result enumOutOfMemory = Result.valueForString(example);
		example = mContext.getString(R.string.too_many_pending_requests_caps);
		Result enumTooManyPendingRequests = Result.valueForString(example);
		example = mContext.getString(R.string.invalid_id_caps);
		Result enumInvalidId = Result.valueForString(example);
		example = mContext.getString(R.string.duplicate_name_caps);
		Result enumDuplicateName = Result.valueForString(example);
		example = mContext.getString(R.string.too_many_applications_caps);
		Result enumTooManyApplications = Result.valueForString(example);
		example = mContext.getString(R.string.application_registered_already_caps);
		Result enumApplicationRegisteredAlready = Result.valueForString(example);
		example = mContext.getString(R.string.unsupported_version_caps);
		Result enumUnsupportedVersion = Result.valueForString(example);
		example = mContext.getString(R.string.wrong_language_caps);
		Result enumWrongLanguage = Result.valueForString(example);
		example = mContext.getString(R.string.application_not_registered_caps);
		Result enumApplicationNotRegistered = Result.valueForString(example);
		example = mContext.getString(R.string.in_use_caps);
		Result enumInUse = Result.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_not_allowed_caps);
		Result enumVehicleDataNotAllowed = Result.valueForString(example);
		example = mContext.getString(R.string.vehicle_data_not_available_caps);
		Result enumVehicleDataNotAvailable = Result.valueForString(example);
		example = mContext.getString(R.string.rejected_caps);
		Result enumRejected = Result.valueForString(example);
		example = mContext.getString(R.string.aborted_caps);
		Result enumAborted = Result.valueForString(example);
		example = mContext.getString(R.string.ignored_caps);
		Result enumIgnored = Result.valueForString(example);
		example = mContext.getString(R.string.unsupported_resource_caps);
		Result enumUnsupportedResource = Result.valueForString(example);
		example = mContext.getString(R.string.file_not_found_caps);
		Result enumFileNotFound = Result.valueForString(example);
		example = mContext.getString(R.string.generic_error_caps);
		Result enumGenericError = Result.valueForString(example);
		example = mContext.getString(R.string.disallowed_caps);
		Result enumDisallowed = Result.valueForString(example);
		example = mContext.getString(R.string.user_disallowed_caps);
		Result enumUserDisallowed = Result.valueForString(example);
		example = mContext.getString(R.string.timed_out_caps);
		Result enumTimedOut = Result.valueForString(example);
		example = mContext.getString(R.string.cancel_route_caps);
		Result enumCancelRoute = Result.valueForString(example);
		example = mContext.getString(R.string.truncated_data_caps);
		Result enumTruncatedData = Result.valueForString(example);
		example = mContext.getString(R.string.retry_caps);
		Result enumRetry = Result.valueForString(example);
		example = mContext.getString(R.string.warnings_caps);
		Result enumWarnings = Result.valueForString(example);
		example = mContext.getString(R.string.saved_caps);
		Result enumSaved = Result.valueForString(example);
		example = mContext.getString(R.string.invalid_cert_caps);
		Result enumInvalidCert = Result.valueForString(example);
		example = mContext.getString(R.string.expired_cert_caps);
		Result enumExpiredCert = Result.valueForString(example);
		example = mContext.getString(R.string.resume_failed_caps);
		Result enumResumeFailed = Result.valueForString(example);
		
		assertNotNull("SUCCESS returned null", enumSuccess);
		assertNotNull("INVALID_DATA returned null", enumInvalidData);
		assertNotNull("UNSUPPORTED_REQUEST returned null", enumUnsupportedRequest);
		assertNotNull("OUT_OF_MEMORY returned null", enumOutOfMemory);
		assertNotNull("TOO_MANY_PENDING_REQUESTS returned null", enumTooManyPendingRequests);
		assertNotNull("INVALID_ID returned null", enumInvalidId);
		assertNotNull("DUPLICATE_NAME returned null", enumDuplicateName);
		assertNotNull("TOO_MANY_APPLICATIONS returned null", enumTooManyApplications);
		assertNotNull("APPLICATION_REGISTERED_ALREADY returned null", enumApplicationRegisteredAlready);
		assertNotNull("UNSUPPORTED_VERSION returned null", enumUnsupportedVersion);
		assertNotNull("WRONG_LANGUAGE returned null", enumWrongLanguage);
		assertNotNull("APPLICATION_NOT_REGISTERED returned null", enumApplicationNotRegistered);
		assertNotNull("IN_USE returned null", enumInUse);
		assertNotNull("VEHICLE_DATA_NOT_ALLOWED returned null", enumVehicleDataNotAllowed);
		assertNotNull("VEHICLE_DATA_NOT_AVAILABLE returned null", enumVehicleDataNotAvailable);
		assertNotNull("REJECTED returned null", enumRejected);
		assertNotNull("ABORTED returned null", enumAborted);
		assertNotNull("IGNORED returned null", enumIgnored);
		assertNotNull("UNSUPPORTED_RESOURCE returned null", enumUnsupportedResource);
		assertNotNull("FILE_NOT_FOUND returned null", enumFileNotFound);
		assertNotNull("GENERIC_ERROR returned null", enumGenericError);
		assertNotNull("DISALLOWED returned null", enumDisallowed);
		assertNotNull("USER_DISALLOWED returned null", enumUserDisallowed);
		assertNotNull("TIMED_OUT returned null", enumTimedOut);
		assertNotNull("CANCEL_ROUTE returned null", enumCancelRoute);
		assertNotNull("TRUNCATED_DATA returned null", enumTruncatedData);
		assertNotNull("RETRY returned null", enumRetry);
		assertNotNull("WARNINGS returned null", enumWarnings);
		assertNotNull("SAVED returned null", enumSaved);
		assertNotNull("INVALID_CERT returned null", enumInvalidCert);
		assertNotNull("EXPIRED_CERT returned null", enumExpiredCert);
		assertNotNull("RESUME_FAILED returned null", enumResumeFailed);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		try {
		    Result temp = Result.valueForString(example);
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
		    Result temp = Result.valueForString(example);
            assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}	

	/**
	 * Verifies the possible enum values of Result.
	 */
	public void testListEnum() {
 		List<Result> enumValueList = Arrays.asList(Result.values());

		List<Result> enumTestList = new ArrayList<Result>();
		enumTestList.add(Result.SUCCESS);
		enumTestList.add(Result.INVALID_DATA);
		enumTestList.add(Result.UNSUPPORTED_REQUEST);
		enumTestList.add(Result.OUT_OF_MEMORY);
		enumTestList.add(Result.TOO_MANY_PENDING_REQUESTS);
		enumTestList.add(Result.INVALID_ID);		
		enumTestList.add(Result.DUPLICATE_NAME);
		enumTestList.add(Result.TOO_MANY_APPLICATIONS);	
		enumTestList.add(Result.APPLICATION_REGISTERED_ALREADY);
		enumTestList.add(Result.UNSUPPORTED_VERSION);	
		enumTestList.add(Result.WRONG_LANGUAGE);	
		enumTestList.add(Result.APPLICATION_NOT_REGISTERED);
		enumTestList.add(Result.IN_USE);	
		enumTestList.add(Result.VEHICLE_DATA_NOT_ALLOWED);
		enumTestList.add(Result.VEHICLE_DATA_NOT_AVAILABLE);	
		enumTestList.add(Result.REJECTED);	
		enumTestList.add(Result.ABORTED);
		enumTestList.add(Result.IGNORED);
		enumTestList.add(Result.UNSUPPORTED_RESOURCE);
		enumTestList.add(Result.FILE_NOT_FOUND);
		enumTestList.add(Result.GENERIC_ERROR);
		enumTestList.add(Result.DISALLOWED);		
		enumTestList.add(Result.USER_DISALLOWED);
		enumTestList.add(Result.TIMED_OUT);	
		enumTestList.add(Result.CANCEL_ROUTE);
		enumTestList.add(Result.TRUNCATED_DATA);	
		enumTestList.add(Result.RETRY);	
		enumTestList.add(Result.WARNINGS);
		enumTestList.add(Result.SAVED);	
		enumTestList.add(Result.INVALID_CERT);
		enumTestList.add(Result.EXPIRED_CERT);	
		enumTestList.add(Result.RESUME_FAILED);	

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}