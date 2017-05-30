package com.smartdevicelink.test.protocol.enums;

import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.smartdevicelink.R;
import com.smartdevicelink.protocol.enums.MessageType;

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
			assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		} catch (IllegalArgumentException exception) {
			fail("Invalid enum throws IllegalArgumentException");
		}
	}
	
	public void testNullEnum () {
		String example = null;
		
		try {
			MessageType temp = MessageType.valueForString(example);
			assertNull(mContext.getString(R.string.result_of_valuestring_should_be_null), temp);
		}
		catch (NullPointerException exception) {
            fail(mContext.getString(R.string.invalid_enum_throws_illegal_argument_exception));
		}
	}
	
	public void testListEnum() {
 		List<MessageType> enumValueList = Arrays.asList(MessageType.values());

		List<MessageType> enumTestList = new ArrayList<MessageType>();
		enumTestList.add(MessageType.UNDEFINED);
		enumTestList.add(MessageType.BULK);
		enumTestList.add(MessageType.RPC);

		assertTrue(mContext.getString(R.string.enum_value_list_does_not_match_enum_class_list),
					enumValueList.containsAll(enumTestList) &&
					enumTestList.containsAll(enumValueList));
	}
}