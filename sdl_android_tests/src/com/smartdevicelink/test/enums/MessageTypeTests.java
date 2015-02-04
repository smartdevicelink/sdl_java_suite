package com.smartdevicelink.test.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.enums.MessageType;

public class MessageTypeTests extends TestCase {

	public void testValidEnums () {	
		String example = "request";
		MessageType enumRequest = MessageType.valueForString(example);
		example = "response";
		MessageType enumResponse = MessageType.valueForString(example);
		example = "notification";
		MessageType enumNotification = MessageType.valueForString(example);
		
		assertNotNull("request returned null", enumRequest);
		assertNotNull("response returned null", enumResponse);
		assertNotNull("notification returned null", enumNotification);
	}
	
	public void testInvalidEnum () {
		String example = "reQuesT";
		try {
			MessageType.valueForString(example);
			fail("Sample string did not throw an IllegalArgumentException");
		}
		catch (IllegalArgumentException exception) {
		}
	}
	
	public void testNullEnum () {
		String example = null;
		try {
			MessageType.valueForString(example);
			fail("Sample string did not throw a NullPointerException");
		}
		catch (NullPointerException exception) {
		}
	}	
	
	public void testListEnum() {
 		List<MessageType> enumValueList = Arrays.asList(MessageType.values());

		List<MessageType> enumTestList = new ArrayList<MessageType>();
		enumTestList.add(MessageType.request);
		enumTestList.add(MessageType.response);
		enumTestList.add(MessageType.notification);

		assertTrue("Enum value list does not match enum class list", 
				enumValueList.containsAll(enumTestList) && enumTestList.containsAll(enumValueList));
	}
	
}
