package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.TBTState;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.enums.TbtState}
 */
public class TbtStateTests extends TestCase {

	/**
	 * Verifies that the enum values are not null upon valid assignment.
	 */
	public void testValidEnums () {	
		String example = "ROUTE_UPDATE_REQUEST";
		TBTState enumRouteUpdateRequest = TBTState.valueForString(example);
		example = "ROUTE_ACCEPTED";
		TBTState enumRouteAccepted = TBTState.valueForString(example);
		example = "ROUTE_REFUSED";
		TBTState enumRouteRefused = TBTState.valueForString(example);
		example = "ROUTE_CANCELLED";
		TBTState enumRouteCancelled = TBTState.valueForString(example);
		example = "ETA_REQUEST";
		TBTState enumEtaEquest = TBTState.valueForString(example);
		example = "NEXT_TURN_REQUEST";
		TBTState enumNextTurnRequest = TBTState.valueForString(example);
		example = "ROUTE_STATUS_REQUEST";
		TBTState enumRouteStatusRequest = TBTState.valueForString(example);
		example = "ROUTE_SUMMARY_REQUEST";
		TBTState enumRouteSummaryRequest = TBTState.valueForString(example);
		example = "TRIP_STATUS_REQUEST";
		TBTState enumTripStatusRequest = TBTState.valueForString(example);
		example = "ROUTE_UPDATE_REQUEST_TIMEOUT";
		TBTState enumRouteUpdateRequestTimeout = TBTState.valueForString(example);
		
		assertNotNull("ROUTE_UPDATE_REQUEST returned null", enumRouteUpdateRequest);
		assertNotNull("ROUTE_ACCEPTED returned null", enumRouteAccepted);
		assertNotNull("ROUTE_REFUSED returned null", enumRouteRefused);
		assertNotNull("ROUTE_CANCELLED returned null", enumRouteCancelled);
		assertNotNull("ETA_REQUEST returned null", enumEtaEquest);
		assertNotNull("NEXT_TURN_REQUEST returned null", enumNextTurnRequest);
		assertNotNull("ROUTE_STATUS_REQUEST returned null", enumRouteStatusRequest);
		assertNotNull("ROUTE_SUMMARY_REQUEST returned null", enumRouteSummaryRequest);
		assertNotNull("TRIP_STATUS_REQUEST returned null", enumTripStatusRequest);
		assertNotNull("ROUTE_UPDATE_REQUEST_TIMEOUT returned null", enumRouteUpdateRequestTimeout);
	}

	/**
	 * Verifies that an invalid assignment is null.
	 */
	public void testInvalidEnum () {
		String example = "rOUte_UpDAte_reQuesT";
		try {
		    TBTState temp = TBTState.valueForString(example);
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
		    TBTState temp = TBTState.valueForString(example);
            assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}	

	/**
	 * Verifies the possible enum values of TBTState.
	 */
	public void testListEnum() {
 		List<TBTState> enumValueList = Arrays.asList(TBTState.values());

		List<TBTState> enumTestList = new ArrayList<TBTState>();
		enumTestList.add(TBTState.ROUTE_UPDATE_REQUEST);
		enumTestList.add(TBTState.ROUTE_ACCEPTED);
		enumTestList.add(TBTState.ROUTE_REFUSED);
		enumTestList.add(TBTState.ROUTE_CANCELLED);
		enumTestList.add(TBTState.ETA_REQUEST);
		enumTestList.add(TBTState.NEXT_TURN_REQUEST);		
		enumTestList.add(TBTState.ROUTE_STATUS_REQUEST);
		enumTestList.add(TBTState.ROUTE_SUMMARY_REQUEST);	
		enumTestList.add(TBTState.TRIP_STATUS_REQUEST);
		enumTestList.add(TBTState.ROUTE_UPDATE_REQUEST_TIMEOUT);	

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
}