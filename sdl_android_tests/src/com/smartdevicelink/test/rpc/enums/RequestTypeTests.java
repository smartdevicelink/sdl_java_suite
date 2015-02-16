package com.smartdevicelink.test.rpc.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.RequestType;

public class RequestTypeTests extends TestCase {

	public void testValidEnums () {	
		String example = "HTTP";
		RequestType enumHttp = RequestType.valueForString(example);
		example = "FILE_RESUME";
		RequestType enumFileResume = RequestType.valueForString(example);
		example = "AUTH_REQUEST";
		RequestType enumAuthRequest = RequestType.valueForString(example);
		example = "AUTH_CHALLENGE";
		RequestType enumAuthChallenge = RequestType.valueForString(example);
		example = "AUTH_ACK";
		RequestType enumAuthAck = RequestType.valueForString(example);
		example = "PROPRIETARY";
		RequestType enumProprietary = RequestType.valueForString(example);
		
		assertNotNull("HTTP returned null", enumHttp);
		assertNotNull("FILE_RESUME returned null", enumFileResume);
		assertNotNull("AUTH_REQUEST returned null", enumAuthRequest);
		assertNotNull("AUTH_CHALLENGE returned null", enumAuthChallenge);
		assertNotNull("AUTH_ACK returned null", enumAuthAck);
		assertNotNull("PROPRIETARY returned null", enumProprietary);
	}
	
	public void testInvalidEnum () {
		String example = "hTTp";
		try {
			RequestType.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			RequestType.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
			//If the method throws this exception then this test will be shown as passed.
		}
	}	
	
	public void testListEnum() {
 		List<RequestType> enumValueList = Arrays.asList(RequestType.values());

		List<RequestType> enumTestList = new ArrayList<RequestType>();
		enumTestList.add(RequestType.HTTP);
		enumTestList.add(RequestType.FILE_RESUME);
		enumTestList.add(RequestType.AUTH_REQUEST);
		enumTestList.add(RequestType.AUTH_CHALLENGE);
		enumTestList.add(RequestType.AUTH_ACK);
		enumTestList.add(RequestType.PROPRIETARY);		

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
