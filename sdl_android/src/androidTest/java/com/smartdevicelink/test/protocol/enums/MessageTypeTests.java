package com.smartdevicelink.test.protocol.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.protocol.enums.MessageType;

import junit.framework.TestCase;

public class MessageTypeTests extends AndroidTestCase {
	
	public void testValidEnums () {	
		String example = mContext.getString(R.string.undefined_caps);
		MessageType enumUndefined = MessageType.valueForString(example);
		example = mContext.getString(R.string.bulk_caps);
		MessageType enumBulk = MessageType.valueForString(example);
		example = mContext.getString(R.string.rpc_caps);
		MessageType enumRpc = MessageType.valueForString(example);
		
		assertNotNull("UNDEFINED returned null", enumUndefined);
		assertNotNull("BULK returned null", enumBulk);
		assertNotNull("RPC returned null", enumRpc);
	}
	
	public void testInvalidEnum () {
		String example = mContext.getString(R.string.invalid_enum);
		
		try {
			MessageType temp = MessageType.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		} catch (IllegalArgumentException exception) {
			fail("Invalid enum throws IllegalArgumentException");
		}
	}
	
	public void testNullEnum () {
		String example = null;
		
		try {
			MessageType temp = MessageType.valueForString(example);
			assertNull("Result of valueForString should be null.", temp);
		}
		catch (NullPointerException exception) {
            fail("Null string throws NullPointerException.");
		}
	}
	
	public void testListEnum() {
 		List<MessageType> enumValueList = Arrays.asList(MessageType.values());

		List<MessageType> enumTestList = new ArrayList<MessageType>();
		enumTestList.add(MessageType.UNDEFINED);
		enumTestList.add(MessageType.BULK);
		enumTestList.add(MessageType.RPC);

		assertTrue("Enum value list does not match enum class list", 
					enumValueList.containsAll(enumTestList) &&
					enumTestList.containsAll(enumValueList));
	}
}