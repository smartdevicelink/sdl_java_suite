package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SendHapticData;
import com.smartdevicelink.proxy.rpc.Rectangle;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by brettywhite on 8/9/17.
 */

public class SendHapticDataTests extends BaseRpcTests {

	private Rectangle spatialStruct;

	@Override
	protected RPCMessage createMessage(){
		spatialStruct = new Rectangle();
		spatialStruct.setX(Test.GENERAL_FLOAT);
		spatialStruct.setY(Test.GENERAL_FLOAT);
		spatialStruct.setWidth(Test.GENERAL_FLOAT);
		spatialStruct.setHeight(Test.GENERAL_FLOAT);

		SendHapticData msg = new SendHapticData();
		msg.setHapticSpatialData(spatialStruct);

		return msg;
	}

	@Override
	protected String getMessageType(){
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType(){
		return FunctionID.SEND_HAPTIC_DATA.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion){
		JSONObject result = new JSONObject();

		try{
			result.put(SendHapticData.KEY_HAPTIC_SPATIAL_DATA, spatialStruct.serializeJSON());
		}catch(JSONException e){
			fail(Test.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		Rectangle testSpatialStruct = ((SendHapticData) msg).getHapticSpatialData();

		// Valid Tests
		assertEquals(Test.MATCH, spatialStruct, testSpatialStruct);
		// Invalid/Null Tests
		SendHapticData msg = new SendHapticData();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getHapticSpatialData());
	}

}
