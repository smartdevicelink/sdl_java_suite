package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.HapticRect;
import com.smartdevicelink.proxy.rpc.Rectangle;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

/**
 * Created by brettywhite on 8/24/17.
 */

public class HapticRectTests extends TestCase {

	private HapticRect msg;

	@Override
	public void setUp() {
		msg = new HapticRect();

		msg.setId(Test.GENERAL_INTEGER);
		msg.setRect(Test.GENERAL_RECTANGLE);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		Integer id = msg.getId();
		Rectangle rect = msg.getRect();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, id);
		assertEquals(Test.MATCH, Test.GENERAL_RECTANGLE, rect);

		// Invalid/Null Tests
		HapticRect msg = new HapticRect();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getId());
		assertNull(Test.NULL, msg.getRect());
	}
}
