package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.Result;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.Result}
 */
public class ResultTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "SUCCESS";
		Result enumSuccess = Result.valueForString(example);
		example = "INVALID_DATA";
		Result enumInvalidData = Result.valueForString(example);
		example = "UNSUPPORTED_REQUEST";
		Result enumUnsupportedRequest = Result.valueForString(example);
		example = "OUT_OF_MEMORY";
		Result enumOutOfMemory = Result.valueForString(example);
		example = "TOO_MANY_PENDING_REQUESTS";
		Result enumTooManyPendingRequests = Result.valueForString(example);
		example = "INVALID_ID";
		Result enumInvalidId = Result.valueForString(example);
		example = "DUPLICATE_NAME";
		Result enumDuplicateName = Result.valueForString(example);
		example = "TOO_MANY_APPLICATIONS";
		Result enumTooManyApplications = Result.valueForString(example);
		example = "APPLICATION_REGISTERED_ALREADY";
		Result enumApplicationRegisteredAlready = Result.valueForString(example);
		example = "UNSUPPORTED_VERSION";
		Result enumUnsupportedVersion = Result.valueForString(example);
		example = "WRONG_LANGUAGE";
		Result enumWrongLanguage = Result.valueForString(example);
		example = "APPLICATION_NOT_REGISTERED";
		Result enumApplicationNotRegistered = Result.valueForString(example);
		example = "IN_USE";
		Result enumInUse = Result.valueForString(example);
		example = "VEHICLE_DATA_NOT_ALLOWED";
		Result enumVehicleDataNotAllowed = Result.valueForString(example);
		example = "VEHICLE_DATA_NOT_AVAILABLE";
		Result enumVehicleDataNotAvailable = Result.valueForString(example);
		example = "REJECTED";
		Result enumRejected = Result.valueForString(example);
		example = "ABORTED";
		Result enumAborted = Result.valueForString(example);
		example = "IGNORED";
		Result enumIgnored = Result.valueForString(example);
		example = "UNSUPPORTED_RESOURCE";
		Result enumUnsupportedResource = Result.valueForString(example);
		example = "FILE_NOT_FOUND";
		Result enumFileNotFound = Result.valueForString(example);
		example = "GENERIC_ERROR";
		Result enumGenericError = Result.valueForString(example);
		example = "DISALLOWED";
		Result enumDisallowed = Result.valueForString(example);
		example = "USER_DISALLOWED";
		Result enumUserDisallowed = Result.valueForString(example);
		example = "TIMED_OUT";
		Result enumTimedOut = Result.valueForString(example);
		example = "CANCEL_ROUTE";
		Result enumCancelRoute = Result.valueForString(example);
		example = "TRUNCATED_DATA";
		Result enumTruncatedData = Result.valueForString(example);
		example = "RETRY";
		Result enumRetry = Result.valueForString(example);
		example = "WARNINGS";
		Result enumWarnings = Result.valueForString(example);
		example = "SAVED";
		Result enumSaved = Result.valueForString(example);
		example = "INVALID_CERT";
		Result enumInvalidCert = Result.valueForString(example);
		example = "EXPIRED_CERT";
		Result enumExpiredCert = Result.valueForString(example);
		example = "RESUME_FAILED";
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
		String example = "suCcesS";
		try {
		    Result temp = Result.valueForString(example);
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
		    Result temp = Result.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
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

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}	
}